package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class MenuOverlayDispatcher {
    private final MenuRenderer menuRenderer = new MenuRenderer();

    public void paint(Graphics2D g, GameState state, MenuButtonCache menuButtons, int width, int height,
                      MenuOverlayMode mode) {
        switch (mode) {
            case TITLE -> menuRenderer.paintTitle(g, state, width, height, menuButtons.buttons());
            case PAUSED -> menuRenderer.paintPause(g, state, width, height, menuButtons.buttons());
            case HELP -> menuRenderer.paintHelp(g, state, width, height, menuButtons.buttons());
            case SETTINGS -> menuRenderer.paintSettings(g, state, width, height, menuButtons.buttons());
            case LEVEL_CLEAR -> menuRenderer.paintLevelClear(g, state, width, height, menuButtons.buttons());
            case GAME_OVER -> menuRenderer.paintGameOver(g, state, width, height, menuButtons.buttons());
        }
    }
}
