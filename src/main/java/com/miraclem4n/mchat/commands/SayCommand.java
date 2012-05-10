package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.API;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand implements CommandExecutor {
    mChatSuite plugin;

    public SayCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatsay")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.say")) {
                        MessageUtil.sendMessage(sender, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.say"));
                        return true;
                    }
                }

                plugin.getServer().broadcastMessage(LocaleType.FORMAT_SAY.getValue() + " " +  message);
                return true;
            }
        }

        return false;
    }
}
