package com.starmaze.ui;

import com.starmaze.model.GameState;
import com.starmaze.model.Position;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public final class PickupRenderer {
    private static final Color CRYSTAL_HIGHLIGHT = new Color(255, 255, 255, 180);
    private static final int CRYSTAL_MIN_RADIUS = 5;
    private static final int CRYSTAL_RADIUS_DIVISOR = 4;
    private static final int CRYSTAL_PULSE_AMPLITUDE = 2;
    private static final int CRYSTAL_PULSE_X_FACTOR = 17;
    private static final double CRYSTAL_PULSE_SPEED = 0.12;
    private static final int PHASE_CELL_RADIUS = 6;
    private static final int PHASE_CELL_LINE_INSET = 2;
    private static final float REWIND_CELL_STROKE_WIDTH = 2.2f;

    public void paint(Graphics2D g, GameState state, BoardMetrics metrics) {
        for (Position p : state.crystals()) {
            paintPickup(g, p, metrics, state.tick(), PickupVisualStyle.CRYSTAL);
        }
        for (Position p : state.phaseCells()) {
            paintPickup(g, p, metrics, state.tick(), PickupVisualStyle.PHASE_CELL);
        }
        for (Position p : state.rewindCells()) {
            paintPickup(g, p, metrics, state.tick(), PickupVisualStyle.REWIND_CELL);
        }
    }

    private void paintPickup(Graphics2D g, Position p, BoardMetrics metrics, int tick, PickupVisualStyle style) {
        int tileSize = metrics.tileSize();
        int cx = metrics.centerX(p);
        int cy = metrics.centerY(p);
        int r = Math.max(CRYSTAL_MIN_RADIUS, tileSize / CRYSTAL_RADIUS_DIVISOR);
        int pulse = (int) (CRYSTAL_PULSE_AMPLITUDE
                * Math.sin((tick + p.x() * CRYSTAL_PULSE_X_FACTOR) * CRYSTAL_PULSE_SPEED));
        g.setColor(style.glowColor());
        g.fillOval(cx - r * 2, cy - r * 2, r * 4, r * 4);
        g.setColor(style.bodyColor());
        if (style.shape() == PickupShape.DIAMOND) {
            Path2D diamond = new Path2D.Double();
            diamond.moveTo(cx, cy - r - pulse);
            diamond.lineTo(cx + r, cy);
            diamond.lineTo(cx, cy + r + pulse);
            diamond.lineTo(cx - r, cy);
            diamond.closePath();
            g.fill(diamond);
        } else if (style.shape() == PickupShape.ROUNDED_CELL) {
            g.fillRoundRect(cx - r, cy - r, r * 2, r * 2, PHASE_CELL_RADIUS, PHASE_CELL_RADIUS);
            g.setColor(CRYSTAL_HIGHLIGHT);
            g.drawLine(cx, cy - r + PHASE_CELL_LINE_INSET, cx, cy + r - PHASE_CELL_LINE_INSET);
        } else {
            g.setStroke(new BasicStroke(REWIND_CELL_STROKE_WIDTH));
            g.drawOval(cx - r, cy - r, r * 2, r * 2);
            g.drawLine(cx - r, cy, cx + r, cy);
            g.drawLine(cx, cy - r, cx, cy + r);
        }
    }
}
