package com.starmaze.ui;

import com.starmaze.model.TileType;

import java.awt.Graphics2D;

public final class BoardTileRenderer {
    private static final int RIFT_DIAGONAL_INSET = 7;

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
        BoardWallVisual visual = BoardWallVisual.fromTileSize(size);
        g.setColor(visual.fillColor());
        g.fillRoundRect(px + visual.fillInset(), py + visual.fillInset(), visual.fillSize(),
                visual.fillSize(), visual.fillRadius(), visual.fillRadius());
        g.setColor(visual.strokeColor());
        g.drawRoundRect(px + visual.strokeInset(), py + visual.strokeInset(), visual.strokeSize(), visual.strokeSize(),
                visual.strokeRadius(), visual.strokeRadius());
    }

    private void paintRift(Graphics2D g, int tileX, int tileY, int px, int py, int size, int tick) {
        BoardRiftVisual visual = BoardRiftVisual.from(tileX, tileY, size, tick);
        g.setColor(visual.fillColor());
        g.fillRoundRect(px + visual.fillInset(), py + visual.fillInset(), visual.fillSize(), visual.fillSize(),
                visual.fillRadius(), visual.fillRadius());
        g.setColor(visual.strokeColor());
        g.drawOval(px + visual.ovalInset(), py + visual.ovalInset(), visual.ovalSize(), visual.ovalSize());
        g.drawLine(px + RIFT_DIAGONAL_INSET, py + size - RIFT_DIAGONAL_INSET - 1,
                px + size - RIFT_DIAGONAL_INSET - 1, py + RIFT_DIAGONAL_INSET);
    }

    private void paintFloor(Graphics2D g, int px, int py, int size) {
        BoardFloorVisual visual = BoardFloorVisual.standard();
        g.setColor(visual.fillColor());
        g.fillRect(px, py, size, size);
        g.setColor(visual.gridColor());
        g.drawRect(px, py, size, size);
    }
}
