package com.starmaze.ui;

import java.awt.Font;
import java.util.List;

public enum TitleMenuContentSpec {
    STANDARD(List.of(
            new TitleMenuContentLine(VisualConfig.CYAN, Font.BOLD, 58, 16, state -> MenuText.TITLE),
            new TitleMenuContentLine(VisualConfig.INK, Font.BOLD, 20, 54, state -> MenuText.SUBTITLE),
            new TitleMenuContentLine(VisualConfig.MUTED, Font.PLAIN, 15, 86, state -> MenuText.TITLE_HINT),
            new TitleMenuContentLine(VisualConfig.MUTED, Font.PLAIN, 13, 310,
                    state -> MenuText.HIGH_SCORE + state.saveData().highScore()
                            + MenuText.GAMES_PLAYED + state.saveData().gamesPlayed()
                            + MenuText.VICTORIES + state.saveData().victories())));

    private final List<TitleMenuContentLine> lines;

    TitleMenuContentSpec(List<TitleMenuContentLine> lines) {
        this.lines = lines;
    }

    public List<TitleMenuContentLine> lines() {
        return lines;
    }
}
