package com.starmaze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class HudMessageRenderer {
    private static final Color MUTED = VisualConfig.MUTED;
    private static final int MESSAGE_LEFT_GUARD = 850;
    private static final int RIGHT_MESSAGE_MARGIN = 46;
    private static final int MESSAGE_FONT_SIZE = 13;

    public void paint(Graphics2D g, String message, int width, int baselineY) {
        g.setFont(UiFonts.of(Font.PLAIN, MESSAGE_FONT_SIZE));
        g.setColor(MUTED);
        int rightX = width - RIGHT_MESSAGE_MARGIN;
        drawRight(g, fitMessage(g, message, rightX), rightX, baselineY);
    }

    private String fitMessage(Graphics2D g, String message, int rightX) {
        int available = rightX - MESSAGE_LEFT_GUARD;
        if (available <= 0) {
            return HudText.COMPACT_HINT;
        }
        if (g.getFontMetrics().stringWidth(message) <= available) {
            return message;
        }
        if (g.getFontMetrics().stringWidth(HudText.COMPACT_HINT) <= available) {
            return HudText.COMPACT_HINT;
        }
        return "";
    }

    private void drawRight(Graphics2D g, String text, int rightX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, rightX - metrics.stringWidth(text), baselineY);
    }
}
