package com.starmaze;

import com.starmaze.ui.GameFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class StarMazeApp {
    private StarMazeApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // The custom painted UI works even with the default look and feel.
            }
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}
