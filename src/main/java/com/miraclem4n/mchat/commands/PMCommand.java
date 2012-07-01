package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PMCommand implements CommandExecutor {
    mChatSuite plugin;

    public PMCommand(mChatSuite instance) {
        plugin = instance;
    }

    String message = "";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("pmchat"))
            return true;

        if (!MiscUtil.hasCommandPerm(sender, "mchat.pm.pm"))
            return true;

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (args.length < 2)
            return false;

        message = "";
        for (int i = 1; i < args.length; ++i)
            message += " " + args[i];

        if (plugin.getServer().getPlayer(args[0]) == null) {
            player.sendMessage(formatPNF(args[0]));
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(args[0]);
        String rName = recipient.getName();
        String senderName = Parser.parsePlayerName(pName, world);

        player.sendMessage(formatPMSend(rName, recipient.getWorld().getName(), message));

        if (plugin.spoutB) {
            if (ConfigType.MCHAT_SPOUT.getObject().toBoolean()) {
                final SpoutPlayer sRecipient = (SpoutPlayer) recipient;

                if (sRecipient.isSpoutCraftEnabled()) {
                    if (plugin.lastPMd != null)
                        plugin.lastPMd.remove(rName);

                    plugin.lastPMd.put(rName, pName);
                    sRecipient.sendNotification("[pmChat] From:", player.getName(), Material.PAPER);

                    Runnable runnable = new Runnable() {
                        public void run() {
                            for (int i = 0; i < ((message.length() / 40) + 1); i++) {
                                sRecipient.sendNotification(formatPM(message, ((40 * i) + 1), ((i * 40) + 20)), formatPM(message, ((i * 40) + 21), ((i * 40) + 40)), Material.PAPER);
                                waiting(2);
                            }
                        }
                    };

                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable, 2 * 20);
                    return true;
                }
            }
        }

        plugin.lastPMd.put(rName, pName);
        MessageUtil.log(formatPMRecieve(senderName, world, message));
        recipient.sendMessage(formatPMRecieve(senderName, world, message));
        return true;
    }

    void waiting(int n) {
        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < n * 1000);
    }

    String formatPM(String message, Integer start, Integer finish) {
        while (message.length() < finish) message += " ";
        return message.substring(start, finish);
    }

    String formatPNF(String playerNotFound) {
        return MessageUtil.format("&4Player &e'" + playerNotFound + "'&4 not Found.");
    }

    String formatPMSend(String recipient, String world, String message) {
        return MessageUtil.addColour("&fMe &1-&2-&3-&4> &f" + Parser.parsePlayerName(recipient, world) + "&f: " + message);
    }

    String formatPMRecieve(String sender, String world, String message) {
        return MessageUtil.addColour(Parser.parsePlayerName(sender, world) + " &1-&2-&3-&4> &fMe: " + message);
    }
}
