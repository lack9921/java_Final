package com.starmaze.model;

import java.util.List;

public record RewindSnapshot(Position player, List<Position> enemyPositions, int phaseTicks) {
}
