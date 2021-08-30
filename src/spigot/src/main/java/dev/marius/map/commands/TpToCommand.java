package dev.marius.map.commands;

import dev.marius.map.Command;
import dev.marius.map.Plugin;
import dev.marius.map.util.IntUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.*;

public class TpToCommand extends Command {
    public TpToCommand() {
        super("tpto");
    }

    @Override
    protected void execute(Player player, String @NotNull [] args) {
        if (!(args.length == 2 || args.length == 4)) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/tpto player <Player> " + ChatColor.RED +
                    "or " + ChatColor.GRAY + "/tpto coord <x> <y> <z>");
            return;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("player")) {
            hasPermission(player, Plugin.getConfiguration().getTeleportToPlayerPermission(), () -> {
                Player target;
                if ((target = Bukkit.getPlayer(args[1])) == null) {
                    player.sendMessage(prefix + ChatColor.RED + "Error: There is no player with this name");
                    return;
                }

                player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
            });
        } else if (args.length == 4 && args[0].equalsIgnoreCase("coord")) {
            hasPermission(player, Plugin.getConfiguration().getTeleportToCoordPermission(), () -> {
                Integer x = IntUtil.getFromString(args[1]);
                Integer y = IntUtil.getFromString(args[2]);
                Integer z = IntUtil.getFromString(args[3]);

                if (x == null || y == null || z == null) {
                    player.sendMessage(prefix + ChatColor.RED + "Error: Please enter valid numbers as coordinates");
                } else {
                    player.teleport(new Location(player.getWorld(), x, y, z), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
            });
        }
    }

    @Override
    protected String tabComplete(Player player, String @NotNull [] args) {
        switch (args.length) {
            case 1:
                return "player,coord";
            case 2:
                if (args[0].equalsIgnoreCase("player"))
                    return String.join(",", Bukkit.getOnlinePlayers().stream().filter(target -> target != player)
                            .map(HumanEntity::getName).toArray(String[]::new));
                else if (args[0].equalsIgnoreCase("coord"))
                    return "x";
                else
                    return "";
            case 3:
                return args[0].equalsIgnoreCase("coord") ? "y" : "";
            case 4:
                return args[0].equalsIgnoreCase("coord") ? "z" : "";
            default:
                return "";
        }
    }
}
