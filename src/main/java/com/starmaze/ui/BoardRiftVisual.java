package com.starmaze.ui;

import java.awt.Color;

public record BoardRiftVisual(Color fillColor, Color strokeColor, int fillInset, int fillRadius, int fillSize,
                              int ovalInset, int ovalSize) {
    private static final Color RIFT_FILL = new Color(26, 23, 51);
    private static final Color RIFT_STROKE = new Color(186, 107, 255);
    private static final int RIFT_INSET = 2;
    private static final int RIFT_RADIUS = 7;
    private static final int RIFT_PULSE_BASE = 50;
    private static final int RIFT_PULSE_AMPLITUDE = 45;
    private static final int RIFT_PULSE_X_FACTOR = 7;
    private static final int RIFT_PULSE_Y_FACTOR = 11;
    private static final double RIFT_PULSE_SPEED = 0.08;
    private static final int RIFT_ALPHA_BASE = 120;
    private static final int RIFT_OVAL_INSET_DIVISOR = 5;
    private static final int RIFT_OVAL_SIZE_NUMERATOR = 3;

    public static BoardRiftVisual from(int tileX, int tileY, int size, int tick) {
        int pulse = (int) (RIFT_PULSE_BASE + RIFT_PULSE_AMPLITUDE
                * Math.sin((tick + tileX * RIFT_PULSE_X_FACTOR + tileY * RIFT_PULSE_Y_FACTOR) * RIFT_PULSE_SPEED));
        int ovalInset = size / RIFT_OVAL_INSET_DIVISOR;
        int ovalSize = size * RIFT_OVAL_SIZE_NUMERATOR / RIFT_OVAL_INSET_DIVISOR;
        return new BoardRiftVisual(RIFT_FILL, UiColors.withAlpha(RIFT_STROKE, RIFT_ALPHA_BASE + pulse),
                RIFT_INSET, RIFT_RADIUS, size - RIFT_INSET * 2, ovalInset, ovalSize);
    }
}
