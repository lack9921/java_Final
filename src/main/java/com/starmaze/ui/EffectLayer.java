package com.starmaze.ui;

import com.starmaze.model.VisualEffectEvent;

import java.util.List;

public final class EffectLayer {
    private final ActiveEffectFactory effectFactory = new ActiveEffectFactory();
    private final ActiveEffectList effects = new ActiveEffectList();

    public void advance(List<VisualEffectEvent> events, int renderFrame) {
        for (VisualEffectEvent event : events) {
            effects.add(effectFactory.fromEvent(event, renderFrame));
        }
        effects.pruneExpired(renderFrame);
    }

    public List<ActiveEffect> effects() {
        return effects.view();
    }
}
