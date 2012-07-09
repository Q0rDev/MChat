package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {
    MChat plugin;

    public AFKCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatafk")
                || !MiscUtil.hasCommandPerm(sender,"mchat.afk.self"))
            return true;

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't be AFK.");
            return true;
        }

        String message = "";

        if (args.length > 0) {
            for (String arg : args)
                message += " " + arg;

            message = message.trim();
        } else
            message = LocaleType.MESSAGE_AFK_DEFAULT.getVal();

        Player player = (Player) sender;

        if (isAfk(player.getName())) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + player.getName());
            return true;
        }

        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + sender.getName() + " " + message);
        return true;
    }

    Boolean isAfk(String player) {
        return plugin.isAFK.get(player) != null
                && plugin.isAFK.get(player);
    }
}
