package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.io.IOException;

public class GUIEvent extends ScreenListener {
    mChatSuite plugin;

    public GUIEvent(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void onButtonClick(ButtonClickEvent event) {
        Player player = event.getPlayer();
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        if (sPlayer.getMainScreen().getActivePopup().getId() != mChatSuite.mGUI.idMap.get(player.getName()))
            return;


        if (event.getButton().equals(mChatSuite.mButtons.buttonMap.get("Close|" + player.getName())))
            mChatSuite.mGUI.closePopup(player);

        else if (event.getButton().equals(mChatSuite.mButtons.buttonMap.get("nFormat|" + player.getName())))
            editOption("format.name",
                    mChatSuite.mTextFields.textFieldMap.get("nFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mButtons.buttonMap.get("cFormat|" + player.getName())))
            editOption("format.chat",
                    mChatSuite.mTextFields.textFieldMap.get("cFormat|" + player.getName()).getText());
    }

    public void editOption(String option, Object value) {
        plugin.mConfig.set(option, value);

        try {
            plugin.mConfig.save(plugin.mConfigF);
        } catch (IOException ignored) {}

        plugin.loadConfigs();
        plugin.setupConfigs();
    }
}
