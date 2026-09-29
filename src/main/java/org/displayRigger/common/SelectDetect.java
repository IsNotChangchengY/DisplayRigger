package org.displayRigger.common;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class SelectDetect {
    private static final double maxDistance = 5;
    private static final double raySize = 0.3;

    public static void detectSelect(PlayerMoveEvent event) {//TODO:待添加检测逻辑
        Player player = event.getPlayer();
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, direction, maxDistance, raySize, entity -> entity != player);
        if (rayTraceResult == null) {return;}
        Entity hitEntity = rayTraceResult.getHitEntity();
        if (!(hitEntity instanceof Display display)) {return;}
        player.sendMessage(display.toString());
        display.setGlowing(true);
        display.setBrightness(new Display.Brightness(15,15));
    }
}
