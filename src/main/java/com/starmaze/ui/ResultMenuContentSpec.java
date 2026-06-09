package com.starmaze.ui;

import java.awt.Font;
import java.util.List;

public enum ResultMenuContentSpec {
    LEVEL_CLEAR(List.of(
            new ResultMenuContentLine(VisualConfig.MINT, Font.BOLD, 31, 58,
                    state -> MenuText.LEVEL_CLEAR_TITLE),
            new ResultMenuContentLine(VisualConfig.INK, Font.PLAIN, 15, 94,
                    state -> MenuText.levelClearSummary(state.levelIndex(), state.score())),
            new ResultMenuContentLine(VisualConfig.MUTED, Font.PLAIN, 15, 124,
                    state -> MenuText.RANK_PREFIX + "★".repeat(state.runRank())
                            + "☆".repeat(5 - state.runRank())))),
    GAME_OVER(List.of(
            new ResultMenuContentLine(VisualConfig.PINK, Font.BOLD, 31, 58,
                    state -> MenuText.GAME_OVER_TITLE),
            new ResultMenuContentLine(VisualConfig.INK, Font.PLAIN, 15, 94,
                    state -> state.message()),
            new ResultMenuContentLine(VisualConfig.MUTED, Font.PLAIN, 15, 124,
                    state -> MenuText.FINAL_SCORE + state.score()
                            + MenuText.BEST_SCORE + state.saveData().highScore())));

    private final List<ResultMenuContentLine> lines;

    ResultMenuContentSpec(List<ResultMenuContentLine> lines) {
        this.lines = lines;
    }

    public List<ResultMenuContentLine> lines() {
        return lines;
    }
}
