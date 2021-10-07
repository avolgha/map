package dev.marius.map.spigot.util;

import org.jetbrains.annotations.*;

public class IntUtil {
    private IntUtil() {}

    public static @Nullable Integer getFromString(String payload) {
        try {
            return Integer.parseInt(payload);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
