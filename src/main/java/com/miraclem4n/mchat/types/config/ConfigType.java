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
    MCHAT_SPOUT("mchat.spout"),
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

    MCHATE_ENABLE("mchate.enable"),
    MCHATE_HC_AFK("mchate.eHQAFK"),
    MCHATE_USE_GROUPED_LIST("mchate.useGroupedList"),
    MCHATE_LIST_VAR("mchate.listVar"),
    MCHATE_COLLAPSED_LIST_VAR("mchate.collapsedListVars"),
    MCHATE_AFK_TIMER("mchate.AFKTimer"),
    MCHATE_AFK_KICK_TIMER("mchate.AFKKickTimer"),
    MCHATE_USE_AFK_LIST("mchate.useAFKList"),

    PMCHAT_ENABLE("pmchat.enable"),
    PMCHAT_SPOUT("pmchat.spoutPM"),

    ALIASES_ME("aliases.mchatme"),
    ALIASES_WHO("aliases.mchatwho"),
    ALIASES_LIST("aliases.mchatlist"),
    ALIASES_SAY("aliases.mchatsay"),
    ALIASES_AFK("aliases.mchatafk"),
    ALIASES_AFK_OTHER("aliases.mchatafkother"),
    ALIASES_PM("aliases.pmchat"),
    ALIASES_PM_REPLY("aliases.pmchatreply"),
    ALIASES_PM_INVITE("aliases.pmchatinvite"),
    ALIASES_PM_ACCEPT("aliases.pmchataccept"),
    ALIASES_PM_DENY("aliases.pmchatdeny"),
    ALIASES_PM_LEAVE("aliases.pmchatleave"),
    ALIASES_SHOUT("aliases.mchatshout"),
    ALIASES_MUTE("aliases.mchatmute"),
    ALIASES_CHANNEL("aliases.mchannel");

    private final String option;
    private final Object object;

    ConfigType(String option) {
        this.option = option;
        this.object = getObject();
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
        return object instanceof Boolean ? (Boolean) object : false;
    }

    public String getString() {
        return object != null ? object.toString() : null;
    }

    public Integer getInteger() {
        return object instanceof Number ? (Integer) object : 0;
    }

    public Double getDouble() {
        return object instanceof Number ? (Double) object : 0;
    }

    public ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<String>();

        if (object instanceof ArrayList) {
            ArrayList aList = (ArrayList) object;

            for (Object obj : aList)
                list.add((String) obj);

        }

        return list;
    }
}
