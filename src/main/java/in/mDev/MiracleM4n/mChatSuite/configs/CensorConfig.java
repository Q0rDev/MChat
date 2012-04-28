package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class CensorConfig {
    mChatSuite plugin;
    YamlConfiguration config;
    YamlConfigurationOptions configO;

    public CensorConfig(mChatSuite instance) {
        plugin = instance;

        config = plugin.censor;

        configO = config.options();
        configO.indent(4);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(plugin.censorF);
        plugin.censor = config;
        config.options().indent(4);
    }

    public void save() {
        try {
            plugin.censor = config;
            plugin.censor.save(plugin.censorF);

            MessageUtil.log(plugin.getLocale().getOption(LocaleType.CONFIG_UPDATED).replace("%config%", "censor.yml"));
        } catch (Exception ignored) {}
    }

    public void load() {
        if (!(plugin.censorF.exists()))
            defaultConfig();
    }

    void defaultConfig() {
        configO.header("Censor File");

        config.set("fuck", "fawg");
        config.set("cunt", "punt");
        config.set("shit", "feces");
        config.set("dick", "LARGE PENIS");
        config.set("miracleman", "MiracleM4n");

        save();
    }
}
