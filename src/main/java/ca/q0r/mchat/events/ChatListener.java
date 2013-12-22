package ca.q0r.mchat.events;

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
        String pName = player.getName();

        String world = player.getWorld().getName();
        String msg = event.getMessage();
        String eventFormat = Parser.parseChatMessage(pName, world, msg);

        if (msg == null) {
            return;
        }

        setListName(player, Parser.parseTabbedList(pName, world));

        // Chat Distance Stuff
        if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                if (players.getWorld() != player.getWorld()
                        || players.getLocation().distance(player.getLocation()) > ConfigType.MCHAT_CHAT_DISTANCE.getDouble()) {
                    if (isSpy(players.getName(), players.getWorld().getName())) {
                        players.sendMessage(eventFormat.replace(LocaleType.FORMAT_LOCAL.getVal(), LocaleType.FORMAT_FORWARD.getVal()));
                    }

                    event.getRecipients().remove(players);
                }
            }
        }

        event.setFormat(eventFormat);
    }

    private Boolean isSpy(String player, String world) {
        if (API.checkPermissions(player, world, "mchat.spy")) {
            API.isSpying().put(player, true);
            return true;
        }

        API.isSpying().put(player, false);
        return false;
    }

    private void setListName(Player player, String listName) {
        try {
            if (listName.length() > 15) {
                listName = listName.substring(0, 16);
                player.setPlayerListName(listName);
            }

            player.setPlayerListName(listName);
        } catch (Exception ignored) { }
    }
}