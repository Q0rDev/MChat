package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class ChannelConfig {
    mChatSuite plugin;
    YamlConfiguration config;
    YamlConfigurationOptions configO;

    public ChannelConfig(mChatSuite instance) {
        plugin = instance;

        config = plugin.channels;

        configO = config.options();
        configO.indent(4);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(plugin.channelsF);
        plugin.channels = config;
        config.options().indent(4);
    }

    public void save() {
        try {
            plugin.channels = config;
            plugin.channels.save(plugin.channelsF);

            Messanger.log(plugin.getLocale().getOption(LocaleType.CONFIG_UPDATED).replace("%config%", "channels.yml"));
        } catch (Exception ignored) {}
    }

    void defaultConfig() {
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
        if (!(plugin.channelsF.exists()))
            defaultConfig();

        plugin.getChannelManager().loadChannels();
    }
}
