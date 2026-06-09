package com.starmaze.ui;

import com.starmaze.model.GameState;

public final class HudMessageSelector {
    public String select(GameState state) {
        return state.messageTicks() > 0 ? state.message() : HudText.DEFAULT_HINT;
    }
}
