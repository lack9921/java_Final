package com.starmaze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HelpMenuContentRenderer {
    private static final Color CYAN = VisualConfig.CYAN;
    private static final Color INK = VisualConfig.INK;
    private static final Color HELP_DOT = UiColors.withAlpha(CYAN, 140);
    private static final int PANEL_TITLE_FONT_SIZE = 30;
    private static final int HELP_FONT_SIZE = 16;
    private static final int HELP_TITLE_X_OFFSET = 34;
    private static final int HELP_TITLE_BASELINE_OFFSET = 52;
    private static final int HELP_FIRST_LINE_BASELINE_OFFSET = 94;
    private static final int HELP_LINE_X_OFFSET = 38;
    private static final int HELP_DOT_SIZE = 8;
    private static final int HELP_DOT_BASELINE_OFFSET = 12;
    private static final int HELP_TEXT_X_OFFSET = 20;
    private static final int HELP_LINE_GAP = 42;

    public void paint(Graphics2D g, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, PANEL_TITLE_FONT_SIZE));
        g.setColor(CYAN);
        g.drawString(MenuText.HELP_TITLE, panel.x() + HELP_TITLE_X_OFFSET, panel.y() + HELP_TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, HELP_FONT_SIZE));
        g.setColor(INK);
        int line = panel.y() + HELP_FIRST_LINE_BASELINE_OFFSET;
        for (String helpText : MenuText.HELP_LINES) {
            line = helpLine(g, panel.x() + HELP_LINE_X_OFFSET, line, helpText);
        }
    }

    private int helpLine(Graphics2D g, int x, int y, String text) {
        g.setColor(HELP_DOT);
        g.fillOval(x, y - HELP_DOT_BASELINE_OFFSET, HELP_DOT_SIZE, HELP_DOT_SIZE);
        g.setColor(INK);
        g.drawString(text, x + HELP_TEXT_X_OFFSET, y);
        return y + HELP_LINE_GAP;
    }
}
