package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.util.function.Function;

public record TitleMenuContentLine(Color color, int fontStyle, int fontSize, int baselineOffset,
                                   Function<GameState, String> textProvider) {
    public String text(GameState state) {
        return textProvider.apply(state);
    }
}
