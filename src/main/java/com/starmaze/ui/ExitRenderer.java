package com.starmaze.ui;

import com.starmaze.model.GameState;
import com.starmaze.model.Position;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;

public final class ExitRenderer {
    private static final String LOCKED_EXIT_TEXT = "\uD83D\uDD12";
    private static final float EXIT_STROKE_WIDTH = 2.4f;
    private static final int EXIT_RADIUS_INSET = 4;
    private static final int LOCK_FONT_MIN_SIZE = 11;
    private static final int LOCK_FONT_DIVISOR = 3;
    private static final int LOCK_BASELINE_DIVISOR = 8;

    public void paint(Graphics2D g, GameState state, BoardMetrics metrics) {
        Position p = state.level().exit();
        int tileSize = metrics.tileSize();
        int cx = metrics.centerX(p);
        int cy = metrics.centerY(p);
        int r = tileSize / 2 - EXIT_RADIUS_INSET;
        ExitVisualStyle style = ExitVisualStyle.forOpenState(state.isExitOpen());
        g.setStroke(new BasicStroke(EXIT_STROKE_WIDTH));
        g.setColor(style.fillColor());
        g.fillOval(cx - r, cy - r, r * 2, r * 2);
        g.setColor(style.ringColor());
        g.drawOval(cx - r, cy - r, r * 2, r * 2);
        g.drawOval(cx - r / 2, cy - r / 2, r, r);
        if (style.lockVisible()) {
            g.setFont(UiFonts.of(Font.BOLD, Math.max(LOCK_FONT_MIN_SIZE, tileSize / LOCK_FONT_DIVISOR)));
            drawCentered(g, LOCKED_EXIT_TEXT, cx, cy + tileSize / LOCK_BASELINE_DIVISOR);
        }
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        g.drawString(text, centerX - g.getFontMetrics().stringWidth(text) / 2, baselineY);
    }
}
