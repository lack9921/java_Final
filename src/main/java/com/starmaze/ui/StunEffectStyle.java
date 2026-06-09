package com.starmaze.ui;

import java.awt.Color;
import java.awt.Point;

public record StunEffectStyle(int alpha, int orbit, int dotCount, int dotRadius) {
    private static final Color GOLD = VisualConfig.GOLD;
    private static final int STUN_MAX_ALPHA = 180;
    private static final int STUN_DOT_COUNT = 3;
    private static final int STUN_DOT_RADIUS = 3;
    private static final double STUN_ORBIT_BASE = 0.42;
    private static final double STUN_ORBIT_GROWTH = 0.25;
    private static final double STUN_ORBIT_Y_SCALE = 0.55;
    private static final double STUN_SPIN_SPEED = 0.23;

    public static StunEffectStyle fromProgress(double progress, int tileSize) {
        int alpha = (int) (STUN_MAX_ALPHA * (1.0 - progress));
        int orbit = (int) (tileSize * (STUN_ORBIT_BASE + STUN_ORBIT_GROWTH * progress));
        return new StunEffectStyle(alpha, orbit, STUN_DOT_COUNT, STUN_DOT_RADIUS);
    }

    public boolean visible() {
        return alpha > 0;
    }

    public Color dotColor() {
        return UiColors.withAlpha(GOLD, alpha);
    }

    public Point dotCenter(int centerX, int centerY, int renderFrame, int dotIndex) {
        double angle = renderFrame * STUN_SPIN_SPEED + dotIndex * Math.PI * 2.0 / dotCount;
        int x = centerX + (int) (Math.cos(angle) * orbit);
        int y = centerY + (int) (Math.sin(angle) * orbit * STUN_ORBIT_Y_SCALE);
        return new Point(x, y);
    }
}
