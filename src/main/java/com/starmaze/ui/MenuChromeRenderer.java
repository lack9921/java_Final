package com.starmaze.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public final class MenuChromeRenderer {
    private static final Color OVERLAY = new Color(3, 8, 18);
    private static final Color PANEL_FILL = new Color(12, 22, 38, 232);
    private static final Color PANEL_STROKE = new Color(93, 211, 225, 118);
    private static final int PANEL_RADIUS = 20;
    private static final float PANEL_STROKE_WIDTH = 1.5f;

    public void paintOverlay(Graphics2D g, int width, int height, float alpha) {
        g.setColor(UiColors.withAlpha(OVERLAY, Math.round(alpha * 255)));
        g.fillRect(0, 0, width, height);
    }

    public void paintPanel(Graphics2D g, MenuPanel panel) {
        g.setColor(PANEL_FILL);
        g.fillRoundRect(panel.x(), panel.y(), panel.w(), panel.h(), PANEL_RADIUS, PANEL_RADIUS);
        g.setColor(PANEL_STROKE);
        g.setStroke(new BasicStroke(PANEL_STROKE_WIDTH));
        g.drawRoundRect(panel.x(), panel.y(), panel.w(), panel.h(), PANEL_RADIUS, PANEL_RADIUS);
    }
}
