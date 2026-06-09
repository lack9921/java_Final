package com.starmaze.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.RejectedExecutionException;

public final class SoundEngine {
    private static final int SAMPLE_RATE = 44100;
    private static final int MAX_QUEUED_SOUNDS = 12;
    private static final long MIN_EFFECT_GAP_NANOS = 35_000_000L;
    private static final AudioFormat TONE_FORMAT = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);

    private final ExecutorService executor;
    private final Map<SoundEffect, byte[]> toneCache = new EnumMap<>(SoundEffect.class);
    private final Map<SoundEffect, Long> lastPlayed = new EnumMap<>(SoundEffect.class);

    private volatile boolean enabled;
    private volatile boolean audioAvailable = true;
    private volatile boolean closed;

    public SoundEngine(boolean enabled) {
        this.enabled = enabled;
        executor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(MAX_QUEUED_SOUNDS),
                r -> {
                    Thread thread = new Thread(r, "starmaze-audio");
                    thread.setDaemon(true);
                    return thread;
                },
                new ThreadPoolExecutor.DiscardPolicy()
        );
        for (SoundEffect effect : SoundEffect.values()) {
            toneCache.put(effect, createTone(effect));
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isAudioAvailable() {
        return audioAvailable;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void play(SoundEffect effect) {
        if (!enabled || !audioAvailable || closed) {
            return;
        }
        long now = System.nanoTime();
        Long previous = lastPlayed.get(effect);
        if (previous != null && now - previous < MIN_EFFECT_GAP_NANOS) {
            return;
        }
        lastPlayed.put(effect, now);
        try {
            executor.submit(() -> playTone(effect));
        } catch (RejectedExecutionException ignored) {
            // Dropping a sound is preferable to stalling gameplay during shutdown.
        }
    }

    private void playTone(SoundEffect effect) {
        try {
            byte[] data = toneCache.get(effect);
            Clip clip = AudioSystem.getClip();
            clip.open(TONE_FORMAT, data, 0, data.length);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            });
            clip.start();
        } catch (Exception ex) {
            audioAvailable = false;
        }
    }

    private byte[] createTone(SoundEffect effect) {
        int samples = Math.max(1, SAMPLE_RATE * effect.durationMs() / 1000);
        byte[] data = new byte[samples];
        double angular = 2.0 * Math.PI * effect.frequency() / SAMPLE_RATE;
        for (int i = 0; i < samples; i++) {
            double attack = Math.min(1.0, i / (SAMPLE_RATE * 0.015));
            double release = Math.min(1.0, (samples - i) / (SAMPLE_RATE * 0.035));
            double envelope = Math.max(0.0, Math.min(attack, release));
            double harmonic = Math.sin(i * angular) + 0.35 * Math.sin(i * angular * 2.0);
            data[i] = (byte) (harmonic * 60.0 * effect.volume() * envelope);
        }
        return data;
    }

    public void shutdown() {
        closed = true;
        executor.shutdownNow();
    }
}
