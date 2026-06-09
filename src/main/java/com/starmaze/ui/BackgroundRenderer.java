package com.starmaze.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public final class BackgroundRenderer {
    private static final Color GRID_COLOR = new Color(64, 103, 130, 45);
    private static final float GRID_STROKE_WIDTH = 1f;

    private final StarField starField = new StarField(VisualConfig.BACKGROUND_STAR_COUNT);

    public void paint(Graphics2D g, int width, int height, int tick) {
        g.setPaint(new GradientPaint(0, 0, VisualConfig.BACK_TOP, 0, height, VisualConfig.BACK_BOTTOM));
        g.fillRect(0, 0, width, height);
        g.setStroke(new BasicStroke(GRID_STROKE_WIDTH));
        starField.paint(g, width, height, tick);
        g.setColor(GRID_COLOR);
        for (int x = 0; x < width; x += VisualConfig.BACKGROUND_GRID_SIZE) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y < height; y += VisualConfig.BACKGROUND_GRID_SIZE) {
            g.drawLine(0, y, width, y);
        }
    }
}
