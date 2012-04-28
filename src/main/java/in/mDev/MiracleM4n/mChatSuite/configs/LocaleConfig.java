package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class LocaleConfig {
    mChatSuite plugin;
    YamlConfiguration config;
    YamlConfigurationOptions configO;
    Boolean hasChanged = false;

    public LocaleConfig(mChatSuite instance) {
        plugin = instance;

        config = plugin.locale;

        configO = config.options();
        configO.indent(4);
    }

    String playerDied = "%player% has died!";
    String playerDamaged = "%player% has lost health! %health% health left.";
    String youDied = "You have died!";
    String youDamaged = "You have lost health! %health% health left.";
    String typingMessage = "*Typing*";
    String spoutChatColour = "dark_red";
    String configUpdated = "%config% has been updated.";
    String configReloaded = "%config% Reloaded.";
    String stillAFK = "You are still AFK.";
    String noPermissions = "You do not have '%permission%'.";
    String notAFK = "%player% is no longer AFK.";
    String isAFK = "%player% is now AFK. [%reason%]";
    String sayFormat = "&6[Server]&e";
    String shoutFormat = "[Shout]";
    String localFormat = "[L]";
    String forwardFormat = "[F]";
    String spyFormat = "[Spy]";

    public String getOption(LocaleType option) {
        if (config.isSet(option.getOption()))
            return MessageUtil.addColour(config.getString(option.getOption()));

        return "";
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(plugin.localeF);
        plugin.locale = config;
        config.options().indent(4);
    }

    public void save() {
        try {
            plugin.locale = config;
            plugin.locale.save(plugin.localeF);

            hasChanged = false;

            MessageUtil.log(plugin.getLocale().getOption(LocaleType.CONFIG_UPDATED).replace("%config%", "locale.yml"));
        } catch (Exception ignored) {}
    }

    void defaultLocale() {
        config.set("message.spout.colour", spoutChatColour);
        config.set("message.spout.typing", typingMessage);
        config.set("message.player.died", playerDied);
        config.set("message.player.damaged", playerDamaged);
        config.set("message.you.died", youDied);
        config.set("message.you.damaged", youDamaged);
        config.set("message.config.updated", configUpdated);
        config.set("message.general.noPerms", noPermissions);
        config.set("message.config.reloaded", configReloaded);
        config.set("message.player.stillAfk", stillAFK);
        config.set("message.player.notAfk", notAFK);
        config.set("message.player.afk", isAFK);
        config.set("format.say", sayFormat);
        config.set("format.shout", shoutFormat);
        config.set("format.spy", spyFormat);
        config.set("format.local", localFormat);
        config.set("format.forward", forwardFormat);

        hasChanged = true;
    }

    public void load() {
        if (!(plugin.localeF.exists()))
            defaultLocale();

        checkOption("message.spout.colour", spoutChatColour);
        checkOption("message.spout.typing", typingMessage);
        checkOption("message.player.died", playerDied);
        checkOption("message.player.damaged", playerDamaged);
        checkOption("message.you.died", youDied);
        checkOption("message.you.damaged", youDamaged);
        checkOption("message.config.updated", configUpdated);
        checkOption("message.general.noPerms", noPermissions);
        checkOption("message.config.reloaded", configReloaded);
        checkOption("message.player.stillAfk", stillAFK);
        checkOption("message.player.notAfk", notAFK);
        checkOption("message.player.afk", isAFK);
        checkOption("format.say", sayFormat);
        checkOption("format.shout", shoutFormat);
        checkOption("format.spy", spyFormat);
        checkOption("format.local", localFormat);
        checkOption("format.forward", forwardFormat);

        if (hasChanged) {
            configO.header("Locale file.");

            save();
        }
    }

    void checkOption(String option, Object dOption) {
        if (config.get(option) == null) {
            config.set(option, dOption);
            hasChanged = true;
        }
    }
}
