package com.starmaze.ui;

import com.starmaze.audio.SoundEffect;
import com.starmaze.model.GameMode;
import com.starmaze.model.GameState;

public final class MenuActionHandler {
    public void handle(GameState state, String action) {
        state.soundEngine().play(SoundEffect.MENU);
        switch (action) {
            case MenuActions.START -> state.startNewGame();
            case MenuActions.HELP -> state.setMode(GameMode.HELP);
            case MenuActions.SETTINGS -> state.setMode(GameMode.SETTINGS);
            case MenuActions.RESUME -> state.togglePause();
            case MenuActions.RESTART_LEVEL -> state.restartLevel();
            case MenuActions.NEXT -> state.nextLevel();
            case MenuActions.SOUND -> state.toggleSound();
            case MenuActions.TITLE -> state.setMode(GameMode.TITLE);
            default -> {
            }
        }
    }
}
