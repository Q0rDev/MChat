package ca.q0r.mchat.events.custom;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event that is fired when Variables are replaced.
 */
public class ReplaceEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    /** Forced Override of HandlerList
     *
     * @return HandlerList
     */
    public HandlerList getHandlers() {
        return handlers;
    }

    /** Forced Override of HandlerList
     *
     * @return HandlerList
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String var, value, format;
    private boolean cancelled;

    /** Instantiates Event
     *
     * @param var Variable being processed.
     * @param value Value of Variable.
     * @param format Format being replaced.
     */
    public ReplaceEvent(String var, String value, String format) {
        this.var = var;
        this.value = value;
        this.format = format;

        this.cancelled = false;
    }

    /** Checks whether the Event is cancelled.
     *
     * @return Event cancellation state.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /** Get Variable being processed.
     *
     * @return Variable being processed.
     */
    public String getVariable() {
        return var;
    }

    /** Get Value being processed.
     *
     * @return Value being processed.
     */
    public String getValue() {
        return value;
    }

    /** Get Format being replaced.
     *
     * @return Format being replaced.
     */
    public String getFormat() {
        return format;
    }

    /** Get Replaced Format after processing.
     *
     * @return Replaced Format after processing.
     */
    public String getReplacedFormat() {
        return format.replace(var, value);

    }

    /** Sets Value being processed.
     *
     * @param value Value being processed.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /** Sets Format being replaced.
     *
     * @param format Format being replaced.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /** Sets whether the Event is cancelled.
     *
     * @param cancel Event cancellation state.
     */
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}