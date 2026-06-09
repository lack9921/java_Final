package com.starmaze.ui;

import javax.swing.Timer;

public final class GamePanelTimer {
    private final Timer timer;

    public GamePanelTimer(Runnable frameAdvance) {
        this.timer = new Timer(VisualConfig.FRAME_DELAY_MS, event -> frameAdvance.run());
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public boolean isRunning() {
        return timer.isRunning();
    }
}
