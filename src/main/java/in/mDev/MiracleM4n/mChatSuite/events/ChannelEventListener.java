package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.channel.Channel;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import in.mDev.MiracleM4n.mChatSuite.util.Messanger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.*;

public class ChannelEventListener implements Listener {
    mChatSuite plugin;

    public ChannelEventListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();

        Set<Channel> channels = plugin.getChannelManager().getPlayersActiveChannels(player.getName());

        if (channels.size() < 1)
            return;

        if (event.getMessage() == null)
            return;

        for (Channel channel : channels)
            Messanger.log(channel.getName());

        for (Channel channel : channels)
            channel.sendMessageFrom(player,
                    plugin.getAPI().ParseChatMessage(player.getName(), player.getWorld().getName(), event.getMessage()));

        event.setCancelled(true);
    }
}
