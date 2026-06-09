package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;

public final class HudPhaseColorSelector {
    public Color select(GameState state) {
        return state.isPhaseActive() ? VisualConfig.PINK : VisualConfig.CYAN;
    }
}
