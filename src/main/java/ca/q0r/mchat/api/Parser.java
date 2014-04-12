package ca.q0r.mchat.api;

import ca.q0r.mchat.events.custom.ParseEvent;
import ca.q0r.mchat.types.EventType;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.yml.locale.LocaleType;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Class used to parse messages / events / misc.
 */
public class Parser {
    /**
     * Core Formatting
     *
     * @param uuid   UUID of Player being reflected upon.
     * @param world  Player's World.
     * @param msg    Message being displayed.
     * @param format Resulting Format.
     * @return Formatted Message.
     */
    public static String parseMessage(UUID uuid, String world, String msg, String format) {
        ParseEvent event = new ParseEvent(uuid, world, msg, format);

        Bukkit.getServer().getPluginManager().callEvent(event);

        return event.getParsed();
    }

    /**
     * Chat Formatting
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg   Message being displayed.
     * @return Formatted Chat Message.
     */
    public static String parseChatMessage(UUID uuid, String world, String msg) {
        return parseMessage(uuid, world, msg, LocaleType.FORMAT_CHAT.getRaw());
    }

    /**
     * Player Name Formatting
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Player Name.
     */
    public static String parsePlayerName(UUID uuid, String world) {
        return parseMessage(uuid, world, "", LocaleType.FORMAT_NAME.getRaw());
    }

    /**
     * Event Message Formatting
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @param type  Event Type being formatted.
     * @return Formatted Event Message.
     */
    public static String parseEvent(UUID uuid, String world, EventType type) {
        return parseMessage(uuid, world, "", API.replace(Reader.getEventMessage(type), "player", parsePlayerName(uuid, world), IndicatorType.LOCALE_VAR));
    }

    /**
     * TabbedList Formatting
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted TabbedList Name.
     */
    public static String parseTabbedList(UUID uuid, String world) {
        return parseMessage(uuid, world, "", LocaleType.FORMAT_TABBED_LIST.getRaw());
    }

    /**
     * ListCommand Formatting.
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted ListCommand Name.
     */
    public static String parseListCmd(UUID uuid, String world) {
        return parseMessage(uuid, world, "", LocaleType.FORMAT_LIST_CMD.getRaw());
    }

    /**
     * Me Formatting.
     *
     * @param uuid  UUID of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg   Message being displayed.
     * @return Formatted Me Message.
     */
    public static String parseMe(UUID uuid, String world, String msg) {
        return parseMessage(uuid, world, msg, LocaleType.FORMAT_ME.getRaw());
    }
}