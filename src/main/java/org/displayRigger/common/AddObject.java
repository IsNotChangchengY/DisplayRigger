package org.displayRigger.common;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.displayRigger.DisplayRigger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddObject {

    private static final Map<UUID, Display> addedDisplays = new HashMap<>();

    public static boolean isAdding(UUID playerId) {
        return addedDisplays.containsKey(playerId);
    }

    public static void objectAddInit(Display display, Player player) {
        UUID playerId = player.getUniqueId();
        if (addedDisplays.containsKey(playerId)) {
            player.sendMessage(ChatColor.YELLOW + "请先确认当前添加的对象");
            return;
        }
        addedDisplays.put(playerId, display);
        player.sendMessage(ChatColor.WHITE + "当前选择：" + display.getUniqueId());

        display.setGlowColorOverride(Color.YELLOW);
        new BukkitRunnable() {
            @Override
            public void run() {
                display.setGlowing(true);
            }
        }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 10);
        player.sendMessage(ChatColor.YELLOW + "确认添加对象：/dr addobject <groupID> <objectID>");
        player.sendMessage(ChatColor.YELLOW + "取消添加对象：/dr cancel");
    }

    public static void objectAddConfirm(String groupID, String objectID, Player player) {
        UUID playerId = player.getUniqueId();
        Display display = addedDisplays.get(playerId);
        if (display == null) {
            player.sendMessage(ChatColor.YELLOW + "当前没有选择的对象");
            return;
        }

        UUID uuid = display.getUniqueId();
        if (GroupManager.addObjectToGroup(groupID, objectID, uuid, player)) {
            display.setGlowColorOverride(Color.GREEN);
            player.sendMessage(ChatColor.GREEN + "添加成功");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 7);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Display stillAdding = addedDisplays.get(playerId);
                    if (stillAdding == display) {
                        display.setGlowColorOverride(Color.WHITE);
                        display.setGlowing(false);
                        addedDisplays.remove(playerId);
                    }
                }
            }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 40);
        } else {
            player.sendMessage(ChatColor.RED + "添加失败");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 7);
            display.setGlowColorOverride(Color.WHITE);
            display.setGlowing(false);
            addedDisplays.remove(playerId);
        }
    }

    public static void objectAddCancel(Player player) {
        UUID playerId = player.getUniqueId();
        Display display = addedDisplays.get(playerId);
        if (display == null) {
            player.sendMessage(ChatColor.YELLOW + "当前没有选择的对象");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "已取消添加对象");
        display.setGlowColorOverride(Color.WHITE);
        display.setGlowing(false);
        addedDisplays.remove(playerId);
    }

    public static void cleanupPlayer(UUID playerId) {
        Display display = addedDisplays.remove(playerId);
        if (display != null) {
            display.setGlowColorOverride(Color.WHITE);
            display.setGlowing(false);
        }
    }
}