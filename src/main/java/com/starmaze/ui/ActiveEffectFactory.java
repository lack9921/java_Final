package com.starmaze.ui;

import com.starmaze.model.VisualEffectEvent;

public final class ActiveEffectFactory {
    public ActiveEffect fromEvent(VisualEffectEvent event, int renderFrame) {
        return new ActiveEffect(event.type(), event.position(), renderFrame);
    }
}
