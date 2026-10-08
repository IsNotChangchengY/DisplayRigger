package org.displayRigger.common;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectDetect {
    private static final double maxDistance = 5;
    private static final double raySize = 0.1;
    private static final Map<UUID, Display> focusedDisplays = new HashMap<>();

    public static boolean isSelecting(UUID playerId) {
        return AddObject.isAdding(playerId);
    }

    public static void focus(Player player) {
        UUID playerId = player.getUniqueId();
        if (AddObject.isAdding(playerId)) {
            return;
        }
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, direction, maxDistance, raySize, entity -> entity != player);
        if (rayTraceResult == null) {
            loseFocus(playerId);
            return;
        }
        Entity hitEntity = rayTraceResult.getHitEntity();
        if (!(hitEntity instanceof Display display)) {
            loseFocus(playerId);
            return;
        }
        if (display == focusedDisplays.get(playerId)) {
            return;
        }
        loseFocus(playerId);
        display.setGlowColorOverride(Color.WHITE);
        display.setGlowing(true);
        focusedDisplays.put(playerId, display);
        player.playSound(eyeLocation, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
    }

    public static void loseFocus(UUID playerId) {
        Display display = focusedDisplays.remove(playerId);
        if (display == null) {
            return;
        }
        display.setGlowing(false);
    }

    public static Display getFocusedDisplay(UUID playerId) {
        return focusedDisplays.get(playerId);
    }

    }