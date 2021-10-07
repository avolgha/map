package dev.marius.map.script;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.*;

public class ScriptContext {
    public static @NotNull Block getBlock(@NotNull World world, int x, int y, int z) {
        return world.getBlockAt(x, y, z);
    }
}
