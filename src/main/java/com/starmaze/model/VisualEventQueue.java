package com.starmaze.model;

import java.util.ArrayList;
import java.util.List;

public final class VisualEventQueue {
    private final List<VisualEffectEvent> events = new ArrayList<>();

    public void emit(VisualEffectType type, Position position, int tick) {
        events.add(new VisualEffectEvent(type, position, tick));
    }

    public List<VisualEffectEvent> drain() {
        List<VisualEffectEvent> drained = new ArrayList<>(events);
        events.clear();
        return drained;
    }

    public void clear() {
        events.clear();
    }
}
