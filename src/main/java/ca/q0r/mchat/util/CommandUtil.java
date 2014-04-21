package ca.q0r.mchat.util;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Command Utility Class. Used for Command related tasks.
 */
public class CommandUtil {
    /**
     * Command Permission Checker
     *
     * @param sender     Object Sending Command.
     * @param permission Permissions attached to command.
     * @return Result of Permission check.
     */
    public static Boolean hasCommandPerm(CommandSender sender, String permission) {
        if (!API.checkPermissions(sender, permission)) {
            MessageUtil.sendMessage(sender, API.replace(LocaleType.MESSAGE_NO_PERMS.getVal(), "permission", permission, IndicatorType.LOCALE_VAR));
            return false;
        }

        return true;
    }

    /**
     * Checks if player is online before sending command.
     *
     * @param sender Object sending command.
     * @param player Player to check for.
     * @return Result of Online Check.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static Boolean isOnlineForCommand(CommandSender sender, String player) {
        if (Bukkit.getServer().getPlayer(player) == null) {
            MessageUtil.sendMessage(sender, "&4Player &e'" + player + "'&4 not Found.");
            return false;
        }

        return true;
    }

    /**
     * Checks if player is online before sending command.
     *
     * @param sender Object sending command.
     * @param uuid UUID to check for.
     * @return Result of Online Check.
     */
    public static Boolean isOnlineForCommand(CommandSender sender, UUID uuid) {
        if (API.getPlayer(uuid.toString()) == null) {
            MessageUtil.sendMessage(sender, "&4Player &e'" + uuid + "'&4 not Found.");
            return false;
        }

        return true;
    }

    /**
     * Checks if player is online before sending command.
     *
     * @param sender Object sending command.
     * @param player Player to check for.
     * @return Result of Online Check.
     */
    public static Boolean isOnlineForCommand(CommandSender sender, Player player) {
        if (player == null) {
            MessageUtil.sendMessage(sender, "&4Player not Found.");
            return false;
        }

        return true;
    }
}