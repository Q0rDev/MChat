package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteCommand implements CommandExecutor {
    mChatSuite plugin;

    public MuteCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchatmute"))
            return true;

        if (!MiscUtil.hasCommandPerm(sender, "mchat.mute"))
            return true;

        if (args.length > 0) {
            String target = args[0];

            if (plugin.getServer().getPlayer(args[0]) != null)
                target = plugin.getServer().getPlayer(args[0]).getName();

            if (plugin.isMuted.get(target) != null && plugin.isMuted.get(target)) {
                plugin.isMuted.put(target, false);

                MessageUtil.sendMessage(sender, "Target '" + target + "' successfully unmuted. To mute use this command again.");
            } else {
                plugin.isMuted.put(target, true);

                MessageUtil.sendMessage(sender, "Target '" + target + "' successfully muted. To unmute use this command again.");
            }

            return true;
        }

        return false;
    }
}