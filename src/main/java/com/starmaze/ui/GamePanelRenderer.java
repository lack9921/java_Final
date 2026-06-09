package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics;
import java.awt.Graphics2D;

public final class GamePanelRenderer {
    private final GameViewRenderer viewRenderer = new GameViewRenderer();

    public void paint(Graphics graphics, GameState state, EffectLayer effectLayer, MenuButtonCache menuButtons,
                      int width, int height, int frame) {
        Graphics2D g = (Graphics2D) graphics.create();
        RenderQuality.apply(g);
        viewRenderer.paint(g, state, effectLayer, menuButtons, width, height, frame);
        g.dispose();
    }

    public int boardCacheBuilds() {
        return viewRenderer.boardCacheBuilds();
    }
}
