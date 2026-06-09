package com.starmaze.audio;

public enum SoundEffect {
    MENU(540, 80, 0.20),
    MOVE(260, 35, 0.12),
    BUMP(100, 70, 0.17),
    COLLECT(740, 100, 0.22),
    POWER(920, 120, 0.20),
    PHASE(420, 180, 0.18),
    REWIND(220, 210, 0.20),
    WIN(880, 260, 0.25),
    LOSE(130, 360, 0.20);

    private final int frequency;
    private final int durationMs;
    private final double volume;

    SoundEffect(int frequency, int durationMs, double volume) {
        this.frequency = frequency;
        this.durationMs = durationMs;
        this.volume = volume;
    }

    public int frequency() {
        return frequency;
    }

    public int durationMs() {
        return durationMs;
    }

    public double volume() {
        return volume;
    }
}
