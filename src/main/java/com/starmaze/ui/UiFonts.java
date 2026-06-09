package com.starmaze.ui;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public final class UiFonts {
    private static final String FAMILY = "Microsoft YaHei UI";
    private static final Map<String, Font> CACHE = new HashMap<>();

    private UiFonts() {
    }

    public static Font of(int style, int size) {
        String key = style + ":" + size;
        return CACHE.computeIfAbsent(key, ignored -> new Font(FAMILY, style, size));
    }
}
