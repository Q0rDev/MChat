package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import me.desmin88.mobdisguise.MobDisguise;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

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

    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
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
                event.setFormat(plugin.getAPI().ParseChatMessage(pMDName, msg));
            }

        // For Cruxsky
        if (plugin.getAPI().ParseTabbedList(player).length() > 15) {
            String pLName = plugin.getAPI().ParseTabbedList(player);
            pLName = pLName.substring(0, 16);
            player.setPlayerListName(pLName);
        } else
            player.setPlayerListName(plugin.getAPI().ParseTabbedList(player));

        // PMChat
        if (plugin.mChatPB) {
            if (plugin.isConv.get(pName) == null)
                plugin.isConv.put(pName, false);

            if (plugin.isConv.get(pName)) {
                Player recipient = plugin.getServer().getPlayer(plugin.chatPartner.get(pName));
                recipient.sendMessage(plugin.getAPI().addColour("&4[Convo] " + plugin.getAPI().ParseChatMessage(pName, msg)));
                player.sendMessage(plugin.getAPI().addColour("&4[Convo] " + plugin.getAPI().ParseChatMessage(pName, msg)));
                plugin.getAPI().log(plugin.getAPI().addColour("&4[Convo] " + plugin.getAPI().ParseChatMessage(pName, msg)));
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
            final String sPName = sPlayer.getName();

            sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getAPI().addColour(msg) + ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + " -" + '\n' + plugin.getAPI().ParsePlayerName(player));

            plugin.chatt.put(player.getName(), false);

            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    SpoutPlayer sPlayer = (SpoutPlayer) plugin.getServer().getPlayer(sPName);

                    if (sPlayer != null)
                        sPlayer.setTitle(plugin.getAPI().ParsePlayerName(sPlayer));
                }
            }, 7 * 20);
        }

        event.setFormat(plugin.getAPI().ParseChatMessage(pName, msg));
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
                plugin.getInfoWriter().addPlayer(pName, plugin.mIDefaultGroup);

        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            public void run() {
                // For Cruxsky
                if (plugin.getAPI().ParseTabbedList(player).length() > 15) {
                    String pLName = plugin.getAPI().ParseTabbedList(player);
                    pLName = pLName.substring(0, 16);
                    player.setPlayerListName(pLName);
                } else
                    player.setPlayerListName(plugin.getAPI().ParseTabbedList(player));
            }
        }, 20L);

        if (plugin.spoutB) {
            SpoutPlayer sPlayer = (SpoutPlayer) player;
            sPlayer.setTitle(plugin.getAPI().ParsePlayerName(player));
        }

        if (plugin.alterEvents)
            if (plugin.sJoinB) {
                suppressEventMessage(plugin.getAPI().ParseEventName(pName) + " " + plugin.getInfoReader().getEventMessage("Join"), "mchat.suppress.join", "mchat.bypass.suppress.join", plugin.sJoinI);
                event.setJoinMessage("");
            } else
                event.setJoinMessage(plugin.getAPI().ParseEventName(pName) + " " + plugin.getInfoReader().getEventMessage("Join"));
    }

    public void onPlayerKick(PlayerKickEvent event) {
        if (!plugin.alterEvents)
            return;

        if (event.isCancelled())
            return;

        String pName = event.getPlayer().getName();
        String msg = event.getLeaveMessage();

        String reason = event.getReason();

        String kickMsg = plugin.getInfoReader().getEventMessage("Kick");

        kickMsg = plugin.getAPI().addColour(kickMsg.replace("+reason", reason).replace("+r", reason));

        if (msg == null)
            return;

        if (plugin.sKickB) {
            suppressEventMessage(plugin.getAPI().ParseEventName(pName) + " " + kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick",plugin.sKickI);
            event.setLeaveMessage("");
        } else
            event.setLeaveMessage(plugin.getAPI().ParseEventName(pName) + " " + kickMsg);
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        String pName = event.getPlayer().getName();
        String msg = event.getQuitMessage();

        if (!plugin.alterEvents)
            return;

        if (msg == null)
            return;

        if (plugin.sQuitB) {
            suppressEventMessage(plugin.getAPI().ParseEventName(pName) + " " + plugin.getInfoReader().getEventMessage("Quit"), "mchat.suppress.quit", "mchat.bypass.suppress.quit", plugin.sQuitI);
            event.setQuitMessage("");
        } else
            event.setQuitMessage(plugin.getAPI().ParseEventName(pName) + " " + plugin.getInfoReader().getEventMessage("Quit"));
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        BlockState block = event.getClickedBlock().getState();

        if (block.getTypeId() == 63) {
            Sign sign = (Sign) block;
            if (sign.getLine(0).equals("[mChat]"))
                if (plugin.getServer().getPlayer(sign.getLine(2)) != null)
                    if (sign.getLine(3) != null) {
                        sign.setLine(1, plugin.getAPI().addColour("&f" + (plugin.getAPI().ParseMessage(sign.getLine(2), "", sign.getLine(3)))));
                        sign.update(true);
                    }
        }
    }

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

                player.sendMessage(mChatSuite.mCSender.formatMessage(plugin.getLocale().getOption("stillAFK")));
            } else
                player.performCommand("mafk");
    }

    void suppressEventMessage(String format, String permNode, String overrideNode, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers())  {
            if (plugin.getAPI().checkPermissions(player, overrideNode)) {
                player.sendMessage(format);
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!plugin.getAPI().checkPermissions(player, permNode))
                    player.sendMessage(format);
        }

        plugin.getAPI().log(format);
    }

    public void run() {
    }
}

