package com.github.pluginsell.xpshop.commands.admin;

import com.github.pluginsell.xpshop.Main;
import com.github.pluginsell.xpshop.inventories.ExpShopInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XpShopAdmin {
    public static void shopAdmin(Player player, String[] args) {
        String adminType = null;

        if (args.length >= 2) {
            adminType = argType(player, args[1]);
        }
        if(adminType != null) {
            if(adminType.equalsIgnoreCase("enable")) {
                shopEnable(player);
            } else if(adminType.equalsIgnoreCase("disable")) {
                shopDisable(player);
            } else if (adminType.equalsIgnoreCase("add")) {
                int amount = 0;
                int cost = 1;
                try {
                    amount = Integer.parseInt(args[2]);
                    cost = Integer.parseInt(args[3]);
                } catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {}
                shopItemAdd(player, amount, cost);
            } else if(adminType.equalsIgnoreCase("remove")) {
                if(ExpShopInventory.xpInv(true) != null) {
                    player.openInventory(ExpShopInventory.xpInv(true));
                } else {
                    player.sendMessage(Main.prefix + Main.color("&cXP Shop has been disabled."));
                }
            } else {
                player.sendMessage(Main.prefix + Main.color("&a/xpshop admin (enable | disable | add | remove)"));
            }
        } else {
            player.sendMessage(Main.prefix + Main.color("&a/xpshop admin (enable | disable | add | remove)"));
        }
    }

    private static String argType(Player player, String string) {
        if (!player.hasPermission("shopui.xpshop.admin")) {
            player.sendMessage(Main.prefix + Main.color("&cYou do not have permission to use that command."));
            return null;
        }
        if (string == null) {
            player.sendMessage(Main.prefix + Main.color("&a/xpshop admin (enable | disable | add | remove)"));
        }
        return string;
    }

    private static void shopEnable(Player player) {
        if (Main.data.getConfig().contains("shop.xpshop.disabled")) {
            if (!Main.data.getConfig().getBoolean("shop.xpshop.disabled")) {
                player.sendMessage(Main.prefix + Main.color("&cXP Shop has already been enabled."));
            } else {
                Main.data.getConfig().set("shop.xpshop.disabled", false);
                Bukkit.broadcastMessage(Main.prefix + Main.color("&aXP Shop has been enabled."));
            }
        } else {
            Main.data.getConfig().set("shop.xpshop.disabled", false);
            Bukkit.broadcastMessage(Main.prefix + Main.color("&aXP Shop has been enabled."));
        }
        Main.data.saveConfig();
        Main.data.reloadConfig();
    }

    private static void shopDisable(Player player) {
        if (Main.data.getConfig().contains("shop.xpshop.disabled")) {
            if (Main.data.getConfig().getBoolean("shop.xpshop.disabled")) {
                player.sendMessage(Main.prefix + Main.color("&cXP Shop has already been disabled."));
            } else {
                Main.data.getConfig().set("shop.xpshop.disabled", true);
                Bukkit.broadcastMessage(Main.prefix + Main.color("&cXP Shop has been disabled."));
            }
        } else {
            Main.data.getConfig().set("shop.xpshop.disabled", true);
            Bukkit.broadcastMessage(Main.prefix + Main.color("&cXP Shop has been disabled."));
        }
        Main.data.saveConfig();
        Main.data.reloadConfig();
    }

    private static void shopItemAdd(Player player, int amount, int cost) {
        if (player.getItemInHand() != null && !player.getItemInHand().getType().equals(Material.AIR)) {
            ItemStack itemStack = player.getItemInHand().clone();
            if(amount <= 0) {
                amount = player.getItemInHand().getAmount();
            } else if(amount > 64) {
                amount = 64;
            }
            String setInt = "1";
            boolean addItem = true;
            if(!Main.data.getXpShop().getKeys(false).isEmpty()) {
                setInt = String.valueOf(Main.data.getXpShop().getKeys(false).size() + 1);
                for (String integer: Main.data.getXpShop().getKeys(false)) {
                    ItemStack item = Main.data.getXpShop().getItemStack(integer).clone();
                    ItemMeta meta = item.getItemMeta();
                    if(meta.hasLore()) {
                        List<String> lores = meta.getLore();
                        for (String lore: meta.getLore()) {
                            if(lore.contains("Cost:")) {
                                lores.remove(lore);
                            }
                        }
                        meta.setLore(lores);
                        item.setItemMeta(meta);
                    }
                    itemStack.setAmount(item.getAmount());
                    if(itemStack.equals(item)) {
                        addItem = false;
                    }
                }
            }
            if(addItem) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(Main.color("&6Cost: &d" + cost + " XP"));
                if(itemMeta.hasLore()) {
                    lore.addAll(itemMeta.getLore());
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                itemStack.setAmount(amount);
                Main.data.getXpShop().set(setInt, itemStack);
                Main.data.saveXpShop();
                Main.data.reloadXpShop();
                player.sendMessage(Main.prefix + Main.color("&aYou have added an item to the shop."));
            } else {
                player.sendMessage(Main.prefix + Main.color("&cThat item has already been added."));
            }

        } else {
            player.sendMessage(Main.prefix + Main.color("&cYou are not holding an item."));
        }
    }

}
