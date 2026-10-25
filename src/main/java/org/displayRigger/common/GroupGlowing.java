package org.displayRigger.common;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.displayRigger.DisplayRigger;
import org.displayRigger.Listener.SelectListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupGlowing {

    private static final Map<String, Set<Display>> activeGlows = new HashMap<>();

    public static void setGlowing(String groupID, Player player) {
        Set<Display> oldDisplays = activeGlows.get(groupID);
        if (oldDisplays != null) {
            oldDisplays.forEach(display -> {
                display.setGlowColorOverride(null);
                display.setGlowing(false);
            });
        }

        Set<String> objects = GroupManager.getObjects(groupID);
        Set<Display> displays = new HashSet<>();
        player.performCommand("dr cancel");
        SelectDetect.loseFocus(player.getUniqueId());
        SelectListener.setSelectState(false);
        for (String objectID : objects) {
            if (Bukkit.getEntity(GroupManager.getObjectUuid(groupID, objectID)) instanceof Display display) {
                displays.add(display);
            }
        }
        displays.forEach(display -> {
            display.setGlowColorOverride(Color.BLUE);
            display.setGlowing(true);
        });

        activeGlows.put(groupID, displays);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (activeGlows.get(groupID) == displays) {
                    cancelGlowing(displays);
                    activeGlows.remove(groupID);
                }
            }
        }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 20 * 3);
    }

    public static void cancelGlowing(Set<Display> displays) {
        displays.forEach(display -> {
            display.setGlowColorOverride(null);
            display.setGlowing(false);
        });
        SelectListener.setSelectState(true);
    }
}