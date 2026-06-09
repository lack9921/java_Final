package com.starmaze.ui;

import com.starmaze.model.GameState;

import javax.swing.JPanel;
import java.awt.Graphics;

public final class GamePanel extends JPanel {
    private final GameState state;
    private final GamePanelTimer timer;
    private final MenuButtonCache menuButtons = new MenuButtonCache();
    private final EffectLayer effectLayer = new EffectLayer();
    private final GamePanelRenderer panelRenderer = new GamePanelRenderer();
    private final GamePanelInputBinder inputBinder = new GamePanelInputBinder();
    private final GameLoopController loopController;

    public GamePanel(GameState state) {
        this.state = state;
        this.loopController = new GameLoopController(state, effectLayer, menuButtons);
        GamePanelConfigurator.apply(this);
        inputBinder.install(state, this, menuButtons, this::requestGameFocus, this::repaint);
        timer = new GamePanelTimer(this::advanceFrame);
        timer.start();
    }

    public void requestGameFocus() {
        requestFocusInWindow();
    }

    public void stopLoop() {
        timer.stop();
    }

    public boolean isLoopRunning() {
        return timer.isRunning();
    }

    private void advanceFrame() {
        loopController.advanceFrame(getWidth(), getHeight());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        panelRenderer.paint(graphics, state, effectLayer, menuButtons, getWidth(), getHeight(), loopController.frame());
    }

}
