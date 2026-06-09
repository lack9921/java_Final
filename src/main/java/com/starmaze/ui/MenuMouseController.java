package com.starmaze.ui;

import com.starmaze.model.GameState;

import javax.swing.JComponent;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class MenuMouseController {
    private final GameState state;
    private final JComponent component;
    private final MenuButtonCache buttons;
    private final MenuActionHandler actionHandler;
    private final Runnable focusRequester;
    private final Runnable repaint;

    public MenuMouseController(GameState state, JComponent component, MenuButtonCache buttons,
                               MenuActionHandler actionHandler, Runnable focusRequester, Runnable repaint) {
        this.state = state;
        this.component = component;
        this.buttons = buttons;
        this.actionHandler = actionHandler;
        this.focusRequester = focusRequester;
        this.repaint = repaint;
    }

    public void install() {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateButtons();
                MenuButton button = buttons.hit(e.getX(), e.getY());
                if (button != null) {
                    actionHandler.handle(state, button.action());
                    focusRequester.run();
                    repaint.run();
                }
            }
        });
        component.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateButtons();
                component.setCursor(buttons.hit(e.getX(), e.getY()) != null
                        ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                        : Cursor.getDefaultCursor());
            }
        });
    }

    private void updateButtons() {
        buttons.update(state.mode(), component.getWidth(), component.getHeight());
    }
}
