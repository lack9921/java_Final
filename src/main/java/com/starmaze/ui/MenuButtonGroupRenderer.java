package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public final class MenuButtonGroupRenderer {
    private final MenuButtonRenderer buttonRenderer = new MenuButtonRenderer();

    public void paint(Graphics2D g, GameState state, List<MenuButton> buttons) {
        List<String> labels = new ArrayList<>(buttons.size());
        for (MenuButton button : buttons) {
            labels.add(MenuLabels.forAction(state, button.action()));
        }
        buttonRenderer.paint(g, buttons, labels);
    }
}
