package com.starmaze.model;

public final class PhaseRules {
    private PhaseRules() {
    }

    public static int gain(int current, int amount) {
        return Math.min(GameConfig.PHASE_MAX, current + amount);
    }

    public static int restartFloor(int current) {
        return Math.max(GameConfig.PHASE_RESTART_MIN, current);
    }

    public static int recharge(int current, int tick) {
        if (current >= GameConfig.PHASE_MAX || tick % GameConfig.PHASE_RECHARGE_INTERVAL_TICKS != 0) {
            return current;
        }
        return gain(current, GameConfig.PHASE_RECHARGE_AMOUNT);
    }

    public static int afterRewind(int snapshotPhaseTicks) {
        return Math.max(0, snapshotPhaseTicks / 2);
    }

    public static boolean canAfford(int current) {
        return current >= GameConfig.PHASE_COST;
    }
}
