package com.starmaze.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class LevelPopulator {
    public LevelPopulation populate(Level level, int levelIndex, Random random) {
        Position player = level.start();
        List<Position> candidates = new ArrayList<>(level.floorPositions());
        candidates.removeIf(p -> distance(p, player) < GameConfig.CRYSTAL_MIN_START_DISTANCE
                || p.equals(level.exit()) || level.tileAt(p) == TileType.RIFT);
        Collections.shuffle(candidates, random);

        Set<Position> crystals = new LinkedHashSet<>();
        int crystalCount = Math.min(GameConfig.CRYSTAL_MAX_COUNT,
                GameConfig.CRYSTAL_BASE_COUNT + levelIndex * GameConfig.CRYSTAL_PER_LEVEL);
        crystals.addAll(take(candidates, crystalCount));
        int crystalsNeeded = Math.max(GameConfig.CRYSTAL_MIN_NEEDED,
                Math.min(crystals.size(), GameConfig.CRYSTAL_BASE_NEEDED + levelIndex));

        candidates.removeAll(crystals);
        Collections.shuffle(candidates, random);
        Set<Position> phaseCells = new LinkedHashSet<>();
        int phaseCount = Math.min(GameConfig.PHASE_CELL_MAX_COUNT,
                GameConfig.PHASE_CELL_BASE_COUNT + levelIndex / GameConfig.PHASE_CELL_LEVEL_DIVISOR);
        phaseCells.addAll(take(candidates, phaseCount));

        candidates.removeAll(phaseCells);
        Collections.shuffle(candidates, random);
        Set<Position> rewindCells = new LinkedHashSet<>();
        int rewindCount = Math.min(GameConfig.REWIND_CELL_MAX_COUNT,
                GameConfig.REWIND_CELL_BASE_COUNT + levelIndex / GameConfig.REWIND_CELL_LEVEL_DIVISOR);
        rewindCells.addAll(take(candidates, rewindCount));

        candidates.removeAll(rewindCells);
        candidates.removeIf(p -> distance(p, player) < GameConfig.ENEMY_MIN_PLAYER_DISTANCE
                || distance(p, level.exit()) < GameConfig.ENEMY_MIN_EXIT_DISTANCE);
        Collections.shuffle(candidates, random);
        List<Enemy> enemies = new ArrayList<>();
        int enemyCount = Math.min(GameConfig.ENEMY_MAX_COUNT,
                GameConfig.ENEMY_START_COUNT + levelIndex / GameConfig.ENEMY_LEVEL_DIVISOR);
        for (Position position : take(candidates, enemyCount)) {
            enemies.add(new Enemy(position, Direction.CARDINALS.get(random.nextInt(Direction.CARDINALS.size()))));
        }

        return new LevelPopulation(crystals, phaseCells, rewindCells, enemies, crystalsNeeded);
    }

    private List<Position> take(List<Position> source, int max) {
        if (source.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(source.subList(0, Math.min(max, source.size())));
    }

    private int distance(Position a, Position b) {
        return a.manhattanDistance(b);
    }
}
