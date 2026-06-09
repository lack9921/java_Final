package com.starmaze.ui;

import java.util.List;

public enum HudRenderStep {
    PANEL,
    STATS,
    PHASE_METER,
    REWIND_CHARGES,
    MESSAGE;

    public static List<HudRenderStep> standardOrder() {
        return List.of(PANEL, STATS, PHASE_METER, REWIND_CHARGES, MESSAGE);
    }
}
