package com.starmaze.ui;

public final class EffectProgress {
    private EffectProgress() {
    }

    public static double fromFrames(int startFrame, int renderFrame) {
        int age = renderFrame - startFrame;
        return Math.max(0.0, Math.min(1.0, age / (double) VisualConfig.EFFECT_LIFETIME_FRAMES));
    }
}
