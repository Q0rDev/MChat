package in.mDev.MiracleM4n.mChatSuite.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class LocaleUtil {
    static YamlConfiguration config;
    static File file;

    public static void initialize() {
        load();
    }

    public static void load() {
        file = new File("plugins/mChatSuite/locale.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("mChatSuite Locale");

        loadDefaults();
    }

    private static void loadDefaults() {
        checkOption("message.spout.colour", "dark_red");
        checkOption("message.spout.typing", "*Typing*");
        checkOption("message.config.updated", "%config% has been updated.");
        checkOption("message.general.noPerms", "You do not have '%permission%'.");
        checkOption("message.config.reloaded", "%config% Reloaded.");
        checkOption("message.player.stillAfk", "You are still AFK.");
        checkOption("message.player.notAfk", "%player% is no longer AFK.");
        checkOption("message.player.afk", "%player% is now AFK. [%reason%]");
        checkOption("format.say", "&6[Server]&e");
        checkOption("format.shout", "[Shout]");
        checkOption("format.spy", "[Spy]");
        checkOption("format.local", "[L]");
        checkOption("format.forward", "[F]");
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
}
