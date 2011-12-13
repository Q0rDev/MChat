package in.mDev.MiracleM4n.mChatSuite.configListeners;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.IOException;

public class MLanguageListener {
    mChatSuite plugin;
    YamlConfiguration config;

    public MLanguageListener(mChatSuite plugin, YamlConfiguration config) {
        this.plugin = plugin;
        this.config = config;
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

    public String getOption(String option) {
        if (this.config.isSet(option))
            return this.config.getString(option);

        return "";
    }

    public void load() {
        plugin.mELocale = YamlConfiguration.loadConfiguration(plugin.mELocaleF);

        checkLocale();
    }

    void defaultLocale() {
        YamlConfigurationOptions configO = config.options();

        configO.header(
            "mChatSuite Locale file."
        );

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

        try {
            config.save(plugin.mELocaleF);
        } catch (IOException ignored) {}
    }

    public void checkLocale() {
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

        if (hasChanged) {
            configO.header(
                "mChatSuite Locale file."
            );

            try {
                config.save(plugin.mELocaleF);
            } catch (IOException ignored) {}

            System.out.println("[" + plugin.pdfFile.getName() + "] locale.yml " + configUpdated);
        }
    }

    void checkOption(String option, Object dOption) {
        if (config.get(option) == null) {
            config.set(option, dOption);
            hasChanged = true;
        }
    }
}
