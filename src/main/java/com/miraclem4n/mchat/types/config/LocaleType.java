package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.util.MessageUtil;

public enum LocaleType {
    FORMAT_FORWARD("format.forward"),
    FORMAT_LOCAL("format.local"),
    FORMAT_PM_RECEIVED("format.pm.received"),
    FORMAT_PM_SENT("format.pm.sent"),
    FORMAT_SAY("format.say"),
    FORMAT_SHOUT("format.shout"),
    FORMAT_SPY("format.spy"),

    MESSAGE_AFK_AFK("message.afk.afk"),
    MESSAGE_AFK_DEFAULT("message.afk.default"),
    MESSAGE_CONFIG_RELOADED("message.config.reloaded"),
    MESSAGE_CONFIG_UPDATED("message.config.updated"),
    MESSAGE_CONVERSATION_ACCEPTED("message.convo.accepted"),
    MESSAGE_CONVERSATION_CONVERSATION("message.convo.convo"),
    MESSAGE_CONVERSATION_DENIED("message.convo.denied"),
    MESSAGE_CONVERSATION_ENDED("message.convo.ended"),
    MESSAGE_CONVERSATION_HAS_REQUEST("message.convo.hasRequest"),
    MESSAGE_CONVERSATION_INVITED("message.convo.invited"),
    MESSAGE_CONVERSATION_INVITE_SENT("message.convo.inviteSent"),
    MESSAGE_CONVERSATION_LEFT("message.convo.left"),
    MESSAGE_CONVERSATION_NOT_IN("message.convo.notIn"),
    MESSAGE_CONVERSATION_NOT_STARTED("message.convo.notStarted"),
    MESSAGE_CONVERSATION_NO_PENDING("message.convo.noPending"),
    MESSAGE_CONVERSATION_STARTED("message.convo.started"),
    MESSAGE_INFO_ALTERATION("message.info.alteration"),
    MESSAGE_LIST_HEADER("message.list.header"),
    MESSAGE_MUTE_MISC("message.general.mute"),
    MESSAGE_NO_PERMS("message.general.noPerms"),
    MESSAGE_PLAYER_AFK("message.player.afk"),
    MESSAGE_PLAYER_DAMAGED("message.player.damaged"),
    MESSAGE_PLAYER_DIED("message.player.damaged"),
    MESSAGE_PLAYER_NOT_AFK("message.player.notAfk"),
    MESSAGE_PLAYER_STILL_AFK("message.player.stillAfk"),
    MESSAGE_PM_NO_PM("message.pm.noPm"),
    MESSAGE_PM_RECEIVED("message.pm.sent"),
    MESSAGE_PM_SENT("message.pm.sent"),
    MESSAGE_SHOUT_NO_INPUT("message.shout.noInput"),
    MESSAGE_SPOUT_COLOUR("message.spout.colour"),
    MESSAGE_SPOUT_PM("message.spout.pmFrom"),
    MESSAGE_SPOUT_TYPING("message.spout.typing"),
    MESSAGE_YOU_DAMAGED("message.you.damaged"),
    MESSAGE_YOU_DIED("message.you.died"),

    PLAYER_NOT_FOUND("message.player.notOnline"),
    PLAYER_NOT_ONLINE("message.player.notFound");

    private final String option;

    LocaleType(String option) {
        this.option = option;
    }

    public String getValue() {
        if (LocaleUtil.getConfig().isSet(option))
            return MessageUtil.addColour(LocaleUtil.getConfig().getString(option));

        return "Locale Option '" + option + "' not found!";
    }
}
