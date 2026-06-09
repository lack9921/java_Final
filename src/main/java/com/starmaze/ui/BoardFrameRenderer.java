package com.starmaze.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public final class BoardFrameRenderer {
    private static final Color FRAME_FILL = new Color(6, 12, 24, 170);
    private static final Color FRAME_STROKE = new Color(102, 211, 223, 105);
    private static final int FRAME_PAD = 14;
    private static final int FRAME_RADIUS = 18;

    public void paint(Graphics2D g, BoardMetrics metrics) {
        int x = metrics.x() - FRAME_PAD;
        int y = metrics.y() - FRAME_PAD;
        int w = metrics.width() + FRAME_PAD * 2;
        int h = metrics.height() + FRAME_PAD * 2;
        g.setColor(FRAME_FILL);
        g.fillRoundRect(x, y, w, h, FRAME_RADIUS, FRAME_RADIUS);
        g.setColor(FRAME_STROKE);
        g.drawRoundRect(x, y, w, h, FRAME_RADIUS, FRAME_RADIUS);
    }
}
