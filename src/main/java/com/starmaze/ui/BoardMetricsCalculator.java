package com.starmaze.ui;

import com.starmaze.model.Level;

public final class BoardMetricsCalculator {
    public BoardMetrics compute(Level level, int panelWidth, int panelHeight) {
        int availableW = panelWidth - VisualConfig.BOARD_HORIZONTAL_MARGIN;
        int availableH = panelHeight - VisualConfig.BOARD_VERTICAL_RESERVED;
        int tileSize = Math.max(VisualConfig.TILE_MIN_SIZE,
                Math.min(VisualConfig.TILE_MAX_SIZE, Math.min(availableW / level.width(), availableH / level.height())));
        int boardW = level.width() * tileSize;
        int boardH = level.height() * tileSize;
        int x = (panelWidth - boardW) / 2;
        int y = VisualConfig.BOARD_TOP_BASELINE + Math.max(0, (availableH - boardH) / 2);
        return new BoardMetrics(x, y, boardW, boardH, tileSize);
    }
}
