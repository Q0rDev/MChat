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

    public void openPopup(Player player) {
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        sPlayer.getMainScreen().attachPopupScreen(createPopup(player));
    }

    public void closePopup(Player player) {
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        sPlayer.getMainScreen().getActivePopup().close();
    }

    PopupScreen createPopup(Player player) {
        PopupScreen popup = new GenericPopup();

        mChatSuite.mLabels.attachLabels(player, popup);
        mChatSuite.mTextFields.attachTextFields(player, popup);
        mChatSuite.mButtons.attachButtons(player, popup);

        attachStaticItems(popup);

        idMap.put(player.getName(), popup.getId());

        return popup;
    }

    PopupScreen attachStaticItems(PopupScreen popup) {
        GenericTexture mainPic = new GenericTexture();

        mainPic.setUrl("http://mdev.in/plugins/mChatSuite/Main.png");
        mainPic.setWidth(230);
        mainPic.setHeight(56);
        mainPic.setY(mainPic.getMaxHeight()-57);

        popup.attachWidget(plugin, mainPic);

        return popup;
    }
}
