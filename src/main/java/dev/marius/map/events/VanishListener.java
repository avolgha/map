package dev.marius.map.events;

import dev.marius.map.Plugin;
import dev.marius.map.commands.VanishCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.*;

public class VanishListener implements Listener {
    private final Plugin instance;

    public VanishListener(Plugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(@NotNull final PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission(Plugin.getConfiguration().getVanishBypassPermission())) return;

        for (Player vanished : VanishCommand.IN_VANISH) {
            event.getPlayer().hidePlayer(instance, vanished);
        }
    }

    @EventHandler
    public void onQuit(@NotNull final PlayerQuitEvent event) {
        if (VanishCommand.IN_VANISH.contains(event.getPlayer())) {
            VanishCommand.IN_VANISH.remove(event.getPlayer());
        }
    }
}
