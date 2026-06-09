package com.starmaze.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class RewindHistory {
    private final ArrayDeque<RewindSnapshot> snapshots = new ArrayDeque<>();

    public void clear() {
        snapshots.clear();
    }

    public int size() {
        return snapshots.size();
    }

    public void record(Position player, Iterable<Enemy> enemies, int phaseTicks) {
        List<Position> enemyPositions = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyPositions.add(enemy.position());
        }
        snapshots.addLast(new RewindSnapshot(player, enemyPositions, phaseTicks));
        while (snapshots.size() > GameConfig.REWIND_SNAPSHOT_LIMIT) {
            snapshots.removeFirst();
        }
    }

    public RewindSnapshot lookBack() {
        List<RewindSnapshot> history = new ArrayList<>(snapshots);
        return history.get(Math.max(0, history.size() - GameConfig.REWIND_LOOKBACK_SNAPSHOTS));
    }
}
