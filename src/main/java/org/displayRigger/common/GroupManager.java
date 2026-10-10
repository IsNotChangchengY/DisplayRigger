package org.displayRigger.common;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.DisplayRigger;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GroupManager {

    private static YamlConfiguration yamlConfiguration;
    private static File file;
    private static final Map<UUID, String> uuidToGroupCache = new HashMap<>();


    public static void load() {
        file = new File(JavaPlugin.getPlugin(DisplayRigger.class).getDataFolder(), "data.yml");
        yamlConfiguration = YamlConfiguration.loadConfiguration(file);

        uuidToGroupCache.clear();
        for (String groupID : getGroups()) {
            for (String objectID : getObjects(groupID)) {
                String uuidStr = yamlConfiguration.getString(groupID + "." + objectID + ".uuid");
                if (uuidStr != null) {
                    uuidToGroupCache.put(UUID.fromString(uuidStr), groupID);
                }
            }
        }
    }

    private static void saveAsync() {
        final String data = yamlConfiguration.saveToString();
        JavaPlugin plugin = JavaPlugin.getPlugin(DisplayRigger.class);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                Files.writeString(file.toPath(), data);
            } catch (Exception e) {
                Bukkit.getLogger().warning(ChatColor.RED + "数据保存失败: " + e.getMessage());
            }
        });
    }

    public static void groupAdd(String groupID, CommandSender sender) {
        if (yamlConfiguration.isSet(groupID)) {
            sender.sendMessage(ChatColor.RED + "该组已存在");
            return;
        }
        yamlConfiguration.createSection(groupID);
        saveAsync();
        sender.sendMessage(ChatColor.GREEN + "组" + groupID + "已添加");
    }

    public static boolean groupExist(String groupID) {
        return yamlConfiguration.isSet(groupID);
    }

    public static boolean objectIDExist(String groupID, String objectID) {
        return (yamlConfiguration.isSet(groupID + "." + objectID));
    }

    public static boolean objectUuidExist(String groupID, UUID uuid) {
        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(groupID);
        if (configurationSection == null) {
            return false;
        }
        for (String key : configurationSection.getKeys(false)) {
            String storedUuid = configurationSection.getString(key + ".uuid");
            if (storedUuid != null && uuid.equals(UUID.fromString(storedUuid))) {
                return true;
            }
        }
        return false;
    }

    public static boolean addObjectToGroup(String groupID, String objectID, UUID uuid, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return false;
        }
        if (objectIDExist(groupID, objectID)) {
            player.sendMessage(ChatColor.RED + "已存在同名对象");
            return false;
        }
        if (objectUuidExist(groupID, uuid)) {
            player.sendMessage(ChatColor.RED + "已存在相同UUID的对象");
            return false;
        }
        yamlConfiguration.set(groupID + "." + objectID + ".uuid", uuid.toString());
        uuidToGroupCache.put(uuid, groupID);
        saveAsync();
        return true;
    }

    public static void removeGroup(String groupID, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return;
        }
        uuidToGroupCache.values().removeIf(groupID::equals);
        yamlConfiguration.set(groupID, null);
        saveAsync();
        player.sendMessage(ChatColor.GREEN + "组" + groupID + "已删除");
    }

    public static void removeObjectFromGroup(String groupID, String objectID, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return;
        }
        if (!objectIDExist(groupID, objectID)) {
            player.sendMessage(ChatColor.RED + "该对象不存在");
            return;
        }
        String uuidStr = yamlConfiguration.getString(groupID + "." + objectID + ".uuid");
        if (uuidStr != null) {
            uuidToGroupCache.remove(UUID.fromString(uuidStr));
        }
        yamlConfiguration.set(groupID + "." + objectID, null);
        saveAsync();
        player.sendMessage(ChatColor.GREEN + "对象" + objectID + "已删除");
    }

    public static Set<String> getGroups() {
        return yamlConfiguration.getKeys(false);
    }

    public static Set<String> getObjects(String groupID) {
        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(groupID);
        if (configurationSection == null) {
            return Set.of();
        }
        return configurationSection.getKeys(false);
    }

    public static String getGroupByObjectUuid(UUID uuid) {
        return uuidToGroupCache.get(uuid);
    }
}