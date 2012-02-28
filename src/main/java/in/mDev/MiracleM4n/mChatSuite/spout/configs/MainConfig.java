package in.mDev.MiracleM4n.mChatSuite.spout.configs;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;
import org.spout.api.util.config.Configuration;

import java.io.File;

public class MainConfig {
    mChatSuite plugin;
    Boolean hasChanged = false;

    public MainConfig(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.mConfig = new Configuration(plugin.mConfigF);
        plugin.mConfig.load();
    }

    void save() {
        plugin.mConfig.save();
        plugin.mConfig.load();
    }

    public void load() {
        Configuration config = plugin.mConfig;

        checkConfig();

        plugin.dateFormat = config.getString("format.date", plugin.dateFormat);
        plugin.chatFormat = config.getString("format.chat", plugin.chatFormat);
        plugin.nameFormat = config.getString("format.name", plugin.nameFormat);
        plugin.eventFormat = config.getString("format.event", plugin.eventFormat);
        plugin.tabbedListFormat = config.getString("format.tabbedList", plugin.tabbedListFormat);
        plugin.listCmdFormat = config.getString("format.listCmd", plugin.listCmdFormat);
        plugin.meFormat = config.getString("format.me", plugin.meFormat);

        plugin.joinMessage = config.getString("message.join", plugin.joinMessage);
        plugin.leaveMessage = config.getString("message.leave", plugin.leaveMessage);
        plugin.kickMessage = config.getString("message.kick", plugin.kickMessage);
        plugin.deathInFire = config.getString("message.deathInFire", plugin.deathInFire);
        plugin.deathOnFire = config.getString("message.deathOnFire", plugin.deathOnFire);
        plugin.deathLava = config.getString("message.deathLava", plugin.deathLava);
        plugin.deathInWall = config.getString("message.deathInWall", plugin.deathInWall);
        plugin.deathDrown = config.getString("message.deathInWall", plugin.deathInWall);
        plugin.deathStarve = config.getString("message.deathStarve", plugin.deathStarve);
        plugin.deathCactus = config.getString("message.deathCactus", plugin.deathCactus);
        plugin.deathFall = config.getString("message.deathFall", plugin.deathFall);
        plugin.deathOutOfWorld = config.getString("message.deathOutOfWorld", plugin.deathOutOfWorld);
        plugin.deathGeneric = config.getString("message.deathGeneric", plugin.deathGeneric);
        plugin.deathExplosion = config.getString("message.deathExplosion", plugin.deathExplosion);
        plugin.deathMagic = config.getString("message.deathMagic", plugin.deathMagic);
        plugin.deathEntity = config.getString("message.deathEntity", plugin.deathEntity);
        plugin.deathArrow = config.getString("message.deathArrow", plugin.deathArrow);
        plugin.deathFireball = config.getString("message.deathFireball", plugin.deathFireball);
        plugin.deathThrown = config.getString("message.deathThrown", plugin.deathThrown);
        plugin.hMasterT = config.getString("message.heroesMasterT", plugin.hMasterT);
        plugin.hMasterF = config.getString("message.heroesMasterF", plugin.hMasterF);

        plugin.mAPIOnly = config.getBoolean("mchat.apiOnly", plugin.mAPIOnly);
        plugin.alterEvents = config.getBoolean("mchat.alterEvents", plugin.alterEvents);
        plugin.alterDMessages = config.getBoolean("mchat.alterDeathEvents", plugin.alterDMessages);
        plugin.chatDistance = config.getDouble("mchat.chatDistance", plugin.chatDistance);
        plugin.varIndicator = config.getString("mchat.varIndicator", plugin.varIndicator);
        plugin.cusVarIndicator = config.getString("mchat.cusVarIndicator", plugin.cusVarIndicator);
        plugin.spoutEnabled = config.getBoolean("mchat.spout", plugin.spoutEnabled);
        plugin.useIPRestrict = config.getBoolean("mchat.IPCensor", plugin.useIPRestrict);

        plugin.sDeathB = config.getBoolean("suppress.useDeath", plugin.sDeathB);
        plugin.sJoinB = config.getBoolean("suppress.useJoin", plugin.sJoinB);
        plugin.sKickB = config.getBoolean("suppress.useKick", plugin.sKickB);
        plugin.sQuitB = config.getBoolean("suppress.useQuit", plugin.sQuitB);
        plugin.sDeathI = config.getInteger("suppress.maxDeath", plugin.sDeathI);
        plugin.sJoinI = config.getInteger("suppress.maxJoin", plugin.sJoinI);
        plugin.sKickI = config.getInteger("suppress.maxKick", plugin.sKickI);
        plugin.sQuitI = config.getInteger("suppress.maxQuit", plugin.sQuitI);

        plugin.eBroadcast = config.getBoolean("external.enableBroadcast", plugin.eBroadcast);
        plugin.eBroadcastPort = config.getInteger("external.broadcastPort", plugin.eBroadcastPort);
        plugin.eBroadcastIP = config.getString("external.broadcastIP", plugin.eBroadcastIP);

        plugin.useNewInfo = config.getBoolean("info.useNewInfo", plugin.useNewInfo);
        plugin.useLeveledNodes = config.getBoolean("info.useLeveledNodes", plugin.useLeveledNodes);
        plugin.useOldNodes = config.getBoolean("info.useOldNodes", plugin.useOldNodes);
        plugin.useAddDefault = config.getBoolean("info.addNewPlayers", plugin.useAddDefault);
        plugin.mIDefaultGroup = config.getString("info.defaultGroup", plugin.mIDefaultGroup);

        plugin.mChatEB = config.getBoolean("mchate.enable", plugin.mChatEB);
        plugin.useAFKList = config.getBoolean("mchate.useAFKList", plugin.useAFKList);
        plugin.healthNotify = config.getBoolean("mchate.eHealthNotify", plugin.healthNotify);
        plugin.healthAchievement = config.getBoolean("mchate.eHealthAchievement", plugin.healthAchievement);
        plugin.mAFKHQ = config.getBoolean("mchate.eHQAFK", plugin.mAFKHQ);
        plugin.useGroupedList = config.getBoolean("mchate.useGroupedList", plugin.useGroupedList);
        plugin.listVar = config.getString("mchate.listVar", plugin.listVar);
        plugin.cLVars = config.getString("mchate.collapsedListVars", plugin.cLVars);
        plugin.AFKTimer = config.getInteger("mchate.AFKTimer", plugin.AFKTimer);
        plugin.AFKKickTimer = config.getInteger("mchate.AFKKickTimer", plugin.AFKKickTimer);

        plugin.mChatPB = config.getBoolean("pmchat.enable", plugin.spoutPM);
        plugin.spoutPM = config.getBoolean("pmchat.spoutPM", plugin.spoutPM);

        if (plugin.AFKTimer < 10 && plugin.AFKTimer > 0)
            plugin.AFKTimer = 10;

        if (plugin.AFKKickTimer < 20 && plugin.AFKKickTimer > 0)
            plugin.AFKKickTimer = 20;

    }

    void defaultConfig() {
        Configuration config = plugin.mConfig;

        config.setHeader("mChat Configuration File");

        config.setValue("format.date", plugin.dateFormat);
        config.setValue("format.chat", plugin.chatFormat);
        config.setValue("format.name", plugin.nameFormat);
        config.setValue("format.event", plugin.eventFormat);
        config.setValue("format.tabbedList", plugin.tabbedListFormat);
        config.setValue("format.listCmd", plugin.listCmdFormat);
        config.setValue("format.me", plugin.meFormat);

        config.setValue("message.join", plugin.joinMessage);
        config.setValue("message.leave", plugin.leaveMessage);
        config.setValue("message.kick", plugin.kickMessage);
        config.setValue("message.deathInFire", plugin.deathInFire);
        config.setValue("message.deathOnFire", plugin.deathOnFire);
        config.setValue("message.deathLava", plugin.deathLava);
        config.setValue("message.deathInWall", plugin.deathInWall);
        config.setValue("message.deathInWall", plugin.deathInWall);
        config.setValue("message.deathStarve", plugin.deathStarve);
        config.setValue("message.deathCactus", plugin.deathCactus);
        config.setValue("message.deathFall", plugin.deathFall);
        config.setValue("message.deathOutOfWorld", plugin.deathOutOfWorld);
        config.setValue("message.deathGeneric", plugin.deathGeneric);
        config.setValue("message.deathExplosion", plugin.deathExplosion);
        config.setValue("message.deathMagic", plugin.deathMagic);
        config.setValue("message.deathEntity", plugin.deathEntity);
        config.setValue("message.deathArrow", plugin.deathArrow);
        config.setValue("message.deathFireball", plugin.deathFireball);
        config.setValue("message.deathThrown", plugin.deathThrown);
        config.setValue("message.heroesMasterT", plugin.hMasterT);
        config.setValue("message.heroesMasterF", plugin.hMasterF);

        config.setValue("mchat.apiOnly", plugin.mAPIOnly);
        config.setValue("mchat.alterEvents", plugin.alterEvents);
        config.setValue("mchat.alterDeathEvents", plugin.alterDMessages);
        config.setValue("mchat.chatDistance", plugin.chatDistance);
        config.setValue("mchat.varIndicator", plugin.varIndicator);
        config.setValue("mchat.cusVarIndicator", plugin.cusVarIndicator);
        config.setValue("mchat.spout", plugin.spoutEnabled);
        config.setValue("mchat.IPCensor", plugin.useIPRestrict);

        config.setValue("suppress.useDeath", plugin.sDeathB);
        config.setValue("suppress.useJoin", plugin.sJoinB);
        config.setValue("suppress.useKick", plugin.sKickB);
        config.setValue("suppress.useQuit", plugin.sQuitB);
        config.setValue("suppress.maxDeath", plugin.sQuitI);
        config.setValue("suppress.maxJoin", plugin.sJoinI);
        config.setValue("suppress.maxKick", plugin.sKickI);
        config.setValue("suppress.maxQuit", plugin.sQuitI);

        config.setValue("external.enableBroadcast", plugin.eBroadcast);
        config.setValue("external.broadcastPort", plugin.eBroadcastPort);
        config.setValue("external.broadcastIP", plugin.eBroadcastIP);

        config.setValue("info.useNewInfo", plugin.useNewInfo);
        config.setValue("info.useLeveledNodes", plugin.useLeveledNodes);
        config.setValue("info.useOldNodes", plugin.useOldNodes);
        config.setValue("info.addNewPlayers", plugin.useAddDefault);
        config.setValue("info.defaultGroup", plugin.mIDefaultGroup);

        config.setValue("mchate.enable", plugin.mChatEB);
        config.setValue("mchate.eHealthNotify", plugin.healthNotify);
        config.setValue("mchate.eHealthAchievement", plugin.healthAchievement);
        config.setValue("mchate.eHQAFK", plugin.mAFKHQ);
        config.setValue("mchate.useGroupedList", plugin.useGroupedList);
        config.setValue("mchate.listVar", plugin.listVar);
        config.setValue("mchate.collapsedListVars", plugin.cLVars);
        config.setValue("mchate.AFKTimer", plugin.AFKTimer);
        config.setValue("mchate.AFKKickTimer", plugin.AFKKickTimer);
        config.setValue("mchate.useAFKList", plugin.useAFKList);

        config.setValue("pmchat.enable", plugin.spoutPM);
        config.setValue("pmchat.spoutPM", plugin.spoutPM);

        save();
    }

    void checkConfig() {
        Configuration config = plugin.mConfig;

        if (!(new File(plugin.getDataFolder(), "config.yml").exists()))
            defaultConfig();

        removeOption(config, "auto-Changed");
        removeOption(config, "mchat.suppressMessages");

        removeOption(config, "mchat.enableList");


        editOption(config, "mchat-date-format", "format.date");
        editOption(config, "mchat-message-format", "format.chat");
        editOption(config, "mchat-name-format", "format.name");
        editOption(config, "mchat-playerEvent-format", "format.event");
        editOption(config, "mchat-playerList-format", "format.list");
        editOption(config, "mchat-join-message", "message.join");
        editOption(config, "mchat-leave-message", "message.leave");
        editOption(config, "mchat-kick-message", "message.kick");
        editOption(config, "mchat-API-only", "mchat.apiOnly");
        editOption(config, "mchat-format-events", "mchat.formatEvents");
        editOption(config, "mchat-chat-distance", "mchat.chatDistance");
        editOption(config, "mchat-info-only", "info.useNewInfo");
        editOption(config, "mchat-oldNodes-only", "info.useOldNodes");
        editOption(config, "mchat-add-info-players", "info.addNewPlayers");

        editOption(config, "format.list", "format.tabbedList");

        editOption(config, "mchat.formatEvents", "mchat.alterEvents");

        checkOption(config, "format.date", plugin.dateFormat);
        checkOption(config, "format.chat", plugin.chatFormat);
        checkOption(config, "format.name", plugin.nameFormat);
        checkOption(config, "format.event", plugin.eventFormat);
        checkOption(config, "format.tabbedList", plugin.tabbedListFormat);
        checkOption(config, "format.listCmd", plugin.listCmdFormat);
        checkOption(config, "format.me", plugin.meFormat);

        checkOption(config, "message.join", plugin.joinMessage);
        checkOption(config, "message.leave", plugin.leaveMessage);
        checkOption(config, "message.kick", plugin.kickMessage);
        checkOption(config, "message.deathInFire", plugin.deathInFire);
        checkOption(config, "message.deathOnFire", plugin.deathOnFire);
        checkOption(config, "message.deathLava", plugin.deathLava);
        checkOption(config, "message.deathInWall", plugin.deathInWall);
        checkOption(config, "message.deathInWall", plugin.deathInWall);
        checkOption(config, "message.deathStarve", plugin.deathStarve);
        checkOption(config, "message.deathCactus", plugin.deathCactus);
        checkOption(config, "message.deathFall", plugin.deathFall);
        checkOption(config, "message.deathOutOfWorld", plugin.deathOutOfWorld);
        checkOption(config, "message.deathGeneric", plugin.deathGeneric);
        checkOption(config, "message.deathExplosion", plugin.deathExplosion);
        checkOption(config, "message.deathMagic", plugin.deathMagic);
        checkOption(config, "message.deathEntity", plugin.deathEntity);
        checkOption(config, "message.deathArrow", plugin.deathArrow);
        checkOption(config, "message.deathFireball", plugin.deathFireball);
        checkOption(config, "message.deathThrown", plugin.deathThrown);
        checkOption(config, "message.heroesMasterT", plugin.hMasterT);
        checkOption(config, "message.heroesMasterF", plugin.hMasterF);

        checkOption(config, "mchat.apiOnly", plugin.mAPIOnly);
        checkOption(config, "mchat.alterEvents", plugin.alterEvents);
        checkOption(config, "mchat.alterDeathMessages", plugin.alterDMessages);
        checkOption(config, "mchat.chatDistance", plugin.chatDistance);
        checkOption(config, "mchat.varIndicator", plugin.varIndicator);
        checkOption(config, "mchat.cusVarIndicator", plugin.cusVarIndicator);
        checkOption(config, "mchat.spout", plugin.spoutEnabled);
        checkOption(config, "mchat.IPCensor", plugin.useIPRestrict);

        checkOption(config, "suppress.useDeath", plugin.sDeathB);
        checkOption(config, "suppress.useJoin", plugin.sJoinB);
        checkOption(config, "suppress.useKick", plugin.sKickB);
        checkOption(config, "suppress.useQuit", plugin.sQuitB);
        checkOption(config, "suppress.maxDeath", plugin.sQuitI);
        checkOption(config, "suppress.maxJoin", plugin.sJoinI);
        checkOption(config, "suppress.maxKick", plugin.sKickI);
        checkOption(config, "suppress.maxQuit", plugin.sQuitI);

        checkOption(config, "external.enableBroadcast", plugin.eBroadcast);
        checkOption(config, "external.broadcastPort", plugin.eBroadcastPort);
        checkOption(config, "external.broadcastIP", plugin.eBroadcastIP);

        checkOption(config, "info.useNewInfo", plugin.useNewInfo);
        checkOption(config, "info.useLeveledNodes", plugin.useLeveledNodes);
        checkOption(config, "info.useOldNodes", plugin.useOldNodes);
        checkOption(config, "info.addNewPlayers", plugin.useAddDefault);
        checkOption(config, "info.defaultGroup", plugin.mIDefaultGroup);

        checkOption(config, "mchate.enable", plugin.mChatEB);
        checkOption(config, "mchate.eHealthNotify", plugin.healthNotify);
        checkOption(config, "mchate.eHealthAchievement", plugin.healthAchievement);
        checkOption(config, "mchate.eHQAFK", plugin.mAFKHQ);
        checkOption(config, "mchate.useGroupedList", plugin.useGroupedList);
        checkOption(config, "mchate.listVar", plugin.listVar);
        checkOption(config, "mchate.collapsedListVars", plugin.cLVars);
        checkOption(config, "mchate.AFKTimer", plugin.AFKTimer);
        checkOption(config, "mchate.AFKKickTimer", plugin.AFKKickTimer);
        checkOption(config, "mchate.useAFKList", plugin.useAFKList);

        checkOption(config, "pmchat.enable", plugin.mChatPB);
        checkOption(config, "pmchat.spoutPM", plugin.spoutPM);

        if (hasChanged) {
            config.setHeader("mChat Configuration File");

            save();
        }
    }

    void checkOption(Configuration config, String option, Object defValue) {
        if (config.getValue(option) == null) {
            config.setValue(option, defValue);
            hasChanged = true;
        }
    }

    void editOption(Configuration config, String oldOption, String newOption) {
        if (config.getValue(oldOption) != null) {
            config.setValue(newOption, config.getValue(oldOption));
            config.setValue(oldOption, null);
            hasChanged = true;
        }
    }

    void removeOption(Configuration config, String option) {
        if (config.getValue(option) != null) {
            config.setValue(option, null);
            hasChanged = true;
        }
    }
}
