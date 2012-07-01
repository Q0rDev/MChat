package com.miraclem4n.mchat.util;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.types.config.LocaleType;
import org.bukkit.command.CommandSender;

public class MiscUtil {
    public static Boolean hasCommandPerm(CommandSender sender, String permission) {
        if (!API.checkPermissions(sender, permission)) {
            MessageUtil.sendMessage(sender, LocaleType.NO_PERMS.getValue().replace("%permission%", permission));
            return false;
        }

        return true;
    }
}
