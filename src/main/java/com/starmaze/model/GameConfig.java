package com.starmaze.model;

public final class GameConfig {
    private GameConfig() {
    }

    public static final int PHASE_MAX = 100;
    public static final int PHASE_COST = 30;
    public static final int PHASE_INITIAL = 70;
    public static final int PHASE_RESTART_MIN = 45;
    public static final int PHASE_DURATION_TICKS = 54;
    public static final int PHASE_RECHARGE_INTERVAL_TICKS = 10;
    public static final int PHASE_RECHARGE_AMOUNT = 1;

    public static final int REWIND_MAX = 3;
    public static final int REWIND_INITIAL_CHARGES = 1;
    public static final int REWIND_LOOKBACK_SNAPSHOTS = 42;
    public static final int REWIND_MIN_SNAPSHOTS = 8;
    public static final int REWIND_SNAPSHOT_LIMIT = 130;
    public static final int REWIND_SNAPSHOT_INTERVAL_TICKS = 3;
    public static final int REWIND_ENEMY_STUN_TICKS = 10;

    public static final int ENEMY_MOVE_INTERVAL_TICKS = 8;
    public static final int ENEMY_ALERT_BASE_RANGE = 8;
    public static final int ENEMY_ALERT_LEVEL_BONUS_MAX = 5;
    public static final int ENEMY_MAX_COUNT = 7;
    public static final int ENEMY_START_COUNT = 2;
    public static final int ENEMY_LEVEL_DIVISOR = 2;
    public static final int ENEMY_MIN_PLAYER_DISTANCE = 8;
    public static final int ENEMY_MIN_EXIT_DISTANCE = 4;
    public static final double ENEMY_PATROL_KEEP_DIRECTION_CHANCE = 0.82;
    public static final int ENEMY_PHASE_STUN_TICKS = 24;

    public static final int CRYSTAL_MAX_COUNT = 16;
    public static final int CRYSTAL_BASE_COUNT = 7;
    public static final int CRYSTAL_PER_LEVEL = 2;
    public static final int CRYSTAL_MIN_NEEDED = 4;
    public static final int CRYSTAL_BASE_NEEDED = 5;
    public static final int CRYSTAL_MIN_START_DISTANCE = 5;

    public static final int PHASE_CELL_MAX_COUNT = 5;
    public static final int PHASE_CELL_BASE_COUNT = 2;
    public static final int PHASE_CELL_LEVEL_DIVISOR = 2;
    public static final int REWIND_CELL_MAX_COUNT = 3;
    public static final int REWIND_CELL_BASE_COUNT = 1;
    public static final int REWIND_CELL_LEVEL_DIVISOR = 3;

    public static final int SCORE_RESTART_PENALTY = 50;
    public static final int SCORE_LEVEL_CLEAR_BASE = 150;
    public static final int SCORE_LEVEL_CLEAR_PER_LEVEL = 35;
    public static final int SCORE_CRYSTAL_BASE = 80;
    public static final int SCORE_CRYSTAL_PER_LEVEL = 8;
    public static final int SCORE_PHASE_CELL = 35;
    public static final int SCORE_REWIND_CELL = 45;
    public static final int SCORE_RIFT = 20;
    public static final int SCORE_PHASE_ACTIVATE = 10;
    public static final int SCORE_REWIND_PENALTY = 25;
    public static final int SCORE_PHASE_STUN = 25;
    public static final int SCORE_MOVE_COST = 1;
    public static final int SCORE_PHASE_WALL_STEP = 1;
    public static final int SCORE_LEVEL_BONUS_MIN = 100;
    public static final int SCORE_LEVEL_BONUS_BASE = 600;
    public static final int SCORE_LEVEL_BONUS_MOVE_COST = 2;
    public static final int SCORE_LEVEL_BONUS_PHASE_MULTIPLIER = 3;
    public static final int SCORE_LEVEL_BONUS_REWIND_MULTIPLIER = 60;

    public static final int PHASE_GAIN_CRYSTAL = 18;
    public static final int PHASE_GAIN_CELL = 40;
    public static final int PHASE_GAIN_RIFT = 22;
    public static final int PHASE_GAIN_NEXT_LEVEL = 30;
    public static final int RIFT_TELEPORT_MIN_DISTANCE = 6;

    public static final int MESSAGE_DURATION_TICKS = 120;

    public static final int RANK_ONE_SCORE = 800;
    public static final int RANK_TWO_SCORE = 1800;
    public static final int RANK_THREE_SCORE = 3600;
    public static final int RANK_FOUR_SCORE = 6000;

    public static final int LEVEL_MAX_WIDTH = 31;
    public static final int LEVEL_BASE_WIDTH = 19;
    public static final int LEVEL_WIDTH_PER_LEVEL = 2;
    public static final int LEVEL_MAX_HEIGHT = 23;
    public static final int LEVEL_BASE_HEIGHT = 15;
    public static final int LEVEL_HEIGHT_PER_STEP = 2;
    public static final int LEVEL_HEIGHT_LEVEL_DIVISOR = 2;
    public static final int LEVEL_LOOP_DENSITY_DIVISOR = 7;
    public static final int LEVEL_LOOP_PER_LEVEL = 3;
    public static final double LEVEL_RANDOM_LOOP_CHANCE = 0.08;
    public static final int LEVEL_RIFT_MAX_COUNT = 8;
    public static final int LEVEL_RIFT_BASE_COUNT = 2;
    public static final int LEVEL_RIFT_MIN_START_DISTANCE = 6;
}
