package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommand implements CommandExecutor {
    MChat plugin;

    public WhoCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatwho")
                || !MiscUtil.hasCommandPerm(sender, "mchat.who"))
            return true;

        if (args.length > 0) {
            if (MiscUtil.isOnlineForCommand(sender, args[0]))
                formatWho(sender, plugin.getServer().getPlayer(args[0]));

            return true;
        }

        return false;
    }

    void formatWho(CommandSender sender, Player recipient) {
        String recipientName = Parser.parsePlayerName(recipient.getName(), recipient.getWorld().getName());
        Integer locX = (int) recipient.getLocation().getX();
        Integer locY = (int) recipient.getLocation().getY();
        Integer locZ = (int) recipient.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);
        String world = recipient.getWorld().getName();

        MessageUtil.sendColouredMessage(sender, "Player Name: " + recipient.getName());
        MessageUtil.sendColouredMessage(sender, "Display Name: " + recipient.getDisplayName());
        MessageUtil.sendColouredMessage(sender, "Formatted Name: " + recipientName);
        MessageUtil.sendColouredMessage(sender, "Player's Location: [ " + loc + " ]");
        MessageUtil.sendColouredMessage(sender, "Player's World: " + world);
        MessageUtil.sendColouredMessage(sender, "Player's Health: " + API.createHealthBar(recipient) + " " + recipient.getHealth() + "/20");
        MessageUtil.sendColouredMessage(sender, "Player's IP: " + recipient.getAddress().getHostString());
        MessageUtil.sendColouredMessage(sender, "Current Item: " + recipient.getItemInHand().getType().name());
        MessageUtil.sendColouredMessage(sender, "Entity ID: #" + recipient.getEntityId());
    }
}
