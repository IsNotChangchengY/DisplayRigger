package org.displayRigger.common;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class SelectDetect {
    private static final double maxDistance = 5;
    private static final double raySize = 0.1;
    private static Display focusedDisplay;
    private static boolean selectState = true;

    public static void setSelectState(boolean selectState) {
        SelectDetect.selectState = selectState;
    }

    public static void focus(PlayerMoveEvent event) {
        if (!selectState) {return;}
        Player player = event.getPlayer();
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, direction, maxDistance, raySize, entity -> entity != player);
        if (rayTraceResult == null) {
            loseFocus();
            return;
        }
        Entity hitEntity = rayTraceResult.getHitEntity();
        if (!(hitEntity instanceof Display display)) {
            loseFocus();
            return;
        }
        if(display == focusedDisplay) {return;}
        loseFocus();
        display.setGlowColorOverride(Color.WHITE);
        display.setGlowing(true);
        focusedDisplay = display;
        player.playSound(eyeLocation, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
    }

    public static void loseFocus() {
        if(focusedDisplay == null) {return;}
        focusedDisplay.setGlowing(false);
        focusedDisplay = null;
}

    public static Display getFocusedDisplay() {
        return focusedDisplay;
    }
}