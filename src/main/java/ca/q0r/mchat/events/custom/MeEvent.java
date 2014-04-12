package ca.q0r.mchat.events.custom;

import ca.q0r.mchat.api.Parser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Event that is fired when /mchatme is used.
 */
public class MeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private UUID uuid;
    private String world, message;

    /** Instantiates Event
     *
     * @param uuid UUID of player that is executing this event.
     * @param world World the Player is in.
     * @param message Message being relayed.
     */
    public MeEvent(UUID uuid, String world, String message) {
        this.uuid = uuid;
        this.world = world;
        this.message = message;
    }

    /** Gets UUID of Player.
     *
     * @return UUID of Player.
     */
    public UUID getUuid() {
        return uuid;
    }

    /** Gets Message the player is sending.
     *
     * @return Message the player is sending.
     */
    public String getMessage() {
        return message;
    }

    /** Gets format being used.
     *
     * @return Format being used.
     */
    public String getFormat() {
        return Parser.parseMe(uuid, world, message);
    }

    /** Sets Message being sent.
     *
     * @param message Message being sent.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}