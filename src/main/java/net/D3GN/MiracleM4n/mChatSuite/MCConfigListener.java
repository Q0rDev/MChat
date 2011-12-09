package net.D3GN.MiracleM4n.mChatSuite;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.File;
import java.io.IOException;

public class MCConfigListener {
    mChatSuite plugin;

    public MCConfigListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    void load() {
        plugin.mCConfig = YamlConfiguration.loadConfiguration(plugin.mCConfigF);
        plugin.mCConfig.options().indent(4);
    }

    void save() {
        try {
            plugin.mCConfig.save(plugin.mCConfigF);
        } catch (IOException ignored) {}
    }

    void loadConfig() {
        if (!(new File(plugin.getDataFolder(), "censor.yml").exists())
         && !(new File("plugins/mChat/censor.yml").exists()))
            defaultConfig();
    }

    void defaultConfig() {
        YamlConfiguration config = plugin.mCConfig;
        YamlConfigurationOptions configO = config.options();

        configO.header(
            " mChat Censor File"
        );

        config.set("fuck", "fawg");
        config.set("cunt", "punt");
        config.set("shit", "feces");
        config.set("dick", "LARGE PENIS");
        config.set("miracleman", "MiracleM4n");

        save();
    }
}
