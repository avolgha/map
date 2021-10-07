package dev.marius.map.script.rhino;

import org.mozilla.javascript.ClassShutter;

public class MinecraftHidingClassShutter implements ClassShutter {
    @Override
    public boolean visibleToScripts(String fullClassName) {
        if (!fullClassName.contains(".")) {
            return false;
        }
        return !fullClassName.startsWith("net.minecraft");
    }
}
