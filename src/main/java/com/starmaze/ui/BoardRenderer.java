package com.starmaze.ui;

import com.starmaze.model.Level;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class BoardRenderer {
    private final BoardMetricsCalculator metricsCalculator = new BoardMetricsCalculator();
    private final BoardFrameRenderer frameRenderer = new BoardFrameRenderer();
    private final BoardRiftRenderer riftRenderer = new BoardRiftRenderer();
    private final BoardStaticCacheRenderer staticCacheRenderer = new BoardStaticCacheRenderer();

    public BoardMetrics computeMetrics(Level level, int panelWidth, int panelHeight) {
        return metricsCalculator.compute(level, panelWidth, panelHeight);
    }

    public BufferedImage buildStaticCache(Level level, BoardMetrics metrics) {
        return staticCacheRenderer.build(level, metrics);
    }

    public void paintFrame(Graphics2D g, BoardMetrics metrics) {
        frameRenderer.paint(g, metrics);
    }

    public void paintRifts(Graphics2D g, Level level, BoardMetrics metrics, int tick) {
        riftRenderer.paint(g, level, metrics, tick);
    }
}
