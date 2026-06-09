package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class EntityLayerRenderer {
    private final EntityRenderer entityRenderer = new EntityRenderer();

    public void paint(Graphics2D g, GameState state, BoardMetrics metrics) {
        for (EntityRenderOrder layer : EntityRenderOrder.gameplayOrder()) {
            paintLayer(g, state, metrics, layer);
        }
    }

    private void paintLayer(Graphics2D g, GameState state, BoardMetrics metrics, EntityRenderOrder layer) {
        switch (layer) {
            case PICKUPS -> entityRenderer.paintPickups(g, state, metrics);
            case EXIT -> entityRenderer.paintExit(g, state, metrics);
            case ENEMIES -> entityRenderer.paintEnemies(g, state.enemies(), metrics);
            case PLAYER -> entityRenderer.paintPlayer(g, state, metrics);
        }
    }
}
