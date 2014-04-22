package ca.q0r.mchat.events.bukkit;

import ca.q0r.mchat.MChat;
import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Parser;
import ca.q0r.mchat.yml.config.ConfigType;
import ca.q0r.mchat.yml.locale.LocaleType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {
    private MChat plugin;

    public ChatListener(MChat instance) {
        plugin = instance;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();

        String world = player.getWorld().getName();
        String msg = event.getMessage();
        String eventFormat = Parser.parseChatMessage(player.getUniqueId(), world, msg);

        if (msg == null) {
            return;
        }

        setListName(player);

        // Chat Distance Stuff
        if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()
                        || players.getLocation().distance(player.getLocation()) > ConfigType.MCHAT_CHAT_DISTANCE.getDouble()) {
                    if (isSpy(players.getUniqueId(), players.getWorld().getName())) {
                        players.sendMessage(eventFormat.replace(LocaleType.FORMAT_LOCAL.getVal(), LocaleType.FORMAT_FORWARD.getVal()));
                    }

                    event.getRecipients().remove(players);
                }
            }
        }

        event.setFormat(eventFormat);
    }

    private Boolean isSpy(UUID uuid, String world) {
        if (API.checkPermissions(uuid, world, "mchat.spy")) {
            API.getSpying().put(uuid.toString(), true);
            return true;
        }

        API.getSpying().put(uuid.toString(), false);
        return false;
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