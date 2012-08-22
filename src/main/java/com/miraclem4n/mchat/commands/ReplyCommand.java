package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.HashMap;

public class ReplyCommand implements CommandExecutor {
    MChat plugin;

    public ReplyCommand(MChat instance) {
        plugin = instance;
    }

    String message = "";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("pmchatreply")
                || !MiscUtil.hasCommandPerm(sender, "mchat.pm.reply"))
            return true;

        //TODO Allow Console's to PM
        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        message = "";

        for (String arg : args)
            message += " " + arg;

        if (plugin.lastPMd.get(pName) == null) {
            MessageUtil.sendMessage(player, LocaleType.MESSAGE_PM_NO_PM.getVal());
            return true;
        }

        String rName = plugin.lastPMd.get(pName);
        Player recipient = plugin.getServer().getPlayer(rName);

        if (!MiscUtil.isOnlineForCommand(sender, recipient))
            return true;

        String senderName = Parser.parsePlayerName(pName, world);

        HashMap<String, String> rMap = new HashMap<String, String>();

        rMap.put("recipient", Parser.parsePlayerName(rName, recipient.getWorld().getName()));
        rMap.put("sender", Parser.parsePlayerName(senderName, world));
        rMap.put("msg", message);

        player.sendMessage(API.replace(LocaleType.FORMAT_PM_SENT.getVal(), rMap, IndicatorType.LOCALE_VAR));

        if (plugin.spoutB) {
            if (ConfigType.MCHAT_SPOUT.getBoolean()) {
                final SpoutPlayer sRecipient = (SpoutPlayer) recipient;

                if (sRecipient.isSpoutCraftEnabled()) {
                    plugin.lastPMd.put(rName, pName);

                    Runnable runnable = new Runnable() {
                        public void run() {
                            for (int i = 0; i < ((message.length() / 40) + 1); i++) {
                                sRecipient.sendNotification(formatPM(message, ((40 * i) + 1), ((i * 40) + 20)), formatPM(message, ((i * 40) + 21), ((i * 40) + 40)), Material.PAPER);
                                waiting(2000);
                            }
                        }
                    };

                    sRecipient.sendNotification(LocaleType.MESSAGE_SPOUT_PM.getVal(), player.getName(), Material.PAPER);
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable, 2 * 20);
                    return true;
                }
            }
        }

        plugin.lastPMd.put(rName, pName);

        recipient.sendMessage(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));
        MessageUtil.log(API.replace(LocaleType.FORMAT_PM_RECEIVED.getVal(), rMap, IndicatorType.LOCALE_VAR));

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
        while (message.length() <= finish) message += " ";
        return message.substring(start, finish);
    }
}
