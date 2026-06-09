package com.starmaze.ui;

import com.starmaze.model.VisualEffectType;

import java.awt.Graphics2D;

public final class EffectRenderDispatcher {
    private final RingEffectRenderer ringEffectRenderer = new RingEffectRenderer();
    private final StunEffectRenderer stunEffectRenderer = new StunEffectRenderer();

    public void paint(Graphics2D g, VisualEffectType type, int cx, int cy, double progress, int tileSize,
                      int renderFrame) {
        EffectRenderSpec spec = EffectRenderSpec.forType(type);
        switch (spec.kind()) {
            case RING -> ringEffectRenderer.paint(g, cx, cy, progress, spec.ringColor(), tileSize, spec.ringScale());
            case STUN -> stunEffectRenderer.paint(g, cx, cy, progress, tileSize, renderFrame);
        }
    }
}
