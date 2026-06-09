package com.starmaze.ui;

import com.starmaze.model.GameMode;

import java.util.Collections;
import java.util.List;

public final class MenuButtonCache {
    private final MenuLayout layout = new MenuLayout();
    private List<MenuButton> buttons = Collections.emptyList();
    private GameMode lastMode;
    private int lastWidth = -1;
    private int lastHeight = -1;

    public List<MenuButton> update(GameMode mode, int width, int height) {
        if (mode == lastMode && width == lastWidth && height == lastHeight) {
            return buttons;
        }
        buttons = layout.layout(mode, width, height);
        lastMode = mode;
        lastWidth = width;
        lastHeight = height;
        return buttons;
    }

    public List<MenuButton> buttons() {
        return buttons;
    }

    public MenuButton hit(int x, int y) {
        for (MenuButton button : buttons) {
            if (button.contains(x, y)) {
                return button;
            }
        }
        return null;
    }
}
