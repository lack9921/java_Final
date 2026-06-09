package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class GameViewRenderer {
    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();
    private final GameplayLayerRenderer gameplayLayerRenderer = new GameplayLayerRenderer();
    private final MenuOverlayRenderer menuOverlayRenderer = new MenuOverlayRenderer();

    public void paint(Graphics2D g, GameState state, EffectLayer effectLayer, MenuButtonCache menuButtons,
                      int width, int height, int frame) {
        backgroundRenderer.paint(g, width, height, state.tick());
        if (state.level() != null) {
            gameplayLayerRenderer.paint(g, state, effectLayer, width, height, frame);
        }
        menuOverlayRenderer.paint(g, state, menuButtons, width, height);
    }

    public int boardCacheBuilds() {
        return gameplayLayerRenderer.boardCacheBuilds();
    }

}
