package com.starmaze.ui;

import java.awt.Font;
import java.util.List;

public enum SimpleMenuContentSpec {
    PAUSE(List.of(
            new SimpleMenuContentLine(MenuText.PAUSED, VisualConfig.INK, Font.BOLD, 32, 54),
            new SimpleMenuContentLine(MenuText.PAUSE_HINT, VisualConfig.MUTED, Font.PLAIN, 14, 84))),
    SETTINGS(List.of(
            new SimpleMenuContentLine(MenuText.SETTINGS_TITLE, VisualConfig.CYAN, Font.BOLD, 30, 54),
            new SimpleMenuContentLine(MenuText.SETTINGS_SAVE_HINT, VisualConfig.MUTED, Font.PLAIN, 15, 92)));

    private final List<SimpleMenuContentLine> lines;

    SimpleMenuContentSpec(List<SimpleMenuContentLine> lines) {
        this.lines = lines;
    }

    public List<SimpleMenuContentLine> lines() {
        return lines;
    }
}
