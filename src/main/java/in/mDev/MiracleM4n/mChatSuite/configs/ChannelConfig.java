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

        config.set("Global.prefix", "[");
        config.set("Global.suffix", "]");
        config.set("Global.type", "Global");
        config.set("Global.distance", 0);
        config.set("Global.default", true);
        config.set("Local.prefix", "[");
        config.set("Local.suffix", "]");
        config.set("Local.type", "Local");
        config.set("Local.distance", 60);
        config.set("Local.default", false);
        config.set("Private.prefix", "[");
        config.set("Private.suffix", "]");
        config.set("Private.type", "Private");
        config.set("Private.distance", 0);
        config.set("Private.default", false);
        config.set("World.prefix", "[");
        config.set("World.suffix", "]");
        config.set("World.type", "World");
        config.set("World.distance", 0);
        config.set("World.default", false);
        config.set("Chunk.prefix", "[");
        config.set("Chunk.suffix", "]");
        config.set("Chunk.type", "Chunk");
        config.set("Chunk.distance", 5);
        config.set("Chunk.default", false);
        config.set("Password.prefix", "[");
        config.set("Password.suffix", "]");
        config.set("Password.type", "Password");
        config.set("Password.distance", 0);
        config.set("Password.password", "hello");
        config.set("Password.passworded", true);
        config.set("Password.default", false);

        save();
    }

    public void load() {
        if (!(new File(plugin.getDataFolder(), "channels.yml").exists()))
            defaultConfig();

        plugin.getChannelManager().loadChannels();
    }
}



