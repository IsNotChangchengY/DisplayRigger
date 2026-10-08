package org.displayRigger.Listener;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.displayRigger.common.EditingStick;
import org.displayRigger.common.AddObject;
import org.displayRigger.common.SelectDetect;

import java.util.UUID;

public class ClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null || !event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(EditingStick.EDITING_STICK_KEY)) {
            return;
        }
        UUID playerId = event.getPlayer().getUniqueId();
        if (SelectDetect.getFocusedDisplay(playerId) == null) {
            return;
        }
        AddObject.objectAddInit(SelectDetect.getFocusedDisplay(playerId), event.getPlayer());
        SelectDetect.loseFocus(playerId);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,1);
    }
}