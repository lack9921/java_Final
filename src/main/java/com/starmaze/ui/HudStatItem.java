package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.util.function.Function;

public record HudStatItem(int x, Function<GameState, String> textProvider) {
    public String text(GameState state) {
        return textProvider.apply(state);
    }
}
