package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.File;
import java.io.IOException;

public class ChannelConfig {
    mChatSuite plugin;

    public ChannelConfig(mChatSuite plugin) {
        this.plugin = plugin;

        plugin.mChConfig.options().indent(4);
    }

    public void reload() {
        plugin.mChConfig = YamlConfiguration.loadConfiguration(plugin.mChConfigF);
        plugin.mChConfig.options().indent(4);
        
        load();
    }

    void save() {
        try {
            plugin.mChConfig.save(plugin.mChConfigF);
        } catch (IOException ignored) {}
    }

    void defaultConfig() {
        YamlConfiguration config = plugin.mChConfig;
        YamlConfigurationOptions configO = config.options();

        configO.header("Channels Config");

        config.set("global.prefix", "[");
        config.set("global.suffix", "]");
        config.set("global.type", "global");
        config.set("global.distance", 0);
        config.set("global.default", true);
        config.set("global.prefix", "[");
        config.set("global.suffix", "]");
        config.set("global.type", "global");
        config.set("global.distance", 60);
        config.set("global.default", false);
        config.set("private.prefix", "[");
        config.set("private.suffix", "]");
        config.set("private.type", "private");
        config.set("private.distance", 0);
        config.set("private.default", false);
        config.set("world.prefix", "[");
        config.set("world.suffix", "]");
        config.set("world.type", "world");
        config.set("world.distance", 0);
        config.set("world.default", false);
        config.set("chunk.prefix", "[");
        config.set("chunk.suffix", "]");
        config.set("chunk.type", "chunk");
        config.set("chunk.distance", 5);
        config.set("chunk.default", false);
        config.set("password.prefix", "[");
        config.set("password.suffix", "]");
        config.set("password.type", "password");
        config.set("password.distance", 0);
        config.set("password.password", "hello");
        config.set("password.passworded", true);
        config.set("password.default", false);

        save();
    }

    public void load() {
        if (!(new File(plugin.getDataFolder(), "channels.yml").exists()))
            defaultConfig();

        plugin.getChannelManager().loadChannels();
    }
}



