package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.PopupScreen;

import java.util.HashMap;

public class Buttons {
    mChatSuite plugin;

    public Buttons(mChatSuite plugin) {
        this.plugin = plugin;
    }
    
    HashMap<String, GenericButton> buttonMap = new HashMap<String, GenericButton>();

    PopupScreen attachButtons(Player player, PopupScreen popup) {
        createButtons(player);

        popup.attachWidget(plugin, getCloseButton(player));
        popup.attachWidget(plugin, getNameFormatButton(player));
        popup.attachWidget(plugin, getChatFormatButton(player));

        return popup;
    }

    void createButtons(Player player) {
        createCloseButton(player);
        createNameFormatButton(player);
        createChatFormatButton(player);
    }

    void createCloseButton(Player player) {
        GenericButton label = new GenericButton();

        label.setHeight(20);
        label.setWidth(60);
        label.setY(label.getMaxHeight()-21);
        label.setX(label.getMaxWidth()-61);
        label.setText("Close");
        label.setTooltip("Close");

        buttonMap.put("Close|" + player.getName(), label);
    }

    void createNameFormatButton(Player player) {
        GenericButton label = new GenericButton();

        label.setHeight(20);
        label.setWidth(60);
        label.setY(10);
        label.setX(202);
        label.setText("Submit");

        buttonMap.put("nFormat|" + player.getName(), label);
    }

    void createChatFormatButton(Player player) {
        GenericButton label = new GenericButton();

        label.setHeight(20);
        label.setWidth(60);
        label.setY(38);
        label.setX(202);
        label.setText("Submit");

        buttonMap.put("cFormat|" + player.getName(), label);
    }

    GenericButton getCloseButton(Player player) {
        return buttonMap.get("Close|" + player.getName());

    }

    GenericButton getNameFormatButton(Player player) {
        return buttonMap.get("nFormat|" + player.getName());
    }

    GenericButton getChatFormatButton(Player player) {
        return buttonMap.get("cFormat|" + player.getName());
    }
}
