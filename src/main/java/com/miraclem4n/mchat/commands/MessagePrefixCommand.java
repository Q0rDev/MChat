package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MessagePrefixCommand implements CommandExecutor {
    mChatSuite plugin;

    public MessagePrefixCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatmessageprefix")
                || !MiscUtil.hasCommandPerm(sender, "mchat.messageprefix"))
            return true;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("set")) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                plugin.mPrefix.put(sender.getName(), message);

                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                plugin.mPrefix.put(sender.getName(), "");

                return true;
            }
        }

        return false;
    }
}
