package org.displayRigger.common;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.DisplayRigger;

import java.io.File;
import java.util.UUID;

public class GroupManager {

    private static YamlConfiguration yamlConfiguration;
    private static File file;

    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public static void load() {
        file = new File(JavaPlugin.getPlugin(DisplayRigger.class).getDataFolder(), "data.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    }

    public static void groupAdd(String groupID, CommandSender sender) {
        if (yamlConfiguration.isSet(groupID)) {
            sender.sendMessage(ChatColor.RED + "该组已存在");
            return;
        }
        yamlConfiguration.createSection(groupID);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "添加组失败");
        }
        sender.sendMessage(ChatColor.GREEN + "组" + groupID + "已添加");
    }

    public static ConfigurationSection getGroup(String groupID) {
        return yamlConfiguration.getConfigurationSection(groupID);
    }

    public static boolean addObjectToGroup(String groupID, String objectID, UUID uuid) {
        if (!yamlConfiguration.isSet(groupID)) {
            return false;
        }
        yamlConfiguration.set(groupID, objectID);
        yamlConfiguration.set(groupID + "." + objectID + ".uuid", uuid);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
