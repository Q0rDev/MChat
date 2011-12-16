package in.mDev.MiracleM4n.mChatSuite.GUI;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.HashMap;

public class Main {
    mChatSuite plugin;

    public Main(mChatSuite plugin) {
        this.plugin = plugin;
    }

    HashMap<String, PopupScreen> popupMap = new HashMap<String, PopupScreen>();

    public void closePopup(Player player) {
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        if (sPlayer.getMainScreen().getActivePopup() != null)
            sPlayer.getMainScreen().getActivePopup().close();
    }

    public void openMainPopup(Player player) {
        openPopup(player, "Main");
    }

    public void openPopup(Player player, String name) {
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        closePopup(player);

        PopupScreen popup = new GenericPopup();

        mChatSuite.mPages.attachPage(player, popup, name);

        popupMap.put(player.getName(), popup);

        sPlayer.getMainScreen().attachPopupScreen(popup);
    }
}
