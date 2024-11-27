package com.github.pluginsell.xpshop.commands.xpshop;

import com.github.pluginsell.xpshop.Main;
import com.github.pluginsell.xpshop.inventories.ExpShopInventory;
import org.bukkit.entity.Player;

public class XpShop {
    public static void openShop(Player player) {
        if(player.hasPermission("shopui.xpshop")) {
            if((Main.data.getConfig().contains("shop.xpshop.disabled") && Main.data.getConfig().getBoolean("shop.xpshop.disabled")) || ExpShopInventory.xpInv(false) == null) {
                player.sendMessage(Main.prefix + Main.color("&cXP Shop has been disabled."));
            } else {
                player.openInventory(ExpShopInventory.xpInv(false));
            }
        } else {
            player.sendMessage(Main.prefix + Main.color("&cYou do not have permission to use that command."));
        }
    }
}
