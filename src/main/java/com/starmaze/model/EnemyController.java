package com.starmaze.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class EnemyController {
    public void advanceEnemy(Level level, int levelIndex, Position player, boolean phaseActive,
                             Enemy enemy, Set<Position> occupied, Random random) {
        if (enemy.stunnedTicks() > 0) {
            enemy.coolDown();
            return;
        }
        occupied.remove(enemy.position());
        Direction next = chooseDirection(level, levelIndex, player, phaseActive, enemy, occupied, random);
        Position target = enemy.position().translate(next);
        if (level.isWalkableForEnemy(target) && !occupied.contains(target)) {
            enemy.setPosition(target);
            enemy.setDirection(next);
        } else {
            enemy.setDirection(Direction.CARDINALS.get(random.nextInt(Direction.CARDINALS.size())));
        }
        occupied.add(enemy.position());
    }

    public Direction chooseDirection(Level level, int levelIndex, Position player, boolean phaseActive,
                                     Enemy enemy, Set<Position> occupied, Random random) {
        int alertRange = GameConfig.ENEMY_ALERT_BASE_RANGE
                + Math.min(GameConfig.ENEMY_ALERT_LEVEL_BONUS_MAX, levelIndex);
        if (!phaseActive && distance(enemy.position(), player) <= alertRange) {
            Direction chase = PathFinder.findStepToward(level, enemy.position(), player, occupied);
            if (chase != null) {
                return chase;
            }
        }

        Position patrol = enemy.position().translate(enemy.direction());
        if (level.isWalkableForEnemy(patrol) && !occupied.contains(patrol)
                && random.nextDouble() < GameConfig.ENEMY_PATROL_KEEP_DIRECTION_CHANCE) {
            return enemy.direction();
        }

        List<Direction> directions = new ArrayList<>(Direction.CARDINALS);
        Collections.shuffle(directions, random);
        for (Direction direction : directions) {
            Position target = enemy.position().translate(direction);
            if (level.isWalkableForEnemy(target) && !occupied.contains(target)) {
                return direction;
            }
        }
        return enemy.direction().opposite();
    }

    private int distance(Position a, Position b) {
        return a.manhattanDistance(b);
    }
}
