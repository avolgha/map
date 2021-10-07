package dev.marius.map.spigot.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.*;

public class ItemUtility {
    public static @NotNull ItemStack getPlaceholderStack() {
        ItemStack stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GRAY + "");
        stack.setItemMeta(meta);
        return stack;
    }
}
