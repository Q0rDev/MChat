package com.miraclem4n.mchat.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CensorUtil {
    private static YamlConfiguration config;
    private static File file;

    public static void initialize() {
        load();
    }

    public static void dispose() {
        config = null;
        file = null;
    }

    public static void load() {
        file = new File("plugins/MChat/censor.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("MChat Censor");

        if (!file.exists()) {
            loadDefaults();
        }
    }

    private static void loadDefaults() {
        set("fuck", "fawg");
        set("cunt", "punt");
        set("shit", "feces");
        set("dick", "74RG3 P3N1S");
        set("miracleman", "MiracleM4n");
        set("dretax", "DreTax");

        save();
    }

    public static void set(String key, Object obj) {
        config.set(key, obj);
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
