package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigType;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    MChat plugin;

    public PlayerListener(MChat instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String world = player.getWorld().getName();

        final String pName = player.getName();
        String msg = event.getJoinMessage();

        if (msg == null) {
            return;
        }

        // For Lazy People
        if (ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean()) {
            if (YmlManager.getYml(YmlType.INFO_YML).getConfig().get("users." + pName) == null) {
                Writer.addBase(pName, ConfigType.INFO_DEFAULT_GROUP.getString(), false);
            }
        }

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                setListName(player, Parser.parseTabbedList(pName, world));
            }
        }, 20L);

        if (ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            if (ConfigType.SUPPRESS_USE_JOIN.getBoolean()) {
                suppressEventMessage(Parser.parseEvent(pName, world, EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", ConfigType.SUPPRESS_MAX_JOIN.getInteger());
                event.setJoinMessage(null);
            } else {
                event.setJoinMessage(Parser.parseEvent(pName, world, EventType.JOIN));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        if (!ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getLeaveMessage();

        if (msg == null) {
            return;
        }

        String reason = event.getReason();

        String kickMsg = Parser.parseEvent(pName, world, EventType.KICK).replace(IndicatorType.MISC_VAR.getValue() + "reason", reason).replace(IndicatorType.MISC_VAR.getValue() + "r", reason);

        if (ConfigType.SUPPRESS_USE_KICK.getBoolean()) {
            suppressEventMessage(kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick", ConfigType.SUPPRESS_MAX_KICK.getInteger());
            event.setLeaveMessage(null);
        } else {
            event.setLeaveMessage(kickMsg);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getQuitMessage();

        if (!ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            return;
        }

        if (msg == null) {
            return;
        }

        if (ConfigType.SUPPRESS_USE_QUIT.getBoolean()) {
            suppressEventMessage(Parser.parseEvent(pName, world, EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", ConfigType.SUPPRESS_MAX_QUIT.getInteger());
            event.setQuitMessage(null);
        } else {
            event.setQuitMessage(Parser.parseEvent(pName, world, EventType.QUIT));
        }
    }

    void suppressEventMessage(String format, String permNode, String overrideNode, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers())  {
            if (API.checkPermissions(player.getName(), player.getWorld().getName(), overrideNode)) {
                player.sendMessage(format);
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max)) {
                if (!API.checkPermissions(player.getName(), player.getWorld().getName(), permNode)) {
                    player.sendMessage(format);
                }
            }
        }

        MessageUtil.log(format);
    }

    void setListName(Player player, String listName) {
        try {
            if (listName.length() > 15) {
                listName = listName.substring(0, 16);
                player.setPlayerListName(listName);
            }

            player.setPlayerListName(listName);
        } catch(Exception ignored) {}
    }
}
