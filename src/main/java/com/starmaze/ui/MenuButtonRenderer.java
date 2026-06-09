package com.starmaze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

public final class MenuButtonRenderer {
    private static final Color INK = VisualConfig.INK;
    private static final Color BUTTON_FILL = new Color(30, 56, 78, 235);
    private static final Color BUTTON_STROKE = new Color(95, 224, 232, 145);
    private static final int BUTTON_RADIUS = 12;
    private static final int BUTTON_FONT_SIZE = 15;
    private static final int BUTTON_TEXT_BASELINE_OFFSET = 5;

    public void paint(Graphics2D g, List<MenuButton> buttons, List<String> labels) {
        for (int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);
            paintButton(g, button.x(), button.y(), button.w(), button.h(), labels.get(i));
        }
    }

    private void paintButton(Graphics2D g, int x, int y, int w, int h, String text) {
        g.setColor(BUTTON_FILL);
        g.fillRoundRect(x, y, w, h, BUTTON_RADIUS, BUTTON_RADIUS);
        g.setColor(BUTTON_STROKE);
        g.drawRoundRect(x, y, w, h, BUTTON_RADIUS, BUTTON_RADIUS);
        g.setFont(UiFonts.of(Font.BOLD, BUTTON_FONT_SIZE));
        g.setColor(INK);
        drawCentered(g, text, x + w / 2, y + h / 2 + BUTTON_TEXT_BASELINE_OFFSET);
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
