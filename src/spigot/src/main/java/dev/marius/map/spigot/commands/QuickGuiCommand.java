package dev.marius.map.spigot.commands;

import dev.marius.map.spigot.Command;
import dev.marius.map.spigot.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class QuickGuiCommand extends Command {
    public QuickGuiCommand() {
        super("quickgui");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/quickgui <type>");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getQuickGuiPermission(), () -> {
            String type = args[0];
            if (type.equalsIgnoreCase("list")) {
                player.sendMessage(Arrays.stream(InventoryType.values()).map(Enum::name).collect(Collectors.joining()));
            } else {
                try {
                    InventoryType invType = InventoryType.valueOf(type.toUpperCase());
                    player.openInventory(Bukkit.createInventory(null, invType));
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Error: there is no inventory type with this name");
                }
            }
        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return args.length == 0 ? Arrays.stream(InventoryType.values()).map(Enum::name).collect(Collectors.joining()) : "";
    }
}
