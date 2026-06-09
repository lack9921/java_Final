package com.starmaze.ui;

import java.awt.Graphics2D;

public final class StarField {
    private static final int STAR_X_STEP = 137;
    private static final int STAR_Y_STEP = 83;
    private static final int STAR_PHASE_STEP = 11;

    private final Star[] stars;

    public StarField(int count) {
        stars = new Star[count];
        for (int i = 0; i < count; i++) {
            stars[i] = new Star(i * STAR_X_STEP, i * STAR_Y_STEP, i * STAR_PHASE_STEP);
        }
    }

    public void paint(Graphics2D g, int width, int height, int tick) {
        for (Star star : stars) {
            StarSprite sprite = StarSprite.from(star.baseX(), star.baseY(), star.phase(), width, height, tick);
            g.setColor(sprite.color());
            g.fillRect(sprite.x(), sprite.y(), sprite.size(), sprite.size());
        }
    }

    private record Star(int baseX, int baseY, int phase) {
    }
}
