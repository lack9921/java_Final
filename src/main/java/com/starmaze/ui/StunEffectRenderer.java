package com.starmaze.ui;

import java.awt.Graphics2D;
import java.awt.Point;

public final class StunEffectRenderer {
    public void paint(Graphics2D g, int cx, int cy, double t, int tileSize, int renderFrame) {
        StunEffectStyle style = StunEffectStyle.fromProgress(t, tileSize);
        if (!style.visible()) {
            return;
        }
        g.setColor(style.dotColor());
        for (int i = 0; i < style.dotCount(); i++) {
            Point dot = style.dotCenter(cx, cy, renderFrame, i);
            int radius = style.dotRadius();
            g.fillOval(dot.x - radius, dot.y - radius, radius * 2, radius * 2);
        }
    }
}
