package com.starmaze.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class SaveData {
    private final Path file;
    private int highScore;
    private int gamesPlayed;
    private int victories;
    private boolean soundEnabled = true;

    private SaveData(Path file) {
        this.file = file;
    }

    public static SaveData loadDefault() {
        Path file = Path.of(System.getProperty("user.home"), ".starmaze", "save.properties");
        SaveData data = new SaveData(file);
        data.load();
        return data;
    }

    public static SaveData loadFrom(Path file) {
        SaveData data = new SaveData(file);
        data.load();
        return data;
    }

    private void load() {
        if (!Files.exists(file)) {
            return;
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
            highScore = parseInt(props.getProperty("highScore"), 0);
            gamesPlayed = parseInt(props.getProperty("gamesPlayed"), 0);
            victories = parseInt(props.getProperty("victories"), 0);
            soundEnabled = Boolean.parseBoolean(props.getProperty("soundEnabled", "true"));
        } catch (IOException ignored) {
            // A damaged save file should not prevent the game from opening.
        }
    }

    public void save() {
        Properties props = new Properties();
        props.setProperty("highScore", Integer.toString(highScore));
        props.setProperty("gamesPlayed", Integer.toString(gamesPlayed));
        props.setProperty("victories", Integer.toString(victories));
        props.setProperty("soundEnabled", Boolean.toString(soundEnabled));
        try {
            Files.createDirectories(file.getParent());
            try (OutputStream out = Files.newOutputStream(file)) {
                props.store(out, "Star Maze save data");
            }
        } catch (IOException ignored) {
            // Saves are nice-to-have; gameplay must continue without them.
        }
    }

    public int highScore() {
        return highScore;
    }

    public int gamesPlayed() {
        return gamesPlayed;
    }

    public int victories() {
        return victories;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.soundEnabled = soundEnabled;
    }

    public void rememberScore(int score) {
        if (score > highScore) {
            highScore = score;
            save();
        }
    }

    public void recordGame(int score, boolean victory) {
        gamesPlayed++;
        if (victory) {
            victories++;
        }
        if (score > highScore) {
            highScore = score;
        }
        save();
    }

    private int parseInt(String value, int fallback) {
        if (value == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }
}
