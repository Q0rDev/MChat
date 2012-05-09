package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.channel.Channel;
import in.mDev.MiracleM4n.mChatSuite.channel.ChannelManager;
import in.mDev.MiracleM4n.mChatSuite.configs.InfoUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.EventType;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import in.mDev.MiracleM4n.mChatSuite.types.config.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import me.desmin88.mobdisguise.MobDisguise;
import org.bukkit.ChatColor;
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
    public void onPlayerChat(PlayerChatEvent event) {
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
        String mPName = player.getName();
        String pLName = plugin.getParser().parseTabbedList(pName, world);
        String msg = event.getMessage();
        String eventFormat = plugin.getParser().parseChatMessage(pName, world, msg);

        if (msg == null)
            return;

        // Message Prefix
        if (plugin.mPrefix.get(pName) != null
                && !plugin.mPrefix.get(pName).isEmpty())
            msg = plugin.mPrefix.get(pName) + msg;

        // Broken MobDisguise Support
        if (plugin.mobD)
            if (MobDisguise.p2p.get(pName) != null) {
                mPName = MobDisguise.p2p.get(pName);
                pLName = plugin.getParser().parseTabbedList(mPName, world);
                eventFormat = plugin.getParser().parseChatMessage(mPName, world, msg);
            }

        // Fix for Too long List Names
        if (pLName.length() > 15) {
            pLName = pLName.substring(0, 16);
            setListName(player, pLName);
        } else
            setListName(player, pLName);

        // PMChat
        if (ConfigType.PMCHAT_ENABLE.getObject().toBoolean()) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(MessageUtil.addColour("&4[Convo] " + eventFormat));
                player.sendMessage(MessageUtil.addColour("&4[Convo] " + eventFormat));
                MessageUtil.log(MessageUtil.addColour("&4[Convo] " + eventFormat));
                event.setCancelled(true);
            }
        }

        // MChatEssentials
        if (ConfigType.MCHATE_ENABLE.getObject().toBoolean()) {
            if (plugin.isAFK.get(pName) != null)
                if (plugin.isAFK.get(pName))
                    player.performCommand("mafk");

            plugin.lastMove.put(pName, new Date().getTime());
        }

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            final String sPName = mPName;

            sPlayer.setTitle(ChatColor.valueOf(LocaleType.SPOUT_COLOUR.getValue().toUpperCase())
                    + "- " + MessageUtil.addColour(msg) + ChatColor.valueOf(LocaleType.SPOUT_COLOUR.getValue().toUpperCase())
                    + " -" + '\n' + plugin.getParser().parsePlayerName(mPName, world));

            plugin.chatt.put(pName, false);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    SpoutPlayer sPlayer = (SpoutPlayer) plugin.getServer().getPlayer(sPName);

                    if (sPlayer != null)
                        sPlayer.setTitle(plugin.getParser().parsePlayerName(sPName, sPlayer.getWorld().getName()));
                }
            }, 7 * 20);
        }

        // Chat Distance Stuff
        if (ConfigType.MCHAT_CHAT_DISTANCE.getObject().toDouble() > 0)
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()
                        || players.getLocation().distance(player.getLocation()) > ConfigType.MCHAT_CHAT_DISTANCE.getObject().toDouble()) {
                    if (isSpy(players.getName(), players.getWorld().getName()))
                        players.sendMessage(eventFormat.replace(LocaleType.FORMAT_LOCAL.getValue(), LocaleType.FORMAT_FORWARD.getValue()));

                    event.getRecipients().remove(players);
                }
            }

        event.setFormat(eventFormat);
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
            dChannel.broadcastMessage(plugin.getParser().parsePlayerName(pName, world) + " has joined channel " + dChannel.getName() + "!");
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
                plugin.getWriter().addBase(pName, ConfigType.INFO_DEFAULT_GROUP.getObject().toString());

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                // For Cruxsky
                if (plugin.getParser().parseTabbedList(rPName, world).length() > 15) {
                    String pLName = plugin.getParser().parseTabbedList(rPName, world);
                    pLName = pLName.substring(0, 16);
                    setListName(player, pLName);
                } else
                    setListName(player, plugin.getParser().parseTabbedList(rPName, world));
            }
        }, 20L);

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTitle(plugin.getParser().parsePlayerName(mPName, world));
        }

        if (ConfigType.MCHAT_ALTER_EVENTS.getObject().toBoolean())
            if (ConfigType.SUPPRESS_USE_JOIN.getObject().toBoolean()) {
                suppressEventMessage(plugin.getParser().parseEventName(mPName, world) + " " + plugin.getReader().getEventMessage(EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", ConfigType.SUPPRESS_MAX_JOIN.getObject().toInteger());
                event.setJoinMessage(null);
            } else
                event.setJoinMessage(plugin.getParser().parseEventName(mPName, world) + " " + plugin.getReader().getEventMessage(EventType.JOIN));
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

        String kickMsg = plugin.getReader().getEventMessage(EventType.KICK);

        kickMsg = MessageUtil.addColour(kickMsg.replace(ConfigType.MCHAT_VAR_INDICATOR.getObject().toString() + "reason", reason).replace(ConfigType.MCHAT_VAR_INDICATOR.getObject().toString() + "r", reason));

        if (ConfigType.SUPPRESS_USE_KICK.getObject().toBoolean()) {
            suppressEventMessage(plugin.getParser().parseEventName(pName, world) + " " + kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick", ConfigType.SUPPRESS_MAX_KICK.getObject().toInteger());
            event.setLeaveMessage(null);
        } else
            event.setLeaveMessage(plugin.getParser().parseEventName(pName, world) + " " + kickMsg);
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
            suppressEventMessage(plugin.getParser().parseEventName(pName, world) + " " + plugin.getReader().getEventMessage(EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", ConfigType.SUPPRESS_MAX_QUIT.getObject().toInteger());
            event.setQuitMessage(null);
        } else
            event.setQuitMessage(plugin.getParser().parseEventName(pName, world) + " " + plugin.getReader().getEventMessage(EventType.QUIT));
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
            if (plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), overrideNode)) {
                player.sendMessage(format);
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), permNode))
                    player.sendMessage(format);
        }

        MessageUtil.log(format);
    }

    Boolean isSpy(String player, String world) {
        if (plugin.getAPI().checkPermissions(player, world, "mchat.spy")) {
            plugin.isSpying.put(player, true);
            return true;
        }

        plugin.isSpying.put(player, false);
        return false;
    }

    void setListName(Player player, String listName) {
        try {
            player.setPlayerListName(listName);
        } catch(Exception ignored) {}
    }
}

