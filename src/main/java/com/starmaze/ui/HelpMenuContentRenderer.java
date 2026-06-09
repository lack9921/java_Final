package com.starmaze.ui;

import java.awt.Font;
import java.awt.Graphics2D;

public final class HelpMenuContentRenderer {
    private final HelpMenuContentSpec spec = HelpMenuContentSpec.standard();

    public void paint(Graphics2D g, MenuPanel panel) {
        g.setFont(UiFonts.of(Font.BOLD, spec.titleFontSize()));
        g.setColor(spec.titleColor());
        g.drawString(spec.title(), panel.x() + spec.titleXOffset(), panel.y() + spec.titleBaselineOffset());
        g.setFont(UiFonts.of(Font.PLAIN, spec.lineFontSize()));
        for (int i = 0; i < spec.lines().size(); i++) {
            helpLine(g, panel.x() + spec.lineXOffset(), panel.y() + spec.lineBaseline(i), spec.lines().get(i));
        }
    }

    private void helpLine(Graphics2D g, int x, int y, String text) {
        g.setColor(spec.dotColor());
        g.fillOval(x, y - spec.dotBaselineOffset(), spec.dotSize(), spec.dotSize());
        g.setColor(spec.textColor());
        g.drawString(text, x + spec.textXOffset(), y);
    }
}
