package com.starmaze.ui;

import java.awt.Color;

public record EnemyVisualStyle(Color bodyColor, int glowAlpha) {
    private static final Color ENEMY_STUNNED = new Color(128, 154, 170);
    private static final int ENEMY_GLOW_ALPHA = 50;

    public static EnemyVisualStyle forStunState(boolean stunned) {
        return stunned
                ? new EnemyVisualStyle(ENEMY_STUNNED, ENEMY_GLOW_ALPHA)
                : new EnemyVisualStyle(VisualConfig.PINK, ENEMY_GLOW_ALPHA);
    }

    public Color glowColor() {
        return UiColors.withAlpha(bodyColor, glowAlpha);
    }
}
