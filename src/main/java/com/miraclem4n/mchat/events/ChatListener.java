package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.Date;

public class ChatListener implements Listener {
    MChat plugin;

    public ChatListener(MChat instance) {
        plugin = instance;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        String pName = player.getName();

        String world = player.getWorld().getName();
        String pLName = Parser.parseTabbedList(pName, world);
        String msg = event.getMessage();
        String eventFormat = Parser.parseChatMessage(pName, world, msg);

        if (msg == null)
            return;

        // Fix for Too long List Names
        if (pLName.length() > 15) {
            pLName = pLName.substring(0, 16);
            setListName(player, pLName);
        } else
            setListName(player, pLName);

        // Chat Distance Stuff
        if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0)
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()
                        || players.getLocation().distance(player.getLocation()) > ConfigType.MCHAT_CHAT_DISTANCE.getDouble()) {
                    if (isSpy(players.getName(), players.getWorld().getName()))
                        players.sendMessage(eventFormat.replace(LocaleType.FORMAT_LOCAL.getVal(), LocaleType.FORMAT_FORWARD.getVal()));

                    event.getRecipients().remove(players);
                }
            }

        event.setFormat(eventFormat);
    }

    Boolean isSpy(String player, String world) {
        if (API.checkPermissions(player, world, "mchat.spy")) {
            MChat.isSpying.put(player, true);
            return true;
        }

        MChat.isSpying.put(player, false);
        return false;
    }

    void setListName(Player player, String listName) {
        try {
            player.setPlayerListName(listName);
        } catch(Exception ignored) {}
    }
}
