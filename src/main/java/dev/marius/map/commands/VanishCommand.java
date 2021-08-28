package dev.marius.map.commands;

import dev.marius.map.Command;
import dev.marius.map.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends Command {
    public static final List<Player> IN_VANISH = new ArrayList<>();

    public VanishCommand() {
        super("vanish");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length != 0) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: §7/vanish");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getVanishPermission(), () -> {
            if (IN_VANISH.contains(player)) {
                IN_VANISH.remove(player);

                for (Player target : Bukkit.getOnlinePlayers()) target.showPlayer(instance, player);

                player.sendMessage(prefix + ChatColor.GREEN + "You are not longer vanished to other players");
            } else {
                IN_VANISH.add(player);

                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!target.hasPermission(Plugin.getConfiguration().getVanishBypassPermission())) {
                        target.hidePlayer(instance, player);
                    }
                }

                player.sendMessage(prefix + ChatColor.GREEN + "You are vanished now to other players (except admins)");
            }
        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return "";
    }
}
