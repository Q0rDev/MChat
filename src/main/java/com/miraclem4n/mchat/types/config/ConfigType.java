package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
    MCHAT_API_ONLY("mchat.apiOnly"),
    MCHAT_ALTER_EVENTS("mchat.alter.events"),
    MCHAT_ALTER_DEATH("mchat.alter.death"),
    MCHAT_CHAT_DISTANCE("mchat.chatDistance"),
    MCHAT_VAR_INDICATOR("mchat.varIndicator"),
    MCHAT_LOCALE_VAR_INDICATOR("mchat.localeVarIndicator"),
    MCHAT_CUS_VAR_INDICATOR("mchat.cusVarIndicator"),
    MCHAT_IP_CENSOR("mchat.IPCensor"),
    MCHAT_CAPS_LOCK_RANGE("mchat.cLockRange"),

    SUPPRESS_USE_DEATH("suppress.useDeath"),
    SUPPRESS_USE_JOIN("suppress.useJoin"),
    SUPPRESS_USE_KICK("suppress.useKick"),
    SUPPRESS_USE_QUIT("suppress.useQuit"),
    SUPPRESS_MAX_DEATH("suppress.maxDeath"),
    SUPPRESS_MAX_JOIN("suppress.maxJoin"),
    SUPPRESS_MAX_KICK("suppress.maxKick"),
    SUPPRESS_MAX_QUIT("suppress.maxQuit"),

    INFO_USE_NEW_INFO("info.useNewInfo"),
    INFO_USE_LEVELED_NODES("info.useLeveledNodes"),
    INFO_USE_OLD_NODES("info.useOldNodes"),
    INFO_ADD_NEW_PLAYERS("info.addNewPlayers"),
    INFO_DEFAULT_GROUP("info.defaultGroup");

    private final String option;

    ConfigType(String option) {
        this.option = option;
    }

    public Boolean getBoolean() {
        Boolean b = ConfigUtil.getConfig().getBoolean(option);

        if (b != null) {
            return b;
        }

        return false;
    }

    public String getString() {
        String s = ConfigUtil.getConfig().getString(option);

        if (s != null) {
            return MessageUtil.addColour(s);
        }

        return "";
    }

    public Integer getInteger() {
        Integer i = ConfigUtil.getConfig().getInt(option);

        if (i != null) {
            return i;
        }

        return 0;
    }

    public Double getDouble() {
        Double d = ConfigUtil.getConfig().getDouble(option);

        if (d != null) {
            return d;
        }

        return 0.0;
    }

    public List<String> getList() {
        List<String> list = ConfigUtil.getConfig().getStringList(option);

        if (list != null) {
            ArrayList<String> l = new ArrayList<String>();

            for (String s : list) {
                l.add(MessageUtil.addColour(s));
            }

            return l;
        }

        return list;
    }
}
