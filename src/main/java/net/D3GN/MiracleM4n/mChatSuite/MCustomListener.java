package net.D3GN.MiracleM4n.mChatSuite;

import org.bukkit.ChatColor;

import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MCustomListener extends InputListener {
    mChatSuite plugin;

    public MCustomListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void onKeyPressedEvent(KeyPressedEvent event) {
        SpoutPlayer player = event.getPlayer();
        Keyboard key = event.getKey();
        Keyboard chatKey = player.getChatKey();
        Keyboard forwardKey = player.getForwardKey();
        Keyboard backwardKey = player.getBackwardKey();
        Keyboard leftKey = player.getLeftKey();
        Keyboard rightKey = player.getRightKey();

        if (plugin.chatt.get(player.getName()) == null)
            plugin.chatt.put(player.getName(), false);

        if (key == null) return;

        if (key.equals(chatKey)) {
            player.setTitle(ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + plugin.mAPI.addColour(plugin.lListener.typingMessage) + '\n' + plugin.mAPI.ParsePlayerName(player));
            plugin.chatt.put(player.getName(), true);
        }

        if (plugin.chatt.get(player.getName())) {
            if ((key.equals(forwardKey)) ||
                    (key.equals(backwardKey)) ||
                            (key.equals(leftKey)) ||
                                    (key.equals(rightKey))) {
                player.setTitle(plugin.mAPI.ParsePlayerName(player));
                plugin.chatt.put(player.getName(), false);
            }
        }
    }
}

