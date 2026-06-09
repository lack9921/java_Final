package com.starmaze.ui;

public record ScreenShakeOffset(int dx, int dy) {
    public boolean active() {
        return dx != 0 || dy != 0;
    }
}
