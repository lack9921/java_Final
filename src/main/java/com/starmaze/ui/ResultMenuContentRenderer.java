package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class ResultMenuContentRenderer {
    public void paintLevelClear(Graphics2D g, GameState state, MenuPanel panel) {
        paint(g, state, panel, ResultMenuContentSpec.LEVEL_CLEAR);
    }

    public void paintGameOver(Graphics2D g, GameState state, MenuPanel panel) {
        paint(g, state, panel, ResultMenuContentSpec.GAME_OVER);
    }

    private void paint(Graphics2D g, GameState state, MenuPanel panel, ResultMenuContentSpec spec) {
        for (ResultMenuContentLine line : spec.lines()) {
            g.setFont(UiFonts.of(line.fontStyle(), line.fontSize()));
            g.setColor(line.color());
            drawCentered(g, line.text(state), panel.centerX(), panel.y() + line.baselineOffset());
        }
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
