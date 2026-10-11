package org.displayRigger.common;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.DisplayRigger;

import java.io.File;

public class ConfigManager {
    private static YamlConfiguration yamlConfiguration;

    public static void loadConfig() {
        File configPath = new File(JavaPlugin.getPlugin(DisplayRigger.class).getDataFolder(), "config.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(configPath);
    }

    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
