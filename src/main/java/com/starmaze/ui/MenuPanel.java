package com.starmaze.ui;

public record MenuPanel(int x, int y, int w, int h) {
    public int centerX() {
        return x + w / 2;
    }
}
