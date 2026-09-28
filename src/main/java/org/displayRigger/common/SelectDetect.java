package org.displayRigger.common;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class SelectDetect {

    public static void detectSelect(PlayerMoveEvent event) {//TODO:待添加检测逻辑
        Location location = event.getTo();
        if (location == null) {
            return;
        }
        float pitch = location.getPitch();
        float yaw = location.getYaw();
    }
}
