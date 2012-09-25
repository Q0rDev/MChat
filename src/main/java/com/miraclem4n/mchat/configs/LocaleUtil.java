package com.miraclem4n.mchat.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocaleUtil {
    static YamlConfiguration config;
    static File file;
    static Boolean changed;

    public static void initialize() {
        load();
    }

    public static void dispose() {
        config = null;
        file = null;
        changed = null;
    }

    public static void load() {
        file = new File("plugins/mChatSuite/locale.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("MChat Locale");

        changed = false;

        loadDefaults();
    }

    private static void loadDefaults() {
        checkOption("format.forward", "[F]");
        checkOption("format.local", "[L]");
        checkOption("format.pm.received", "%sender &1-&2-&3-&4> &fMe: %msg");
        checkOption("format.pm.sent", "&fMe &1-&2-&3-&4> &4%recipient&f: %msg");
        checkOption("format.say", "&6[Server]&e %msg");
        checkOption("format.shout", "[Shout]");
        checkOption("format.spy", "[Spy]");

        checkOption("format.date", "HH:mm:ss");
        checkOption("format.name", "+p+dn+s&e");
        checkOption("format.tabbedList", "+p+dn+s");
        checkOption("format.listCmd", "+p+dn+s");
        checkOption("format.me", "* +p+dn+s&e +m");

        checkOption("message.afk.afk", "AFK");
        checkOption("message.afk.default", "Away From Keyboard");
        checkOption("message.config.reloaded", "%config Reloaded.");
        checkOption("message.config.updated", "%config has been updated.");
        checkOption("message.convo.accepted", "Convo request with &5'&4%player&5'&4 has been accepted.");
        checkOption("message.convo.convo", "&4[Convo] ");
        checkOption("message.convo.denied", "You have denied a Convo request from &5'&4%player&5'&4.");
        checkOption("message.convo.ended", "Conversation with '%player' has ended.");
        checkOption("message.convo.hasRequest", "&5'&4%player&5'&4 Already has a Convo request.");
        checkOption("message.convo.inviteSent", "You have invited &5'&4%player&5'&4 to have a Convo.");
        checkOption("message.convo.invited", "You have been invited to a Convo by &5'&4%player&5'&4 use /pmchataccept to accept.");
        checkOption("message.convo.left", "You have left the Conversation with '%player'.");
        checkOption("message.convo.noPending", "No pending Convo request.");
        checkOption("message.convo.notIn", "You are not currently in a Convo.");
        checkOption("message.convo.notStarted", "Convo request with &5'&4%player&5'&4 has been denied.");
        checkOption("message.convo.started", "You have started a Convo with &5'&4%player&5'&4.");
        checkOption("message.general.mute", "Target '%player' successfully %muted. To %mute use this command again.");
        checkOption("message.general.noPerms", "You do not have '%permission'.");
        checkOption("message.info.alteration", "Info Alteration Successful.");
        checkOption("message.list.header", "&6-- There are &8%players&6 out of the maximum of &8%max&6 Players online. --");
        checkOption("message.player.afk", "%player is now AFK. [ %reason ]");
        checkOption("message.player.notAfk", "%player is no longer AFK.");
        checkOption("message.player.notFound", "");
        checkOption("message.player.notOnline", "");
        checkOption("message.player.stillAfk", "You are still AFK.");
        checkOption("message.pm.noPm", "No one has yet PM'd you.");
        checkOption("message.shout.noInput", "You can't shout nothing!");
        checkOption("message.spout.colour", "dark_red");
        checkOption("message.spout.pmFrom", "[PMChat] From:");
        checkOption("message.spout.typing", "*Typing*");
        checkOption("message.event.join", "%player has joined the game.");
        checkOption("message.event.leave", "%player has left the game.");
        checkOption("message.event.kick", "%player has been kicked from the game. [ %reason ]");
        checkOption("message.death.inFire", "%player went up in flames.");
        checkOption("message.death.onFire", "%player burned to death.");
        checkOption("message.death.lava", "%player tried to swim in lava.");
        checkOption("message.death.inWall", "%player suffocated in a wall.");
        checkOption("message.death.drown", "%player drowned.");
        checkOption("message.death.starve", "%player starved to death.");
        checkOption("message.death.cactus", "%player was pricked to death.");
        checkOption("message.death.fall", "%player hit the ground too hard.");
        checkOption("message.death.outOfWorld", "%player fell out of the world.");
        checkOption("message.death.generic", "%player died.");
        checkOption("message.death.explosion", "%player blew up.");
        checkOption("message.death.magic", "%player was killed by magic.");
        checkOption("message.death.entity", "%player was slain by %killer.");
        checkOption("message.death.arrow", "%player was shot by %killer.");
        checkOption("message.death.fireball", "%player was fireballed by %killer.");
        checkOption("message.death.thrown", "%player was pummeled by %killer.");
        checkOption("message.heroes.isMaster", "The Great");
        checkOption("message.heroes.notMaster", "The Squire");

        save();
    }

    public static void set(String key, Object obj) {
        config.set(key, obj);

        changed = true;
    }

    public static Boolean save() {
        if (!changed)
            return false;

        try {
            config.save(file);
            changed = false;
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
}
