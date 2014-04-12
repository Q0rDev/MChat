package ca.q0r.mchat.events.custom;

import ca.q0r.mchat.api.Parser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class MeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private UUID uuid;
    private String world, message;

    public MeEvent(UUID uuid, String world, String message) {
        this.uuid = uuid;
        this.world = world;
        this.message = message;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }

    public String getFormat() {
        return Parser.parseMe(uuid, world, message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}