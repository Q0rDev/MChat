package com.miraclem4n.mchat.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CensorUtil {
    static YamlConfiguration config;
    static File file;

    public static void initialize() {
        load();
    }

    public static void load() {
        file = new File("plugins/mChatSuite/censor.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("mChatSuite Censor");

        if (!file.exists())
            loadDefaults();
    }

    private static void loadDefaults() {
        set("fuck", "fawg");
        set("cunt", "punt");
        set("shit", "feces");
        set("dick", "LARGE PENIS");
        set("miracleman", "MiracleM4n");

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
