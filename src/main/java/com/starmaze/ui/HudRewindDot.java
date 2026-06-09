package com.starmaze.ui;

import java.awt.Color;

public record HudRewindDot(int x, int y, int size, boolean filled) {
    private static final Color GOLD = VisualConfig.GOLD;
    private static final Color REWIND_EMPTY = new Color(55, 65, 82);
    private static final int REWIND_DOT_GAP = 20;
    private static final int REWIND_DOT_SIZE = 13;
    private static final int REWIND_DOT_X_OFFSET = 38;
    private static final int REWIND_DOT_Y_OFFSET = 9;

    public static HudRewindDot from(int originX, int originY, int index, int charges) {
        return new HudRewindDot(
                originX + REWIND_DOT_X_OFFSET + index * REWIND_DOT_GAP,
                originY - REWIND_DOT_Y_OFFSET,
                REWIND_DOT_SIZE,
                index < charges);
    }

    public Color fillColor() {
        return filled ? GOLD : REWIND_EMPTY;
    }
}
