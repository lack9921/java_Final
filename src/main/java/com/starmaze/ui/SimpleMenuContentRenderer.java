package com.starmaze.ui;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class SimpleMenuContentRenderer {
    public void paintPause(Graphics2D g, MenuPanel panel) {
        paint(g, panel, SimpleMenuContentSpec.PAUSE);
    }

    public void paintSettings(Graphics2D g, MenuPanel panel) {
        paint(g, panel, SimpleMenuContentSpec.SETTINGS);
    }

    private void paint(Graphics2D g, MenuPanel panel, SimpleMenuContentSpec spec) {
        for (SimpleMenuContentLine line : spec.lines()) {
            g.setFont(UiFonts.of(line.fontStyle(), line.fontSize()));
            g.setColor(line.color());
            drawCentered(g, line.text(), panel.centerX(), panel.y() + line.baselineOffset());
        }
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
