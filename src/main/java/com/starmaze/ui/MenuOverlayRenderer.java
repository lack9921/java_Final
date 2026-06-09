package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class MenuOverlayRenderer {
    private final MenuOverlayDispatcher dispatcher = new MenuOverlayDispatcher();

    public void paint(Graphics2D g, GameState state, MenuButtonCache menuButtons, int width, int height) {
        MenuOverlayMode.from(state.mode())
                .ifPresent(mode -> dispatcher.paint(g, state, menuButtons, width, height, mode));
    }
}
