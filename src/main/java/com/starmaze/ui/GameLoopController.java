package com.starmaze.ui;

import com.starmaze.model.GameState;

public final class GameLoopController {
    private final GameState state;
    private final EffectLayer effectLayer;
    private final MenuButtonCache menuButtons;
    private final RenderClock renderClock = new RenderClock();

    public GameLoopController(GameState state, EffectLayer effectLayer, MenuButtonCache menuButtons) {
        this.state = state;
        this.effectLayer = effectLayer;
        this.menuButtons = menuButtons;
    }

    public void advanceFrame(int width, int height) {
        int frame = renderClock.advance();
        if (renderClock.shouldUpdateLogic()) {
            state.update();
        }
        effectLayer.advance(state.drainVisualEffects(), frame);
        menuButtons.update(state.mode(), width, height);
    }

    public int frame() {
        return renderClock.frame();
    }
}
