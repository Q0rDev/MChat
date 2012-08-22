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

        if (plugin.isMuted.get(pName) != null
                && plugin.isMuted.get(pName)) {
            event.setCancelled(true);

            return;
        }

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

        // Conversation
        if (ConfigType.PMCHAT_ENABLE.getBoolean()) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
                player.sendMessage(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
                MessageUtil.log(MessageUtil.addColour(LocaleType.MESSAGE_CONVERSATION_CONVERSATION.getVal() + eventFormat));
                event.setCancelled(true);
            }
        }

        // MChatEssentials
        if (ConfigType.MCHATE_ENABLE.getBoolean()) {
            if (plugin.isAFK.get(pName) != null)
                if (plugin.isAFK.get(pName))
                    player.performCommand("mchatafk");

            plugin.lastMove.put(pName, new Date().getTime());
        }

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            final String sPName = pName;

            sPlayer.setTitle(ChatColor.valueOf(LocaleType.MESSAGE_SPOUT_COLOUR.getRaw().toUpperCase())
                    + "- " + MessageUtil.addColour(msg) + ChatColor.valueOf(LocaleType.MESSAGE_SPOUT_COLOUR.getRaw().toUpperCase())
                    + " -" + '\n' + Parser.parsePlayerName(pName, world));

            plugin.chatt.put(pName, false);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    SpoutPlayer sPlayer = (SpoutPlayer) plugin.getServer().getPlayer(sPName);

                    if (sPlayer != null)
                        sPlayer.setTitle(Parser.parsePlayerName(sPName, sPlayer.getWorld().getName()));
                }
            }, 7 * 20);
        }

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
