package com.starmaze.ui;

import com.starmaze.model.Level;
import com.starmaze.model.TileType;

import java.awt.Graphics2D;

public final class BoardRiftRenderer {
    private final BoardTileRenderer tileRenderer = new BoardTileRenderer();

    public void paint(Graphics2D g, Level level, BoardMetrics metrics, int tick) {
        for (int y = 0; y < level.height(); y++) {
            for (int x = 0; x < level.width(); x++) {
                if (level.tileAt(x, y) == TileType.RIFT) {
                    tileRenderer.paint(g, x, y, TileType.RIFT, metrics.x(), metrics.y(), metrics.tileSize(), tick);
                }
            }
        }
    }
}
