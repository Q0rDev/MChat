package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
    private MChat plugin;

    public MeCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatme")
                || !MiscUtil.hasCommandPerm(sender, "mchat.me")) {
            return true;
        }

        if (args.length > 0) {
            String message = "";

            for (String arg : args) {
                message += " " + arg;
            }

            message = message.trim();

            if (sender instanceof Player) {
                Player player = (Player) sender;
                World world = player.getWorld();

                plugin.getServer().broadcastMessage(Parser.parseMe(player.getName(), world.getName(), message));
                return true;
            } else {
                String senderName = "Console";
                plugin.getServer().broadcastMessage("* " + senderName + " " + message);
                MessageUtil.log("* " + senderName + " " + message);
                return true;
            }
        }

        return false;
    }
}
