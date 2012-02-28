package in.mDev.MiracleM4n.mChatSuite.spout.configs;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;
import org.spout.api.util.config.Configuration;

import java.io.File;

public class InfoConfig {
    mChatSuite plugin;
    Boolean hasChanged = false;

    public InfoConfig(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.mIConfig = new Configuration(plugin.mIConfigF);
        plugin.mIConfig.load();
    }

    void save() {
        plugin.mIConfig.save();
        plugin.mIConfig.load();
    }

    void defaultConfig() {
        Configuration config = plugin.mIConfig;

        config.setHeader("mChat Info Config");

        config.setValue("groupnames.admin", "[a]");
        config.setValue("groupnames.sadmin", "[sa]");
        config.setValue("groupnames.jadmin", "[ja]");
        config.setValue("groupnames.member", "[m]");

        config.setValue("worldnames.D3GN", "[D]");
        config.setValue("worldnames.DtK", "[DtK]");
        config.setValue("worldnames.Nether", "[N]");
        config.setValue("worldnames.Hello", "[H]");

        config.setValue("users.MiracleM4n.group", "admin");
        config.setValue("users.MiracleM4n.worlds.DtK.prefix", "");
        config.setValue("users.MiracleM4n.info.suffix", "");
        config.setValue("users.MiracleM4n.info.prefix", "");

        config.setValue("groups.admin.worlds.DtK.prefix", "");
        config.setValue("groups.admin.info.prefix", "");
        config.setValue("groups.admin.info.suffix", "");
        config.setValue("groups.admin.info.custVar", "");

        config.setValue("mname.MiracleM4n", "M1r4c13M4n");
        config.setValue("mname.Jessica_RS", "M1r4c13M4n's Woman");

        save();
    }

    public void load() {
        if (!(new File(plugin.getDataFolder(), "info.yml").exists())
                && !(new File("plugins/mChat/info.yml").exists()))
            defaultConfig();

        Configuration config = plugin.mIConfig;

        if (config.getValue("users") == null) {
            config.setValue("users.MiracleM4n.group", "admin");
            config.setValue("users.MiracleM4n.worlds.DtK.prefix", "");
            config.setValue("users.MiracleM4n.info.suffix", "");
            config.setValue("users.MiracleM4n.info.prefix", "");

            hasChanged = true;
        }

        if (config.getValue("groups") == null) {
            config.setValue("groups.admin.worlds.DtK.prefix", "");
            config.setValue("groups.admin.info.prefix", "");
            config.setValue("groups.admin.info.suffix", "");
            config.setValue("groups.admin.info.custVar", "");

            hasChanged = true;
        }

        if (config.getValue("groupnames") == null) {
            config.setValue("groupnames.admin", "[a]");
            config.setValue("groupnames.sadmin", "[sa]");
            config.setValue("groupnames.jadmin", "[ja]");
            config.setValue("groupnames.member", "[m]");

            hasChanged = true;
        }

        if (config.getValue("worldnames") == null) {
            config.setValue("worldnames.D3GN", "[D]");
            config.setValue("worldnames.DtK", "[DtK]");
            config.setValue("worldnames.Nether", "[N]");
            config.setValue("worldnames.Hello", "[H]");

            hasChanged = true;
        }

        if (config.getValue("mname") == null) {
            config.setValue("mname.MiracleM4n", "M1r4c13M4n");
            config.setValue("mname.Jessica_RS", "M1r4c13M4n's Woman");

            hasChanged = true;
        }


        if (hasChanged) {
            config.setHeader("mChat Info Config");

            save();
        }
    }
}



