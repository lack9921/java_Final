package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;
import java.util.List;

public enum MenuOverlaySpec {
    TITLE {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintTitle(g, state, width, height, buttons);
        }
    },
    PAUSED {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintPause(g, state, width, height, buttons);
        }
    },
    HELP {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintHelp(g, state, width, height, buttons);
        }
    },
    SETTINGS {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintSettings(g, state, width, height, buttons);
        }
    },
    LEVEL_CLEAR {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintLevelClear(g, state, width, height, buttons);
        }
    },
    GAME_OVER {
        @Override
        public void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                          List<MenuButton> buttons) {
            renderer.paintGameOver(g, state, width, height, buttons);
        }
    };

    public static MenuOverlaySpec forMode(MenuOverlayMode mode) {
        return valueOf(mode.name());
    }

    public abstract void paint(MenuRenderer renderer, Graphics2D g, GameState state, int width, int height,
                               List<MenuButton> buttons);
}
