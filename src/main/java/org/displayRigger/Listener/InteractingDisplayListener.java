package org.displayRigger.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;
import org.displayRigger.common.ConfigManager;
import org.displayRigger.common.DisplayGroupTriggeredEvent;
import org.displayRigger.common.GroupManager;

import java.util.UUID;

public class InteractingDisplayListener implements Listener {

    private static final double interactionDistance = ConfigManager.getYamlConfiguration().getDouble("interact.distance", 3);
    private static final double interactionRaySize = ConfigManager.getYamlConfiguration().getDouble("interact.ray_size", 0.15);

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), interactionDistance, interactionRaySize, entity -> entity != player);
        if (rayTraceResult == null) {
            return;
        }
        Entity hitEntity = rayTraceResult.getHitEntity();
        if (!(hitEntity instanceof Display display)) {
            return;
        }
        UUID uuid = display.getUniqueId();
        String groupID = GroupManager.getGroupByObjectUuid(uuid);
        if (groupID == null) {
            return;
        }
        String objectID = GroupManager.getObjectIDByUuid(uuid);
        if (objectID == null) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new DisplayGroupTriggeredEvent(event.getPlayer(), display, groupID, objectID, event.getAction()));
    }
}