package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Reader;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.channels.Channel;
import com.miraclem4n.mchat.channels.ChannelManager;
import com.miraclem4n.mchat.configs.InfoUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.EventType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.Date;

public class PlayerListener implements Listener {
    mChatSuite plugin;

    public PlayerListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();

        if (!ConfigType.MCHATE_ENABLE.getObject().toBoolean())
            return;

        plugin.lastMove.put(pName, new Date().getTime());

        for (String aliases : plugin.getCommand("mchatafk").getAliases())
            if (event.getMessage().contains("/" + aliases) ||
                    event.getMessage().contains("/mchatafk"))
                return;

        if (plugin.isAFK.get(pName) == null)
            return;

        if (plugin.isAFK.get(pName))
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + pName);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String world = player.getWorld().getName();

        String pName = player.getName();
        String mPName = player.getName();
        String msg = event.getJoinMessage();

        Channel dChannel = ChannelManager.getDefaultChannel();
        Channel cChannel = ChannelManager.getChannel(pName);

        if (dChannel != null && cChannel == null) {
            dChannel.addOccupant(pName, true);
            dChannel.broadcastMessage(Parser.parsePlayerName(pName, world) + " has joined channel " + dChannel.getName() + "!");
        }

        final String rPName = mPName;

        if (msg == null)
            return;

        if (ConfigType.MCHATE_ENABLE.getObject().toBoolean()) {
            plugin.chatt.put(player.getName(), false);
            plugin.isAFK.put(player.getName(), false);
            plugin.lastMove.put(player.getName(), new Date().getTime());
        }

        // For Lazy People
        if (ConfigType.INFO_ADD_NEW_PLAYERS.getObject().toBoolean())
            if (InfoUtil.getConfig().get("users." + pName) == null)
                Writer.addBase(pName, ConfigType.INFO_DEFAULT_GROUP.getObject().toString());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                // For Cruxsky
                if (Parser.parseTabbedList(rPName, world).length() > 15) {
                    String pLName = Parser.parseTabbedList(rPName, world);
                    pLName = pLName.substring(0, 16);
                    setListName(player, pLName);
                } else
                    setListName(player, Parser.parseTabbedList(rPName, world));
            }
        }, 20L);

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTitle(Parser.parsePlayerName(mPName, world));
        }

        if (ConfigType.MCHAT_ALTER_EVENTS.getObject().toBoolean())
            if (ConfigType.SUPPRESS_USE_JOIN.getObject().toBoolean()) {
                suppressEventMessage(Parser.parseEventName(mPName, world) + " " + Reader.getEventMessage(EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", ConfigType.SUPPRESS_MAX_JOIN.getObject().toInteger());
                event.setJoinMessage(null);
            } else
                event.setJoinMessage(Parser.parseEventName(mPName, world) + " " + Reader.getEventMessage(EventType.JOIN));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        if (!ConfigType.MCHAT_ALTER_EVENTS.getObject().toBoolean())
            return;

        if (event.isCancelled())
            return;

        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getLeaveMessage();

        if (msg == null)
            return;

        String reason = event.getReason();

        String kickMsg = Reader.getEventMessage(EventType.KICK);

        kickMsg = MessageUtil.addColour(kickMsg.replace(ConfigType.MCHAT_VAR_INDICATOR.getObject().toString() + "reason", reason).replace(ConfigType.MCHAT_VAR_INDICATOR.getObject().toString() + "r", reason));

        if (ConfigType.SUPPRESS_USE_KICK.getObject().toBoolean()) {
            suppressEventMessage(Parser.parseEventName(pName, world) + " " + kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick", ConfigType.SUPPRESS_MAX_KICK.getObject().toInteger());
            event.setLeaveMessage(null);
        } else
            event.setLeaveMessage(Parser.parseEventName(pName, world) + " " + kickMsg);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getQuitMessage();

        if (!ConfigType.MCHAT_ALTER_EVENTS.getObject().toBoolean())
            return;

        if (msg == null)
            return;

        if (ConfigType.SUPPRESS_USE_QUIT.getObject().toBoolean()) {
            suppressEventMessage(Parser.parseEventName(pName, world) + " " + Reader.getEventMessage(EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", ConfigType.SUPPRESS_MAX_QUIT.getObject().toInteger());
            event.setQuitMessage(null);
        } else
            event.setQuitMessage(Parser.parseEventName(pName, world) + " " + Reader.getEventMessage(EventType.QUIT));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        Integer fromX = from.getBlockX();
        Integer fromY = from.getBlockY();
        Integer fromZ = from.getBlockZ();

        Integer toX = to.getBlockX();
        Integer toY = to.getBlockY();
        Integer toZ = to.getBlockZ();


        String fromLoc = from.getWorld().getName() + "|" + fromX + "|" + fromY + "|" + fromZ;
        String toLoc = to.getWorld().getName() + "|" + toX + "|" + toY + "|" + toZ;

        if (fromLoc.equalsIgnoreCase(toLoc))
            return;

        if (event.isCancelled())
            return;

        Player player = event.getPlayer();

        if (!ConfigType.MCHATE_ENABLE.getObject().toBoolean())
            return;

        plugin.lastMove.put(player.getName(), new Date().getTime());

        if (plugin.isAFK.get(player.getName()) == null)
            return;

        if (plugin.isAFK.get(player.getName()))
            if (ConfigType.MCHATE_HC_AFK.getObject().toBoolean()) {
                if (plugin.AFKLoc.get(player.getName()) != null)
                    player.teleport(plugin.AFKLoc.get(player.getName()));

                MessageUtil.sendMessage(player, LocaleType.PLAYER_STILL_AFK.getValue());
            } else
                player.performCommand("mafk");
    }

    void suppressEventMessage(String format, String permNode, String overrideNode, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers())  {
            if (API.checkPermissions(player.getName(), player.getWorld().getName(), overrideNode)) {
                player.sendMessage(format);
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!API.checkPermissions(player.getName(), player.getWorld().getName(), permNode))
                    player.sendMessage(format);
        }

        MessageUtil.log(format);
    }

    void setListName(Player player, String listName) {
        try {
            player.setPlayerListName(listName);
        } catch(Exception ignored) {}
    }
}

