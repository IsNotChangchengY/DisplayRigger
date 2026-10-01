package org.displayRigger.common;

import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.displayRigger.DisplayRigger;

public class AddObject {

    private static Display addedDisplay;

    public static void objectAddInit(Display display) {
        addedDisplay=display;

        addedDisplay.setGlowColorOverride(Color.YELLOW);
        new BukkitRunnable(){
            @Override
            public void run() {
                addedDisplay.setGlowing(true);
            }
        }.runTaskLater(JavaPlugin.getPlugin(DisplayRigger.class), 10);
    }

    public static void objectAddConfirm() {
        //TODO: 确认添加对象
    }
}
