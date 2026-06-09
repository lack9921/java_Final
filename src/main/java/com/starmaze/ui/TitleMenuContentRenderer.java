package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class TitleMenuContentRenderer {
    private static final Color CYAN = VisualConfig.CYAN;
    private static final Color INK = VisualConfig.INK;
    private static final Color MUTED = VisualConfig.MUTED;
    private static final int TITLE_FONT_SIZE = 58;
    private static final int TITLE_SUBTITLE_FONT_SIZE = 20;
    private static final int TITLE_HINT_FONT_SIZE = 15;
    private static final int TITLE_STATS_FONT_SIZE = 13;
    private static final int TITLE_BASELINE_OFFSET = 16;
    private static final int TITLE_SUBTITLE_BASELINE_OFFSET = 54;
    private static final int TITLE_HINT_BASELINE_OFFSET = 86;
    private static final int TITLE_STATS_BASELINE_OFFSET = 310;

    public void paint(Graphics2D g, GameState state, int centerX, int top) {
        g.setFont(UiFonts.of(Font.BOLD, TITLE_FONT_SIZE));
        g.setColor(CYAN);
        drawCentered(g, MenuText.TITLE, centerX, top + TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.BOLD, TITLE_SUBTITLE_FONT_SIZE));
        g.setColor(INK);
        drawCentered(g, MenuText.SUBTITLE, centerX, top + TITLE_SUBTITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, TITLE_HINT_FONT_SIZE));
        g.setColor(MUTED);
        drawCentered(g, MenuText.TITLE_HINT, centerX, top + TITLE_HINT_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, TITLE_STATS_FONT_SIZE));
        g.setColor(MUTED);
        drawCentered(g, MenuText.HIGH_SCORE + state.saveData().highScore()
                + MenuText.GAMES_PLAYED + state.saveData().gamesPlayed()
                + MenuText.VICTORIES + state.saveData().victories(), centerX, top + TITLE_STATS_BASELINE_OFFSET);
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
