package in.mDev.MiracleM4n.mChatSuite.spout.events;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

import org.spout.api.event.Listener;

public class CustomListener implements Listener {
    mChatSuite plugin;

    public CustomListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
    @EventHandler
    public void onKeyPressedEvent(PlayerKeyEvent event) {
        Player player = event.getPlayer();
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
            player.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + plugin.getAPI().addColour(plugin.getLocale().getOption("typingMessage")) + '\n' + plugin.getAPI().ParsePlayerName(player.getName(), player.getEntity().getWorld().getName()));
            plugin.chatt.put(player.getName(), true);
        }

        if (plugin.chatt.get(player.getName())) {
            if ((key.equals(forwardKey)) ||
                    (key.equals(backwardKey)) ||
                    (key.equals(leftKey)) ||
                    (key.equals(rightKey))) {
                player.setTitle(plugin.getAPI().ParsePlayerName(player.getName(), player.getEntity().getWorld().getName()));
                plugin.chatt.put(player.getName(), false);
            }
        }
    }
    */
}

