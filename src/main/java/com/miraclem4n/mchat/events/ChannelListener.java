package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.channels.Channel;
import com.miraclem4n.mchat.channels.ChannelManager;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;

public class ChannelListener implements Listener {
    mChatSuite plugin;

    public ChannelListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();

        Set<Channel> channels = ChannelManager.getPlayersActiveChannels(player.getName());

        if (channels.size() < 1)
            return;

        if (event.getMessage() == null)
            return;

        for (Channel channel : channels)
            if (channel.getOccupantAvailability(player.getName()))
                channel.sendMessageFrom(player,
                        Parser.parseChatMessage(player.getName(), player.getWorld().getName(), event.getMessage()));

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();

        Channel dChannel = ChannelManager.getDefaultChannel();
        Set<Channel> cChannel = ChannelManager.getPlayersActiveChannels(pName);

        if (cChannel.size() < 1 && dChannel != null && !dChannel.getOccupants().contains(pName)) {
            dChannel.addOccupant(pName, true);
            dChannel.broadcastMessage(Parser.parsePlayerName(pName, world) + " has joined channel " + dChannel.getName() + "!");
        }
    }
}
