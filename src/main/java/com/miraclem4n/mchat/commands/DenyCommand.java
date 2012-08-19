package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommand implements CommandExecutor {
    MChat plugin;

    public DenyCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatdeny"))
            return true;

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't use conversation commands.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String pWorld = player.getWorld().getName();

        String rName = plugin.getInvite.get(pName);

        if (rName == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_CONVERSATION_NO_PENDING.getVal());
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(rName);
        String rWorld = recipient.getWorld().getName();

        if (MiscUtil.isOnlineForCommand(sender, recipient)) {
            plugin.getInvite.remove(pName);

            plugin.isConv.put(pName, false);
            plugin.isConv.put(rName, false);

            MessageUtil.sendMessage(player, API.replace(LocaleType.MESSAGE_CONVERSATION_DENIED.getVal(), "player", Parser.parsePlayerName(rName, rWorld), IndicatorType.LOCALE_VAR));
            MessageUtil.sendMessage(recipient, API.replace(LocaleType.MESSAGE_CONVERSATION_NOT_STARTED.getVal(), "player", Parser.parsePlayerName(pName, pWorld), IndicatorType.LOCALE_VAR));
        }

        return true;
    }
}
