package com.starmaze.model;

import java.util.List;
import java.util.Set;

public record LevelPopulation(Set<Position> crystals,
                              Set<Position> phaseCells,
                              Set<Position> rewindCells,
                              List<Enemy> enemies,
                              int crystalsNeeded) {
}
