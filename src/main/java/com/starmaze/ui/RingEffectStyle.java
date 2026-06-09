package com.starmaze.ui;

import java.awt.Color;

public record RingEffectStyle(int alpha, int radius, float strokeWidth) {
    private static final int RING_MAX_ALPHA = 150;
    private static final double RING_BASE_RADIUS = 0.35;
    private static final float RING_MIN_STROKE = 1.4f;
    private static final double RING_MAX_STROKE = 3.0;

    public static RingEffectStyle fromProgress(double progress, int tileSize, double scale) {
        int alpha = (int) (RING_MAX_ALPHA * (1.0 - progress));
        int radius = (int) (tileSize * (RING_BASE_RADIUS + scale * progress));
        float strokeWidth = Math.max(RING_MIN_STROKE, (float) (RING_MAX_STROKE * (1.0 - progress)));
        return new RingEffectStyle(alpha, radius, strokeWidth);
    }

    public boolean visible() {
        return alpha > 0;
    }

    public Color strokeColor(Color baseColor) {
        return UiColors.withAlpha(baseColor, alpha);
    }

    public Color fillColor(Color baseColor) {
        return UiColors.withAlpha(baseColor, alpha / 4);
    }
}
