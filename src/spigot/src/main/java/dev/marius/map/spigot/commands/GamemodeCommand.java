package dev.marius.map.spigot.commands;

import dev.marius.map.spigot.Command;
import dev.marius.map.spigot.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.*;

import java.util.Locale;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode");
        this.addAlias("gm");
    }

    @Override
    protected void execute(Player player, String @NotNull [] args) {
        if (args.length == 0 || args.length > 2) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/gamemode <0, 1, 2, 3> [Target]");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getGamemodePermission(), () -> {
            if (args.length == 1) {
                GameMode gamemode = fetchGamemode(args[0]);
                if (gamemode != null) {
                    player.setGameMode(gamemode);
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Error: Unknown GameMode");
                }
            } else {
                Player target;
                if ((target = Bukkit.getPlayer(args[1])) == null) {
                    player.sendMessage(prefix + ChatColor.RED + "Error: There is no player with this name");
                    return;
                }

                GameMode gamemode = fetchGamemode(args[0]);
                if (gamemode != null) {
                    target.setGameMode(gamemode);
                    player.sendMessage(prefix + ChatColor.GREEN + "You set the GameMode for " + ChatColor.GOLD + args[1]);
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Error: Unknown GameMode");
                }
            }
        });
    }

    @Contract(pure = true)
    private @Nullable GameMode fetchGamemode(@NotNull String name) {
        switch (name.toLowerCase(Locale.ROOT)) {
            case "0": case "survival":
                return GameMode.SURVIVAL;
            case "1": case "creative":
                return GameMode.CREATIVE;
            case "2": case "adventure":
                return GameMode.ADVENTURE;
            case "3": case "spectator":
                return GameMode.SPECTATOR;
            default:
                return null;
        }
    }

    @Override
    protected String tabComplete(Player player, String @NotNull [] args) {
        return args.length == 1
                ? "survival,creative,adventure,spectator"
                : (args.length == 2
                    ? String.join(",", Bukkit.getOnlinePlayers().stream()
                        .filter(target -> target != player)
                        .map(HumanEntity::getName).toArray(String[]::new))
                    : "");
    }
}
