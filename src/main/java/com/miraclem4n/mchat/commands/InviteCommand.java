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

public class InviteCommand implements CommandExecutor {
    mChatSuite plugin;

    public InviteCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("pmchatinvite"))
            return false;

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (args.length < 1)
            return false;

        if (!API.checkPermissions(sender, "mchat.pm.invite")) {
            MessageUtil.sendMessage(player, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.pm.invite"));
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(args[0]);
        String rName = recipient.getName();
        String rWorld = recipient.getWorld().getName();

        if (recipient == null) {
            MessageUtil.sendMessage(player, "&4Player &e'" + args[0] + "'&4 not Found.");
            return true;
        }

        if (plugin.getInvite.get(rName) == null) {
            plugin.getInvite.put(rName, pName);

            MessageUtil.sendMessage(player, "You have invited &5'&4" + Parser.parsePlayerName(rName, rWorld) + "&5'&4 to have a Convo.");
            MessageUtil.sendMessage(recipient, "You have been invited to a Convo by &5'&4" + Parser.parsePlayerName(pName, world) + "&5'&4 use /pmchataccept to accept.");
        } else
            MessageUtil.sendMessage(player, "&5'&4" + Parser.parsePlayerName(rName, rWorld) + "&5'&4 Already has a Convo request.");

        return true;
    }
}
