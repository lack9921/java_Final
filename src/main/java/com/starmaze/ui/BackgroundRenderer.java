package com.starmaze.ui;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public final class BackgroundRenderer {
    private final StarField starField = new StarField(VisualConfig.BACKGROUND_STAR_COUNT);
    private final BackgroundGridSpec gridSpec = BackgroundGridSpec.standard();

    public void paint(Graphics2D g, int width, int height, int tick) {
        g.setPaint(new GradientPaint(0, 0, VisualConfig.BACK_TOP, 0, height, VisualConfig.BACK_BOTTOM));
        g.fillRect(0, 0, width, height);
        starField.paint(g, width, height, tick);
        g.setStroke(new BasicStroke(gridSpec.strokeWidth()));
        g.setColor(gridSpec.color());
        for (int x : gridSpec.verticalLines(width)) {
            g.drawLine(x, 0, x, height);
        }
        for (int y : gridSpec.horizontalLines(height)) {
            g.drawLine(0, y, width, y);
        }
    }
}
