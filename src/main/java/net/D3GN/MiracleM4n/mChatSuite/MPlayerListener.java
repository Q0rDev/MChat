package net.D3GN.MiracleM4n.mChatSuite;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import me.desmin88.mobdisguise.MobDisguise;

import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.Date;

public class MPlayerListener extends PlayerListener implements Runnable {
    mChatSuite plugin;

    public MPlayerListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (!plugin.mChatEB)
            return;

        if (event.getMessage().contains("/mafk"))
            return;

        for (String aliases : plugin.getCommand("mafk").getAliases())
            if (event.getMessage().contains("/" + aliases))
                return;

        if (plugin.isAFK.get(player.getName()))
            player.performCommand("mafk");
    }

    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled())
            return;

        final Player player = event.getPlayer();
        String pName = player.getName();
        String msg = event.getMessage();

        if (msg == null)
            return;

        // For Dragonslife
        if (plugin.chatDistance > 0)
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()) {
                    event.getRecipients().remove(players);
                } else if (players.getLocation().distance(player.getLocation()) > plugin.chatDistance)
                    event.getRecipients().remove(players);
            }

        // For Obama?
        if (plugin.mobD)
            if (MobDisguise.p2p.get(pName) != null) {
                String pMDName = MobDisguise.p2p.get(pName);
                event.setFormat(plugin.mAPI.ParseChatMessage(pMDName, msg));
            }

        // For Cruxsky
        if (plugin.mAPI.ParseTabbedList(player).length() > 15) {
                String pLName = plugin.mAPI.ParseTabbedList(player);
                pLName = pLName.substring(0, 16);
                player.setPlayerListName(pLName);
        } else
            player.setPlayerListName(plugin.mAPI.ParseTabbedList(player));

        // PMChat
        if (plugin.mChatPB) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(plugin.mAPI.addColour("&4[Convo] " + plugin.mAPI.ParseChatMessage(pName, msg)));
                player.sendMessage(plugin.mAPI.addColour("&4[Convo] " + plugin.mAPI.ParseChatMessage(pName, msg)));
                plugin.mAPI.log(plugin.mAPI.addColour("&4[Convo] " + plugin.mAPI.ParseChatMessage(pName, msg)));
                event.setCancelled(true);
            }
        }

        // MChatEssentials
        if (plugin.mChatEB)
            if (plugin.isAFK.get(player.getName()))
                player.performCommand("mafk");

        if (plugin.spoutB) {
            final SpoutPlayer sPlayer = (SpoutPlayer) player;

            sPlayer.setTitle(ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + "- " + plugin.mAPI.addColour(msg) + ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + " -" + '\n' + plugin.mAPI.ParsePlayerName(player));

            plugin.chatt.put(player.getName(), false);

            Runnable runnable = new Runnable() {
                public void run() {
                    sPlayer.setTitle(plugin.mAPI.ParsePlayerName(player));
                }
            };

            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, 7*20);
        }

        event.setFormat(plugin.mAPI.ParseChatMessage(pName, msg));
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        String pName = player.getName();
        String msg = event.getJoinMessage();

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
                plugin.mIReader.addPlayer(pName, plugin.mIDefaultGroup);

        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                // For Cruxsky
                if (plugin.mAPI.ParseTabbedList(player).length() > 15) {
                        String pLName = plugin.mAPI.ParseTabbedList(player);
                        pLName = pLName.substring(0, 16);
                        player.setPlayerListName(pLName);
                } else
                    player.setPlayerListName(plugin.mAPI.ParseTabbedList(player));
            }
        }, 20L);

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTitle(plugin.mAPI.ParsePlayerName(player));
        }

        if (plugin.alterEvents)
            if (plugin.sJoinB) {
                suppressEventMessage(plugin.mAPI.ParseEventName(pName) + " " + plugin.mAPI.getEventMessage("Join"), "mchat.suppress.join", plugin.sJoinI);
                event.setJoinMessage("");
            } else
                event.setJoinMessage(plugin.mAPI.ParseEventName(pName) + " " + plugin.mAPI.getEventMessage("Join"));
    }

    public void onPlayerKick(PlayerKickEvent event) {
        if (!plugin.alterEvents)
            return;

        if (event.isCancelled())
            return;

        String pName = event.getPlayer().getName();
        String msg = event.getLeaveMessage();

        String reason = event.getReason();

        String kickMsg = plugin.mAPI.getEventMessage("Kick");

        kickMsg = plugin.mAPI.addColour(kickMsg.replace("+reason", reason).replace("+r", reason));

        if (msg == null)
            return;

        if (plugin.sKickB) {
            suppressEventMessage(plugin.mAPI.ParseEventName(pName) + " " + kickMsg, "mchat.suppress.kick", plugin.sKickI);
            event.setLeaveMessage("");
        } else
            event.setLeaveMessage(plugin.mAPI.ParseEventName(pName) + " " + kickMsg);
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String msg = event.getQuitMessage();

        if (!plugin.alterEvents)
            return;

        if (msg == null)
            return;

        if (plugin.sQuitB) {
            suppressEventMessage(plugin.mAPI.ParseEventName(pName) + " " + plugin.mAPI.getEventMessage("Quit"), "mchat.suppress.quit", plugin.sQuitI);
            event.setQuitMessage("");
        } else
            event.setQuitMessage(plugin.mAPI.ParseEventName(pName) + " " + plugin.mAPI.getEventMessage("Quit"));
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        BlockState block = event.getClickedBlock().getState();

        if (block.getTypeId() == 63) {
            Sign sign = (Sign)block;
            if (sign.getLine(0).equals("[mChat]"))
                if (plugin.getServer().getPlayer(sign.getLine(2)) != null)
                    if (sign.getLine(3) != null) {
                        sign.setLine(1, plugin.mAPI.addColour("&f" + (plugin.mAPI.ParseChatMessage(sign.getLine(2), "", sign.getLine(3)))));
                        sign.update(true);
                    }
        }
    }

    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!plugin.mChatEB)
            return;

        plugin.lastMove.put(player.getName(), new Date().getTime());

        if (plugin.isAFK.get(player.getName()))
            if(plugin.mAFKHQ) {
                if(plugin.AFKLoc.get(player.getName()) != null)
                    player.teleport(plugin.AFKLoc.get(player.getName()));

                player.sendMessage(plugin.mCSender.formatMessage(plugin.lListener.sAFK));
            } else
                player.performCommand("mafk");
    }

    public void run() {}

    void suppressEventMessage(String format, String permNode, Integer max) {
        if (!(plugin.getServer().getOnlinePlayers().length > max))
            for (Player player : plugin.getServer().getOnlinePlayers())
                if (!plugin.mAPI.checkPermissions(player, permNode))
                    player.sendMessage(format);

        plugin.mAPI.log(format);
    }
}

