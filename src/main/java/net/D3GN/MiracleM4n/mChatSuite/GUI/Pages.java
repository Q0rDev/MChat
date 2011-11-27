package net.D3GN.MiracleM4n.mChatSuite.GUI;

import net.D3GN.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.entity.Player;

import org.getspout.spoutapi.gui.*;

import java.util.HashMap;

public class Pages {
    mChatSuite plugin;

    public Pages(mChatSuite plugin) {
        this.plugin = plugin;
    }

    HashMap<String, GenericLabel> labelMap = new HashMap<String, GenericLabel>();
    HashMap<String, GenericTextField> textFieldMap = new HashMap<String, GenericTextField>();
    HashMap<String, GenericButton> buttonMap = new HashMap<String, GenericButton>();

    void attachPage(Player player, PopupScreen popup, String name) {
        createCloseButton(player);

        if (!name.equals("Main"))
            createBackButton(player);

        if (name.equals("Main"))
            MainPage(player, popup);

        else if (name.equals("OptionsPage"))
            OptionsPage(player, popup);

        else if (name.equals("mChatEPage"))
            mChatEPage(player, popup);

        else if (name.equals("pmChatPage"))
            pmChatPage(player, popup);

        else if (name.equals("FormatPage"))
            FormatPage(player, popup);

        else if (name.equals("MessagePage"))
            MessagePage(player, popup);

        else if (name.equals("MessageEventPage"))
            MessageEventPage(player, popup);

        else if (name.equals("MessageDeathPage"))
            MessageDeathPage(player, popup);

        else if (name.equals("MessageOtherPage"))
            MessageOtherPage(player, popup);

        else if (name.equals("InfoPage"))
            InfoPage(player, popup);

        else if (name.equals("EditOInfoPage"))
            EditOInfoPage(player, popup);

        else if (name.equals("EditLInfoPage"))
            EditLInfoPage(player, popup);

        else if (name.equals("EditInfoPage"))
            EditInfoPage(player, popup);

        else if (name.equals("EditPlayerPage"))
            EditPlayerPage(player, popup);

        else if (name.equals("EditGroupPage"))
            EditGroupPage(player, popup);

        if (!name.equals("Main"))
            popup.attachWidget(plugin, getButton(player, "Back"));

        popup.attachWidget(plugin, getButton(player, "Close"));
    }

    void MainPage(Player player, PopupScreen popup) {
        createGenericButton(player, "GOptions", "General Options", 12, 84, 200, 40);
        createGenericButton(player, "FOptions", "Format Options", 12, 126, 200, 40);
        createGenericButton(player, "MOptions", "Message Options", 216, 84, 200, 40);
        createGenericButton(player, "IOptions", "Info Options", 216, 126, 200, 40);

        popup.attachWidget(plugin, getButton(player, "GOptions"));
        popup.attachWidget(plugin, getButton(player, "FOptions"));
        popup.attachWidget(plugin, getButton(player, "MOptions"));
        popup.attachWidget(plugin, getButton(player, "IOptions"));
    }

    void OptionsPage(Player player, PopupScreen popup) {

    }

    void mChatEPage(Player player, PopupScreen popup) {

    }

    void pmChatPage(Player player, PopupScreen popup) {

    }

    void FormatPage(Player player, PopupScreen popup) {
        createGenericLabel(player, "cFormat", "Chat Format:", 2, 84, 200, 8);
        createGenericLabel(player, "nFormat", "Name Format:", 2, 114, 200, 8);
        createGenericLabel(player, "dFormat", "Date Format:", 2, 142, 200, 8);
        createGenericLabel(player, "eFormat", "Event Format:", 2, 170, 200, 8);
        createGenericLabel(player, "mFormat", "Me Format:", 216, 84, 200, 8);
        createGenericLabel(player, "tFormat", "Tabbed List Format:", 216, 114, 200, 8);
        createGenericLabel(player, "lFormat", "List Command Format:", 216, 142, 200, 8);

        createGenericTextField(player, "cFormat", plugin.mConfig.getString("format.chat"), "Chat Format", 2, 96, 150, 16);
        createGenericTextField(player, "nFormat", plugin.mConfig.getString("format.name"), "Name Format", 2, 124, 150, 16);
        createGenericTextField(player, "dFormat", plugin.mConfig.getString("format.date"), "Date Format", 2, 152, 150, 16);
        createGenericTextField(player, "eFormat", plugin.mConfig.getString("format.event"), "Event Format", 2, 180, 150, 16);
        createGenericTextField(player, "mFormat", plugin.mConfig.getString("format.me"), "Me Format", 216, 96, 150, 16);
        createGenericTextField(player, "tFormat", plugin.mConfig.getString("format.tabbedList"), "Tabbed list Format", 216, 124, 150, 16);
        createGenericTextField(player, "lFormat", plugin.mConfig.getString("format.listCmd"), "List Command Format", 216, 152, 150, 16);

        createGenericSubmitButton(player, "nFormat", 154, 94);
        createGenericSubmitButton(player, "cFormat", 154, 122);
        createGenericSubmitButton(player, "dFormat", 154, 150);
        createGenericSubmitButton(player, "eFormat", 154, 180);
        createGenericSubmitButton(player, "mFormat", 368, 94);
        createGenericSubmitButton(player, "tFormat", 368, 122);
        createGenericSubmitButton(player, "lFormat", 368, 150);

        popup.attachWidget(plugin, getLabel(player, "cFormat"));
        popup.attachWidget(plugin, getLabel(player, "nFormat"));
        popup.attachWidget(plugin, getLabel(player, "dFormat"));
        popup.attachWidget(plugin, getLabel(player, "eFormat"));
        popup.attachWidget(plugin, getLabel(player, "mFormat"));
        popup.attachWidget(plugin, getLabel(player, "tFormat"));
        popup.attachWidget(plugin, getLabel(player, "lFormat"));

        popup.attachWidget(plugin, getTextField(player, "cFormat"));
        popup.attachWidget(plugin, getTextField(player, "nFormat"));
        popup.attachWidget(plugin, getTextField(player, "dFormat"));
        popup.attachWidget(plugin, getTextField(player, "eFormat"));
        popup.attachWidget(plugin, getTextField(player, "mFormat"));
        popup.attachWidget(plugin, getTextField(player, "tFormat"));
        popup.attachWidget(plugin, getTextField(player, "lFormat"));

        popup.attachWidget(plugin, getButton(player, "cFormat"));
        popup.attachWidget(plugin, getButton(player, "nFormat"));
        popup.attachWidget(plugin, getButton(player, "dFormat"));
        popup.attachWidget(plugin, getButton(player, "eFormat"));
        popup.attachWidget(plugin, getButton(player, "mFormat"));
        popup.attachWidget(plugin, getButton(player, "tFormat"));
        popup.attachWidget(plugin, getButton(player, "lFormat"));
    }

    void MessagePage(Player player, PopupScreen popup) {

    }

    void MessageEventPage(Player player, PopupScreen popup) {

    }

    void MessageDeathPage(Player player, PopupScreen popup) {

    }

    void MessageOtherPage(Player player, PopupScreen popup) {

    }

    void InfoPage(Player player, PopupScreen popup) {

    }

    void EditOInfoPage(Player player, PopupScreen popup) {

    }

    void EditLInfoPage(Player player, PopupScreen popup) {

    }

    void EditInfoPage(Player player, PopupScreen popup) {

    }

    void EditPlayerPage(Player player, PopupScreen popup) {

    }

    void EditGroupPage(Player player, PopupScreen popup) {

    }

    void createCloseButton(Player player) {
        GenericButton button = new GenericButton();

        button.setHeight(20);
        button.setWidth(60);
        button.setY(button.getMaxHeight()-22);
        button.setX(button.getMaxWidth()-62);
        button.setText("Close");
        button.setTooltip("Close");

        buttonMap.put("Close|" + player.getName(), button);
    }

    void createBackButton(Player player) {
        GenericButton button = new GenericButton();

        button.setHeight(20);
        button.setWidth(60);
        button.setY(button.getMaxHeight()-22);
        button.setX(button.getMinWidth()+2);
        button.setText("Back");
        button.setTooltip("Back");

        buttonMap.put("Back|" + player.getName(), button);
    }

    void createGenericButton(Player player, String name, String text, int atX, int atY, int SizeX, int SizeY) {
        GenericButton button = new GenericButton();

        button.setHeight(SizeY);
        button.setWidth(SizeX);
        button.setX(atX);
        button.setY(atY);
        button.setText(text);
        button.setTooltip(text);

        buttonMap.put(name + "|" + player.getName(), button);
    }

    void createGenericSubmitButton(Player player, String name, int atX, int atY) {
        GenericButton button = new GenericButton();

        button.setHeight(20);
        button.setWidth(60);
        button.setX(atX);
        button.setY(atY);
        button.setText("Submit");
        button.setTooltip("Submit");

        buttonMap.put(name + "|" + player.getName(), button);
    }

    void createGenericTextField(Player player, String name, String text, String toolTip, int atX, int atY, int SizeX, int SizeY) {
        GenericTextField field = new GenericTextField();

        field.setHeight(SizeY);
        field.setWidth(SizeX);
        field.setX(atX);
        field.setY(atY);
        field.setText(text);
        field.setTooltip(toolTip);
        field.setMaximumCharacters(100);

        textFieldMap.put(name + "|" + player.getName(), field);
    }

    void createGenericLabel(Player player, String name, String text, int atX, int atY, int SizeX, int SizeY) {
        GenericLabel label = new GenericLabel();

        label.setHeight(SizeY);
        label.setWidth(SizeX);
        label.setX(atX);
        label.setY(atY);
        label.setText(text);
        label.setTextColor(new Color(1.0F, 0, 0, 1.0F));

        labelMap.put(name + "|" + player.getName(), label);
    }

    GenericButton getButton(Player player, String name) {
        return buttonMap.get(name + "|" + player.getName());
    }

    GenericTextField getTextField(Player player, String name) {
        return textFieldMap.get(name + "|" + player.getName());
    }

    GenericLabel getLabel(Player player, String name) {
        return labelMap.get(name + "|" + player.getName());
    }
}
