package org.displayRigger.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.displayRigger.common.DisplayGroupTriggeredEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onTestEvent(DisplayGroupTriggeredEvent event) {
        event.getPlayer().sendMessage("测试事件触发" + event.getGroupID() + event.getDisplay().getUniqueId() + event.getAction());
    }
}
