package com.starmaze.model;

public final class LevelProgressRules {
    private LevelProgressRules() {
    }

    public static int nextLevelEntryBonus(int nextLevelIndex) {
        return GameConfig.SCORE_LEVEL_CLEAR_BASE + nextLevelIndex * GameConfig.SCORE_LEVEL_CLEAR_PER_LEVEL;
    }

    public static int nextLevelPhaseGain() {
        return GameConfig.PHASE_GAIN_NEXT_LEVEL;
    }

    public static int restartPenalty() {
        return GameConfig.SCORE_RESTART_PENALTY;
    }
}
