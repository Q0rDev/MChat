package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.api.Parser;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

public class CustomListener implements Listener {
    mChatSuite plugin;

    public CustomListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler
    public void onKeyPressedEvent(KeyPressedEvent event) {
        SpoutPlayer player = event.getPlayer();

        String pName = player.getName();

        Keyboard key = event.getKey();
        Keyboard chatKey = player.getChatKey();
        Keyboard forwardKey = player.getForwardKey();
        Keyboard backwardKey = player.getBackwardKey();
        Keyboard leftKey = player.getLeftKey();
        Keyboard rightKey = player.getRightKey();

        if (plugin.chatt.get(pName) == null)
            plugin.chatt.put(pName, false);

        if (key == null) return;

        if (key.equals(chatKey)) {
            player.setTitle(ChatColor.valueOf(LocaleType.SPOUT_COLOUR.getValue().toUpperCase()) + MessageUtil.addColour(LocaleType.SPOUT_TYPING.getValue()) + '\n' + Parser.parsePlayerName(pName, player.getWorld().getName()));
            plugin.chatt.put(player.getName(), true);
        }

        if (plugin.chatt.get(player.getName())) {
            if ((key.equals(forwardKey)) ||
                    (key.equals(backwardKey)) ||
                    (key.equals(leftKey)) ||
                    (key.equals(rightKey))) {
                player.setTitle(Parser.parsePlayerName(pName, player.getWorld().getName()));
                plugin.chatt.put(pName, false);
            }
        }
    }
}

