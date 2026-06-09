package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HudRewindRenderer {
    private static final Color GOLD = VisualConfig.GOLD;
    private static final Color MUTED = VisualConfig.MUTED;
    private static final Color REWIND_EMPTY = new Color(55, 65, 82);
    private static final Color REWIND_STROKE = new Color(255, 255, 255, 110);
    private static final int REWIND_DOT_GAP = 20;
    private static final int REWIND_DOT_SIZE = 13;
    private static final int REWIND_DOT_X_OFFSET = 38;
    private static final int REWIND_DOT_Y_OFFSET = 9;

    public void paint(Graphics2D g, int x, int y, int charges) {
        g.setFont(UiFonts.of(Font.PLAIN, 12));
        g.setColor(MUTED);
        g.drawString(HudText.REWIND_LABEL, x, y + 4);
        for (int i = 0; i < GameState.REWIND_MAX; i++) {
            int dotX = x + REWIND_DOT_X_OFFSET + i * REWIND_DOT_GAP;
            g.setColor(i < charges ? GOLD : REWIND_EMPTY);
            g.fillOval(dotX, y - REWIND_DOT_Y_OFFSET, REWIND_DOT_SIZE, REWIND_DOT_SIZE);
            g.setColor(REWIND_STROKE);
            g.drawOval(dotX, y - REWIND_DOT_Y_OFFSET, REWIND_DOT_SIZE, REWIND_DOT_SIZE);
        }
    }
}
