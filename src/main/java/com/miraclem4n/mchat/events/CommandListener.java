package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.configs.ConfigUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Map;

public class CommandListener implements Listener {
    public CommandListener() {}

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        String command = msg.split(" ")[0].replace("/", "");

        for (Map.Entry<String, List<String>> entry : ConfigUtil.getAliasMap().entrySet()) {
            for (String comm : entry.getValue()) {
                if (comm.equalsIgnoreCase(command)) {
                    event.setMessage(msg.replaceFirst("/" + command, "/" + entry.getKey()));
                    return;
                }
            }
        }
    }
}
