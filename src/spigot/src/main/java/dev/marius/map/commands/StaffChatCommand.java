package dev.marius.map.commands;

import dev.marius.map.Command;
import dev.marius.map.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffChatCommand extends Command {
    public StaffChatCommand() {
        super("sm");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.GRAY + "/sm <Message ...>");
            return;
        }

        hasPermission(player, Plugin.getConfiguration().getStaffChatSendPermission(), () -> {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }

            Bukkit.getOnlinePlayers().stream()
                    .filter(target -> target.hasPermission(Plugin.getConfiguration().getStaffChatReceivePermission()))
                    .forEach(target -> target.sendMessage(Plugin.getConfiguration().getStaffMessagePrefix() +
                            String.format(ChatColor.GOLD + "%s " + ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "%s",
                                    target.getDisplayName(), ChatColor.translateAlternateColorCodes('&', message.toString()))));

        });
    }

    @Override
    protected String tabComplete(Player player, String[] args) {
        return "<Message ...>";
    }
}
