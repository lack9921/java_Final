package com.starmaze.ui;

import com.starmaze.model.Level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class BoardLayerRenderer {
    private final BoardRenderer boardRenderer = new BoardRenderer();
    private BoardCacheKey cacheKey;
    private BufferedImage boardCache;
    private int cacheBuilds;

    public BoardMetrics paintBaseLayer(Graphics2D g, Level level, int width, int height) {
        BoardMetrics metrics = boardRenderer.computeMetrics(level, width, height);
        ensureBoardCache(level, metrics);
        boardRenderer.paintFrame(g, metrics);
        if (boardCache != null) {
            g.drawImage(boardCache, metrics.x(), metrics.y(), null);
        }
        return metrics;
    }

    public void paintDynamicTiles(Graphics2D g, Level level, BoardMetrics metrics, int tick) {
        boardRenderer.paintRifts(g, level, metrics, tick);
    }

    public int cacheBuilds() {
        return cacheBuilds;
    }

    private void ensureBoardCache(Level level, BoardMetrics metrics) {
        if (boardCache != null && cacheKey != null && cacheKey.matches(level, metrics)) {
            return;
        }
        cacheKey = BoardCacheKey.from(level, metrics);
        boardCache = boardRenderer.buildStaticCache(level, metrics);
        cacheBuilds++;
    }
}
