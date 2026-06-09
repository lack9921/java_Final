package com.starmaze.ui;

import java.awt.Color;

public final class VisualConfig {
    private VisualConfig() {
    }

    public static final int FRAME_DELAY_MS = 16;
    public static final int LOGIC_FRAME_DIVISOR = 2;
    public static final int WINDOW_DEFAULT_WIDTH = 1100;
    public static final int WINDOW_DEFAULT_HEIGHT = 780;
    public static final int WINDOW_MIN_WIDTH = 860;
    public static final int WINDOW_MIN_HEIGHT = 640;
    public static final int HUD_X = 24;
    public static final int HUD_Y = 18;
    public static final int HUD_HEIGHT = 58;
    public static final int BOARD_HORIZONTAL_MARGIN = 96;
    public static final int BOARD_VERTICAL_RESERVED = 180;
    public static final int BOARD_TOP_BASELINE = 96;
    public static final int TILE_MIN_SIZE = 18;
    public static final int TILE_MAX_SIZE = 42;
    public static final int BACKGROUND_STAR_COUNT = 80;
    public static final int BACKGROUND_GRID_SIZE = 48;
    public static final int EFFECT_LIFETIME_FRAMES = 34;
    public static final int MAX_EFFECTS = 64;
    public static final int SHAKE_MAX_PIXELS = 7;

    public static final Color BACK_TOP = new Color(8, 13, 29);
    public static final Color BACK_BOTTOM = new Color(16, 24, 44);
    public static final Color CYAN = new Color(70, 224, 232);
    public static final Color MINT = new Color(88, 231, 166);
    public static final Color PINK = new Color(255, 92, 150);
    public static final Color GOLD = new Color(255, 205, 98);
    public static final Color INK = new Color(225, 238, 246);
    public static final Color MUTED = new Color(143, 164, 180);
}
