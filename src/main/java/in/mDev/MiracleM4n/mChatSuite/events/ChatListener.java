package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import in.mDev.MiracleM4n.mChatSuite.types.config.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import me.desmin88.mobdisguise.MobDisguise;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.Date;

public class ChatListener implements Listener {
    mChatSuite plugin;

    public ChatListener(mChatSuite instance) {
        plugin = instance;
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
