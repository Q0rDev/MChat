package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.io.IOException;
import java.util.HashMap;

public class GUIEvent extends ScreenListener {
    mChatSuite plugin;

    public GUIEvent(mChatSuite plugin) {
        this.plugin = plugin;
    }

    HashMap<String, String> lastPage = new HashMap<String, String>();

    public void onButtonClick(ButtonClickEvent event) {
        Player player = event.getPlayer();
        SpoutPlayer sPlayer = (SpoutPlayer) player;

        if (sPlayer.getMainScreen().getActivePopup().getId() != mChatSuite.mGUI.idMap.get(player.getName()))
            return;

        if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("Back|" + player.getName())))
            mChatSuite.mGUI.openMainPopup(player);

        if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("GOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "OptionsPage");

        if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("GOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "OptionsPage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("mEOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "mChatEPage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("pmOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "pmChatPage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("FOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "FormatPage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("MOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "MesagePage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("IOptions|" + player.getName())))
            mChatSuite.mGUI.openPopup(player, "InfoPage");

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("Close|" + player.getName())))
            mChatSuite.mGUI.closePopup(player);

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("cFormat|" + player.getName())))
            editOption("format.chat",
                    mChatSuite.mPages.textFieldMap.get("cFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("nFormat|" + player.getName())))
            editOption("format.name",
                    mChatSuite.mPages.textFieldMap.get("nFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("dFormat|" + player.getName())))
            editOption("format.date",
                    mChatSuite.mPages.textFieldMap.get("dFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("eFormat|" + player.getName())))
            editOption("format.event",
                    mChatSuite.mPages.textFieldMap.get("eFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("tFormat|" + player.getName())))
            editOption("format.tabbedList",
                    mChatSuite.mPages.textFieldMap.get("tFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("lFormat|" + player.getName())))
            editOption("format.listCmd",
                    mChatSuite.mPages.textFieldMap.get("lFormat|" + player.getName()).getText());

        else if (event.getButton().equals(mChatSuite.mPages.buttonMap.get("mFormat|" + player.getName())))
            editOption("format.me",
                    mChatSuite.mPages.textFieldMap.get("mFormat|" + player.getName()).getText());
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
