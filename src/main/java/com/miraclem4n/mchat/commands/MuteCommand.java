package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
    mChatSuite plugin;

    public MuteCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatmute")
                || !MiscUtil.hasCommandPerm(sender, "mchat.mute"))
            return true;

        if (args.length > 0) {
            String target = args[0];
            Player player = plugin.getServer().getPlayer(args[0]);

            if (player != null)
                target = player.getName();

            if (plugin.isMuted.get(target) != null && plugin.isMuted.get(target)) {
                plugin.isMuted.put(target, false);

                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_MUTE_MISC.getValue().replace("%player%", target).replace("%muted%", "unmuted").replace("%mute%", "mute"));
            } else {
                plugin.isMuted.put(target, true);

                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_MUTE_MISC.getValue().replace("%player%", target).replace("%muted%", "muted").replace("%mute%", "unmute"));
            }

            return true;
        }

        return false;
    }
}