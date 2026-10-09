package org.displayRigger.common;

import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DisplayGroupTriggeredEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public Player getPlayer() {
        return player;
    }

    public Display getDisplay() {
        return display;
    }

    public String getGroupID() {
        return groupID;
    }

    private final Player player;
    private final Display display;
    private final String groupID;

    public DisplayGroupTriggeredEvent(Player player, Display display, String groupID) {
        this.player = player;
        this.display = display;
        this.groupID = groupID;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
