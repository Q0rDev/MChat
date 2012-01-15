package in.mDev.MiracleM4n.mChatSuite.bukkit.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class MBBlockListener extends BlockListener {
    mChatSuite plugin;

    public MBBlockListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;

        if (event.getLine(0).equals("[mChat]"))
            if (plugin.getServer().getPlayer(event.getLine(2)) != null)
                if (event.getLine(3) != null)
                    event.setLine(1, plugin.getAPI().addColour("&f" + (plugin.getAPI().ParseMessage(event.getLine(2), event.getBlock().getWorld().getName(),"", event.getLine(3)))));
    }
}
