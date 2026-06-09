package com.starmaze.model;

public final class GameMessages {
    public static final String START_HINT = "收集星核，打开出口";
    public static final String PHASE_ENDED_SAFE = "相位结束，已回到安全格";
    public static final String WALL_BLOCKED = "实体墙阻挡，空格可短暂相位穿行";
    public static final String PHASE_CELL_COLLECTED = "相位电池已充能";
    public static final String REWIND_CELL_COLLECTED = "获得轨迹回溯次数";
    public static final String RIFT_WARP = "量子裂隙将你折跃到了新的通道";
    public static final String RIFT_PHASE_GAIN = "量子裂隙转化为相位能量";
    public static final String PHASE_ALREADY_ACTIVE = "相位护盾正在运行";
    public static final String PHASE_NOT_ENOUGH = "相位能量不足，继续收集星核或电池";
    public static final String PHASE_ACTIVATED = "相位启动：可穿墙并干扰巡航体";
    public static final String REWIND_EMPTY = "回溯次数不足";
    public static final String REWIND_NOT_READY = "轨迹记录不足，稍后再试";
    public static final String REWIND_DONE = "轨迹回溯完成，巡航体短暂失衡";
    public static final String ENEMY_PHASE_STUN = "相位脉冲干扰了一架巡航体";
    public static final String ENEMY_CAPTURED_PLAYER = "巡航体截获了你的星轨信号";

    private GameMessages() {
    }

    public static String levelStart(int levelIndex, int crystalsNeeded) {
        return "第 " + levelIndex + " 层：收集 " + crystalsNeeded + " 枚星核后进入传送门";
    }

    public static String crystalCollected(int collected, int needed) {
        return "获得星核：" + collected + "/" + needed;
    }

    public static String levelClear(int levelBonus) {
        return "传送门已启动，奖励 +" + levelBonus;
    }

    public static String exitLocked(int remaining) {
        return "传送门锁定：还需要 " + remaining + " 枚星核";
    }
}
