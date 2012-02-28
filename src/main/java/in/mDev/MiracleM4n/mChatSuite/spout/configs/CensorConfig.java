package in.mDev.MiracleM4n.mChatSuite.spout.configs;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

import org.spout.api.util.config.Configuration;

import java.io.File;

public class CensorConfig {
    mChatSuite plugin;

    public CensorConfig(mChatSuite plugin) {
        this.plugin = plugin;

        reload();
    }

    public void reload() {
        plugin.mCConfig = new Configuration(plugin.mCConfigF);
        plugin.mCConfig.load();
    }

    void save() {
        plugin.mCConfig.save();
        plugin.mCConfig.load();
    }

    public void load() {
        if (!(new File(plugin.getDataFolder(), "censor.yml").exists())
                && !(new File("plugins/mChat/censor.yml").exists()))
            defaultConfig();
    }

    void defaultConfig() {
        Configuration config = plugin.mCConfig;

        config.setHeader("mChat Censor File");

        config.setValue("fuck", "fawg");
        config.setValue("cunt", "punt");
        config.setValue("shit", "feces");
        config.setValue("dick", "LARGE PENIS");
        config.setValue("miracleman", "MiracleM4n");

        save();
    }
}
