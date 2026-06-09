package com.starmaze.ui;

import com.starmaze.model.Level;

public record BoardCacheKey(Level level, BoardMetrics metrics) {
    public static BoardCacheKey from(Level level, BoardMetrics metrics) {
        return new BoardCacheKey(level, metrics);
    }

    public boolean matches(Level nextLevel, BoardMetrics nextMetrics) {
        return level == nextLevel && metrics.equals(nextMetrics);
    }
}
