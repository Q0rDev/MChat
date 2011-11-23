package net.D3GN.MiracleM4n.mChatSuite;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class MIConfigListener {
    mChatSuite plugin;

    Boolean hasChanged = false;

    public MIConfigListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    void load() {
        plugin.mIConfig = YamlConfiguration.loadConfiguration(plugin.mIConfigF);
        plugin.mIConfig.options().indent(4);
    }

    void save() {
        try {
            plugin.mIConfig.save(plugin.mIConfigF);
        } catch (IOException ignored) {}
    }

    void defaultConfig() {
        YamlConfiguration config = plugin.mIConfig;
        YamlConfigurationOptions configO = config.options();

        configO.header(
            "mChat Info Config"
        );

        config.set("groupnames.admin", "[a]");
        config.set("groupnames.sadmin", "[sa]");
        config.set("groupnames.jadmin", "[ja]");
        config.set("groupnames.member", "[m]");
        
        config.set("worldnames.D3GN", "[D]");
        config.set("worldnames.DtK", "[DtK]");
        config.set("worldnames.Nether", "[N]");
        config.set("worldnames.Hello", "[H]");

        config.set("users.MiracleM4n.group", "admin");
        config.set("users.MiracleM4n.worlds.DtK.prefix", "");
        config.set("users.MiracleM4n.info.suffix", "");
        config.set("users.MiracleM4n.info.prefix", "");

        config.set("groups.admin.worlds.DtK.prefix", "");
        config.set("groups.admin.info.prefix", "");
        config.set("groups.admin.info.suffix", "");
        config.set("groups.admin.info.custVar", "");

        save();
    }

    void checkConfig() {
        if (!(new File(plugin.getDataFolder(), "info.yml")).exists()) {
            defaultConfig();
        }

        YamlConfiguration config = plugin.mIConfig;
        YamlConfigurationOptions configO = config.options();

        if (config.get("users") == null) {
            config.set("users.MiracleM4n.group", "admin");
            config.set("users.MiracleM4n.worlds.DtK.prefix", "");
            config.set("users.MiracleM4n.info.suffix", "");
            config.set("users.MiracleM4n.info.prefix", "");

            hasChanged = true;
        }

        if (config.get("groups") == null) {
            config.set("groups.admin.worlds.DtK.prefix", "");
            config.set("groups.admin.info.prefix", "");
            config.set("groups.admin.info.suffix", "");
            config.set("groups.admin.info.custVar", "");

            hasChanged = true;
        }

        if (config.get("groupnames") == null) {
            config.set("groupnames.admin", "[a]");
            config.set("groupnames.sadmin", "[sa]");
            config.set("groupnames.jadmin", "[ja]");
            config.set("groupnames.member", "[m]");

            hasChanged = true;
        }

        if (config.get("worldnames") == null) {
            config.set("worldnames.D3GN", "[D]");
            config.set("worldnames.DtK", "[DtK]");
            config.set("worldnames.Nether", "[N]");
            config.set("worldnames.Hello", "[H]");

            hasChanged = true;
        }

        if (hasChanged) {
            configO.header(
                "mChat Info Config"
            );

            save();
        }
    }
}



