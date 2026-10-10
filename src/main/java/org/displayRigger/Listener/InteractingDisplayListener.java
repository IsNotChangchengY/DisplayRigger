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
import org.displayRigger.common.DisplayGroupTriggeredEvent;
import org.displayRigger.common.GroupManager;

import java.util.UUID;

public class InteractingDisplayListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), 3, 0.3);
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
        Bukkit.getPluginManager().callEvent(new DisplayGroupTriggeredEvent(event.getPlayer(),display,groupID));
    }
}