package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.*;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.HashMap;
import java.util.UUID;

public class Main {
    mChatSuite plugin;

    public Main(mChatSuite plugin) {
        this.plugin = plugin;
    }
    HashMap<String, UUID> idMap = new HashMap<String, UUID>();
    HashMap<String, String> popupMap = new HashMap<String, String>();

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

        attachStaticItems(popup);

        idMap.put(player.getName(), popup.getId());

       sPlayer.getMainScreen().attachPopupScreen(popup);
    }

    PopupScreen attachStaticItems(PopupScreen popup) {
        GenericTexture mainPic = new GenericTexture();

        mainPic.setUrl("http://mdev.in/plugins/mChatSuite/Main.png");
        mainPic.setWidth(230);
        mainPic.setHeight(56);
        mainPic.setY(2);
        mainPic.setX(2);

        popup.attachWidget(plugin, mainPic);

        return popup;
    }
}
