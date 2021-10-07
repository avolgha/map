package dev.marius.map.spigot.commands;

import dev.marius.map.spigot.Command;
import dev.marius.map.spigot.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AlertCommand extends Command {
    public AlertCommand() {
        super("alert");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/alert <as | none> <message ...>");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getAlertPermission(), () -> {
            String as = args[0];
            StringBuilder message = new StringBuilder();

            for (int i = 1; i < args.length; i++)
                message.append(args[i]);

            String send;
            if (as.equalsIgnoreCase("none"))
                send = message.toString();
            else
                send = String.format("[%s]: %s", as, message);

            Bukkit.broadcastMessage(ChatColor.RED + "[Alert]: " + ChatColor.translateAlternateColorCodes('&', send));
        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return args.length == 0 ? "<as | none>" : "<message ...>";
    }
}
