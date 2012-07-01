package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SayCommand implements CommandExecutor {
    mChatSuite plugin;

    public SayCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchatsay"))
            return true;

        if (!MiscUtil.hasCommandPerm(sender, "mchat.say"))
            return true;

        if (args.length > 0) {
            String message = "";

            for (String arg : args)
                message += " " + arg;

            message = message.trim();

            plugin.getServer().broadcastMessage(LocaleType.FORMAT_SAY.getValue() + " " +  message);
            return true;
        }

        return false;
    }
}
