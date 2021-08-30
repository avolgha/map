package dev.marius.map.commands;

import dev.marius.map.Command;
import dev.marius.map.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TpHereCommand extends Command {
    public TpHereCommand() {
        super("tphere");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/tphere <Player>");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getTeleportHerePermission(), () -> {
            Player target;
            if ((target = Bukkit.getPlayer(args[0])) == null) {
                player.sendMessage(prefix + ChatColor.RED + "Error: There is no player with this name");
                return;
            }

            player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return args.length == 1 ? String.join(",", Bukkit.getOnlinePlayers().stream().filter(target -> target != player)
                .map(HumanEntity::getName).toArray(String[]::new)) : "";
    }
}
