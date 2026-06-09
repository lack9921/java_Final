package com.starmaze.ui;

import com.starmaze.model.GameState;
import com.starmaze.model.Level;

import java.awt.Graphics2D;

public final class GameSceneRenderer {
    private final BoardLayerRenderer boardLayerRenderer = new BoardLayerRenderer();
    private final EntityLayerRenderer entityLayerRenderer = new EntityLayerRenderer();

    public BoardMetrics paint(Graphics2D g, GameState state, int width, int height) {
        Level level = state.level();
        if (level == null) {
            return null;
        }
        BoardMetrics metrics = boardLayerRenderer.paintBaseLayer(g, level, width, height);
        boardLayerRenderer.paintDynamicTiles(g, level, metrics, state.tick());
        entityLayerRenderer.paint(g, state, metrics);
        return metrics;
    }

    public int boardCacheBuilds() {
        return boardLayerRenderer.cacheBuilds();
    }
}
