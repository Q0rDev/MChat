package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCommand implements CommandExecutor {
    mChatSuite plugin;

    public ShoutCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchatshout"))
            return true;

        if (!MiscUtil.hasCommandPerm(sender, "mchat.shout"))
            return true;

        String message = "";

        for (String arg : args)
            message += " " + arg;

        message = message.trim();

        if (!(sender instanceof Player)) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "say " + message);
            return true;
        }

        if (message.length() < 1) {
            sender.sendMessage("You can't shout nothing!");
            return true;
        }

        Player player = (Player) sender;

        mChatSuite.isShouting.put(sender.getName(), true);

        plugin.getServer().broadcastMessage(Parser.parseChatMessage(player.getName(), player.getWorld().getName(), message));

        mChatSuite.isShouting.put(sender.getName(), false);

        return true;
    }
}
