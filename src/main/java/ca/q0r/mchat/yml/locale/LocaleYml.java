package ca.q0r.mchat.yml.locale;

import ca.q0r.mchat.yml.Yml;

import java.io.File;

public class LocaleYml extends Yml {
    public LocaleYml() {
        super(new File("plugins/MChat/locale.yml"), "MChat Locale");
    }

    public void loadDefaults() {
        checkOption("format.forward", "[F]");
        checkOption("format.local", "[L]");
        checkOption("format.spy", "[Spy]");

        checkOption("format.chat", "+p+dn+s&f: +m");
        checkOption("format.date", "HH:mm:ss");
        checkOption("format.name", "+p+dn+s&e");
        checkOption("format.tabbedList", "+p+dn+s");
        checkOption("format.listCmd", "+p+dn+s");
        checkOption("format.me", "* +p+dn+s&e +m");

        checkOption("message.general.noPerms", "You do not have '%permission'.");
        checkOption("message.general.noPerms", "You do not have '%permission'.");
        checkOption("message.info.alteration", "Info Alteration Successful.");
        checkOption("message.player.stillAfk", "You are still AFK.");
        checkOption("message.player.stillAfk", "You are still AFK.");
        checkOption("message.event.join", "%player has joined the game.");
        checkOption("message.event.leave", "%player has left the game.");
        checkOption("message.event.kick", "%player has been kicked from the game. [ %reason ]");
        checkOption("message.heroes.isMaster", "The Great");
        checkOption("message.heroes.notMaster", "The Squire");

        save();
    }
}