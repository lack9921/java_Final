package com.starmaze.model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

public final class PathFinder {
    private PathFinder() {
    }

    public static Direction findStepToward(Level level, Position from, Position to, Set<Position> occupied) {
        if (from.equals(to)) {
            return null;
        }
        SearchGrid grid = SearchGrid.create(level);

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator
                .comparingInt(Node::estimatedTotalCost)
                .thenComparingInt(Node::costFromStart));
        grid.bestCost()[from.y()][from.x()] = 0;
        open.add(new Node(from, 0, distance(from, to)));

        while (!open.isEmpty()) {
            Node node = open.poll();
            Position current = node.position();
            if (grid.closed()[current.y()][current.x()]) {
                continue;
            }
            if (current.equals(to)) {
                return grid.firstStep()[current.y()][current.x()];
            }
            grid.closed()[current.y()][current.x()] = true;

            for (Direction direction : Direction.CARDINALS) {
                Position next = current.translate(direction);
                if (!level.contains(next) || grid.closed()[next.y()][next.x()] || !level.isWalkableForEnemy(next)) {
                    continue;
                }
                if (!next.equals(to) && occupied.contains(next)) {
                    continue;
                }
                int nextCost = node.costFromStart() + 1;
                if (nextCost >= grid.bestCost()[next.y()][next.x()]) {
                    continue;
                }
                grid.bestCost()[next.y()][next.x()] = nextCost;
                grid.firstStep()[next.y()][next.x()] = current.equals(from)
                        ? direction
                        : grid.firstStep()[current.y()][current.x()];
                open.add(new Node(next, nextCost, nextCost + distance(next, to)));
            }
        }
        return null;
    }

    private static int distance(Position a, Position b) {
        return a.manhattanDistance(b);
    }

    private record SearchGrid(boolean[][] closed, int[][] bestCost, Direction[][] firstStep) {
        private static SearchGrid create(Level level) {
            int width = level.width();
            int height = level.height();
            boolean[][] closed = new boolean[height][width];
            int[][] bestCost = new int[height][width];
            Direction[][] firstStep = new Direction[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    bestCost[y][x] = Integer.MAX_VALUE;
                }
            }
            return new SearchGrid(closed, bestCost, firstStep);
        }
    }

    private record Node(Position position, int costFromStart, int estimatedTotalCost) {
    }
}
