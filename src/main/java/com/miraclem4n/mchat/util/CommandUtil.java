package com.miraclem4n.mchat.util;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.types.IndicatorType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Utility Class. Used for Command related tasks.
 */
public class CommandUtil {
    /**
     * Command Permission Checker
     * @param sender Object Sending Command.
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
     * @param sender Object sending command.
     * @param player Player to check for.
     * @return Result of Online Check.
     */
    public static Boolean isOnlineForCommand(CommandSender sender, String player) {
        if (Bukkit.getServer().getPlayer(player) == null) {
            MessageUtil.sendMessage(sender, "&4Player &e'" + player + "'&4 not Found.");
            return false;
        }

        return true;
    }

    /**
     * Checks if player is online before sending command.
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
