package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigYml;
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

        ConfigYml configYml = (ConfigYml) YmlManager.getYml(YmlType.CONFIG_YML);

        for (Map.Entry<String, List<String>> entry : configYml.getAliasMap().entrySet()) {
            for (String comm : entry.getValue()) {
                if (comm.equalsIgnoreCase(command)) {
                    event.setMessage(msg.replaceFirst("/" + command, "/" + entry.getKey()));
                    return;
                }
            }
        }
    }
}
