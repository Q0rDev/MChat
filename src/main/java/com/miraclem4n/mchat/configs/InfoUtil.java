package com.miraclem4n.mchat.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class InfoUtil {
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
        file = new File("plugins/mChatSuite/info.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("MChat Info");

        changed = false;

        loadDefaults();
    }

    private static void loadDefaults() {
        if (config.get("users") == null) {
            set("users.MiracleM4n.group", "admin");
            set("users.MiracleM4n.worlds.DtK.prefix", "");
            set("users.MiracleM4n.info.suffix", "");
            set("users.MiracleM4n.info.prefix", "");
        }

        if (config.get("groups") == null) {
            set("groups.admin.worlds.DtK.prefix", "");
            set("groups.admin.info.prefix", "");
            set("groups.admin.info.suffix", "");
            set("groups.admin.info.custVar", "");
        }

        if (config.get("groupnames") == null) {
            set("groupnames.admin", "[a]");
            set("groupnames.sadmin", "[sa]");
            set("groupnames.jadmin", "[ja]");
            set("groupnames.member", "[m]");
        }

        if (config.get("worldnames") == null) {
            set("worldnames.D3GN", "[D]");
            set("worldnames.DtK", "[DtK]");
            set("worldnames.Nether", "[N]");
            set("worldnames.Hello", "[H]");
        }

        if (config.get("mname") == null) {
            set("mname.MiracleM4n", "M1r4c13M4n");
            set("mname.Jessica_RS", "M1r4c13M4n's Woman");
        }

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
}
