package in.mDev.MiracleM4n.mChatSuite.bukkit.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.*;

public class BMPlayerListener implements Runnable, Listener {
    mChatSuite plugin;

    public BMPlayerListener(mChatSuite plugin) {
        this.plugin = plugin;
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

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        String pName = player.getName();
        String world = player.getWorld().getName();
        String mPName = player.getName();
        String pLName = plugin.getAPI().ParseTabbedList(pName, world);
        String msg = event.getMessage();
        String eventFormat = plugin.getAPI().ParseChatMessage(pName, world, msg);

        if (msg == null)
            return;

        // For Dragonslife
        if (plugin.chatDistance > 0)
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()) {
                    if (!checkSpy(players.getName(), players.getWorld().getName()))
                        event.getRecipients().remove(players);
                } else if (players.getLocation().distance(player.getLocation()) > plugin.chatDistance)
                    if (!checkSpy(players.getName(), players.getWorld().getName()))
                        event.getRecipients().remove(players);
            }

        // For Obama?
        if (plugin.mobD)
            if (MobDisguise.p2p.get(pName) != null) {
                mPName = MobDisguise.p2p.get(pName);
                pLName = plugin.getAPI().ParseTabbedList(mPName, world);
                eventFormat = plugin.getAPI().ParseChatMessage(mPName, world, msg);
            }

        // For Cruxsky
        if (pLName.length() > 15) {
            pLName = pLName.substring(0, 16);
            doPlayerListStuff(player, pLName);
        } else
            doPlayerListStuff(player, pLName);

        // PMChat
        if (plugin.mChatPB) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(plugin.getAPI().addColour("&4[Convo] " + eventFormat));
                player.sendMessage(plugin.getAPI().addColour("&4[Convo] " + eventFormat));
                plugin.getAPI().log(plugin.getAPI().addColour("&4[Convo] " + eventFormat));
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

            sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getAPI().addColour(msg) + ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + " -" + '\n' + plugin.getAPI().ParsePlayerName(mPName, world));

            plugin.chatt.put(player.getName(), false);

            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    SpoutPlayer sPlayer = (SpoutPlayer) plugin.getServer().getPlayer(sPName);

                    if (sPlayer != null)
                        sPlayer.setTitle(plugin.getAPI().ParsePlayerName(sPlayer.getName(), sPlayer.getWorld().getName()));
                }
            }, 7 * 20);
        }

        event.setFormat(eventFormat);

        if (plugin.eBroadcast) {
            plugin.bMessage.checkState();
            plugin.bMessage.sendMessage(eventFormat);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String world = player.getWorld().getName();
        String pName = player.getName();
        String mPName = player.getName();
        String msg = event.getJoinMessage();

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
            if (plugin.mIConfig.get("users." + pName) == null)
                plugin.getInfoWriter().addBase(pName, plugin.mIDefaultGroup);

        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                // For Cruxsky
                if (plugin.getAPI().ParseTabbedList(rPName, world).length() > 15) {
                    String pLName = plugin.getAPI().ParseTabbedList(rPName, world);
                    pLName = pLName.substring(0, 16);
                    doPlayerListStuff(player, pLName);
                } else
                    doPlayerListStuff(player, plugin.getAPI().ParseTabbedList(rPName, world));
            }
        }, 20L);

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTitle(plugin.getAPI().ParsePlayerName(mPName, world));
        }

        if (plugin.alterEvents)
            if (plugin.sJoinB) {
                suppressEventMessage(plugin.getAPI().ParseEventName(mPName, world) + " " + plugin.getInfoReader().getEventMessage("Join"), "mchat.suppress.join", "mchat.bypass.suppress.join", plugin.sJoinI);
                event.setJoinMessage("");
            } else
                event.setJoinMessage(plugin.getAPI().ParseEventName(mPName, world) + " " + plugin.getInfoReader().getEventMessage("Join"));
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (!plugin.alterEvents)
            return;

        if (event.isCancelled())
            return;

        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getLeaveMessage();

        String reason = event.getReason();

        String kickMsg = plugin.getInfoReader().getEventMessage("Kick");

        kickMsg = plugin.getAPI().addColour(kickMsg.replace("+reason", reason).replace("+r", reason));

        if (msg == null)
            return;

        if (plugin.sKickB) {
            suppressEventMessage(plugin.getAPI().ParseEventName(pName, world) + " " + kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick",plugin.sKickI);
            event.setLeaveMessage("");
        } else
            event.setLeaveMessage(plugin.getAPI().ParseEventName(pName, world) + " " + kickMsg);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getQuitMessage();

        if (!plugin.alterEvents)
            return;

        if (msg == null)
            return;

        if (plugin.sQuitB) {
            suppressEventMessage(plugin.getAPI().ParseEventName(pName, world) + " " + plugin.getInfoReader().getEventMessage("Quit"), "mchat.suppress.quit", "mchat.bypass.suppress.quit", plugin.sQuitI);
            event.setQuitMessage("");
        } else
            event.setQuitMessage(plugin.getAPI().ParseEventName(pName, world) + " " + plugin.getInfoReader().getEventMessage("Quit"));
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
                        sign.setLine(1, plugin.getAPI().addColour("&f" + (plugin.getAPI().ParseMessage(sign.getLine(2), block.getWorld().getName(),"", sign.getLine(3)))));
                        sign.update(true);
                    }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
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

                player.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("stillAFK")));
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

        if (plugin.eBroadcast) {
            plugin.bMessage.checkState();
            plugin.bMessage.sendMessage(format);
        }

        plugin.getAPI().log(format);
    }
    
    Boolean checkSpy(String player, String world) {
        if (plugin.getAPI().checkPermissions(player, world, "mchat.spy")) {
            plugin.isSpying.put(player, true);
            return true;
        }

        plugin.isSpying.put(player, false);
        return false;
    }
    
    void doPlayerListStuff(Player player, String listName) {
        try {
            player.setPlayerListName(listName);
        } catch(Exception ignored) {}
    }

    public void run() {}
}

