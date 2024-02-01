package com.alpineclient.plugin.config;

import com.alpineclient.plugin.Plugin;
import com.alpineclient.plugin.Reference;
import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author BestBearr
 * Created on 22/11/2021
 */
public final class ConfigWrapper {

    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;
    private final String fileName;

    public ConfigWrapper(String fileName) {
        this.plugin = Plugin.getInstance();
        this.fileName = fileName;
    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), this.fileName);

        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defConfigStream = this.plugin.getResource(this.fileName);
        if (defConfigStream != null) {
            this.config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        }
    }

    public void saveConfig() {
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {
            Reference.LOGGER.error("Unable to save config", ex);
        }
    }

    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }
}
