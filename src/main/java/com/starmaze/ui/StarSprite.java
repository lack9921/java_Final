package com.starmaze.ui;

import java.awt.Color;

public record StarSprite(int x, int y, int size, Color color) {
    private static final Color STAR_COLOR = new Color(160, 208, 236);
    private static final int STAR_SCROLL_DIVISOR = 3;
    private static final int STAR_ALPHA_BASE = 38;
    private static final int STAR_ALPHA_AMPLITUDE = 28;
    private static final int STAR_ALPHA_MIN = 15;
    private static final double STAR_ALPHA_SPEED = 0.035;
    private static final int STAR_SIZE = 2;

    public static StarSprite from(int baseX, int baseY, int phase, int width, int height, int tick) {
        int safeWidth = Math.max(1, width);
        int safeHeight = Math.max(1, height);
        int x = Math.floorMod(baseX + tick / STAR_SCROLL_DIVISOR, safeWidth);
        int y = Math.floorMod(baseY, safeHeight);
        int alpha = STAR_ALPHA_BASE
                + (int) (STAR_ALPHA_AMPLITUDE * Math.sin((tick + phase) * STAR_ALPHA_SPEED));
        return new StarSprite(x, y, STAR_SIZE, UiColors.withAlpha(STAR_COLOR, Math.max(STAR_ALPHA_MIN, alpha)));
    }
}
