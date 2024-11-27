package com.github.pluginsell.xpshop;

import com.github.pluginsell.xpshop.commands.XpShopCommand;
import com.github.pluginsell.xpshop.events.ShopInventoryClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.*;


public final class Main extends JavaPlugin {
    public static ConfigManager data;
    public static String prefix = color("&3XPShop &b> ");

    @Override
    public void onEnable() {
        loadConfigManager();
        getCommand("xpshop").setExecutor(new XpShopCommand());
        getPluginManager().registerEvents(new ShopInventoryClickEvent(), this);
        getConsoleSender().sendMessage(prefix + color("&aPlugin has been enabled."));
    }

    @Override
    public void onDisable() {
        loadConfigManager();
        getConsoleSender().sendMessage(prefix + color("&cPlugin has been disabled."));
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void loadConfigManager() {
        data = new ConfigManager(Main.getPlugin(Main.class));
        getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).getConfig().options().copyDefaults();
        getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).saveDefaultConfig();
        data.getXpShop().options().copyDefaults();
        data.saveDefaultXpShop();
        getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).saveDefaultConfig();
    }
}
