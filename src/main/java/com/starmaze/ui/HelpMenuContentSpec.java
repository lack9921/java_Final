package com.starmaze.ui;

import java.awt.Color;
import java.util.List;

public record HelpMenuContentSpec(String title, List<String> lines, Color titleColor, Color textColor,
                                  Color dotColor, int titleFontSize, int lineFontSize, int titleXOffset,
                                  int titleBaselineOffset, int firstLineBaselineOffset, int lineXOffset,
                                  int dotSize, int dotBaselineOffset, int textXOffset, int lineGap) {
    private static final int HELP_TITLE_X_OFFSET = 34;
    private static final int HELP_TITLE_BASELINE_OFFSET = 52;
    private static final int HELP_FIRST_LINE_BASELINE_OFFSET = 94;
    private static final int HELP_LINE_X_OFFSET = 38;
    private static final int HELP_DOT_SIZE = 8;
    private static final int HELP_DOT_BASELINE_OFFSET = 12;
    private static final int HELP_TEXT_X_OFFSET = 20;
    private static final int HELP_LINE_GAP = 42;

    public static HelpMenuContentSpec standard() {
        return new HelpMenuContentSpec(
                MenuText.HELP_TITLE,
                List.of(MenuText.HELP_LINES),
                VisualConfig.CYAN,
                VisualConfig.INK,
                UiColors.withAlpha(VisualConfig.CYAN, 140),
                30,
                16,
                HELP_TITLE_X_OFFSET,
                HELP_TITLE_BASELINE_OFFSET,
                HELP_FIRST_LINE_BASELINE_OFFSET,
                HELP_LINE_X_OFFSET,
                HELP_DOT_SIZE,
                HELP_DOT_BASELINE_OFFSET,
                HELP_TEXT_X_OFFSET,
                HELP_LINE_GAP);
    }

    public int lineBaseline(int index) {
        return firstLineBaselineOffset + index * lineGap;
    }
}
