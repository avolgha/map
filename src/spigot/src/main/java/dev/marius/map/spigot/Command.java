package dev.marius.map.spigot;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {
    protected final String prefix = Plugin.getConfiguration().getChatPrefix();
    protected final Plugin instance = Plugin.instance;

    private final String name;
    private final List<String> aliases;

    public Command(String name) {
        this.name = name;
        this.aliases = new ArrayList<>();
    }

    protected abstract void execute(Player player, String[] args);
    protected abstract String tabComplete(Player player, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command,
                             @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            execute((Player) sender, args);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command,
                                      @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            return Arrays.asList(tabComplete((Player) sender, args).split(","));
        }
        return Collections.emptyList();
    }

    protected void addAlias(String alias) {
        this.aliases.add(alias);
    }

    public void register(@NotNull JavaPlugin plugin) {
        PluginCommand command = plugin.getCommand(this.name);
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
            command.setAliases(this.aliases);
        }
    }

    protected void hasPermission(@NotNull Player player, Permission permission, Runnable action) {
        if (player.hasPermission(permission)) {
            action.run();
        } else {
            player.sendMessage(prefix + ChatColor.RED + "You don't have the permissions to execute this command");
        }
    }
}
