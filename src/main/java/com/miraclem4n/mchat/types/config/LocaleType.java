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
    FORMAT_DATE("format.date"),
    FORMAT_NAME("format.name"),
    FORMAT_TABBED_LIST("format.tabbedList"),
    FORMAT_LIST_CMD("format.listCmd"),
    FORMAT_ME("format.me"),

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
    MESSAGE_EVENT_JOIN("message.event.join"),
    MESSAGE_EVENT_LEAVE("message.event.leave"),
    MESSAGE_EVENT_KICK("message.event.kick"),
    MESSAGE_DEATH_IN_FIRE("message.death.inFire"),
    MESSAGE_DEATH_ON_FIRE("message.death.onFire"),
    MESSAGE_DEATH_LAVA("message.death.lava"),
    MESSAGE_DEATH_IN_WALL("message.death.inWall"),
    MESSAGE_DEATH_DROWN("message.death.drown"),
    MESSAGE_DEATH_STARVE("message.death.starve"),
    MESSAGE_DEATH_CACTUS("message.death.cactus"),
    MESSAGE_DEATH_FALL("message.death.fall"),
    MESSAGE_DEATH_OUT_OF_WORLD("message.death.outOfWorld"),
    MESSAGE_DEATH_GENERIC("message.death.generic"),
    MESSAGE_DEATH_EXPLOSION("message.death.explosion"),
    MESSAGE_DEATH_MAGIC("message.death.magic"),
    MESSAGE_DEATH_ENTITY("message.death.entity"),
    MESSAGE_DEATH_ARROW("message.death.arrow"),
    MESSAGE_DEATH_FIREBALL("message.death.fireball"),
    MESSAGE_DEATH_THROWN("message.death.thrown"),
    MESSAGE_HEROES_TRUE("message.heroes.isMaster"),
    MESSAGE_HEROES_FALSE("message.heroes.notMaster"),

    PLAYER_NOT_FOUND("message.player.notOnline"),
    PLAYER_NOT_ONLINE("message.player.notFound");

    private final String option;

    LocaleType(String option) {
        this.option = option;
    }

    public String getVal() {
        if (LocaleUtil.getConfig().isSet(option))
            return MessageUtil.addColour(LocaleUtil.getConfig().getString(option));

        return "Locale Option '" + option + "' not found!";
    }

    public String getRaw() {
        if (LocaleUtil.getConfig().isSet(option))
            return LocaleUtil.getConfig().getString(option);

        return "Locale Option '" + option + "' not found!";
    }
}
