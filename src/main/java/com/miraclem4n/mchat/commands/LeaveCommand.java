package com.miraclem4n.mchat.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {
    mChatSuite plugin;

    public LeaveCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("pmchatleave"))
            return false;

        Player player = (Player) sender;
        String pName = player.getName();

        String rName = plugin.chatPartner.get(pName);

        Boolean isConv = plugin.isConv.get(pName);

        if (rName == null || isConv == null) {

            plugin.isConv.put(pName, false);
            plugin.chatPartner.remove(pName);

            return true;
        }

        if (isConv) {
            MessageUtil.sendMessage(player, "You have left the convo.");

            Player recipient = plugin.getServer().getPlayer(rName);

            if (recipient != null)
                MessageUtil.sendMessage(recipient, "Conversation has ended.");

            plugin.isConv.put(pName, false);
            plugin.isConv.put(rName, false);

            plugin.chatPartner.remove(rName);
            plugin.chatPartner.remove(pName);
        } else
            MessageUtil.sendMessage(player, "You are not currently in a Convo.");

        return true;
    }
}
