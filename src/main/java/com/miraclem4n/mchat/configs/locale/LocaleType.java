package com.miraclem4n.mchat.configs.locale;

import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.util.MessageUtil;

public enum LocaleType {
    FORMAT_FORWARD("format.forward"),
    FORMAT_LOCAL("format.local"),
    FORMAT_SPY("format.spy"),
    FORMAT_CHAT("format.chat"),
    FORMAT_DATE("format.date"),
    FORMAT_NAME("format.name"),
    FORMAT_TABBED_LIST("format.tabbedList"),
    FORMAT_LIST_CMD("format.listCmd"),
    FORMAT_ME("format.me"),

    MESSAGE_INFO_ALTERATION("message.info.alteration"),
    MESSAGE_NO_PERMS("message.general.noPerms"),
    MESSAGE_EVENT_JOIN("message.event.join"),
    MESSAGE_EVENT_LEAVE("message.event.leave"),
    MESSAGE_EVENT_KICK("message.event.kick"),
    MESSAGE_HEROES_TRUE("message.heroes.isMaster"),
    MESSAGE_HEROES_FALSE("message.heroes.notMaster");

    private final String option;

    LocaleType(String option) {
        this.option = option;
    }

    public String getVal() {
        if (YmlManager.getYml(YmlType.LOCALE_YML).getConfig().isSet(option)) {
            return MessageUtil.addColour(YmlManager.getYml(YmlType.LOCALE_YML).getConfig().getString(option));
        }

        return "Locale Option '" + option + "' not found!";
    }

    public String getRaw() {
        if (YmlManager.getYml(YmlType.LOCALE_YML).getConfig().isSet(option)) {
            return YmlManager.getYml(YmlType.LOCALE_YML).getConfig().getString(option);
        }

        return "Locale Option '" + option + "' not found!";
    }
}
