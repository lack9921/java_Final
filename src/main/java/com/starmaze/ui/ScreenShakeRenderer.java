package com.starmaze.ui;

import java.awt.Graphics2D;
import java.util.List;

public final class ScreenShakeRenderer {
    public void apply(Graphics2D g, List<ActiveEffect> effects, int renderFrame) {
        ScreenShakeOffset offset = ScreenShakeRules.offset(effects, renderFrame);
        if (!offset.active()) {
            return;
        }
        g.translate(offset.dx(), offset.dy());
    }
}
