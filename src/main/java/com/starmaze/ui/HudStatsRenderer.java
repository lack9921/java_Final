package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HudStatsRenderer {
    private static final Color INK = VisualConfig.INK;
    private static final int TITLE_X = 46;
    private static final int LEVEL_X = 166;
    private static final int SCORE_X = 246;
    private static final int HIGH_SCORE_X = 338;
    private static final int CRYSTAL_X = 456;
    private static final int TITLE_FONT_SIZE = 18;
    private static final int STAT_FONT_SIZE = 15;

    public void paint(Graphics2D g, GameState state, int baselineY) {
        g.setFont(UiFonts.of(Font.BOLD, TITLE_FONT_SIZE));
        g.setColor(INK);
        drawString(g, HudText.TITLE, TITLE_X, baselineY);
        g.setFont(UiFonts.of(Font.BOLD, STAT_FONT_SIZE));
        drawString(g, HudText.LEVEL_PREFIX + state.levelIndex() + HudText.LEVEL_SUFFIX, LEVEL_X, baselineY);
        drawString(g, HudText.SCORE_PREFIX + state.score(), SCORE_X, baselineY);
        drawString(g, HudText.HIGH_SCORE_PREFIX + state.saveData().highScore(), HIGH_SCORE_X, baselineY);
        drawString(g, HudText.CRYSTAL_PREFIX + state.crystalsCollected() + "/" + state.crystalsNeeded(), CRYSTAL_X,
                baselineY);
    }

    private void drawString(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
    }
}
