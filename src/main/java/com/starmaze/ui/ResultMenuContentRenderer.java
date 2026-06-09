package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public final class ResultMenuContentRenderer {
    private static final Color INK = VisualConfig.INK;
    private static final Color MINT = VisualConfig.MINT;
    private static final Color MUTED = VisualConfig.MUTED;
    private static final Color PINK = VisualConfig.PINK;
    private static final int RESULT_TITLE_FONT_SIZE = 31;
    private static final int BODY_FONT_SIZE = 15;
    private static final int RESULT_TITLE_BASELINE_OFFSET = 58;
    private static final int RESULT_SUMMARY_BASELINE_OFFSET = 94;
    private static final int RESULT_RANK_BASELINE_OFFSET = 124;

    public void paintLevelClear(Graphics2D g, GameState state, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, RESULT_TITLE_FONT_SIZE));
        g.setColor(MINT);
        drawCentered(g, MenuText.LEVEL_CLEAR_TITLE, panel.centerX(), panel.y() + RESULT_TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, BODY_FONT_SIZE));
        g.setColor(INK);
        drawCentered(g, MenuText.levelClearSummary(state.levelIndex(), state.score()), panel.centerX(),
                panel.y() + RESULT_SUMMARY_BASELINE_OFFSET);
        g.setColor(MUTED);
        drawCentered(g, MenuText.RANK_PREFIX + "★".repeat(state.runRank()) + "☆".repeat(5 - state.runRank()),
                panel.centerX(), panel.y() + RESULT_RANK_BASELINE_OFFSET);
    }

    public void paintGameOver(Graphics2D g, GameState state, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, RESULT_TITLE_FONT_SIZE));
        g.setColor(PINK);
        drawCentered(g, MenuText.GAME_OVER_TITLE, panel.centerX(), panel.y() + RESULT_TITLE_BASELINE_OFFSET);
        g.setFont(UiFonts.of(Font.PLAIN, BODY_FONT_SIZE));
        g.setColor(INK);
        drawCentered(g, state.message(), panel.centerX(), panel.y() + RESULT_SUMMARY_BASELINE_OFFSET);
        g.setColor(MUTED);
        drawCentered(g, MenuText.FINAL_SCORE + state.score() + MenuText.BEST_SCORE + state.saveData().highScore(),
                panel.centerX(), panel.y() + RESULT_RANK_BASELINE_OFFSET);
    }

    private void drawCentered(Graphics2D g, String text, int centerX, int baselineY) {
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(text, centerX - metrics.stringWidth(text) / 2, baselineY);
    }
}
