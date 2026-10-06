package org.displayRigger.common;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.displayRigger.DisplayRigger;

import java.util.UUID;

public class AddObject {

    private static Display addedDisplay;

    public static void objectAddInit(Display display, Player player) {
        if (addedDisplay != null) {
            player.sendMessage(ChatColor.YELLOW + "请先确认当前添加的对象");
            return;
        }
        addedDisplay = display;

        addedDisplay.setGlowColorOverride(Color.YELLOW);
        new BukkitRunnable() {
            @Override
            public void run() {
                addedDisplay.setGlowing(true);
            }
        }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 10);
        player.sendMessage(ChatColor.YELLOW + "通过指令确认添加对象：/dr addobject <groupID> <objectID>");
        player.sendMessage(ChatColor.YELLOW + "通过指令取消添加对象：/dr cancel");
    }

    public static void objectAddConfirm(String groupID, String objectID, Player player) {
        if (addedDisplay == null) {
            player.sendMessage(ChatColor.YELLOW + "当前没有选择的对象");
            return;
        }

        UUID uuid = addedDisplay.getUniqueId();
        if (GroupManager.addObjectToGroup(groupID, objectID, uuid, player)) {
            addedDisplay.setGlowColorOverride(Color.GREEN);
            player.sendMessage(ChatColor.GREEN + "添加成功");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 7);
            SelectDetect.setSelectState(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    addedDisplay.setGlowColorOverride(Color.WHITE);
                    addedDisplay.setGlowing(false);
                    addedDisplay = null;
                }
            }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 40);
        } else {
            player.sendMessage(ChatColor.RED + "添加失败");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 7);
            addedDisplay.setGlowColorOverride(Color.WHITE);
            addedDisplay.setGlowing(false);
            addedDisplay = null;
            SelectDetect.setSelectState(true);
        }
    }

    public static void objectAddCancel(Player player) {
        if (addedDisplay == null) {
            player.sendMessage(ChatColor.YELLOW + "当前没有选择的对象");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "已取消添加对象");
        addedDisplay.setGlowColorOverride(Color.WHITE);
        addedDisplay.setGlowing(false);
        addedDisplay = null;
        SelectDetect.setSelectState(true);
    }
}
