package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class TitleMenuContentRenderer {
    public void paint(Graphics2D g, GameState state, int centerX, int top) {
        for (TitleMenuContentLine line : TitleMenuContentSpec.STANDARD.lines()) {
            g.setFont(UiFonts.of(line.fontStyle(), line.fontSize()));
            g.setColor(line.color());
            drawCentered(g, line.text(state), centerX, top + line.baselineOffset());
        }
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
