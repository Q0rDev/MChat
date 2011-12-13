package in.mDev.MiracleM4n.mChatSuite.eventListeners;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class MBlockListener extends BlockListener {
    mChatSuite plugin;

    public MBlockListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void onSignChange(SignChangeEvent event) {
        if (event.isCancelled()) return;

        if (event.getLine(0).equals("[mChat]"))
            if (plugin.getServer().getPlayer(event.getLine(2)) != null)
                if (event.getLine(3) != null)
                    event.setLine(1, mChatSuite.getAPI().addColour("&f" + (mChatSuite.getAPI().ParseMessage(event.getLine(2), "", event.getLine(3)))));
    }
}
