package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class InfoConfig {
    mChatSuite plugin;
    YamlConfiguration config;
    YamlConfigurationOptions configO;
    Boolean hasChanged = false;

    public InfoConfig(mChatSuite instance) {
        plugin = instance;

        config = plugin.info;

        configO = config.options();
        configO.indent(4);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(plugin.infoF);
        plugin.info = config;
        config.options().indent(4);
    }

    public void save() {
        try {
            plugin.info = config;
            plugin.info.save(plugin.infoF);

            hasChanged = false;

            MessageUtil.log(plugin.getLocale().getOption(LocaleType.CONFIG_UPDATED).replace("%config%", "info.yml"));
        } catch (Exception ignored) {}
    }

    void defaultConfig() {
        configO.header("Info Config");

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

        config.set("mname.MiracleM4n", "M1r4c13M4n");
        config.set("mname.Jessica_RS", "M1r4c13M4n's Woman");

        hasChanged = true;
    }

    public void load() {
        if (!(plugin.configF).exists())
            defaultConfig();

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

        if (config.get("mname") == null) {
            config.set("mname.MiracleM4n", "M1r4c13M4n");
            config.set("mname.Jessica_RS", "M1r4c13M4n's Woman");

            hasChanged = true;
        }


        if (hasChanged) {
            configO.header("Info Config");

            save();
        }
    }
}

