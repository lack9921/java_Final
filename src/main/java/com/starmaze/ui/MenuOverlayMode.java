package com.starmaze.ui;

import com.starmaze.model.GameMode;

import java.util.Optional;

public enum MenuOverlayMode {
    TITLE,
    PAUSED,
    HELP,
    SETTINGS,
    LEVEL_CLEAR,
    GAME_OVER;

    public static Optional<MenuOverlayMode> from(GameMode mode) {
        return switch (mode) {
            case TITLE -> Optional.of(TITLE);
            case PAUSED -> Optional.of(PAUSED);
            case HELP -> Optional.of(HELP);
            case SETTINGS -> Optional.of(SETTINGS);
            case LEVEL_CLEAR -> Optional.of(LEVEL_CLEAR);
            case GAME_OVER -> Optional.of(GAME_OVER);
            case PLAYING -> Optional.empty();
        };
    }
}
