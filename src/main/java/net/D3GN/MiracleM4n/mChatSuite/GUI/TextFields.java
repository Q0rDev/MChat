package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.*;

import java.util.HashMap;

public class TextFields {
    mChatSuite plugin;

    public TextFields(mChatSuite plugin) {
        this.plugin = plugin;
    }

    HashMap<String, GenericTextField> textFieldMap = new HashMap<String, GenericTextField>();

    PopupScreen attachTextFields(Player player, PopupScreen popup) {
        createTextFields(player);

        popup.attachWidget(plugin, getNameFormatTextField(player));
        popup.attachWidget(plugin, getChatFormatTextField(player));

        return popup;
    }

    void createTextFields(Player player) {
        createNameFormatTextField(player);
        createChatFormatTextField(player);
    }

    void createNameFormatTextField(Player player) {
        GenericTextField label = new GenericTextField();

        label.setText(plugin.mConfig.getString("format.name"));
        label.setWidth(200);
        label.setHeight(16);
        label.setY(12);
        label.setX(1);
        label.setTooltip("Name Format");

        textFieldMap.put("nFormat|" + player.getName(), label);
    }

    void createChatFormatTextField(Player player) {
        GenericTextField label = new GenericTextField();

        label.setText(plugin.mConfig.getString("format.chat"));
        label.setWidth(200);
        label.setHeight(16);
        label.setY(40);
        label.setX(1);
        label.setTooltip("Chat Format");

        textFieldMap.put("cFormat|" + player.getName(), label);
    }

    GenericTextField getNameFormatTextField(Player player) {
        return textFieldMap.get("nFormat|" + player.getName());
    }

    GenericTextField getChatFormatTextField(Player player) {
        return textFieldMap.get("cFormat|" + player.getName());
    }
}
