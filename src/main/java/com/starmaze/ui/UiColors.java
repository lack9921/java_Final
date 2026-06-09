package com.starmaze.ui;

import java.awt.Color;

public final class UiColors {
    private UiColors() {
    }

    public static Color withAlpha(Color color, int alpha) {
        int clamped = Math.max(0, Math.min(255, alpha));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), clamped);
    }
}
