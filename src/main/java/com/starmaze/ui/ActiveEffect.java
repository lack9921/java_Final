package com.starmaze.ui;

import com.starmaze.model.Position;
import com.starmaze.model.VisualEffectType;

public record ActiveEffect(VisualEffectType type, Position position, int startFrame) {
}
