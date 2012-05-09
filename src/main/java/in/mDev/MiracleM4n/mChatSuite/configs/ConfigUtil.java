package in.mDev.MiracleM4n.mChatSuite.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class ConfigUtil {
    static YamlConfiguration config;
    static File file;

    static ArrayList<String> meAliases = new ArrayList<String>();
    static ArrayList<String> whoAliases = new ArrayList<String>();
    static ArrayList<String> listAliases = new ArrayList<String>();
    static ArrayList<String> sayAliases = new ArrayList<String>();
    static ArrayList<String> afkAliases = new ArrayList<String>();
    static ArrayList<String> afkOtherAliases = new ArrayList<String>();
    static ArrayList<String> shoutAliases = new ArrayList<String>();
    static ArrayList<String> muteAliases = new ArrayList<String>();
    static ArrayList<String> pmAliases = new ArrayList<String>();
    static ArrayList<String> replyAliases = new ArrayList<String>();
    static ArrayList<String> inviteAliases = new ArrayList<String>();
    static ArrayList<String> acceptAliases = new ArrayList<String>();
    static ArrayList<String> denyAliases = new ArrayList<String>();
    static ArrayList<String> leaveAliases = new ArrayList<String>();
    static ArrayList<String> mChannelAliases = new ArrayList<String>();

    public static void initialize() {
        load();
    }

    public static void dispose() {
        config = null;
        file = null;
    }

    public static void load() {
        file = new File("plugins/mChatSuite/config.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("mChatSuite Config");

        loadDefaults();
    }

    public static void unload() {

    }

    private static void loadDefaults() {
        removeOption("auto-Changed");
        removeOption("mchat.suppressMessages");

        removeOption("mchat.enableList");

        editOption("mchat-date-format", "format.date");
        editOption("mchat-message-format", "format.chat");
        editOption("mchat-name-format", "format.name");
        editOption("mchat-playerEvent-format", "format.event");
        editOption("mchat-playerList-format", "format.list");
        editOption("mchat-join-message", "message.join");
        editOption("mchat-leave-message", "message.leave");
        editOption("mchat-kick-message", "message.kick");
        editOption("mchat-API-only", "mchat.apiOnly");
        editOption("mchat-format-events", "mchat.formatEvents");
        editOption("mchat-chat-distance", "mchat.chatDistance");
        editOption("mchat-info-only", "info.useNewInfo");
        editOption("mchat-oldNodes-only", "info.useOldNodes");
        editOption("mchat-add-info-players", "info.addNewPlayers");

        editOption("format.list", "format.tabbedList");

        editOption("mchat.formatEvents", "mchat.alterEvents");

        editOption("mchat.alterEvents", "mchat.alter.events");
        editOption("mchat.alterDeathMessages", "mchat.alter.death");


        checkOption("format.date", "HH:mm:ss");
        checkOption("format.chat", "+p+dn+s&f: +m");
        checkOption("format.name", "+p+dn+s&e");
        checkOption("format.event", "+p+dn+s&e");
        checkOption("format.tabbedList", "+p+dn+s");
        checkOption("format.listCmd", "+p+dn+s");
        checkOption("format.me", "* +p+dn+s&e +m");

        checkOption("message.join", "has joined the game.");
        checkOption("message.leave", "has left the game.");
        checkOption("message.kick", "has been kicked from the game. [ +r ]");
        checkOption("message.deathInFire", "went up in flames.");
        checkOption("message.deathOnFire", "burned to death.");
        checkOption("message.deathLava", "tried to swim in lava.");
        checkOption("message.deathInWall", "suffocated in a wall.");
        checkOption("message.deathDrown", "drowned.");
        checkOption("message.deathStarve", "starved to death.");
        checkOption("message.deathCactus", "was pricked to death.");
        checkOption("message.deathFall", "hit the ground too hard.");
        checkOption("message.deathOutOfWorld", "fell out of the world.");
        checkOption("message.deathGeneric", "died.");
        checkOption("message.deathExplosion", "blew up.");
        checkOption("message.deathMagic", "was killed by magic.");
        checkOption("message.deathEntity", "was slain by +killer.");
        checkOption("message.deathMobFormat", "a +killer");
        checkOption("message.deathArrow", "was shot by +killer.");
        checkOption("message.deathFireball", "was fireballed by +killer.");
        checkOption("message.deathThrown", "was pummeled by +killer.");
        checkOption("message.heroesMasterT", "The Great");
        checkOption("message.heroesMasterF", "The Squire");

        checkOption("mchat.apiOnly", false);
        checkOption("mchat.alter.events", true);
        checkOption("mchat.alter.death", true);
        checkOption("mchat.chatDistance", -1.0);
        checkOption("mchat.varIndicator", "+");
        checkOption("mchat.cusVarIndicator", "-");
        checkOption("mchat.spout", true);
        checkOption("mchat.IPCensor", true);
        checkOption("mchat.cLockRange", 3);

        checkOption("suppress.useDeath", false);
        checkOption("suppress.useJoin", false);
        checkOption("suppress.useKick", false);
        checkOption("suppress.useQuit", false);
        checkOption("suppress.maxDeath", 30);
        checkOption("suppress.maxJoin", 30);
        checkOption("suppress.maxKick", 30);
        checkOption("suppress.maxQuit", 30);

        checkOption("info.useNewInfo", false);
        checkOption("info.useLeveledNodes", false);
        checkOption("info.useOldNodes", false);
        checkOption("info.addNewPlayers", false);
        checkOption("info.defaultGroup", "default");

        checkOption("mchate.enable", false);
        checkOption("mchate.eHQAFK", true);
        checkOption("mchate.useGroupedList", true);
        checkOption("mchate.listVar", "group");
        checkOption("mchate.collapsedListVars", "default,Default");
        checkOption("mchate.AFKTimer", 30);
        checkOption("mchate.AFKKickTimer", 120);
        checkOption("mchate.useAFKList", false);

        checkOption("pmchat.enable", false);
        checkOption("pmchat.spoutPM", false);

        loadAliases();

        checkOption("aliases.mchatme", meAliases);
        checkOption("aliases.mchatwho", whoAliases);
        checkOption("aliases.mchatlist", listAliases);
        checkOption("aliases.mchatsay", sayAliases);
        checkOption("aliases.mchatafk", afkAliases);
        checkOption("aliases.mchatafkother", afkOtherAliases);
        checkOption("aliases.pmchat", pmAliases);
        checkOption("aliases.pmchatreply", replyAliases);
        checkOption("aliases.pmchatinvite", inviteAliases);
        checkOption("aliases.pmchataccept", acceptAliases);
        checkOption("aliases.pmchatdeny", denyAliases);
        checkOption("aliases.pmchatleave", leaveAliases);
        checkOption("aliases.mchatshout", shoutAliases);
        checkOption("aliases.mchatmute", muteAliases);
        checkOption("aliases.mchannel", mChannelAliases);

        unloadAliases();

        editValue("message.deathInFire", config.getString("message.deathInFire").replace("+CName", "+killer"));
        editValue("message.deathOnFire", config.getString("message.deathOnFire").replace("+CName", "+killer"));
        editValue("message.deathLava", config.getString("message.deathLava").replace("+CName", "+killer"));
        editValue("message.deathInWall", config.getString("message.deathInWall").replace("+CName", "+killer"));
        editValue("message.deathStarve", config.getString("message.deathStarve").replace("+CName", "+killer"));
        editValue("message.deathCactus", config.getString("message.deathCactus").replace("+CName", "+killer"));
        editValue("message.deathFall", config.getString("message.deathFall").replace("+CName", "+killer"));
        editValue("message.deathOutOfWorld", config.getString("message.deathOutOfWorld").replace("+CName", "+killer"));
        editValue("message.deathGeneric", config.getString("message.deathGeneric").replace("+CName", "+killer"));
        editValue("message.deathExplosion", config.getString("message.deathExplosion").replace("+CName", "+killer"));
        editValue("message.deathMagic", config.getString("message.deathMagic").replace("+CName", "+killer"));
        editValue("message.deathEntity", config.getString("message.deathEntity").replace("+CName", "+killer"));
        editValue("message.deathArrow", config.getString("message.deathArrow").replace("+CName", "+killer"));
        editValue("message.deathFireball", config.getString("message.deathFireball").replace("+CName", "+killer"));
        editValue("message.deathThrown", config.getString("message.deathThrown").replace("+CName", "+killer"));
    }

    public static void set(String key, Object obj) {
        config.set(key, obj);

        save();
    }

    public static Boolean save() {
        try {
            config.save(file);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    private static void checkOption(String option, Object defValue) {
        if (!config.isSet(option))
            set(option, defValue);
    }

    private static void editOption(String oldOption, String newOption) {
        if (config.isSet(oldOption)) {
            set(newOption, config.get(oldOption));
            set(oldOption, null);
        }
    }

    private static void removeOption(String option) {
        if (config.isSet(option))
            set(option, null);
    }

    private static void editValue(String option, Object newValue) {
        if (config.isSet(option))
            if (config.get(option) != newValue)
                set(option, newValue);
    }

    private static void loadAliases() {
        meAliases.add("me");

        whoAliases.add("who");

        listAliases.add("list");
        listAliases.add("online");
        listAliases.add("playerlist");

        sayAliases.add("say");

        afkAliases.add("afk");
        afkAliases.add("away");

        afkOtherAliases.add("afko");
        afkOtherAliases.add("awayother");
        afkOtherAliases.add("awayo");

        shoutAliases.add("shout");
        shoutAliases.add("yell");

        muteAliases.add("mute");
        muteAliases.add("quiet");

        pmAliases.add("pm");
        pmAliases.add("msg");
        pmAliases.add("message");
        pmAliases.add("m");
        pmAliases.add("tell");
        pmAliases.add("t");

        replyAliases.add("reply");
        replyAliases.add("r");

        inviteAliases.add("invite");

        acceptAliases.add("accept");

        denyAliases.add("deny");

        leaveAliases.add("leave");

        mChannelAliases.add("channel");
    }

    private static void unloadAliases() {
        meAliases.clear();

        whoAliases.clear();

        listAliases.clear();

        sayAliases.clear();

        afkAliases.clear();

        afkOtherAliases.clear();

        shoutAliases.clear();

        muteAliases.clear();

        pmAliases.clear();

        replyAliases.clear();

        inviteAliases.clear();

        acceptAliases.clear();

        denyAliases.clear();

        leaveAliases.clear();

        mChannelAliases.clear();
    }
}
