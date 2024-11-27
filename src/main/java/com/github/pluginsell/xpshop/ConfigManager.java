package com.github.pluginsell.xpshop;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {
    private final Main plugin;
    private FileConfiguration config;
    private File configFile;

    private FileConfiguration xpShop;
    private File xpShopFile;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource("config.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            config.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return (YamlConfiguration) config;
    }

    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save config to " + configFile + e);
        }
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
    }

    public void reloadXpShop() {
        if (xpShopFile == null) {
            xpShopFile = new File(plugin.getDataFolder(), "xpshop.yml");
        }
        xpShop = YamlConfiguration.loadConfiguration(xpShopFile);
        InputStream defaultStream = plugin.getResource("xpshop.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            xpShop.setDefaults(defaultConfig);
        }
    }

    public YamlConfiguration getXpShop() {
        if (xpShop == null) {
            reloadXpShop();
        }
        return (YamlConfiguration) xpShop;
    }

    public void saveXpShop() {
        if (xpShop == null || xpShopFile == null) {
            return;
        }
        try {
            xpShop.save(xpShopFile);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save messages data to " + xpShopFile + e);
        }
    }

    public void saveDefaultXpShop() {
        if (xpShopFile == null) {
            xpShopFile = new File(plugin.getDataFolder(), "xpshop.yml");
        }
        if (!xpShopFile.exists()) {
            plugin.saveResource("xpshop.yml", false);
        }
    }

}
