package com.starmaze.ui;

import com.starmaze.model.GameState;

import javax.swing.JComponent;

public final class GamePanelInputBinder {
    private final MenuActionHandler menuActionHandler = new MenuActionHandler();

    public void install(GameState state, JComponent component, MenuButtonCache menuButtons,
                        Runnable focusRequester, Runnable repaint) {
        new InputController(state, component, repaint).install();
        new MenuMouseController(state, component, menuButtons, menuActionHandler, focusRequester, repaint).install();
    }
}
