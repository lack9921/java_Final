package com.starmaze.ui;

import java.awt.Color;

public record BoardWallVisual(Color fillColor, Color strokeColor, int fillInset, int strokeInset,
                              int fillRadius, int strokeRadius, int fillSize, int strokeSize) {
    private static final Color WALL_FILL = new Color(23, 43, 65);
    private static final Color WALL_STROKE = new Color(72, 142, 167, 95);
    private static final int WALL_INSET = 1;
    private static final int WALL_STROKE_INSET = 3;
    private static final int WALL_RADIUS = 8;
    private static final int WALL_STROKE_RADIUS = 6;

    public static BoardWallVisual fromTileSize(int size) {
        return new BoardWallVisual(
                WALL_FILL,
                WALL_STROKE,
                WALL_INSET,
                WALL_STROKE_INSET,
                WALL_RADIUS,
                WALL_STROKE_RADIUS,
                size - WALL_INSET * 2,
                size - WALL_STROKE_INSET * 2);
    }
}
