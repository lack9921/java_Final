package com.starmaze.ui;

import com.starmaze.model.VisualEffectType;

import java.awt.Color;

public record EffectRenderSpec(EffectRenderKind kind, Color ringColor, double ringScale) {
    private static final Color RIFT_PURPLE = new Color(186, 107, 255);
    private static final double PHASE_RING_SCALE = 1.6;
    private static final double RIFT_RING_SCALE = 2.5;
    private static final double REWIND_RING_SCALE = 3.1;

    public static EffectRenderSpec forType(VisualEffectType type) {
        return switch (type) {
            case PHASE_BURST -> new EffectRenderSpec(EffectRenderKind.RING, VisualConfig.CYAN, PHASE_RING_SCALE);
            case RIFT_WARP -> new EffectRenderSpec(EffectRenderKind.RING, RIFT_PURPLE, RIFT_RING_SCALE);
            case REWIND_WAVE -> new EffectRenderSpec(EffectRenderKind.RING, VisualConfig.GOLD, REWIND_RING_SCALE);
            case ENEMY_STUN -> new EffectRenderSpec(EffectRenderKind.STUN, null, 0.0);
        };
    }
}
