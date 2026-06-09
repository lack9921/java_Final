package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HudStatsRenderer {
    private static final Color INK = VisualConfig.INK;

    public void paint(Graphics2D g, GameState state, int baselineY) {
        HudStatItem title = HudStatsSpec.titleItem();
        g.setFont(UiFonts.of(Font.BOLD, HudStatsSpec.TITLE_FONT_SIZE));
        g.setColor(INK);
        drawString(g, title.text(state), title.x(), baselineY);
        g.setFont(UiFonts.of(Font.BOLD, HudStatsSpec.STAT_FONT_SIZE));
        for (HudStatItem item : HudStatsSpec.statItems()) {
            drawString(g, item.text(state), item.x(), baselineY);
        }
    }

    private void drawString(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
    }
}
