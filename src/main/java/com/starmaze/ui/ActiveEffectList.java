package com.starmaze.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ActiveEffectList {
    private final List<ActiveEffect> effects = new ArrayList<>();

    public void add(ActiveEffect effect) {
        effects.add(effect);
        trimToCapacity();
    }

    public void pruneExpired(int renderFrame) {
        effects.removeIf(effect -> renderFrame - effect.startFrame() > VisualConfig.EFFECT_LIFETIME_FRAMES);
    }

    public List<ActiveEffect> view() {
        return Collections.unmodifiableList(effects);
    }

    private void trimToCapacity() {
        while (effects.size() > VisualConfig.MAX_EFFECTS) {
            effects.remove(0);
        }
    }
}
