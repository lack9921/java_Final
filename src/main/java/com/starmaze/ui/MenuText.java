package com.starmaze.ui;

public final class MenuText {
    public static final String TITLE = "星轨迷阵";
    public static final String SUBTITLE = "Star Maze  -  相位穿墙与轨迹回溯的霓虹迷宫";
    public static final String TITLE_HINT = "收集星核、躲避巡航体、打开传送门。每一层地图都会重新生成。";
    public static final String HIGH_SCORE = "最高分 ";
    public static final String GAMES_PLAYED = "    游玩 ";
    public static final String VICTORIES = "    通关 ";
    public static final String PAUSED = "已暂停";
    public static final String PAUSE_HINT = "思考路线，或者开一段相位从墙里穿过去。";
    public static final String HELP_TITLE = "玩法说明";
    public static final String SETTINGS_TITLE = "设置";
    public static final String SETTINGS_SAVE_HINT = "记录会保存在用户目录 .starmaze/save.properties";
    public static final String LEVEL_CLEAR_TITLE = "传送成功";
    public static final String RANK_PREFIX = "评级 ";
    public static final String GAME_OVER_TITLE = "任务失败";
    public static final String FINAL_SCORE = "最终分数 ";
    public static final String BEST_SCORE = "    最高分 ";

    public static final String[] HELP_LINES = {
            "目标：收集足够星核后进入绿色传送门，进入下一层。",
            "巡航体会巡逻，接近你时会自动追踪；被碰到则游戏结束。",
            "空格：消耗相位能量，短时间穿墙，并能干扰碰到的巡航体。",
            "R：消耗一次轨迹回溯，将你和巡航体拉回几秒前的位置。",
            "裂隙格会随机折跃或补充能量，适合用来甩开追踪。",
            "每层地图、星核、补给和敌人位置都会重新生成，路线需要临场判断。"
    };

    private MenuText() {
    }

    public static String levelClearSummary(int levelIndex, int score) {
        return "第 " + levelIndex + " 层完成，当前分数 " + score;
    }
}
