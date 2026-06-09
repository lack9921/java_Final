package com.starmaze.ui;

import com.starmaze.model.VisualEffectType;

import java.util.List;

public final class ScreenShakeRules {
    private static final double SHAKE_X_FREQUENCY = 1.7;
    private static final double SHAKE_Y_FREQUENCY = 1.3;
    private static final double SHAKE_Y_SCALE = 0.6;

    private ScreenShakeRules() {
    }

    public static ScreenShakeOffset offset(List<ActiveEffect> effects, int renderFrame) {
        int shake = 0;
        for (ActiveEffect effect : effects) {
            if (shakesScreen(effect.type())) {
                int age = renderFrame - effect.startFrame();
                int remaining = Math.max(0, VisualConfig.EFFECT_LIFETIME_FRAMES - age);
                shake = Math.max(shake, remaining * VisualConfig.SHAKE_MAX_PIXELS
                        / VisualConfig.EFFECT_LIFETIME_FRAMES);
            }
        }
        if (shake <= 0) {
            return new ScreenShakeOffset(0, 0);
        }
        int dx = (int) Math.round(Math.sin(renderFrame * SHAKE_X_FREQUENCY) * shake);
        int dy = (int) Math.round(Math.cos(renderFrame * SHAKE_Y_FREQUENCY) * shake * SHAKE_Y_SCALE);
        return new ScreenShakeOffset(dx, dy);
    }

    public static boolean shakesScreen(VisualEffectType type) {
        return type == VisualEffectType.RIFT_WARP || type == VisualEffectType.REWIND_WAVE;
    }
}
