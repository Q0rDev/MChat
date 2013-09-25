package ca.q0r.mchat.configs.config;

import ca.q0r.mchat.configs.YmlManager;
import ca.q0r.mchat.configs.YmlType;
import ca.q0r.mchat.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for Different Config Values.
 */
public enum ConfigType {
    /** Runs MChat in API Only mode. */ MCHAT_API_ONLY("mchat.apiOnly"),
    /** Allows MChat to do an update check and alert Admins of updates. */ MCHAT_UPDATE_CHECK("mchat.updateCheck"),
    /** Whether or not to Alter Events. */ MCHAT_ALTER_EVENTS("mchat.alter.events"),
    /** Distance Based Chat Value. Negative or 0 disables. */ MCHAT_CHAT_DISTANCE("mchat.chatDistance"),
    /** Variable Indicator. Used in Locale.yml for Variables hardcoded into MChat or Bukkit. */ MCHAT_VAR_INDICATOR("mchat.varIndicator"),
    /** Locale Variable Indicator. Used in locale.yml for Variables used by MChat. */ MCHAT_LOCALE_VAR_INDICATOR("mchat.localeVarIndicator"),
    /** Custom Variable Indicator. Used in locale.yml for Variables added by other Pluginss. */ MCHAT_CUS_VAR_INDICATOR("mchat.cusVarIndicator"),
    /** Whether or not to censor IP's in Chat. */ MCHAT_IP_CENSOR("mchat.IPCensor"),
    /** Range for Auto-Lowercasing. Negative or 0 disables. */ MCHAT_CAPS_LOCK_RANGE("mchat.cLockRange"),

    /** Whether or not to suppress Join Event Message. */ SUPPRESS_USE_JOIN("suppress.useJoin"),
    /** Whether or not to suppress Kick Event Message. */ SUPPRESS_USE_KICK("suppress.useKick"),
    /** Whether or not to suppress Quit Event Message. */ SUPPRESS_USE_QUIT("suppress.useQuit"),
    /** Disables Join Event Message if Value is lower the the amount of Players currently online. */ SUPPRESS_MAX_JOIN("suppress.maxJoin"),
    /** Disables Kick Event Message if Value is lower the the amount of Players currently online.  */ SUPPRESS_MAX_KICK("suppress.maxKick"),
    /** Disables Quit Event Message if Value is lower the the amount of Players currently online.  */ SUPPRESS_MAX_QUIT("suppress.maxQuit"),

    /** Whether or not to use "New Info" Resolving. */ INFO_USE_NEW_INFO("info.useNewInfo"),
    /** Whether or not to use "Leveled Nodes" Resolving. */ INFO_USE_LEVELED_NODES("info.useLeveledNodes"),
    /** Whether or not to use "Old Nodes" Resolving. */ INFO_USE_OLD_NODES("info.useOldNodes"),
    /** Whether or not to Add New Players into <b>info.yml</b> when they join. */ INFO_ADD_NEW_PLAYERS("info.addNewPlayers"),
    /** Default Group to put Players into. */ INFO_DEFAULT_GROUP("info.defaultGroup");

    private final String option;

    private ConfigType(String option) {
        this.option = option;
    }

    /**
     * Boolean Value.
     * @return Boolean Value of Config Key.
     */
    public Boolean getBoolean() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getBoolean(option);
    }

    /**
     * String Value.
     * @return String Value of Config Key.
     */
    public String getString() {
        return MessageUtil.addColour(YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getString(option));
    }

    /**
     * Integer Value.
     * @return Integer Value of Config Key.
     */
    public Integer getInteger() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getInt(option);
    }

    /**
     * Double Value.
     * @return Double Value of Config Key.
     */
    public Double getDouble() {
        return YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getDouble(option);
    }

    /**
     * List Value.
     * @return List Value of Config Key.
     */
    public List<String> getList() {
        List<String> list = YmlManager.getYml(YmlType.CONFIG_YML).getConfig().getStringList(option);
        ArrayList<String> l = new ArrayList<String>();

        for (String string : list) {
            l.add(MessageUtil.addColour(string));
        }

        return l;
    }
}
