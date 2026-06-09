package com.starmaze.ui;

import com.starmaze.model.Direction;
import com.starmaze.model.GameMode;
import com.starmaze.model.GameState;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public final class InputController {
    private final GameState state;
    private final JComponent component;
    private final Runnable repaint;

    public InputController(GameState state, JComponent component, Runnable repaint) {
        this.state = state;
        this.component = component;
        this.repaint = repaint;
    }

    public void install() {
        installMovementBindings();
        installCommandBindings();
    }

    private void installMovementBindings() {
        bindMovement(KeyEvent.VK_UP, "up", Direction.UP);
        bindMovement(KeyEvent.VK_W, "w", Direction.UP);
        bindMovement(KeyEvent.VK_DOWN, "down", Direction.DOWN);
        bindMovement(KeyEvent.VK_S, "s", Direction.DOWN);
        bindMovement(KeyEvent.VK_LEFT, "left", Direction.LEFT);
        bindMovement(KeyEvent.VK_A, "a", Direction.LEFT);
        bindMovement(KeyEvent.VK_RIGHT, "right", Direction.RIGHT);
        bindMovement(KeyEvent.VK_D, "d", Direction.RIGHT);
    }

    private void installCommandBindings() {
        bind(KeyEvent.VK_SPACE, "phase", state::activatePhase);
        bind(KeyEvent.VK_R, "rewind", state::rewind);
        bind(KeyEvent.VK_P, "pause", state::togglePause);
        bind(KeyEvent.VK_ESCAPE, "escape", () -> InputActions.handleEscape(state));
        bind(KeyEvent.VK_ENTER, "enter", () -> InputActions.handleEnter(state));
        bind(KeyEvent.VK_H, "help", () -> state.setMode(GameMode.HELP));
        bind(KeyEvent.VK_M, "sound", state::toggleSound);
    }

    private void bindMovement(int keyCode, String name, Direction direction) {
        bind(keyCode, name, () -> state.movePlayer(direction));
    }

    private void bind(int keyCode, String name, Runnable action) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), name);
        actionMap.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                action.run();
                repaint.run();
            }
        });
    }

}
