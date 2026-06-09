package com.starmaze.ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public final class RenderQuality {
    private RenderQuality() {
    }

    public static void apply(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}
