package com.starmaze.model;

public final class ScoreRules {
    private ScoreRules() {
    }

    public static int levelClearBonus(int moves, int phaseMeter, int rewindCharges) {
        int bonus = GameConfig.SCORE_LEVEL_BONUS_BASE
                - moves * GameConfig.SCORE_LEVEL_BONUS_MOVE_COST
                + phaseMeter * GameConfig.SCORE_LEVEL_BONUS_PHASE_MULTIPLIER
                + rewindCharges * GameConfig.SCORE_LEVEL_BONUS_REWIND_MULTIPLIER;
        return Math.max(GameConfig.SCORE_LEVEL_BONUS_MIN, bonus);
    }

    public static int crystalPickup(int levelIndex) {
        return GameConfig.SCORE_CRYSTAL_BASE + levelIndex * GameConfig.SCORE_CRYSTAL_PER_LEVEL;
    }

    public static int phaseCellPickup() {
        return GameConfig.SCORE_PHASE_CELL;
    }

    public static int rewindCellPickup() {
        return GameConfig.SCORE_REWIND_CELL;
    }

    public static int riftVisit() {
        return GameConfig.SCORE_RIFT;
    }

    public static int phaseActivation() {
        return GameConfig.SCORE_PHASE_ACTIVATE;
    }

    public static int rewindPenalty() {
        return GameConfig.SCORE_REWIND_PENALTY;
    }

    public static int phaseEnemyStun() {
        return GameConfig.SCORE_PHASE_STUN;
    }

    public static int moveCost() {
        return GameConfig.SCORE_MOVE_COST;
    }

    public static int phaseWallStep() {
        return GameConfig.SCORE_PHASE_WALL_STEP;
    }

    public static int runRank(int score) {
        if (score >= GameConfig.RANK_FOUR_SCORE) {
            return 5;
        }
        if (score >= GameConfig.RANK_THREE_SCORE) {
            return 4;
        }
        if (score >= GameConfig.RANK_TWO_SCORE) {
            return 3;
        }
        if (score >= GameConfig.RANK_ONE_SCORE) {
            return 2;
        }
        return 1;
    }
}
