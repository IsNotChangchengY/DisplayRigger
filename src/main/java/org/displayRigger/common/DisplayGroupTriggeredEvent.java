package org.displayRigger.common;

import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;


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

    public Action getAction() {
        return action;
    }

    public String getObjectID() {
        return objectID;
    }

    private final Player player;
    private final Display display;
    private final String groupID;
    private final String objectID;
    private final Action action;

    public DisplayGroupTriggeredEvent(Player player, Display display, String groupID, String objectID, Action action) {
        this.player = player;
        this.display = display;
        this.groupID = groupID;
        this.objectID = objectID;
        this.action = action;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
