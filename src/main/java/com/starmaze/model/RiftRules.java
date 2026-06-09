package com.starmaze.model;

import java.util.Random;

public final class RiftRules {
    private RiftRules() {
    }

    public static RiftOutcome rollOutcome(Random random) {
        return random.nextBoolean() ? RiftOutcome.WARP : RiftOutcome.PHASE_GAIN;
    }

    public static int phaseGain() {
        return GameConfig.PHASE_GAIN_RIFT;
    }

    public static int teleportMinDistance() {
        return GameConfig.RIFT_TELEPORT_MIN_DISTANCE;
    }
}
