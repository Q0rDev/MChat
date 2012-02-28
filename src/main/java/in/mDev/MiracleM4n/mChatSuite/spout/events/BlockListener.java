package in.mDev.MiracleM4n.mChatSuite.spout.events;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

import org.spout.api.event.Listener;

public class BlockListener implements Listener {
    mChatSuite plugin;

    public BlockListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
    @EventHandler
    public void signChange(SignChangeEvent event) {
        if (event.isCancelled())
            return;

        if (event.getLine(0).equals("[mChat]"))
            if (plugin.getGame().getPlayer(event.getLine(2)) != null)
                if (event.getLine(3) != null)
                    event.setLine(1, plugin.getAPI().addColour("&f" + (plugin.getAPI().ParseMessage(event.getLine(2), event.getBlock().getWorld().getName(),"", event.getLine(3)))));
    }
    */
}
