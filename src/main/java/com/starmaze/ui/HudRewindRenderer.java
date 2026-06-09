package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HudRewindRenderer {
    private static final Color MUTED = VisualConfig.MUTED;
    private static final Color REWIND_STROKE = new Color(255, 255, 255, 110);

    public void paint(Graphics2D g, int x, int y, int charges) {
        g.setFont(UiFonts.of(Font.PLAIN, 12));
        g.setColor(MUTED);
        g.drawString(HudText.REWIND_LABEL, x, y + 4);
        for (int i = 0; i < GameState.REWIND_MAX; i++) {
            HudRewindDot dot = HudRewindDot.from(x, y, i, charges);
            g.setColor(dot.fillColor());
            g.fillOval(dot.x(), dot.y(), dot.size(), dot.size());
            g.setColor(REWIND_STROKE);
            g.drawOval(dot.x(), dot.y(), dot.size(), dot.size());
        }
    }
}
