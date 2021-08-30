package dev.marius.map.events;

import dev.marius.map.Plugin;
import dev.marius.map.util.ItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerClickListener implements Listener {
    public static final List<Player> ACCESS_LIST = new ArrayList<>();

    private static final String invName = ChatColor.DARK_GRAY + "» " + ChatColor.DARK_BLUE + ChatColor.BOLD + "PlayerGUI " +
            ChatColor.DARK_GRAY + "- " + ChatColor.AQUA;

    @EventHandler
    public void onPlayerInteractAtEntityEventListener(@NotNull final PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            final Player
                    player = event.getPlayer(),
                    target = (Player) event.getRightClicked();

            if (player.hasPermission(Plugin.getConfiguration().getPlayerguiPermission()) && ACCESS_LIST.contains(player)) {
                final Inventory inventory = Bukkit.createInventory(null, 9*6, invName + target.getName());

                for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemUtility.getPlaceholderStack());

                inventory.setItem(13, getHeadItem(target));
                inventory.setItem(20, getKickButton());
                inventory.setItem(24, getBanButton());

                player.openInventory(inventory);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(@NotNull final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (!player.hasPermission("map.playergui")) return;
        if (!ACCESS_LIST.contains(player)) return;

        if (event.getView().getTitle().startsWith(invName) &&
            event.getCurrentItem() != null &&
            !(event.getCurrentItem().getType() == Material.AIR)) {
            String targetName = event.getView().getTitle().replaceFirst(invName, "");
            Player target;
            if ((target = Bukkit.getPlayer(targetName)) == null) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(Plugin.getConfiguration().getChatPrefix() + ChatColor.RED + "This player isn't online");
                return;
            }

            switch (event.getCurrentItem().getType()) {
                case RED_CONCRETE:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + targetName + " You were banned by an operator");
                    event.getWhoClicked().closeInventory();
                    break;
                case GREEN_CONCRETE:
                    target.kickPlayer(ChatColor.RED + "You were kicked by an operator");
                    event.getWhoClicked().closeInventory();
                    break;
                default:
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_FALL, 1.0F, 1.0F);
                    break;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(@NotNull final PlayerQuitEvent event) {
        ACCESS_LIST.remove(event.getPlayer());
    }

    private @NotNull ItemStack getBanButton() {
        ItemStack stack = new ItemStack(Material.RED_CONCRETE);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "Ban player");
        stack.setItemMeta(meta);
        return stack;
    }

    private @NotNull ItemStack getKickButton() {
        ItemStack stack = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + "Kick player");
        stack.setItemMeta(meta);
        return stack;
    }

    private @NotNull ItemStack getHeadItem(@NotNull Player target) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();

        assert meta != null;
        meta.setDisplayName(String.format(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD + "%s " + ChatColor.DARK_GRAY + "«",
                target.getName()));

        final String format = ChatColor.DARK_GRAY + "» " + ChatColor.GREEN + "%s" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "%s";

        meta.setLore(Arrays.asList(
            String.format(format, "Displayname", target.getUniqueId()),
            String.format(format, "Health", String.format("%.2f", target.getHealth() * 1.0F)),
            String.format(format, "Ping", target.getPing()),
            String.format(format, "Address", target.getAddress().getHostName()),
            String.format(format, "Locale", target.getLocale()),
            String.format(format, "GameMode", target.getGameMode().name())
        ));

        stack.setItemMeta(meta);
        return stack;
    }
}
