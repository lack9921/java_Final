package com.starmaze.ui;

public final class RenderClock {
    private int frame;

    public int advance() {
        frame++;
        return frame;
    }

    public int frame() {
        return frame;
    }

    public boolean shouldUpdateLogic() {
        return frame % VisualConfig.LOGIC_FRAME_DIVISOR == 0;
    }
}
