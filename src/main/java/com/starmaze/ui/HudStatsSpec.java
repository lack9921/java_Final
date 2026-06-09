package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.util.List;

public final class HudStatsSpec {
    private static final int TITLE_X = 46;
    private static final int LEVEL_X = 166;
    private static final int SCORE_X = 246;
    private static final int HIGH_SCORE_X = 338;
    private static final int CRYSTAL_X = 456;
    public static final int TITLE_FONT_SIZE = 18;
    public static final int STAT_FONT_SIZE = 15;

    private HudStatsSpec() {
    }

    public static HudStatItem titleItem() {
        return new HudStatItem(TITLE_X, state -> HudText.TITLE);
    }

    public static List<HudStatItem> statItems() {
        return List.of(
                new HudStatItem(LEVEL_X, state -> HudText.LEVEL_PREFIX + state.levelIndex() + HudText.LEVEL_SUFFIX),
                new HudStatItem(SCORE_X, state -> HudText.SCORE_PREFIX + state.score()),
                new HudStatItem(HIGH_SCORE_X, state -> HudText.HIGH_SCORE_PREFIX + state.saveData().highScore()),
                new HudStatItem(CRYSTAL_X,
                        state -> HudText.CRYSTAL_PREFIX + state.crystalsCollected() + "/" + state.crystalsNeeded()));
    }

    public static List<String> statTexts(GameState state) {
        return statItems().stream().map(item -> item.text(state)).toList();
    }
}
