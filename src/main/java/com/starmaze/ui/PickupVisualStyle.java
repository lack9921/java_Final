package com.starmaze.ui;

import java.awt.Color;

public enum PickupVisualStyle {
    CRYSTAL(VisualConfig.GOLD, PickupShape.DIAMOND),
    PHASE_CELL(VisualConfig.CYAN, PickupShape.ROUNDED_CELL),
    REWIND_CELL(VisualConfig.PINK, PickupShape.CROSS_RING);

    private static final int GLOW_ALPHA = 50;

    private final Color bodyColor;
    private final PickupShape shape;

    PickupVisualStyle(Color bodyColor, PickupShape shape) {
        this.bodyColor = bodyColor;
        this.shape = shape;
    }

    public Color bodyColor() {
        return bodyColor;
    }

    public PickupShape shape() {
        return shape;
    }

    public Color glowColor() {
        return UiColors.withAlpha(bodyColor, GLOW_ALPHA);
    }
}
