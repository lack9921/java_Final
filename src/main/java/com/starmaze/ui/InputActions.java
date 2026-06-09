package com.starmaze.ui;

import com.starmaze.model.GameMode;
import com.starmaze.model.GameState;

public final class InputActions {
    private InputActions() {
    }

    public static void handleEscape(GameState state) {
        switch (state.mode()) {
            case PLAYING, PAUSED -> state.togglePause();
            case HELP, SETTINGS, LEVEL_CLEAR, GAME_OVER -> state.setMode(GameMode.TITLE);
            case TITLE -> {
            }
        }
    }

    public static void handleEnter(GameState state) {
        switch (state.mode()) {
            case TITLE -> state.startNewGame();
            case LEVEL_CLEAR -> state.nextLevel();
            case GAME_OVER -> state.startNewGame();
            case PAUSED -> state.togglePause();
            default -> {
            }
        }
    }
}
