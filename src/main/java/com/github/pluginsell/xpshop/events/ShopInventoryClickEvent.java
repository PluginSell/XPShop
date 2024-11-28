package com.github.pluginsell.xpshop.events;

import com.github.pluginsell.xpshop.Main;
import com.github.pluginsell.xpshop.inventories.ExpShopInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopInventoryClickEvent implements Listener {
    @EventHandler
    void onInventoryClickEvent(InventoryClickEvent e) {
        try {
            if (e.getView().getTopInventory().getTitle().equalsIgnoreCase(ExpShopInventory.xpInv(false).getTitle())) {
                e.setCancelled(true);
                if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    addItem((Player) e.getWhoClicked(), e.getClickedInventory().getItem(e.getSlot()));
                }
            } else if (e.getView().getTopInventory().getTitle().equalsIgnoreCase(ExpShopInventory.xpInv(true).getTitle())) {
                e.setCancelled(true);
                if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    removeItem((Player) e.getWhoClicked(), e.getClickedInventory().getItem(e.getSlot()));
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void removeItem(Player player, ItemStack item) {
        if ((item != null && !(item.getType().equals(Material.AIR))) && (!Main.data.getXpShop().getKeys(false).isEmpty())) {
            for (String number : Main.data.getXpShop().getKeys(false)) {
                if (Main.data.getXpShop().getItemStack(number).equals(item)) {
                    Main.data.getXpShop().set(number, null);
                    Main.data.saveXpShop();
                    Main.data.reloadXpShop();
                    player.sendMessage(Main.prefix + Main.color("&cYou have removed an item from the shop."));
                    player.closeInventory();
                    player.openInventory(ExpShopInventory.xpInv(true));
                }
            }
        } else {
            player.sendMessage(Main.prefix + Main.color("&cThat item is not in the shop."));
            player.closeInventory();
            player.openInventory(ExpShopInventory.xpInv(true));
        }
    }

    private void addItem(Player player, ItemStack item) {
        if (item.getItemMeta().hasLore()) {
            List<String> lores = new ArrayList<>(item.getItemMeta().getLore());
            int cost = 0;
            if (!lores.isEmpty()) {
                for (String lore : item.getItemMeta().getLore()) {
                    if (lore.contains("Cost:")) {
                        lores.remove(lore);
                        item = item.clone();
                        ItemMeta meta = item.getItemMeta();
                        meta.setLore(lores);
                        item.setItemMeta(meta);
                        cost = Integer.parseInt(ChatColor.stripColor(lore.replaceAll(Main.color("&6Cost: "), "").replaceAll(Main.color(" XP"), "")));
                        if (cost <= player.getTotalExperience()) {
                            if (player.getInventory().firstEmpty() != -1) {
                                player.sendMessage(Main.prefix + Main.color("&aYou have purchased an item from the shop."));
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "xp set " + player.getName() + " " + (player.getTotalExperience() - cost));
                                player.getInventory().addItem(item);
                            } else {
                                player.sendMessage(Main.prefix + Main.color("&cThat player's inventory is full."));
                            }
                        } else {
                            player.sendMessage(Main.prefix + Main.color("&cYou do not have enough experience for that."));
                        }
                    }
                }
            }
        }
    }
}
