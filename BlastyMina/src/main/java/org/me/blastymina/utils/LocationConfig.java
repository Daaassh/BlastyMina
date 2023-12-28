package org.me.blastymina.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.me.blastymina.BlastyMina;

import java.io.File;
import java.io.IOException;

public class LocationConfig {

    private BlastyMina plugin;
    private FileConfiguration config;
    private File configFile;

    public LocationConfig(BlastyMina plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "locations.yml");
        this.config = plugin.getConfig();
        createConfig();
    }

    private void createConfig() {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("locations.yml", false);
        }
    }

    public double loadLocation(String key) {
        if (config.contains("locations." + key)) {
            return (double) config.get("locations." + key);
        }
        return 0.0;
    }
    public String loadWorld(String key) {
        if (config.contains("locations." + key)) {
            return (String) config.get("locations." + key);
        }
        return null;
    }
    public void reload() throws IOException, InvalidConfigurationException {
        config.load(configFile);
    }

    public void set(String key, double location) {
        config.set("locations." + key, location);
        saveConfig();
    }
    public void setWorld(String key, String name) {
        config.set("locations." + key, name);
    }
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
