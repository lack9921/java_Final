package com.starmaze.ui;

import java.awt.Color;

public record PlayerVisualStyle(Color bodyColor, int glowAlpha) {
    private static final int PLAYER_PHASE_ALPHA = 80;
    private static final int PLAYER_NORMAL_ALPHA = 48;

    public static PlayerVisualStyle forPhaseState(boolean phaseActive) {
        return phaseActive
                ? new PlayerVisualStyle(VisualConfig.CYAN, PLAYER_PHASE_ALPHA)
                : new PlayerVisualStyle(VisualConfig.MINT, PLAYER_NORMAL_ALPHA);
    }

    public Color glowColor() {
        return UiColors.withAlpha(bodyColor, glowAlpha);
    }
}
