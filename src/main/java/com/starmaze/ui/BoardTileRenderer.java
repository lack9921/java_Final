package com.starmaze.ui;

import com.starmaze.model.TileType;

import java.awt.Color;
import java.awt.Graphics2D;

public final class BoardTileRenderer {
    private static final Color WALL_FILL = new Color(23, 43, 65);
    private static final Color WALL_STROKE = new Color(72, 142, 167, 95);
    private static final Color RIFT_FILL = new Color(26, 23, 51);
    private static final Color RIFT_STROKE = new Color(186, 107, 255);
    private static final Color FLOOR_FILL = new Color(14, 23, 37);
    private static final Color FLOOR_GRID = new Color(52, 75, 93, 65);
    private static final int WALL_INSET = 1;
    private static final int WALL_STROKE_INSET = 3;
    private static final int WALL_RADIUS = 8;
    private static final int WALL_STROKE_RADIUS = 6;
    private static final int RIFT_INSET = 2;
    private static final int RIFT_RADIUS = 7;
    private static final int RIFT_DIAGONAL_INSET = 7;
    private static final int RIFT_PULSE_BASE = 50;
    private static final int RIFT_PULSE_AMPLITUDE = 45;
    private static final int RIFT_PULSE_X_FACTOR = 7;
    private static final int RIFT_PULSE_Y_FACTOR = 11;
    private static final double RIFT_PULSE_SPEED = 0.08;
    private static final int RIFT_ALPHA_BASE = 120;
    private static final int RIFT_OVAL_INSET_DIVISOR = 5;
    private static final int RIFT_OVAL_SIZE_NUMERATOR = 3;

    public void paint(Graphics2D g, int x, int y, TileType type, int originX, int originY, int size, int tick) {
        int px = originX + x * size;
        int py = originY + y * size;
        switch (type) {
            case WALL -> paintWall(g, px, py, size);
            case RIFT -> paintRift(g, x, y, px, py, size, tick);
            case EXIT, FLOOR -> paintFloor(g, px, py, size);
        }
    }

    private void paintWall(Graphics2D g, int px, int py, int size) {
        g.setColor(WALL_FILL);
        g.fillRoundRect(px + WALL_INSET, py + WALL_INSET, size - WALL_INSET * 2,
                size - WALL_INSET * 2, WALL_RADIUS, WALL_RADIUS);
        g.setColor(WALL_STROKE);
        g.drawRoundRect(px + WALL_STROKE_INSET, py + WALL_STROKE_INSET,
                size - WALL_STROKE_INSET * 2, size - WALL_STROKE_INSET * 2,
                WALL_STROKE_RADIUS, WALL_STROKE_RADIUS);
    }

    private void paintRift(Graphics2D g, int tileX, int tileY, int px, int py, int size, int tick) {
        g.setColor(RIFT_FILL);
        g.fillRoundRect(px + RIFT_INSET, py + RIFT_INSET, size - RIFT_INSET * 2,
                size - RIFT_INSET * 2, RIFT_RADIUS, RIFT_RADIUS);
        int pulse = (int) (RIFT_PULSE_BASE + RIFT_PULSE_AMPLITUDE
                * Math.sin((tick + tileX * RIFT_PULSE_X_FACTOR + tileY * RIFT_PULSE_Y_FACTOR) * RIFT_PULSE_SPEED));
        int ovalInset = size / RIFT_OVAL_INSET_DIVISOR;
        int ovalSize = size * RIFT_OVAL_SIZE_NUMERATOR / RIFT_OVAL_INSET_DIVISOR;
        g.setColor(UiColors.withAlpha(RIFT_STROKE, RIFT_ALPHA_BASE + pulse));
        g.drawOval(px + ovalInset, py + ovalInset, ovalSize, ovalSize);
        g.drawLine(px + RIFT_DIAGONAL_INSET, py + size - RIFT_DIAGONAL_INSET - 1,
                px + size - RIFT_DIAGONAL_INSET - 1, py + RIFT_DIAGONAL_INSET);
    }

    private void paintFloor(Graphics2D g, int px, int py, int size) {
        g.setColor(FLOOR_FILL);
        g.fillRect(px, py, size, size);
        g.setColor(FLOOR_GRID);
        g.drawRect(px, py, size, size);
    }
}
