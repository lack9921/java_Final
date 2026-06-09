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

    public void paintTitle(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.TITLE.overlayAlpha());
        int centerX = MenuMetrics.centerX(width);
        int top = MenuMetrics.titleTop(height);
        titleContentRenderer.paint(g, state, centerX, top);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintPause(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.PAUSED.overlayAlpha());
        MenuPanel panel = MenuMetrics.pausePanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        simpleContentRenderer.paintPause(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintHelp(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.HELP.overlayAlpha());
        MenuPanel panel = MenuMetrics.helpPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        helpContentRenderer.paint(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintSettings(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.SETTINGS.overlayAlpha());
        MenuPanel panel = MenuMetrics.settingsPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        simpleContentRenderer.paintSettings(g, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintLevelClear(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.LEVEL_CLEAR.overlayAlpha());
        MenuPanel panel = MenuMetrics.levelClearPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        resultContentRenderer.paintLevelClear(g, state, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

    public void paintGameOver(Graphics2D g, GameState state, int width, int height, List<MenuButton> buttons) {
        chromeRenderer.paintOverlay(g, width, height, MenuOverlayStyle.GAME_OVER.overlayAlpha());
        MenuPanel panel = MenuMetrics.gameOverPanel(width, height);
        chromeRenderer.paintPanel(g, panel);
        resultContentRenderer.paintGameOver(g, state, panel);
        buttonGroupRenderer.paint(g, state, buttons);
    }

}
