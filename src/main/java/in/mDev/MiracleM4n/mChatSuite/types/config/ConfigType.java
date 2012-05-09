package in.mDev.MiracleM4n.mChatSuite.types.config;

import in.mDev.MiracleM4n.mChatSuite.configs.ConfigUtil;
import in.mDev.MiracleM4n.mChatSuite.configs.objects.ConfigObject;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

public enum ConfigType {
    FORMAT_DATE("format.date"),
    FORMAT_CHAT("format.chat"),
    FORMAT_NAME("format.name"),
    FORMAT_EVENT("format.event"),
    FORMAT_TABBED_LIST("format.tabbedList"),
    FORMAT_LIST_CMD("format.listCmd"),
    FORMAT_ME("format.me"),

    MESSAGE_JOIN("message.join"),
    MESSAGE_LEAVE("message.leave"),
    MESSAGE_KICK("message.kick"),
    MESSAGE_DEATH_IN_FIRE("message.deathInFire"),
    MESSAGE_DEATH_ON_FIRE("message.deathOnFire"),
    MESSAGE_DEATH_LAVA("message.deathLava"),
    MESSAGE_DEATH_IN_WALL("message.deathInWall"),
    MESSAGE_DEATH_DROWN("message.deathDrown"),
    MESSAGE_DEATH_STARVE("message.deathStarve"),
    MESSAGE_DEATH_CACTUS("message.deathCactus"),
    MESSAGE_DEATH_FALL("message.deathFall"),
    MESSAGE_DEATH_OUT_OF_WORLD("message.deathOutOfWorld"),
    MESSAGE_DEATH_GENERIC("message.deathGeneric"),
    MESSAGE_DEATH_EXPLOSION("message.deathExplosion"),
    MESSAGE_DEATH_MAGIC("message.deathMagic"),
    MESSAGE_DEATH_ENTITY("message.deathEntity"),
    MESSAGE_DEATH_MOB_FORMAT("message.deathMobFormat"),
    MESSAGE_DEATH_ARROW("message.deathArrow"),
    MESSAGE_DEATH_FIREBALL("message.deathFireball"),
    MESSAGE_DEATH_THROWN("message.deathThrown"),
    MESSAGE_HEROES_TRUE("message.deathMasterT"),
    MESSAGE_HEROES_FALSE("message.deathMasterF"),

    MCHAT_API_ONLY("mchat.apiOnly"),
    MCHAT_ALTER_EVENTS("mchat.alter.events"),
    MCHAT_ALTER_DEATH("mchat.alter.death"),
    MCHAT_CHAT_DISTANCE("mchat.chatDistance"),
    MCHAT_VAR_INDICATOR("mchat.varIndicator"),
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

    ConfigType(String option) {
        this.option = option;
    }

    public ConfigObject getObject() {
        Object value = ConfigUtil.getConfig().get(option);

        if (value instanceof String) {
            String val = (String) value;

            value = MessageUtil.addColour(val);
        }

        return new ConfigObject(value);
    }

    public String getValue() {
        return option;
    }
}
