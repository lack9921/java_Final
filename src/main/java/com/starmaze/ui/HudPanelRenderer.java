package com.starmaze.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public final class HudPanelRenderer {
    private static final Color PANEL_FILL = new Color(10, 18, 35, 190);
    private static final Color PANEL_STROKE = new Color(108, 229, 232, 105);
    private static final int PANEL_RADIUS = 14;

    public void paint(Graphics2D g, int width) {
        int panelWidth = width - VisualConfig.HUD_X * 2;
        g.setColor(PANEL_FILL);
        g.fillRoundRect(VisualConfig.HUD_X, VisualConfig.HUD_Y, panelWidth, VisualConfig.HUD_HEIGHT, PANEL_RADIUS,
                PANEL_RADIUS);
        g.setColor(PANEL_STROKE);
        g.drawRoundRect(VisualConfig.HUD_X, VisualConfig.HUD_Y, panelWidth, VisualConfig.HUD_HEIGHT, PANEL_RADIUS,
                PANEL_RADIUS);
    }
}
