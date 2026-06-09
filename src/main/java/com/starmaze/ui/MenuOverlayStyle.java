package com.starmaze.ui;

public enum MenuOverlayStyle {
    TITLE(0.72f),
    PAUSED(0.58f),
    HELP(0.76f),
    SETTINGS(0.74f),
    LEVEL_CLEAR(0.62f),
    GAME_OVER(0.68f);

    private final float overlayAlpha;

    MenuOverlayStyle(float overlayAlpha) {
        this.overlayAlpha = overlayAlpha;
    }

    public static MenuOverlayStyle forMode(MenuOverlayMode mode) {
        return valueOf(mode.name());
    }

    public float overlayAlpha() {
        return overlayAlpha;
    }
}
