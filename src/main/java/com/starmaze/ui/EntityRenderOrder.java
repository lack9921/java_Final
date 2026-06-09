package com.starmaze.ui;

import java.util.List;

public enum EntityRenderOrder {
    PICKUPS,
    EXIT,
    ENEMIES,
    PLAYER;

    private static final List<EntityRenderOrder> GAMEPLAY_ORDER = List.of(PICKUPS, EXIT, ENEMIES, PLAYER);

    public static List<EntityRenderOrder> gameplayOrder() {
        return GAMEPLAY_ORDER;
    }
}
