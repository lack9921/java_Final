package com.starmaze.ui;

import com.starmaze.model.Level;
import com.starmaze.model.TileType;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class BoardStaticCacheRenderer {
    private final BoardTileRenderer tileRenderer = new BoardTileRenderer();

    public BufferedImage build(Level level, BoardMetrics metrics) {
        BufferedImage cache = new BufferedImage(metrics.width(), metrics.height(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = cache.createGraphics();
        RenderQuality.apply(g);
        for (int y = 0; y < level.height(); y++) {
            for (int x = 0; x < level.width(); x++) {
                TileType tile = level.tileAt(x, y);
                tileRenderer.paint(g, x, y, BoardStaticTileMapper.cacheTile(tile), 0, 0, metrics.tileSize(), 0);
            }
        }
        g.dispose();
        return cache;
    }
}
