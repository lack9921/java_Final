package com.starmaze.ui;

import java.awt.Font;
import java.awt.Graphics2D;

public final class FooterRenderer {
    private static final String HELP_TEXT = "WASD/方向键移动   Space 相位穿墙   R 轨迹回溯   M 音效   H 帮助   Esc/P 暂停";
    private static final int FONT_SIZE = 13;
    private static final int BASELINE_BOTTOM_MARGIN = 28;

    public void paint(Graphics2D g, int width, int height) {
        g.setFont(UiFonts.of(Font.PLAIN, FONT_SIZE));
        g.setColor(VisualConfig.MUTED);
        int baseline = height - BASELINE_BOTTOM_MARGIN;
        g.drawString(HELP_TEXT, width / 2 - g.getFontMetrics().stringWidth(HELP_TEXT) / 2, baseline);
    }
}
