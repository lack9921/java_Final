package com.starmaze.ui;

import javax.swing.JPanel;

public final class GamePanelConfigurator {
    private GamePanelConfigurator() {
    }

    public static void apply(JPanel panel) {
        panel.setFocusable(true);
        panel.setDoubleBuffered(true);
        panel.setBackground(VisualConfig.BACK_TOP);
    }
}
