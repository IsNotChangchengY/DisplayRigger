package org.displayRigger.common;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.displayRigger.DisplayRigger;

import java.util.UUID;

public class AddObject {

    private static Display addedDisplay;

    public static void objectAddInit(Display display, Player player) {
        //todo:单选拦截逻辑
        addedDisplay=display;

        addedDisplay.setGlowColorOverride(Color.YELLOW);
        new BukkitRunnable(){
            @Override
            public void run() {
                addedDisplay.setGlowing(true);
            }
        }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 10);
        player.sendMessage(ChatColor.YELLOW + "/dr add <groupID> <objectID>");
        //todo: 发送确认消息/播放音效 跳转到指令部分
    }

    public static void objectAddConfirm(String groupID, String objectID) {
        //TODO: 确认添加对象
        if (addedDisplay == null) {
            return;
        }
        UUID uuid = addedDisplay.getUniqueId();
        if (GroupManager.addObjectToGroup(groupID, objectID, uuid)) {
            addedDisplay.setGlowColorOverride(Color.GREEN);
            //todo: 发送成功消息/播放音效/重新初始化
        }
        addedDisplay = null;

    }
}
