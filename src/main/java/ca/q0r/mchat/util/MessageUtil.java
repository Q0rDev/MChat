package ca.q0r.mchat.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility Class for various Message related tasks.
 */
public class MessageUtil {
    /**
     * Raw Message Sending.
     * @param sender Object sending message.
     * @param message Message being sent.
     */
    public static void sendRawMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    /**
     * Coloured Message Sending.
     * @param sender Object sending message.
     * @param message Message being sent.
     */
    public static void sendColouredMessage(CommandSender sender, String message) {
        sender.sendMessage(addColour(message));
    }

    /**
     * Message Sending.
     * @param sender Object sending message.
     * @param message Message being sent.
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }

    /**
     * Logger.
     * @param message Object being Logged.
     */
    public static void log(Object message) {
        Bukkit.getConsoleSender().sendMessage(message.toString());
    }

    /**
     * Logger.
     * @param message Object being Logged.
     */
    public static void logFormatted(Object message) {
        log(format(message.toString()));
    }
    /**
     * Colour Formatting.
     * @param string String being Formatted.
     * @return Coloured String.
     */
    public static String addColour(String string) {
        string = string.replace("`e", "")
                .replace("`r", "\u00A7c").replace("`R", "\u00A74")
                .replace("`y", "\u00A7e").replace("`Y", "\u00A76")
                .replace("`g", "\u00A7a").replace("`G", "\u00A72")
                .replace("`a", "\u00A7b").replace("`A", "\u00A73")
                .replace("`b", "\u00A79").replace("`B", "\u00A71")
                .replace("`p", "\u00A7d").replace("`P", "\u00A75")
                .replace("`k", "\u00A70").replace("`s", "\u00A77")
                .replace("`S", "\u00A78").replace("`w", "\u00A7f");

        string = string.replace("<r>", "")
                .replace("<black>", "\u00A70").replace("<navy>", "\u00A71")
                .replace("<green>", "\u00A72").replace("<teal>", "\u00A73")
                .replace("<red>", "\u00A74").replace("<purple>", "\u00A75")
                .replace("<gold>", "\u00A76").replace("<silver>", "\u00A77")
                .replace("<gray>", "\u00A78").replace("<blue>", "\u00A79")
                .replace("<lime>", "\u00A7a").replace("<aqua>", "\u00A7b")
                .replace("<rose>", "\u00A7c").replace("<pink>", "\u00A7d")
                .replace("<yellow>", "\u00A7e").replace("<white>", "\u00A7f");

        string = string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "\u00A7$2");

        string = string.replaceAll("(&([a-fk-orA-FK-OR0-9]))", "\u00A7$2");

        return string.replace("&&", "&");
    }

    /**
     * Colour Removal.
     * @param string String Colour is being removed from.
     * @return DeColoured String.
     */
    public static String removeColour(String string) {
        string = addColour(string);

        return string.replaceAll("(§([a-fk-orA-FK-OR0-9]))", "§z");
    }

    /**
     * Plugin Formatting.
     * @param message Message being appended.
     * @return Message appended to [MChat].
     */
    public static String format(String message) {
        return addColour("&2[&4M&8Chat&2] &6" + message);
    }

    /**
     * Plugin Formatting.
     * @param name Name of plugin to be formatted.
     * @param message Message being appended.
     * @return Message appended to name.
     */
    public static String format(String name, String message) {
        return addColour("&2[&4" + name  + "&2] &6" + message);
    }
}
