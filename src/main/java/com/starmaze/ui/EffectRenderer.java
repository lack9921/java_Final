package com.starmaze.ui;

import java.awt.Graphics2D;
import java.util.List;

public final class EffectRenderer {
    private final EffectRenderDispatcher dispatcher = new EffectRenderDispatcher();
    private final ScreenShakeRenderer screenShakeRenderer = new ScreenShakeRenderer();

    public void applyScreenShake(Graphics2D g, List<ActiveEffect> effects, int renderFrame) {
        screenShakeRenderer.apply(g, effects, renderFrame);
    }

    public void paint(Graphics2D g, List<ActiveEffect> effects, BoardMetrics metrics, int renderFrame) {
        for (ActiveEffect effect : effects) {
            double t = EffectProgress.fromFrames(effect.startFrame(), renderFrame);
            int cx = metrics.centerX(effect.position());
            int cy = metrics.centerY(effect.position());
            dispatcher.paint(g, effect.type(), cx, cy, t, metrics.tileSize(), renderFrame);
        }
    }

}
