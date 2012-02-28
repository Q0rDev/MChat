package in.mDev.MiracleM4n.mChatSuite.spout.configs;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

import org.spout.api.util.config.Configuration;

public class LocaleConfig {
    mChatSuite plugin;
    Configuration config;

    public LocaleConfig(mChatSuite plugin) {
        this.plugin = plugin;
        this.config = plugin.mELocale;

        reload();
    }

    Boolean hasChanged = false;

    String playerDied = "has died!";
    String playerDamaged = "has lost health!";
    String youDied = "You have died!";
    String youDamaged = "You have lost health!";
    String youHave = "You have";
    String healthLeft = "health left.";
    String typingMessage = "*Typing*";
    String spoutChatColour = "dark_red";
    String configUpdated = "has been updated.";
    String stillAFK = "You are still AFK.";
    String noPermissions = "You are not allowed to use";
    String configReloaded = "Config Reloaded.";
    String notAFK = "is no longer AFK.";
    String isAFK = "is now AFK.";
    String AFK = "mAFK";
    String playerOnline = "Players Online";
    String player = "Player";
    String notFound = "not found.";
    String sayName = "&6[Server]&e";
    String shoutFormat = "[Shout]";
    String localFormat = "[L]";
    String spyFormat = "[Spy]";

    public String getOption(String option) {
        if (config.getValue(option) != null)
            return plugin.getAPI().addColour(config.getString(option));

        return "";
    }

    public void reload() {
        plugin.mELocale = new Configuration(plugin.mELocaleF);
        plugin.mELocale.load();

        load();
    }

    void defaultLocale() {

        config.setHeader("mChatSuite Locale file.");

        config.setValue("spoutChatColour", spoutChatColour);
        config.setValue("typingMessage", typingMessage);
        config.setValue("playerDied", playerDied);
        config.setValue("playerDamaged", playerDamaged);
        config.setValue("healthLeft", healthLeft);
        config.setValue("youDied", youDied);
        config.setValue("youDamaged", youDamaged);
        config.setValue("youHave", youHave);
        config.setValue("configUpdated.", configUpdated);
        config.setValue("stillAFK.", stillAFK);
        config.setValue("noPermissions", noPermissions);
        config.setValue("configReloaded", configReloaded);
        config.setValue("notAFK", notAFK);
        config.setValue("isAFK", isAFK);
        config.setValue("AFK", AFK);
        config.setValue("playerOnline", playerOnline);
        config.setValue("player", player);
        config.setValue("notFound", notFound);
        config.setValue("sayName", sayName);
        config.setValue("format.shout", shoutFormat);
        config.setValue("format.local", localFormat);
        config.setValue("format.spy", spyFormat);

        config.save();
    }

    public void load() {
        if (!(plugin.mELocaleF).exists())
            defaultLocale();

        checkOption("spoutChatColour", spoutChatColour);
        checkOption("typingMessage", typingMessage);
        checkOption("playerDied", playerDied);
        checkOption("playerDamaged", playerDamaged);
        checkOption("healthLeft", healthLeft);
        checkOption("youDied", youDied);
        checkOption("youDamaged", youDamaged);
        checkOption("youHave", youHave);
        checkOption("configUpdated.", configUpdated);
        checkOption("stillAFK.", stillAFK);
        checkOption("noPermissions", noPermissions);
        checkOption("configReloaded", configReloaded);
        checkOption("notAFK", notAFK);
        checkOption("isAFK", isAFK);
        checkOption("AFK", AFK);
        checkOption("playerOnline", playerOnline);
        checkOption("player", player);
        checkOption("notFound", notFound);
        checkOption("sayName", sayName);
        checkOption("format.shout", shoutFormat);
        checkOption("format.spy", spyFormat);

        if (hasChanged) {
            config.setHeader("mChatSuite Locale file.");

            config.save();

            System.out.println("[" + plugin.pdfFile.getName() + "] locale.yml " + configUpdated);
        }
    }

    void checkOption(String option, Object dOption) {
        if (config.getValue(option) == null) {
            config.setValue(option, dOption);
            hasChanged = true;
        }
    }
}
