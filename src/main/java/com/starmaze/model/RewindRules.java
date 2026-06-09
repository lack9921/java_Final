package com.starmaze.model;

public final class RewindRules {
    private RewindRules() {
    }

    public static int gainCharge(int current) {
        return Math.min(GameConfig.REWIND_MAX, current + 1);
    }

    public static int spendCharge(int current) {
        return Math.max(0, current - 1);
    }

    public static boolean hasCharge(int current) {
        return current > 0;
    }

    public static boolean hasEnoughHistory(int snapshotCount) {
        return snapshotCount >= GameConfig.REWIND_MIN_SNAPSHOTS;
    }
}
