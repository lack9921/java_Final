package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class MenuOverlayDispatcher {
    private final MenuRenderer menuRenderer = new MenuRenderer();

    public void paint(Graphics2D g, GameState state, MenuButtonCache menuButtons, int width, int height,
                      MenuOverlayMode mode) {
        MenuOverlaySpec.forMode(mode).paint(menuRenderer, g, state, width, height, menuButtons.buttons());
    }
}
