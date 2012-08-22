package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.HashMap;

public class AFKOtherCommand implements CommandExecutor {
    MChat plugin;

    public AFKOtherCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatafkother")
                || !MiscUtil.hasCommandPerm(sender, "mchat.afk.other"))
            return true;

        Player afkTarget = plugin.getServer().getPlayer(args[0]);

        if (!MiscUtil.isOnlineForCommand(sender, afkTarget))
            return true;

        Boolean isAfk = plugin.isAFK.get(afkTarget.getName()) != null &&
                plugin.isAFK.get(afkTarget.getName());

        String notification = LocaleType.MESSAGE_PLAYER_NOT_AFK.getVal();

        String message = "";

        String title = Parser.parsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName());


        if (!isAfk) {
            if (args.length > 1) {
                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];

                message = message.trim();
            } else
                message = "Away From Keyboard";

            notification = LocaleType.MESSAGE_PLAYER_AFK.getVal();

            title = ChatColor.valueOf(LocaleType.MESSAGE_SPOUT_COLOUR.getRaw().toUpperCase()) + "- AFK -" + '\n' + title;
        }

        HashMap<String, String> rMap = new HashMap<String, String>();

        rMap.put("player", Parser.parsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()));
        rMap.put("reason", message);
        rMap.put("r", message);

        if (plugin.spoutB) {
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                SpoutPlayer sPlayers = (SpoutPlayer) players;

                if (sPlayers.isSpoutCraftEnabled()) {
                    sPlayers.sendNotification(afkTarget.getName(), API.replace(notification, "player", "", IndicatorType.LOCALE_VAR), Material.PAPER);
                } else {
                    players.sendMessage(API.replace(notification, rMap, IndicatorType.LOCALE_VAR));
                }
            }

            SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

            sPlayer.setTitle(title);
        } else
            plugin.getServer().broadcastMessage(API.replace(notification, rMap, IndicatorType.LOCALE_VAR));

        afkTarget.setSleepingIgnored(!isAfk);
        plugin.isAFK.put(afkTarget.getName(), !isAfk);

        String pLName = Parser.parseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName());

        if (!isAfk) {
            plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

            pLName = MessageUtil.addColour("<gold>[" + LocaleType.MESSAGE_AFK_AFK.getVal() + "] ") + pLName;
        }

        if (ConfigType.MCHATE_USE_AFK_LIST.getBoolean())
            if (pLName.length() > 15) {
                pLName = pLName.substring(0, 16);
                afkTarget.setPlayerListName(pLName);
            } else
                afkTarget.setPlayerListName(pLName);

        return true;
    }
}
