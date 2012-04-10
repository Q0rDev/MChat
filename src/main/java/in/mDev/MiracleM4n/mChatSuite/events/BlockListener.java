package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener implements Listener {
    mChatSuite plugin;

    public BlockListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler
    public void signChange(SignChangeEvent event) {
        if (event.isCancelled())
            return;

        if (event.getLine(0).equals("[mChat]"))
            if (plugin.getServer().getPlayer(event.getLine(2)) != null)
                if (event.getLine(3) != null)
                    event.setLine(1, Messanger.addColour("&f" + (plugin.getParser().parseMessage(event.getLine(2), event.getBlock().getWorld().getName(), "", event.getLine(3)))));
    }
}
