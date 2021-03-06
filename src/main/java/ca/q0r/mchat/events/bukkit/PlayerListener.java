package ca.q0r.mchat.events.bukkit;

import ca.q0r.mchat.MChat;
import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.api.Writer;
import ca.q0r.mchat.types.EventType;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.config.ConfigType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {
    MChat plugin;

    public PlayerListener(MChat instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        String world = player.getWorld().getName();
        String msg = event.getJoinMessage();
        UUID uuid = player.getUniqueId();

        if (plugin.update && API.checkPermissions(uuid, "mchat.update")) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    MessageUtil.sendMessage(player, "An update is available! Please check");
                    MessageUtil.sendMessage(player, "http://goo.gl/dCwFac for details!");
                }

            }, 50);
        }

        if (msg == null) {
            return;
        }

        if (ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean()) {
            if (YmlManager.getYml(YmlType.INFO_YML).getConfig().get("users." + uuid) == null) {
                Writer.addBase(uuid.toString(), InfoType.USER);
            }
        }


        if (ConfigType.MCHAT_ALTER_LIST.getBoolean()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                public void run() {
                    setListName(player);
                }
            }, 20L);
        }

        if (ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            if (ConfigType.SUPPRESS_USE_JOIN.getBoolean()) {
                suppressEventMessage(Parser.parseEvent(uuid, world, EventType.JOIN), "mchat.suppress.join", "mchat.bypass.suppress.join", ConfigType.SUPPRESS_MAX_JOIN.getInteger());
                event.setJoinMessage(null);
            } else {
                event.setJoinMessage(Parser.parseEvent(uuid, world, EventType.JOIN));
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

        UUID uuid = event.getPlayer().getUniqueId();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getLeaveMessage();

        if (msg == null) {
            return;
        }

        String reason = event.getReason();

        String kickMsg = Parser.parseEvent(uuid, world, EventType.KICK).replace(IndicatorType.MISC_VAR.getValue() + "reason", reason).replace(IndicatorType.MISC_VAR.getValue() + "r", reason);

        if (ConfigType.SUPPRESS_USE_KICK.getBoolean()) {
            suppressEventMessage(kickMsg, "mchat.suppress.kick", "mchat.bypass.suppress.kick", ConfigType.SUPPRESS_MAX_KICK.getInteger());
            event.setLeaveMessage(null);
        } else {
            event.setLeaveMessage(kickMsg);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        String world = event.getPlayer().getWorld().getName();
        String msg = event.getQuitMessage();

        if (!ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            return;
        }

        if (msg == null) {
            return;
        }

        if (ConfigType.SUPPRESS_USE_QUIT.getBoolean()) {
            suppressEventMessage(Parser.parseEvent(uuid, world, EventType.QUIT), "mchat.suppress.quit", "mchat.bypass.suppress.quit", ConfigType.SUPPRESS_MAX_QUIT.getInteger());
            event.setQuitMessage(null);
        } else {
            event.setQuitMessage(Parser.parseEvent(uuid, world, EventType.QUIT));
        }
    }

    private void suppressEventMessage(String format, String permNode, String overrideNode, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (API.checkPermissions(player.getUniqueId(), overrideNode)) {
                player.sendMessage(format);
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().size() > max)) {
                if (!API.checkPermissions(player.getUniqueId(), permNode)) {
                    player.sendMessage(format);
                }
            }
        }

        MessageUtil.log(format);
    }

    private void setListName(Player player) {
        String listName = Parser.parseTabbedList(player.getUniqueId(), player.getWorld().getName());

        try {
            if (listName.length() > 15) {
                listName = listName.substring(0, 16);
                player.setPlayerListName(listName);
            }

            player.setPlayerListName(listName);
        } catch (Exception ignored) {
        }
    }
}