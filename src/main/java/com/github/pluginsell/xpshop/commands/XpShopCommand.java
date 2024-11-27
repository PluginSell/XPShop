package com.github.pluginsell.xpshop.commands;

import com.github.pluginsell.xpshop.commands.admin.XpShopAdmin;
import com.github.pluginsell.xpshop.commands.xpshop.XpShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XpShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("admin")) {
                XpShopAdmin.shopAdmin(player, args);
            } else {
                XpShop.openShop(player);
            }
        } else {
            XpShop.openShop(player);
        }
        return true;
    }
}
