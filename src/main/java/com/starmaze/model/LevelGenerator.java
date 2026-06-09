package com.starmaze.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public final class LevelGenerator {
    private LevelGenerator() {
    }

    public static Level generate(int levelIndex, Random random) {
        int width = odd(Math.min(GameConfig.LEVEL_MAX_WIDTH,
                GameConfig.LEVEL_BASE_WIDTH + levelIndex * GameConfig.LEVEL_WIDTH_PER_LEVEL));
        int height = odd(Math.min(GameConfig.LEVEL_MAX_HEIGHT,
                GameConfig.LEVEL_BASE_HEIGHT
                        + (levelIndex / GameConfig.LEVEL_HEIGHT_LEVEL_DIVISOR) * GameConfig.LEVEL_HEIGHT_PER_STEP));
        TileType[][] tiles = new TileType[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = TileType.WALL;
            }
        }

        Position start = new Position(1, 1);
        carveMaze(tiles, start, random);
        addLoops(tiles, levelIndex, random);

        List<Position> floors = collectFloors(tiles);
        Position exit = farthestReachable(start, tiles);
        tiles[exit.y()][exit.x()] = TileType.EXIT;

        addRifts(tiles, floors, start, exit, random,
                Math.min(GameConfig.LEVEL_RIFT_MAX_COUNT, GameConfig.LEVEL_RIFT_BASE_COUNT + levelIndex));
        floors = collectFloors(tiles);
        return new Level(width, height, tiles, start, exit, floors);
    }

    private static int odd(int value) {
        return value % 2 == 1 ? value : value + 1;
    }

    private static void carveMaze(TileType[][] tiles, Position start, Random random) {
        ArrayDeque<Position> stack = new ArrayDeque<>();
        stack.push(start);
        tiles[start.y()][start.x()] = TileType.FLOOR;
        while (!stack.isEmpty()) {
            Position current = stack.peek();
            List<Direction> directions = new ArrayList<>(Direction.CARDINALS);
            Collections.shuffle(directions, random);
            boolean carved = false;
            for (Direction direction : directions) {
                Position between = current.translate(direction);
                Position next = current.translate(direction.dx() * 2, direction.dy() * 2);
                if (insideCarveArea(tiles, next) && tiles[next.y()][next.x()] == TileType.WALL) {
                    tiles[between.y()][between.x()] = TileType.FLOOR;
                    tiles[next.y()][next.x()] = TileType.FLOOR;
                    stack.push(next);
                    carved = true;
                    break;
                }
            }
            if (!carved) {
                stack.pop();
            }
        }
    }

    private static boolean insideCarveArea(TileType[][] tiles, Position p) {
        return p.x() > 0 && p.y() > 0 && p.y() < tiles.length - 1 && p.x() < tiles[0].length - 1;
    }

    private static void addLoops(TileType[][] tiles, int levelIndex, Random random) {
        int attempts = tiles.length * tiles[0].length / GameConfig.LEVEL_LOOP_DENSITY_DIVISOR
                + levelIndex * GameConfig.LEVEL_LOOP_PER_LEVEL;
        for (int i = 0; i < attempts; i++) {
            int x = 1 + random.nextInt(tiles[0].length - 2);
            int y = 1 + random.nextInt(tiles.length - 2);
            if (tiles[y][x] != TileType.WALL) {
                continue;
            }
            boolean horizontal = tiles[y][x - 1] == TileType.FLOOR && tiles[y][x + 1] == TileType.FLOOR;
            boolean vertical = tiles[y - 1][x] == TileType.FLOOR && tiles[y + 1][x] == TileType.FLOOR;
            if (horizontal || vertical || random.nextDouble() < GameConfig.LEVEL_RANDOM_LOOP_CHANCE) {
                tiles[y][x] = TileType.FLOOR;
            }
        }
    }

    private static List<Position> collectFloors(TileType[][] tiles) {
        List<Position> floors = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (tiles[y][x] == TileType.FLOOR || tiles[y][x] == TileType.RIFT || tiles[y][x] == TileType.EXIT) {
                    floors.add(new Position(x, y));
                }
            }
        }
        return floors;
    }

    private static Position farthestReachable(Position start, TileType[][] tiles) {
        Queue<Position> queue = new ArrayDeque<>();
        Map<Position, Integer> distances = new HashMap<>();
        queue.add(start);
        distances.put(start, 0);
        Position farthest = start;
        while (!queue.isEmpty()) {
            Position current = queue.remove();
            int distance = distances.get(current);
            if (distance > distances.get(farthest)) {
                farthest = current;
            }
            for (Direction direction : Direction.CARDINALS) {
                Position next = current.translate(direction);
                if (next.y() < 0 || next.x() < 0 || next.y() >= tiles.length || next.x() >= tiles[0].length) {
                    continue;
                }
                if (tiles[next.y()][next.x()] == TileType.WALL || distances.containsKey(next)) {
                    continue;
                }
                distances.put(next, distance + 1);
                queue.add(next);
            }
        }
        return farthest;
    }

    private static void addRifts(TileType[][] tiles, List<Position> floors, Position start, Position exit, Random random, int count) {
        List<Position> candidates = new ArrayList<>(floors);
        candidates.removeIf(p -> p.equals(start) || p.equals(exit)
                || p.manhattanDistance(start) < GameConfig.LEVEL_RIFT_MIN_START_DISTANCE);
        Collections.shuffle(candidates, random);
        for (int i = 0; i < Math.min(count, candidates.size()); i++) {
            Position p = candidates.get(i);
            tiles[p.y()][p.x()] = TileType.RIFT;
        }
    }
}
