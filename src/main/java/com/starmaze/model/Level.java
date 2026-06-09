package com.starmaze.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public final class Level {
    private final int width;
    private final int height;
    private final TileType[][] tiles;
    private final Position start;
    private final Position exit;
    private final List<Position> floorPositions;

    public Level(int width, int height, TileType[][] tiles, Position start, Position exit, List<Position> floorPositions) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.start = start;
        this.exit = exit;
        this.floorPositions = Collections.unmodifiableList(new ArrayList<>(floorPositions));
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Position start() {
        return start;
    }

    public Position exit() {
        return exit;
    }

    public List<Position> floorPositions() {
        return floorPositions;
    }

    public boolean contains(Position position) {
        return position.x() >= 0 && position.y() >= 0 && position.x() < width && position.y() < height;
    }

    public TileType tileAt(Position position) {
        return tileAt(position.x(), position.y());
    }

    public TileType tileAt(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return TileType.WALL;
        }
        return tiles[y][x];
    }

    public boolean isWalkableForEnemy(Position position) {
        TileType tile = tileAt(position);
        return tile == TileType.FLOOR || tile == TileType.RIFT || tile == TileType.EXIT;
    }

    public Position nearestWalkable(Position origin) {
        boolean[][] seen = new boolean[height][width];
        Queue<Position> queue = new ArrayDeque<>();
        Position clamped = new Position(
                Math.max(0, Math.min(width - 1, origin.x())),
                Math.max(0, Math.min(height - 1, origin.y()))
        );
        queue.add(clamped);
        seen[clamped.y()][clamped.x()] = true;
        while (!queue.isEmpty()) {
            Position current = queue.remove();
            if (isWalkableForEnemy(current)) {
                return current;
            }
            for (Direction direction : Direction.CARDINALS) {
                Position next = current.translate(direction);
                if (contains(next) && !seen[next.y()][next.x()]) {
                    seen[next.y()][next.x()] = true;
                    queue.add(next);
                }
            }
        }
        return start;
    }

    public Position randomFloorFarFrom(Position origin, Random random, int minDistance) {
        List<Position> candidates = new ArrayList<>();
        for (Position p : floorPositions) {
            if (!p.equals(exit) && p.manhattanDistance(origin) >= minDistance) {
                candidates.add(p);
            }
        }
        if (candidates.isEmpty()) {
            return start;
        }
        return candidates.get(random.nextInt(candidates.size()));
    }
}
