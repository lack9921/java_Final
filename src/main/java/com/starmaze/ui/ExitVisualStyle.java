package com.starmaze.ui;

import java.awt.Color;

public record ExitVisualStyle(Color ringColor, int fillAlpha, boolean lockVisible) {
    private static final Color EXIT_LOCKED = new Color(90, 100, 118);
    private static final int EXIT_OPEN_ALPHA = 80;
    private static final int EXIT_LOCKED_ALPHA = 40;

    public static ExitVisualStyle forOpenState(boolean open) {
        return open
                ? new ExitVisualStyle(VisualConfig.MINT, EXIT_OPEN_ALPHA, false)
                : new ExitVisualStyle(EXIT_LOCKED, EXIT_LOCKED_ALPHA, true);
    }

    public Color fillColor() {
        return UiColors.withAlpha(ringColor, fillAlpha);
    }
}
