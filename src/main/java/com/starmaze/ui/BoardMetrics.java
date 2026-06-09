package com.starmaze.ui;

import com.starmaze.model.Position;

public record BoardMetrics(int x, int y, int width, int height, int tileSize) {
    public int tileX(Position position) {
        return tileX(position.x());
    }

    public int tileY(Position position) {
        return tileY(position.y());
    }

    public int tileX(int tileX) {
        return x + tileX * tileSize;
    }

    public int tileY(int tileY) {
        return y + tileY * tileSize;
    }

    public int centerX(Position position) {
        return tileX(position) + tileSize / 2;
    }

    public int centerY(Position position) {
        return tileY(position) + tileSize / 2;
    }
}
