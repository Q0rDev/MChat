package in.mDev.MiracleM4n.mChatSuite.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ChannelUtil {
    static YamlConfiguration config;
    static File file;

    public static void initialize() {
        load();
    }

    public static void load() {
        file = new File("plugins/mChatSuite/channels.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("mChatSuite Channels");

        if (!file.exists())
            loadDefaults();
    }

    private static void loadDefaults() {
        set("global.prefix", "[");
        set("global.suffix", "]");
        set("global.type", "global");
        set("global.distance", 0);
        set("global.default", true);
        set("global.prefix", "[");
        set("global.suffix", "]");
        set("global.type", "global");
        set("global.distance", 60);
        set("global.default", false);
        set("private.prefix", "[");
        set("private.suffix", "]");
        set("private.type", "private");
        set("private.distance", 0);
        set("private.default", false);
        set("world.prefix", "[");
        set("world.suffix", "]");
        set("world.type", "world");
        set("world.distance", 0);
        set("world.default", false);
        set("chunk.prefix", "[");
        set("chunk.suffix", "]");
        set("chunk.type", "chunk");
        set("chunk.distance", 5);
        set("chunk.default", false);
        set("password.prefix", "[");
        set("password.suffix", "]");
        set("password.type", "password");
        set("password.distance", 0);
        set("password.password", "hello");
        set("password.passworded", true);
        set("password.default", false);

        save();
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
}