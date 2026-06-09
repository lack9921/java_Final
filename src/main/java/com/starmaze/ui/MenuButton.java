package com.starmaze.ui;

public record MenuButton(String action, int x, int y, int w, int h) {
    public boolean contains(int px, int py) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
