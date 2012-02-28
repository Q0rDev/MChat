package in.mDev.MiracleM4n.mChatSuite.spout.GUI;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

import java.util.HashMap;

public class Main {
    mChatSuite plugin;

    public Main(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
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
    */
}
