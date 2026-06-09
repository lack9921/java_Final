package com.starmaze.ui;

public record HudMeterGeometry(int trackX, int trackY, int trackW, int trackH, int fillW) {
    private static final int METER_LABEL_OFFSET = 34;

    public static HudMeterGeometry from(int x, int y, int w, int h, int value, int max) {
        int fill = max <= 0 ? 0 : Math.max(0, Math.min(w, value * w / max));
        return new HudMeterGeometry(x + METER_LABEL_OFFSET, y, w, h, fill);
    }
}
