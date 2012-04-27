package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.types.EventType;
import in.mDev.MiracleM4n.mChatSuite.channel.Channel;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;
import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.*;

public class PlayerListener implements Listener {
    mChatSuite plugin;

    public PlayerListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!plugin.mChatEB)
            return;

        plugin.lastMove.put(player.getName(), new Date().getTime());

        for (String aliases : plugin.getCommand("mchatafk").getAliases())
            if (event.getMessage().contains("/" + aliases) ||
                    event.getMessage().contains("/mchatafk"))
                return;

        if (plugin.isAFK.get(player.getName()) == null)
            return;

        if (plugin.isAFK.get(player.getName()))
            player.performCommand("mafk");
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
        if (plugin.mChatPB) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(Messanger.addColour("&4[Convo] " + eventFormat));
                player.sendMessage(Messanger.addColour("&4[Convo] " + eventFormat));
                Messanger.log(Messanger.addColour("&4[Convo] " + eventFormat));
                event.setCancelled(true);
            }
        }

        // MChatEssentials
        if (plugin.mChatEB) {
            if (plugin.isAFK.get(player.getName()) != null)
                if (plugin.isAFK.get(player.getName()))
                    player.performCommand("mafk");

            plugin.lastMove.put(player.getName(), new Date().getTime());
        }

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            final String sPName = mPName;

            sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption(LocaleType.SPOUT_COLOUR).toUpperCase()) + "- " + Messanger.addColour(msg) + ChatColor.valueOf(plugin.getLocale().getOption(LocaleType.SPOUT_COLOUR).toUpperCase()) + " -" + '\n' + plugin.getParser().parsePlayerName(mPName, world));

            plugin.chatt.put(player.getName(), false);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    SpoutPlayer sPlayer = (SpoutPlayer) plugin.getServer().getPlayer(sPName);

                    if (sPlayer != null)
                        sPlayer.setTitle(plugin.getParser().parsePlayerName(sPlayer.getName(), sPlayer.getWorld().getName()));
                }
            }, 7 * 20);
        }

        // Chat Distance Stuff
        if (plugin.chatDistance > 0)
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()) {
                    if (isSpy(players.getName(), players.getWorld().getName()))
                        players.sendMessage(eventFormat.replace(plugin.getLocale().getOption(LocaleType.FORMAT_LOCAL), plugin.getLocale().getOption(LocaleType.FORMAT_FORWARD)));

                    event.getRecipients().remove(players);
                } else if (players.getLocation().distance(player.getLocation()) > plugin.chatDistance) {
                    if (isSpy(players.getName(), players.getWorld().getName()))
                        players.sendMessage(eventFormat.replace(plugin.getLocale().getOption(LocaleType.FORMAT_LOCAL), plugin.getLocale().getOption(LocaleType.FORMAT_FORWARD)));

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

        Channel dChannel = plugin.getChannelManager().getDefaultChannel();

        if (dChannel != null) {
            dChannel.addOccupant(pName, true);
            dChannel.broadcastMessage(plugin.getParser().parsePlayerName(pName, world) + " has joined channel " + dChannel.getName() + "!");
        }

        if (plugin.mobD)
            if (MobDisguise.p2p.get(pName) != null)
                mPName = MobDisguise.p2p.get(pName);

        final String rPName = mPName;

        if (msg == null)
            return;

        if (plugin.mChatEB) {
            plugin.chatt.put(player.getName(), false);
            plugin.isAFK.put(player.getName(), false);
            plugin.lastMove.put(player.getName(), new Date().getTime());
        }

        // For Lazy People
        if (plugin.useAddDefault)
            if (plugin.info.get("users." + pName) == null)
                plugin.getWriter().addBase(pName, plugin.mIDefaultGroup);

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

        if (plugin.alterEvents)
            if (plugin.sJoinB) {
                suppressEventMessage(plugin.getParser().parseEventName(mPName, world) + " " + plugin.getReader().getEventMessage(EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", plugin.sJoinI);
                event.setJoinMessage(null);
            } else
                event.setJoinMessage(plugin.getParser().parseEventName(mPName, world) + " " + plugin.getReader().getEventMessage(EventType.JOIN));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        if (!plugin.alterEvents)
            return;

        if (event.isCancelled())
            return;

        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getLeaveMessage();

        String reason = event.getReason();

        String kickMsg = plugin.getReader().getEventMessage(EventType.KICK);

        kickMsg = Messanger.addColour(kickMsg.replace(plugin.varIndicator + "reason", reason).replace(plugin.varIndicator + "r", reason));

        if (msg == null)
            return;

        if (plugin.sKickB) {
            suppressEventMessage(plugin.getParser().parseEventName(pName, world) + " " + kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick",plugin.sKickI);
            event.setLeaveMessage(null);
        } else
            event.setLeaveMessage(plugin.getParser().parseEventName(pName, world) + " " + kickMsg);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getQuitMessage();

        if (!plugin.alterEvents)
            return;

        if (msg == null)
            return;

        if (plugin.sQuitB) {
            suppressEventMessage(plugin.getParser().parseEventName(pName, world) + " " + plugin.getReader().getEventMessage(EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", plugin.sQuitI);
            event.setQuitMessage(null);
        } else
            event.setQuitMessage(plugin.getParser().parseEventName(pName, world) + " " + plugin.getReader().getEventMessage(EventType.QUIT));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        BlockState block = event.getClickedBlock().getState();

        if (block.getTypeId() == 63) {
            Sign sign = (Sign) block;
            if (sign.getLine(0).equals("[mChat]"))
                if (plugin.getServer().getPlayer(sign.getLine(2)) != null)
                    if (sign.getLine(3) != null) {
                        sign.setLine(1, Messanger.addColour("&f" + (plugin.getParser().parseMessage(sign.getLine(2), block.getWorld().getName(), "", sign.getLine(3)))));
                        sign.update(true);
                    }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getBlock()
                == event.getFrom().getBlock())
            return;

        Player player = event.getPlayer();

        if (!plugin.mChatEB)
            return;

        plugin.lastMove.put(player.getName(), new Date().getTime());

        if (plugin.isAFK.get(player.getName()) == null)
            return;

        if (plugin.isAFK.get(player.getName()))
            if (plugin.mAFKHQ) {
                if (plugin.AFKLoc.get(player.getName()) != null)
                    player.teleport(plugin.AFKLoc.get(player.getName()));

                Messanger.sendMessage(player, plugin.getLocale().getOption(LocaleType.PLAYER_STILL_AFK));
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

        Messanger.log(format);
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

