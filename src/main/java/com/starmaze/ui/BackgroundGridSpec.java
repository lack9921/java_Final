package com.starmaze.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public record BackgroundGridSpec(Color color, float strokeWidth, int spacing) {
    private static final Color GRID_COLOR = new Color(64, 103, 130, 45);
    private static final float GRID_STROKE_WIDTH = 1f;

    public static BackgroundGridSpec standard() {
        return new BackgroundGridSpec(GRID_COLOR, GRID_STROKE_WIDTH, VisualConfig.BACKGROUND_GRID_SIZE);
    }

    public List<Integer> verticalLines(int width) {
        return positions(width);
    }

    public List<Integer> horizontalLines(int height) {
        return positions(height);
    }

    private List<Integer> positions(int limit) {
        List<Integer> result = new ArrayList<>();
        if (limit <= 0 || spacing <= 0) {
            return result;
        }
        for (int position = 0; position < limit; position += spacing) {
            result.add(position);
        }
        return result;
    }
}
