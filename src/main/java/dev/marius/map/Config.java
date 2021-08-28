package dev.marius.map;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

public class Config {
    private String chatPrefix = "&8[&cMAP&8] &7";
    private String staffMessagePrefix = "&8[&aSM&8] &7";

    private String staffChatReceivePermission = "map.sm.get";
    private String staffChatSendPermission = "map.sm.send";
    private String teleportToPlayerPermission = "map.tpto.player";
    private String teleportToCoordPermission = "map.tpto.coord";
    private String teleportHerePermission = "map.tphere";
    private String gamemodePermission = "map.gamemode";
    private String playerguiPermission = "map.playergui";
    private String vanishPermission = "map.vanish";
    private String vanishBypassPermission = "map.vanish.bypass";

    public String getChatPrefix() {
        return ChatColor.translateAlternateColorCodes('&', chatPrefix);
    }

    public String getStaffMessagePrefix() {
        return ChatColor.translateAlternateColorCodes('&', staffMessagePrefix);
    }

    public Permission getStaffChatReceivePermission() {
        return new Permission(staffChatReceivePermission);
    }

    public Permission getStaffChatSendPermission() {
        return new Permission(staffChatSendPermission);
    }

    public Permission getTeleportToPlayerPermission() {
        return new Permission(teleportToPlayerPermission);
    }

    public Permission getTeleportToCoordPermission() {
        return new Permission(teleportToCoordPermission);
    }

    public Permission getTeleportHerePermission() {
        return new Permission(teleportHerePermission);
    }

    public Permission getGamemodePermission() {
        return new Permission(gamemodePermission);
    }

    public Permission getPlayerguiPermission() {
        return new Permission(playerguiPermission);
    }

    public Permission getVanishPermission() {
        return new Permission(vanishPermission);
    }

    public Permission getVanishBypassPermission() {
        return new Permission(vanishBypassPermission);
    }
}
