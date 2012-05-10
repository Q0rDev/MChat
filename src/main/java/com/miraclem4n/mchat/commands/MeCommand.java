package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
    mChatSuite plugin;

    public MeCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatme")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    World world = player.getWorld();

                    if (API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.me"))
                        plugin.getServer().broadcastMessage(Parser.parseMe(player.getName(), world.getName(), message));
                    else
                        MessageUtil.sendMessage(player, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.me"));

                    return true;
                } else {
                    String senderName = "Console";
                    plugin.getServer().broadcastMessage("* " + senderName + " " + message);
                    MessageUtil.log("* " + senderName + " " + message);
                    return true;
                }
            }
        }

        return false;
    }
}
