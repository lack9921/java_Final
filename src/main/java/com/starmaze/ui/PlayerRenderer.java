package com.starmaze.ui;

import com.starmaze.model.GameState;
import com.starmaze.model.Position;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public final class PlayerRenderer {
    private static final java.awt.Color PLAYER_CORE = new java.awt.Color(5, 15, 26);
    private static final java.awt.Color PLAYER_STROKE = new java.awt.Color(255, 255, 255, 190);
    private static final int ACTOR_RADIUS_INSET = 5;
    private static final int PLAYER_GLOW_PAD = 7;
    private static final float PLAYER_STROKE_WIDTH = 2f;

    public void paint(Graphics2D g, GameState state, BoardMetrics metrics) {
        Position p = state.player();
        int tileSize = metrics.tileSize();
        int cx = metrics.centerX(p);
        int cy = metrics.centerY(p);
        int r = tileSize / 2 - ACTOR_RADIUS_INSET;
        PlayerVisualStyle style = PlayerVisualStyle.forPhaseState(state.isPhaseActive());
        Shape glow = new Ellipse2D.Double(cx - r - PLAYER_GLOW_PAD, cy - r - PLAYER_GLOW_PAD,
                (r + PLAYER_GLOW_PAD) * 2, (r + PLAYER_GLOW_PAD) * 2);
        g.setColor(style.glowColor());
        g.fill(glow);
        g.setColor(style.bodyColor());
        g.fillOval(cx - r, cy - r, r * 2, r * 2);
        g.setColor(PLAYER_CORE);
        g.fillOval(cx - r / 3, cy - r / 3, r * 2 / 3, r * 2 / 3);
        g.setColor(PLAYER_STROKE);
        g.setStroke(new BasicStroke(PLAYER_STROKE_WIDTH));
        g.drawOval(cx - r, cy - r, r * 2, r * 2);
    }
}
