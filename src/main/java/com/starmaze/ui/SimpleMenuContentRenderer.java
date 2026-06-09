package com.starmaze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class SimpleMenuContentRenderer {
    private static final Color CYAN = VisualConfig.CYAN;
    private static final Color INK = VisualConfig.INK;
    private static final Color MUTED = VisualConfig.MUTED;
    private static final int PANEL_TITLE_FONT_SIZE = 30;
    private static final int PAUSE_TITLE_FONT_SIZE = 32;
    private static final int BODY_FONT_SIZE = 15;
    private static final int PAUSE_HINT_FONT_SIZE = 14;
    private static final int PANEL_TITLE_BASELINE_OFFSET = 54;
    private static final int PAUSE_HINT_BASELINE_OFFSET = 84;
    private static final int SETTINGS_HINT_BASELINE_OFFSET = 92;

    public void paintPause(Graphics2D g, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, PAUSE_TITLE_FONT_SIZE));
        g.setColor(INK);
        drawCentered(g, MenuText.PAUSED, panel.centerX(), panel.y() + PANEL_TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, PAUSE_HINT_FONT_SIZE));
        g.setColor(MUTED);
        drawCentered(g, MenuText.PAUSE_HINT, panel.centerX(), panel.y() + PAUSE_HINT_BASELINE_OFFSET);
    }

    public void paintSettings(Graphics2D g, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        g.setColor(CYAN);
        drawCentered(g, MenuText.SETTINGS_TITLE, panel.centerX(), panel.y() + PANEL_TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, BODY_FONT_SIZE));
        g.setColor(MUTED);
        drawCentered(g, MenuText.SETTINGS_SAVE_HINT, panel.centerX(), panel.y() + SETTINGS_HINT_BASELINE_OFFSET);
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
