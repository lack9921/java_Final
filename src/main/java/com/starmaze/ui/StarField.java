package com.starmaze.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public final class StarField {
    private static final Color STAR_COLOR = new Color(160, 208, 236);
    private static final int STAR_X_STEP = 137;
    private static final int STAR_Y_STEP = 83;
    private static final int STAR_PHASE_STEP = 11;
    private static final int STAR_SCROLL_DIVISOR = 3;
    private static final int STAR_ALPHA_BASE = 38;
    private static final int STAR_ALPHA_AMPLITUDE = 28;
    private static final int STAR_ALPHA_MIN = 15;
    private static final double STAR_ALPHA_SPEED = 0.035;
    private static final int STAR_SIZE = 2;

    private final Star[] stars;

    public StarField(int count) {
        stars = new Star[count];
        for (int i = 0; i < count; i++) {
            stars[i] = new Star(i * STAR_X_STEP, i * STAR_Y_STEP, i * STAR_PHASE_STEP);
        }
    }

    public void paint(Graphics2D g, int width, int height, int tick) {
        int safeWidth = Math.max(1, width);
        int safeHeight = Math.max(1, height);
        for (Star star : stars) {
            int x = Math.floorMod(star.baseX() + tick / STAR_SCROLL_DIVISOR, safeWidth);
            int y = Math.floorMod(star.baseY(), safeHeight);
            int alpha = STAR_ALPHA_BASE
                    + (int) (STAR_ALPHA_AMPLITUDE * Math.sin((tick + star.phase()) * STAR_ALPHA_SPEED));
            g.setColor(UiColors.withAlpha(STAR_COLOR, Math.max(STAR_ALPHA_MIN, alpha)));
            g.fillRect(x, y, STAR_SIZE, STAR_SIZE);
        }
    }

    private record Star(int baseX, int baseY, int phase) {
    }
}
