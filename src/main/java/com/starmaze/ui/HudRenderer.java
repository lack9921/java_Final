package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class HudRenderer {
    private final HudPanelRenderer panelRenderer = new HudPanelRenderer();
    private final HudStatsRenderer statsRenderer = new HudStatsRenderer();
    private final HudMeterRenderer meterRenderer = new HudMeterRenderer();
    private final HudRewindRenderer rewindRenderer = new HudRewindRenderer();
    private final HudMessageRenderer messageRenderer = new HudMessageRenderer();
    private final HudMessageSelector messageSelector = new HudMessageSelector();
    private final HudPhaseColorSelector phaseColorSelector = new HudPhaseColorSelector();
    private final HudLayout layout = HudLayout.standard();

    public void paint(Graphics2D g, GameState state, int width) {
        for (HudRenderStep step : HudRenderStep.standardOrder()) {
            paintStep(g, state, width, step);
        }
    }

    private void paintStep(Graphics2D g, GameState state, int width, HudRenderStep step) {
        switch (step) {
            case PANEL -> panelRenderer.paint(g, width);
            case STATS -> statsRenderer.paint(g, state, layout.baseline());
            case PHASE_METER -> meterRenderer.paint(g, layout.phaseMeterX(), layout.phaseMeterY(),
                    layout.phaseMeterW(), layout.phaseMeterH(), state.phaseMeter(), GameState.PHASE_MAX,
                    phaseColorSelector.select(state), HudText.PHASE_LABEL);
            case REWIND_CHARGES -> rewindRenderer.paint(g, layout.rewindX(), layout.rewindY(), state.rewindCharges());
            case MESSAGE -> messageRenderer.paint(g, messageSelector.select(state), width, layout.baseline());
        }
    }
}
