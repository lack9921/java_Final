package com.starmaze.ui;

import com.starmaze.model.Enemy;
import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class EntityRenderer {
    private final PickupRenderer pickupRenderer = new PickupRenderer();
    private final ExitRenderer exitRenderer = new ExitRenderer();
    private final EnemyRenderer enemyRenderer = new EnemyRenderer();
    private final PlayerRenderer playerRenderer = new PlayerRenderer();

    public void paintPickups(Graphics2D g, GameState state, BoardMetrics metrics) {
        pickupRenderer.paint(g, state, metrics);
    }

    public void paintExit(Graphics2D g, GameState state, BoardMetrics metrics) {
        exitRenderer.paint(g, state, metrics);
    }

    public void paintEnemies(Graphics2D g, Iterable<Enemy> enemies, BoardMetrics metrics) {
        enemyRenderer.paint(g, enemies, metrics);
    }

    public void paintPlayer(Graphics2D g, GameState state, BoardMetrics metrics) {
        playerRenderer.paint(g, state, metrics);
    }
}
