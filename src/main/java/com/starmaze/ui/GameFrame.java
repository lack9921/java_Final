package com.starmaze.ui;

import com.starmaze.audio.SoundEngine;
import com.starmaze.model.GameState;
import com.starmaze.model.SaveData;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class GameFrame extends JFrame {
    private final SoundEngine soundEngine;

    public GameFrame() {
        SaveData saveData = SaveData.loadDefault();
        soundEngine = new SoundEngine(saveData.isSoundEnabled());
        GameState state = new GameState(saveData, soundEngine);
        GamePanel panel = new GamePanel(state);
        setTitle("星轨迷阵 Star Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        setMinimumSize(new Dimension(VisualConfig.WINDOW_MIN_WIDTH, VisualConfig.WINDOW_MIN_HEIGHT));
        setSize(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.stopLoop();
                soundEngine.shutdown();
            }
        });
        EventQueue.invokeLater(panel::requestGameFocus);
    }

    public static void closeOwningWindow(GamePanel panel) {
        Window window = javax.swing.SwingUtilities.getWindowAncestor(panel);
        if (window != null) {
            window.dispose();
        }
    }
}
