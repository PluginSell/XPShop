package com.github.pluginsell.xpshop.inventories;

import com.github.pluginsell.xpshop.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ExpShopInventory {
    public static Inventory xpInv(Boolean bool) {
        Inventory inventory = null;
        if (!Main.data.getXpShop().getKeys(false).isEmpty()) {
            int size = 9;
            for (int i = size; i < 54; i += 9) {
                if(Main.data.getXpShop().getKeys(false).size() > i) {
                    size = i+9;
                }
            }
            if(bool) {
                inventory = Bukkit.createInventory(null, size, Main.color("&b&lXP Shop &4&l(Remove)"));
            } else {
                inventory = Bukkit.createInventory(null, size, Main.color("&b&lXP Shop"));
            }
            for (String itemName : Main.data.getXpShop().getKeys(false)) {
                inventory.addItem(Main.data.getXpShop().getItemStack(itemName));
            }
        }
        return inventory;
    }
}
