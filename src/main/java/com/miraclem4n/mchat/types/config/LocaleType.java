package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.util.MessageUtil;

public enum LocaleType {
    FORMAT_FORWARD("format.forward"),
    FORMAT_LOCAL("format.local"),
    FORMAT_SPY("format.spy"),
    FORMAT_DATE("format.date"),
    FORMAT_NAME("format.name"),
    FORMAT_TABBED_LIST("format.tabbedList"),
    FORMAT_LIST_CMD("format.listCmd"),
    FORMAT_ME("format.me"),

    MESSAGE_CONFIG_RELOADED("message.config.reloaded"),
    MESSAGE_CONFIG_UPDATED("message.config.updated"),
    MESSAGE_INFO_ALTERATION("message.info.alteration"),
    MESSAGE_NO_PERMS("message.general.noPerms"),
    MESSAGE_PLAYER_DAMAGED("message.player.damaged"),
    MESSAGE_PLAYER_DIED("message.player.damaged"),
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
