package com.starmaze.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public final class HudMeterRenderer {
    private static final Color MUTED = VisualConfig.MUTED;
    private static final Color METER_BACK = new Color(31, 45, 65);
    private static final Color METER_STROKE = new Color(220, 244, 250, 110);
    private static final int METER_LABEL_OFFSET = 34;

    public void paint(Graphics2D g, int x, int y, int w, int h, int value, int max, Color color, String label) {
        g.setFont(UiFonts.of(Font.PLAIN, 12));
        g.setColor(MUTED);
        g.drawString(label, x, y - 5);
        g.setColor(METER_BACK);
        g.fillRoundRect(x + METER_LABEL_OFFSET, y, w, h, h, h);
        int fill = Math.max(0, Math.min(w, value * w / max));
        g.setColor(color);
        g.fillRoundRect(x + METER_LABEL_OFFSET, y, fill, h, h, h);
        g.setColor(METER_STROKE);
        g.drawRoundRect(x + METER_LABEL_OFFSET, y, w, h, h, h);
    }
}
