package com.miraclem4n.mchat.configs.config;

import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
    MCHAT_API_ONLY("mchat.apiOnly"),
    MCHAT_ALTER_EVENTS("mchat.alter.events"),
    MCHAT_CHAT_DISTANCE("mchat.chatDistance"),
    MCHAT_VAR_INDICATOR("mchat.varIndicator"),
    MCHAT_LOCALE_VAR_INDICATOR("mchat.localeVarIndicator"),
    MCHAT_CUS_VAR_INDICATOR("mchat.cusVarIndicator"),
    MCHAT_IP_CENSOR("mchat.IPCensor"),
    MCHAT_CAPS_LOCK_RANGE("mchat.cLockRange"),

    SUPPRESS_USE_JOIN("suppress.useJoin"),
    SUPPRESS_USE_KICK("suppress.useKick"),
    SUPPRESS_USE_QUIT("suppress.useQuit"),
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
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getBoolean(option, false);
    }

    public String getString() {
        String s = YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getString(option, "");

        if (s != null) {
            return MessageUtil.addColour(s);
        }

        return "";
    }

    public Integer getInteger() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getInt(option, 0);
    }

    public Double getDouble() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getDouble(option, 0.0);
    }

    public List<String> getList() {
        List<String> list = YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getStringList(option);

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
