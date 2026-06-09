package com.starmaze.ui;

import java.awt.Color;

public record BoardFloorVisual(Color fillColor, Color gridColor) {
    private static final Color FLOOR_FILL = new Color(14, 23, 37);
    private static final Color FLOOR_GRID = new Color(52, 75, 93, 65);

    public static BoardFloorVisual standard() {
        return new BoardFloorVisual(FLOOR_FILL, FLOOR_GRID);
    }
}
