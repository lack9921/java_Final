package com.starmaze.ui;

import com.starmaze.model.Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public final class EnemyRenderer {
    private static final Color ENEMY_STROKE = new Color(255, 255, 255, 165);
    private static final int ACTOR_RADIUS_INSET = 5;
    private static final int ENEMY_GLOW_PAD = 4;

    public void paint(Graphics2D g, Iterable<Enemy> enemies, BoardMetrics metrics) {
        for (Enemy enemy : enemies) {
            paintOne(g, enemy, metrics);
        }
    }

    private void paintOne(Graphics2D g, Enemy enemy, BoardMetrics metrics) {
        int tileSize = metrics.tileSize();
        int cx = metrics.centerX(enemy.position());
        int cy = metrics.centerY(enemy.position());
        int r = tileSize / 2 - ACTOR_RADIUS_INSET;
        EnemyVisualStyle style = EnemyVisualStyle.forStunState(enemy.stunnedTicks() > 0);
        g.setColor(style.glowColor());
        g.fillOval(cx - r - ENEMY_GLOW_PAD, cy - r - ENEMY_GLOW_PAD,
                (r + ENEMY_GLOW_PAD) * 2, (r + ENEMY_GLOW_PAD) * 2);
        Path2D ship = new Path2D.Double();
        switch (enemy.direction()) {
            case UP -> {
                ship.moveTo(cx, cy - r);
                ship.lineTo(cx + r, cy + r);
                ship.lineTo(cx, cy + r / 2);
                ship.lineTo(cx - r, cy + r);
            }
            case DOWN -> {
                ship.moveTo(cx, cy + r);
                ship.lineTo(cx + r, cy - r);
                ship.lineTo(cx, cy - r / 2);
                ship.lineTo(cx - r, cy - r);
            }
            case LEFT -> {
                ship.moveTo(cx - r, cy);
                ship.lineTo(cx + r, cy - r);
                ship.lineTo(cx + r / 2, cy);
                ship.lineTo(cx + r, cy + r);
            }
            case RIGHT -> {
                ship.moveTo(cx + r, cy);
                ship.lineTo(cx - r, cy - r);
                ship.lineTo(cx - r / 2, cy);
                ship.lineTo(cx - r, cy + r);
            }
        }
        ship.closePath();
        g.setColor(style.bodyColor());
        g.fill(ship);
        g.setColor(ENEMY_STROKE);
        g.draw(ship);
    }
}
