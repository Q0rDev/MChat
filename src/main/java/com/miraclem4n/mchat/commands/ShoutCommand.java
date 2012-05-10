package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
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

        if (cmd.equalsIgnoreCase("mchatshout")) {
            String message = "";

            for (String arg : args)
                message += " " + arg;

            message = message.trim();

            if (!(sender instanceof Player)) {
                MessageUtil.log(MessageUtil.format("Console's can't shout."));
                return true;
            }

            Player player = (Player) sender;

            if (!API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.shout")) {
                MessageUtil.sendMessage(player, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.shout"));
                return true;
            }

            if (message.length() < 1) {
                sender.sendMessage("You can't shout nothing!");
                return true;
            }

            mChatSuite.isShouting.put(sender.getName(), true);

            plugin.getServer().broadcastMessage(Parser.parseChatMessage(player.getName(), player.getWorld().getName(), message));

            mChatSuite.isShouting.put(sender.getName(), false);

            return true;
        }

        return false;
    }
}
