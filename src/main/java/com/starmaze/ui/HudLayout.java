package com.starmaze.ui;

public record HudLayout(int baseline, int phaseMeterX, int phaseMeterY, int phaseMeterW, int phaseMeterH,
                        int rewindX, int rewindY) {
    private static final int BASELINE = 54;
    private static final int PHASE_METER_X = 570;
    private static final int PHASE_METER_Y = 35;
    private static final int PHASE_METER_W = 150;
    private static final int PHASE_METER_H = 16;
    private static final int REWIND_X = 750;
    private static final int REWIND_Y = 43;

    public static HudLayout standard() {
        return new HudLayout(BASELINE, PHASE_METER_X, PHASE_METER_Y, PHASE_METER_W, PHASE_METER_H, REWIND_X,
                REWIND_Y);
    }
}
