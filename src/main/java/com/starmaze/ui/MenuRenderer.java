package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;
import java.util.List;

public final class MenuRenderer {
    private final MenuChromeRenderer chromeRenderer = new MenuChromeRenderer();
    private final MenuButtonGroupRenderer buttonGroupRenderer = new MenuButtonGroupRenderer();
    private final SimpleMenuContentRenderer simpleContentRenderer = new SimpleMenuContentRenderer();
    private final TitleMenuContentRenderer titleContentRenderer = new TitleMenuContentRenderer();
    private final HelpMenuContentRenderer helpContentRenderer = new HelpMenuContentRenderer();
    private final ResultMenuContentRenderer resultContentRenderer = new ResultMenuContentRenderer();

    private static final float TITLE_OVERLAY_ALPHA = 0.72f;
    private static final float PAUSE_OVERLAY_ALPHA = 0.58f;
    private static final float HELP_OVERLAY_ALPHA = 0.76f;
    private static final float SETTINGS_OVERLAY_ALPHA = 0.74f;
    private static final float LEVEL_CLEAR_OVERLAY_ALPHA = 0.62f;
    private static final float GAME_OVER_OVERLAY_ALPHA = 0.68f;

    public void paintTitle(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, TITLE_OVERLAY_ALPHA);
        int centerX = MenuMetrics.centerX(width);
        int top = MenuMetrics.titleTop(height);
        titleContentRenderer.paint(g, state, centerX, top);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintPause(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, PAUSE_OVERLAY_ALPHA);
        MenuPanel panel = MenuMetrics.pausePanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        simpleContentRenderer.paintPause(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintHelp(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, HELP_OVERLAY_ALPHA);
        MenuPanel panel = MenuMetrics.helpPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        helpContentRenderer.paint(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintSettings(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, SETTINGS_OVERLAY_ALPHA);
        MenuPanel panel = MenuMetrics.settingsPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        simpleContentRenderer.paintSettings(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintLevelClear(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, LEVEL_CLEAR_OVERLAY_ALPHA);
        MenuPanel panel = MenuMetrics.levelClearPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        resultContentRenderer.paintLevelClear(g, state, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintGameOver(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, GAME_OVER_OVERLAY_ALPHA);
        MenuPanel panel = MenuMetrics.gameOverPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        resultContentRenderer.paintGameOver(g, state, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

}
