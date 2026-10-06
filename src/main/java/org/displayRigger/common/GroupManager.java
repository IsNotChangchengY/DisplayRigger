package org.displayRigger.common;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.displayRigger.DisplayRigger;

import java.io.File;
import java.util.UUID;

public class GroupManager {

    private static YamlConfiguration yamlConfiguration;
    private static File file;


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

    public static boolean groupExist(String groupID) {
        return yamlConfiguration.isSet(groupID);
    }

    public static boolean objectExist(String groupID, String objectID) {
        return yamlConfiguration.isSet(groupID + "." + objectID);//todo：不跟据名字比对，根据uuid比对
    }

    public static boolean addObjectToGroup(String groupID, String objectID, UUID uuid, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return false;
        }
        if (objectExist(groupID, objectID)) {
            player.sendMessage(ChatColor.RED + "该对象已存在");
            return false;
        }
        yamlConfiguration.set(groupID + "." + objectID + ".uuid", uuid.toString());
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void removeGroup(String groupID, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return;
        }
        yamlConfiguration.set(groupID, null);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "删除组失败");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "组" + groupID + "已删除");
    }

    public static void removeObjectFromGroup(String groupID, String objectID, Player player) {
        if (!groupExist(groupID)) {
            player.sendMessage(ChatColor.RED + "该组不存在");
            return;
        }
        if (!objectExist(groupID, objectID)) {
            player.sendMessage(ChatColor.RED + "该对象不存在");
            return;
        }
        yamlConfiguration.set(groupID + "." + objectID, null);
        try {
            yamlConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "删除对象失败");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "对象" + objectID + "已删除");
    }
}
