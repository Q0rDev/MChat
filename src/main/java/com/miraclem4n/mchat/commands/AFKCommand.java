package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.API;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {
    mChatSuite plugin;

    public AFKCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't be AFK.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("mchatafk"))
            return false;

        String message = "";

        if (args.length > 0) {
            for (String arg : args)
                message += " " + arg;

            message = message.trim();
        } else
            message = "Away From Keyboard";

        Player player = (Player) sender;

        if (isAfk(player.getName())) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + player.getName());
            return true;
        }

        if (!API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.afk.self")) {
            MessageUtil.sendMessage(player, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.afk.self"));
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
