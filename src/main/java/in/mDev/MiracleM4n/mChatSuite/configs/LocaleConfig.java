package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.IOException;

public class LocaleConfig {
    mChatSuite plugin;
    YamlConfiguration config;
    Boolean hasChanged = false;

    public LocaleConfig(mChatSuite plugin) {
        this.plugin = plugin;
        this.config = plugin.mELocale;

        reload();
    }

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
        if (config.isSet(option))
            return Messanger.addColour(config.getString(option));

        return "";
    }

    public void reload() {
        plugin.mELocale = YamlConfiguration.loadConfiguration(plugin.mELocaleF);

        load();
    }

    void defaultLocale() {
        YamlConfigurationOptions configO = config.options();

        configO.header("Locale file.");

        config.set("spoutChatColour", spoutChatColour);
        config.set("typingMessage", typingMessage);
        config.set("playerDied", playerDied);
        config.set("playerDamaged", playerDamaged);
        config.set("healthLeft", healthLeft);
        config.set("youDied", youDied);
        config.set("youDamaged", youDamaged);
        config.set("youHave", youHave);
        config.set("configUpdated.", configUpdated);
        config.set("stillAFK.", stillAFK);
        config.set("noPermissions", noPermissions);
        config.set("configReloaded", configReloaded);
        config.set("notAFK", notAFK);
        config.set("isAFK", isAFK);
        config.set("AFK", AFK);
        config.set("playerOnline", playerOnline);
        config.set("player", player);
        config.set("notFound", notFound);
        config.set("sayName", sayName);
        config.set("format.shout", shoutFormat);
        config.set("format.local", localFormat);
        config.set("format.spy", spyFormat);

        try {
            config.save(plugin.mELocaleF);
        } catch (IOException ignored) {}
    }

    public void load() {
        if (!(plugin.mELocaleF).exists())
            defaultLocale();

        YamlConfigurationOptions configO = config.options();

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
            configO.header("Locale file.");

            try {
                config.save(plugin.mELocaleF);
            } catch (IOException ignored) {}

            Messanger.log("[" + plugin.pdfFile.getName() + "] locale.yml " + getOption("configUpdated") + ".");
        }
    }

    void checkOption(String option, Object dOption) {
        if (config.get(option) == null) {
            config.set(option, dOption);
            hasChanged = true;
        }
    }
}
