package dev.marius.map.spigot.commands;

import dev.marius.map.spigot.Command;
import dev.marius.map.spigot.Plugin;
import dev.marius.map.spigot.events.PlayerClickListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerguiCommand extends Command {
    public PlayerguiCommand() {
        super("playergui");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length != 0) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: §7/playergui");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getPlayerguiPermission(), () -> {
            if (PlayerClickListener.ACCESS_LIST.contains(player)) {
                PlayerClickListener.ACCESS_LIST.remove(player);
                player.sendMessage(prefix + ChatColor.GREEN + "You have not longer access to player guis");
            } else {
                PlayerClickListener.ACCESS_LIST.add(player);
                player.sendMessage(prefix + ChatColor.GREEN + "You have now access to player guis");
            }
        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return "";
    }
}
