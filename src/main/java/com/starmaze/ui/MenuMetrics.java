package com.starmaze.ui;

public final class MenuMetrics {
    public static final int TITLE_MIN_TOP = 78;
    public static final int TITLE_TOP_DIVISOR = 6;
    public static final int TITLE_BUTTON_W = 220;
    public static final int TITLE_PRIMARY_BUTTON_H = 46;
    public static final int TITLE_BUTTON_H = 42;
    public static final int TITLE_START_Y = 128;
    public static final int TITLE_HELP_Y = 184;
    public static final int TITLE_SETTINGS_Y = 236;

    public static final int SMALL_BUTTON_W = 200;
    public static final int MEDIUM_BUTTON_W = 210;
    public static final int LARGE_BUTTON_W = 230;
    public static final int BUTTON_H = 42;

    public static final int PAUSE_W = 330;
    public static final int PAUSE_H = 290;
    public static final int SETTINGS_W = 330;
    public static final int SETTINGS_H = 260;
    public static final int HELP_MAX_W = 720;
    public static final int HELP_HORIZONTAL_MARGIN = 90;
    public static final int HELP_H = 440;
    public static final int RESULT_W = 380;
    public static final int RESULT_H = 300;
    public static final int GAME_OVER_W = 370;
    public static final int GAME_OVER_H = 290;
    public static final int PAUSE_BUTTON_X = 65;
    public static final int PAUSE_RESUME_Y = 112;
    public static final int PAUSE_RESTART_Y = 164;
    public static final int PAUSE_TITLE_Y = 216;
    public static final int HELP_TITLE_BOTTOM_OFFSET = 74;
    public static final int SETTINGS_BUTTON_X = 65;
    public static final int SETTINGS_SOUND_Y = 122;
    public static final int SETTINGS_TITLE_Y = 178;
    public static final int RESULT_BUTTON_X = 75;
    public static final int RESULT_NEXT_Y = 156;
    public static final int RESULT_TITLE_Y = 210;
    public static final int GAME_OVER_BUTTON_X = 70;
    public static final int GAME_OVER_START_Y = 156;
    public static final int GAME_OVER_TITLE_Y = 210;

    private MenuMetrics() {
    }

    public static int centerX(int width) {
        return width / 2;
    }

    public static int titleTop(int height) {
        return Math.max(TITLE_MIN_TOP, height / TITLE_TOP_DIVISOR);
    }

    public static MenuPanel pausePanel(int width, int height) {
        return centered(width, height, PAUSE_W, PAUSE_H);
    }

    public static MenuPanel settingsPanel(int width, int height) {
        return centered(width, height, SETTINGS_W, SETTINGS_H);
    }

    public static MenuPanel helpPanel(int width, int height) {
        int w = Math.min(HELP_MAX_W, width - HELP_HORIZONTAL_MARGIN);
        return centered(width, height, w, HELP_H);
    }

    public static MenuPanel levelClearPanel(int width, int height) {
        return centered(width, height, RESULT_W, RESULT_H);
    }

    public static MenuPanel gameOverPanel(int width, int height) {
        return centered(width, height, GAME_OVER_W, GAME_OVER_H);
    }

    private static MenuPanel centered(int width, int height, int w, int h) {
        return new MenuPanel((width - w) / 2, (height - h) / 2, w, h);
    }
}
