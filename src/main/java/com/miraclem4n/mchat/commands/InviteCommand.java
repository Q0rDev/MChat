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

public class InviteCommand implements CommandExecutor {
    mChatSuite plugin;

    public InviteCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatinvite")
                || !MiscUtil.hasCommandPerm(sender, "mchat.pm.invite"))
            return true;

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String pWorld = player.getWorld().getName();

        if (args.length < 1)
            return false;

        Player recipient = plugin.getServer().getPlayer(args[0]);
        String rName = recipient.getName();
        String rWorld = recipient.getWorld().getName();

        if (!MiscUtil.isOnlineForCommand(sender, recipient))
            return true;

        if (plugin.getInvite.get(rName) == null) {
            plugin.getInvite.put(rName, pName);

            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_INVITE_SENT.getValue().replace("%player%", Parser.parsePlayerName(rName, rWorld)));
            MessageUtil.sendMessage(recipient, LocaleType.MESSAGE_CONVERSATION_INVITED.getValue().replace("%player%", Parser.parsePlayerName(pName, pWorld)));
        } else
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_HAS_REQUEST.getValue().replace("%player%", Parser.parsePlayerName(rName, rWorld)));

        return true;
    }
}
