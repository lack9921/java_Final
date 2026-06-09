package com.starmaze.ui;

import com.starmaze.model.GameMode;

import java.util.ArrayList;
import java.util.List;

public final class MenuLayout {
    public List<MenuButton> layout(GameMode mode, int width, int height) {
        List<MenuButton> buttons = new ArrayList<>();
        int centerX = MenuMetrics.centerX(width);
        int titleTop = MenuMetrics.titleTop(height);
        switch (mode) {
            case TITLE -> {
                add(buttons, MenuActions.START, centerX - MenuMetrics.TITLE_BUTTON_W / 2,
                        titleTop + MenuMetrics.TITLE_START_Y, MenuMetrics.TITLE_BUTTON_W,
                        MenuMetrics.TITLE_PRIMARY_BUTTON_H);
                add(buttons, MenuActions.HELP, centerX - MenuMetrics.TITLE_BUTTON_W / 2,
                        titleTop + MenuMetrics.TITLE_HELP_Y, MenuMetrics.TITLE_BUTTON_W,
                        MenuMetrics.TITLE_BUTTON_H);
                add(buttons, MenuActions.SETTINGS, centerX - MenuMetrics.TITLE_BUTTON_W / 2,
                        titleTop + MenuMetrics.TITLE_SETTINGS_Y, MenuMetrics.TITLE_BUTTON_W,
                        MenuMetrics.TITLE_BUTTON_H);
            }
            case PAUSED -> {
                MenuPanel panel = MenuMetrics.pausePanel(width, height);
                add(buttons, MenuActions.RESUME, panel.x() + MenuMetrics.PAUSE_BUTTON_X,
                        panel.y() + MenuMetrics.PAUSE_RESUME_Y, MenuMetrics.SMALL_BUTTON_W, MenuMetrics.BUTTON_H);
                add(buttons, MenuActions.RESTART_LEVEL, panel.x() + MenuMetrics.PAUSE_BUTTON_X,
                        panel.y() + MenuMetrics.PAUSE_RESTART_Y, MenuMetrics.SMALL_BUTTON_W, MenuMetrics.BUTTON_H);
                add(buttons, MenuActions.TITLE, panel.x() + MenuMetrics.PAUSE_BUTTON_X,
                        panel.y() + MenuMetrics.PAUSE_TITLE_Y, MenuMetrics.SMALL_BUTTON_W, MenuMetrics.BUTTON_H);
            }
            case HELP -> {
                MenuPanel panel = MenuMetrics.helpPanel(width, height);
                add(buttons, MenuActions.TITLE, panel.centerX() - MenuMetrics.MEDIUM_BUTTON_W / 2,
                        panel.y() + panel.h() - MenuMetrics.HELP_TITLE_BOTTOM_OFFSET, MenuMetrics.MEDIUM_BUTTON_W,
                        MenuMetrics.BUTTON_H);
            }
            case SETTINGS -> {
                MenuPanel panel = MenuMetrics.settingsPanel(width, height);
                add(buttons, MenuActions.SOUND, panel.x() + MenuMetrics.SETTINGS_BUTTON_X,
                        panel.y() + MenuMetrics.SETTINGS_SOUND_Y, MenuMetrics.SMALL_BUTTON_W, MenuMetrics.BUTTON_H);
                add(buttons, MenuActions.TITLE, panel.x() + MenuMetrics.SETTINGS_BUTTON_X,
                        panel.y() + MenuMetrics.SETTINGS_TITLE_Y, MenuMetrics.SMALL_BUTTON_W, MenuMetrics.BUTTON_H);
            }
            case LEVEL_CLEAR -> {
                MenuPanel panel = MenuMetrics.levelClearPanel(width, height);
                add(buttons, MenuActions.NEXT, panel.x() + MenuMetrics.RESULT_BUTTON_X,
                        panel.y() + MenuMetrics.RESULT_NEXT_Y, MenuMetrics.LARGE_BUTTON_W, MenuMetrics.BUTTON_H);
                add(buttons, MenuActions.TITLE, panel.x() + MenuMetrics.RESULT_BUTTON_X,
                        panel.y() + MenuMetrics.RESULT_TITLE_Y, MenuMetrics.LARGE_BUTTON_W, MenuMetrics.BUTTON_H);
            }
            case GAME_OVER -> {
                MenuPanel panel = MenuMetrics.gameOverPanel(width, height);
                add(buttons, MenuActions.START, panel.x() + MenuMetrics.GAME_OVER_BUTTON_X,
                        panel.y() + MenuMetrics.GAME_OVER_START_Y, MenuMetrics.LARGE_BUTTON_W, MenuMetrics.BUTTON_H);
                add(buttons, MenuActions.TITLE, panel.x() + MenuMetrics.GAME_OVER_BUTTON_X,
                        panel.y() + MenuMetrics.GAME_OVER_TITLE_Y, MenuMetrics.LARGE_BUTTON_W, MenuMetrics.BUTTON_H);
            }
            case PLAYING -> {
            }
        }
        return buttons;
    }

    private void add(List<MenuButton> buttons, String action, int x, int y, int w, int h) {
        buttons.add(new MenuButton(action, x, y, w, h));
    }
}
