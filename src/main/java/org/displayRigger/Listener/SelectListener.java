package org.displayRigger.Listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.displayRigger.common.EditingStick;
import org.displayRigger.common.SelectDetect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectListener implements Listener {

    private final Map<UUID, Long> lastLookUpdate = new HashMap<>();
    private static final long THROTTLE_MS = 200;

    @EventHandler
    public void onMoveViewPoint(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (player.getInventory().getItemInMainHand().getItemMeta() == null || !player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(EditingStick.EDITING_STICK_KEY)) {
            SelectDetect.loseFocus();
            return;
        }

        long now = System.currentTimeMillis();
        UUID uniqueId = player.getUniqueId();
        Long last = lastLookUpdate.get(uniqueId);
        if (last != null && now - last < THROTTLE_MS) {
            return;
        }
        lastLookUpdate.put(uniqueId,now);

        SelectDetect.focus(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lastLookUpdate.remove(event.getPlayer().getUniqueId());
    }
}