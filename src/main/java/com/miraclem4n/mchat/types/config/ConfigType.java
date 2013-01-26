package com.miraclem4n.mchat.types.config;

import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.ArrayList;

public enum ConfigType {
    FORMAT_CHAT("format.chat"),

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
    INFO_DEFAULT_GROUP("info.defaultGroup"),

    ALIASES_ME("aliases.mchatme");

    private final String option;

    ConfigType(String option) {
        this.option = option;
    }

    private Object getObject() {
        Object value = ConfigUtil.getConfig().get(option);

        if (value instanceof String) {
            String val = (String) value;

            value = MessageUtil.addColour(val);
        }

        return value;
    }

    public Boolean getBoolean() {
        Object object = getObject();

        return object instanceof Boolean ? (Boolean) object : false;
    }

    public String getString() {
        Object object = getObject();

        return object != null ? object.toString() : "";
    }

    public Integer getInteger() {
        Object object = getObject();

        return object instanceof Number ? (Integer) object : 0;
    }

    public Double getDouble() {
        Object object = getObject();

        return object instanceof Number ? (Double) object : 0.0;
    }

    public ArrayList<String> getList() {
        Object object = getObject();
        ArrayList<String> list = new ArrayList<String>();

        if (object instanceof ArrayList) {
            ArrayList<?> aList = (ArrayList<?>) object;

            for (Object obj : aList) {
                list.add((String) obj);
            }
        }

        return list;
    }
}
