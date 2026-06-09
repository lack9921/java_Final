package com.starmaze.ui;

import com.starmaze.model.GameState;

import java.awt.Graphics2D;

public final class GameplayLayerRenderer {
    private final HudRenderer hudRenderer = new HudRenderer();
    private final FooterRenderer footerRenderer = new FooterRenderer();
    private final GameSceneRenderer sceneRenderer = new GameSceneRenderer();
    private final EffectRenderer effectRenderer = new EffectRenderer();

    public void paint(Graphics2D g, GameState state, EffectLayer effectLayer, int width, int height, int frame) {
        Graphics2D scene = (Graphics2D) g.create();
        effectRenderer.applyScreenShake(scene, effectLayer.effects(), frame);
        hudRenderer.paint(scene, state, width);
        BoardMetrics metrics = sceneRenderer.paint(scene, state, width, height);
        if (metrics != null) {
            effectRenderer.paint(scene, effectLayer.effects(), metrics, frame);
        }
        footerRenderer.paint(scene, width, height);
        scene.dispose();
    }

    public int boardCacheBuilds() {
        return sceneRenderer.boardCacheBuilds();
    }
}
