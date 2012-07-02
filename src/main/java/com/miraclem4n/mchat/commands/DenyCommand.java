package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommand implements CommandExecutor {
    mChatSuite plugin;

    public DenyCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatdeny"))
            return true;

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String pWorld = player.getWorld().getName();

        String rName = plugin.getInvite.get(pName);

        if (rName == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NO_PENDING.getValue());
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(rName);
        String rWorld = recipient.getWorld().getName();

        if (!MiscUtil.isOnlineForCommand(sender, recipient)) {
            plugin.getInvite.remove(pName);

            plugin.isConv.put(pName, false);
            plugin.isConv.put(rName, false);

            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_DENIED.getValue().replace("%player%", Parser.parsePlayerName(rName, rWorld)));
            MessageUtil.sendMessage(recipient, LocaleType.MESSAGE_CONVERSATION_NOT_STARTED.getValue().replace("%player%", Parser.parsePlayerName(pName, pWorld)));
        }

        return true;
    }
}
