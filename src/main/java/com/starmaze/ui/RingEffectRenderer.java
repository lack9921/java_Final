package com.starmaze.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public final class RingEffectRenderer {
    public void paint(Graphics2D g, int cx, int cy, double t, Color color, int tileSize, double scale) {
        RingEffectStyle style = RingEffectStyle.fromProgress(t, tileSize, scale);
        if (!style.visible()) {
            return;
        }
        int radius = style.radius();
        g.setStroke(new BasicStroke(style.strokeWidth()));
        g.setColor(style.strokeColor(color));
        g.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);
        g.setColor(style.fillColor(color));
        g.fillOval(cx - radius / 2, cy - radius / 2, radius, radius);
    }
}
