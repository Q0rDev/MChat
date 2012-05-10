package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.util.MessageUtil;

public enum LocaleType {
    PLAYER_DIED("message.player.damaged"),
    PLAYER_DAMAGED("message.player.damaged"),
    YOU_DIED("message.you.died"),
    YOU_DAMAGED("message.you.damaged"),
    SPOUT_TYPING("message.spout.typing"),
    SPOUT_COLOUR("message.spout.colour"),
    PLAYER_AFK("message.player.afk"),
    PLAYER_NOT_AFK("message.player.notAfk"),
    PLAYER_STILL_AFK("message.player.stillAfk"),
    NO_PERMS("message.general.noPerms"),
    FORMAT_SAY("format.say"),
    FORMAT_SPY("format.spy"),
    FORMAT_LOCAL("format.local"),
    FORMAT_SHOUT("format.shout"),
    FORMAT_FORWARD("format.forward"),
    CONFIG_RELOADED("message.config.reloaded"),
    CONFIG_UPDATED("message.config.updated");

    private final String option;

    LocaleType(String option) {
        this.option = option;
    }

    public String getValue() {
        if (LocaleUtil.getConfig().isSet(option))
            return MessageUtil.addColour(LocaleUtil.getConfig().getString(option));

        return "";
    }
}
