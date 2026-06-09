package com.starmaze.ui;

import com.starmaze.model.TileType;

public final class BoardStaticTileMapper {
    private BoardStaticTileMapper() {
    }

    public static TileType cacheTile(TileType tile) {
        return tile == TileType.RIFT ? TileType.FLOOR : tile;
    }
}
