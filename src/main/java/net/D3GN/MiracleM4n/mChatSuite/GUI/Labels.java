package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.PopupScreen;

import java.util.HashMap;

public class Labels {
    mChatSuite plugin;

    public Labels(mChatSuite plugin) {
        this.plugin = plugin;
    }

    HashMap<String, GenericLabel> labelMap = new HashMap<String, GenericLabel>();

    PopupScreen attachLabels(Player player, PopupScreen popup) {
        createLabels(player);

        popup.attachWidget(plugin, getNameFormatLabel(player));
        popup.attachWidget(plugin, getChatFormatLabel(player));

        return popup;
    }

    void createLabels(Player player) {
        createNameFormatLabel(player);
        createChatFormatLabel(player);
    }

    void createNameFormatLabel(Player player) {
        GenericLabel label = new GenericLabel();

        label.setText("Name Format:");
        label.setTextColor(new Color(1.0F, 0, 0, 1.0F));
        label.setWidth(200);
        label.setHeight(8);
        label.setY(2);
        label.setX(1);

        labelMap.put("nFormat|" + player.getName(), label);
    }

    void createChatFormatLabel(Player player) {
        GenericLabel label = new GenericLabel();

        label.setText("Chat Format:");
        label.setTextColor(new Color(1.0F, 0, 0, 1.0F));
        label.setWidth(200);
        label.setHeight(8);
        label.setY(30);
        label.setX(1);

        labelMap.put("cFormat|" + player.getName(), label);
    }

    GenericLabel getNameFormatLabel(Player player) {
        return labelMap.get("nFormat|" + player.getName());
    }

    GenericLabel getChatFormatLabel(Player player) {
        return labelMap.get("cFormat|" + player.getName());
    }
}
