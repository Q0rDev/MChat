package net.D3GN.MiracleM4n.mChatSuite;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.Map;

public class MInfoReader {
    mChatSuite plugin;

    public MInfoReader(mChatSuite plugin) {
        this.plugin = plugin;
    }

    // Player Info
    public void addPlayer(String player, String defaultGroup) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users")) {
            if (!config.isSet("users." + player)) {
                config.set("users." + player + ".group", defaultGroup);
                config.set("users." + player + ".info.prefix", "");
                config.set("users." + player + ".info.suffix", "");

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}

                addDefaultGroup(defaultGroup);
            }
        }
    }

    protected void addDefaultGroup(String groupName) {
        YamlConfiguration config = plugin.mIConfig;

        if (!config.isSet("groups." + groupName)) {
            config.set("groups." + groupName + ".info.prefix", "");
            config.set("groups." + groupName + ".info.suffix", "");

            try {
                config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void setPlayerGroup(String player, String group) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player)) {
            config.set("users." + player + ".group", group);

            try {
                config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editPlayerName(String player, String newName) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player)) {
            if (!config.isSet("users." + newName)) {
                for (Map.Entry entry : config.getConfigurationSection("users." + player).getValues(true).entrySet()) {
                    if (entry.getValue() instanceof MemorySection)
                        continue;

                    config.set("users." + newName + "." + entry.getKey(), entry.getValue());
                }

                config.set("users." + player, null);

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}
            }
        }
    }

    public void removePlayer(String player) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player)) {
            config.set("users." + player, null);

            try {
                config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void addPlayerInfoVar(String player, String var, String value) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".info")) {
            config.set("users." + player + ".info." + var, value);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editPlayerInfoVar(String player, String oldVar, String newVar) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".info." + oldVar)) {
            for (Map.Entry entry : config.getConfigurationSection("users." + player + ".info." + oldVar).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("users." + newVar + "." + entry.getKey(), entry.getValue());
            }

            config.set("users." + player + ".info." + oldVar, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editPlayerInfoValue(String player, String var, String newValue) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".info." + var)) {
            config.set("users." + player + ".info." + var, newValue);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removePlayerInfoVar(String player, String var) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".info." + var)) {
            config.set("users." + player + ".info." + var, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void addPlayerWorld(String player, String world) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player)) {
            if (!config.isSet("users." + player + ".worlds." + world)) {
                config.set("users." + player + ".worlds." + world + "prefix", "");
                config.set("users." + player + ".worlds." + world + "suffix", "");

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}
            }
        }
    }

    public void editPlayerWorldName(String player, String oldWorld, String newWorld) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".worlds." + oldWorld)) {
            for (Map.Entry entry : config.getConfigurationSection("users." + player + ".worlds." + oldWorld).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("users." + player + ".worlds." + newWorld + "." + entry.getKey(), entry.getValue());
            }

            config.set("users." + player + ".worlds." + oldWorld, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removePlayerWorld(String player, String world) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player)) {
            if (config.isSet("users." + player + ".worlds." + world)) {
                config.set("users." + player + ".worlds." + world, null);

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}
            }
        }
    }

    public void addPlayerWorldVar(String player, String world, String var, String value) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".worlds." + world)) {
            config.set("users." + player + ".worlds." + world + "." + var, value);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editPlayerWorldVar(String player, String world, String oldVar, String newVar) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".worlds." + world + "." + oldVar)) {
            for (Map.Entry entry : config.getConfigurationSection("users." + player + ".worlds." + world + "." + oldVar).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("users." + player + ".worlds." + world + "." + newVar + "." + entry.getKey(), entry.getValue());
            }

            config.set("users." + player + ".worlds." + world + "." + oldVar, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editPlayerWorldValue(String player, String world, String var, String newValue) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".worlds." + world + "." + var)) {
            config.set("users." + player + ".worlds." + world + "." + var, newValue);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removePlayerWorldVar(String player, String world, String var) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("users." + player + ".worlds." + world + "." + var)) {
            config.set("users." + player + ".worlds." + world + "." + var, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    // Group Info
    public void addGroup(String group) {
        YamlConfiguration config = plugin.mIConfig;

        if (!config.isSet("groups." + group)) {
            config.set("groups." + group + ".info.prefix", "");
            config.set("groups." + group + ".info.suffix", "");

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editGroupName(String oldGroup, String newGroup) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + oldGroup)) {
            for (Map.Entry entry : config.getConfigurationSection("groups." + oldGroup).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("groups." + newGroup + "." + entry.getKey(), entry.getValue());
            }

            config.set("groups." + oldGroup, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removeGroup(String group) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group)) {
            config.set("groups." + group, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void addGroupInfoVar(String group, String var, String value) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group)) {
            config.set("groups." + group + ".info." + var, value);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editGroupInfoVar(String group, String oldVar, String newVar) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".info." + oldVar)) {
            for (Map.Entry entry : config.getConfigurationSection("groups." + group + ".info." + oldVar).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("groups." + group + ".info." + newVar + "." + entry.getKey(), entry.getValue());
            }

            config.set("groups." + group + ".info." + oldVar, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editGroupInfoValue(String group, String var, String newValue) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".info." + var)) {
            config.set("groups." + group + ".info." + var, newValue);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removeGroupInfoVar(String group, String var) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".info." + var)) {
            config.set("groups." + group + ".info." + var, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void addGroupWorld(String group, String world) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group)) {
            if (!config.isSet("groups." + group + ".worlds." + world)) {
                config.set("groups." + group + ".worlds." + world, "");

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}
            }
        }
    }

    public void editGroupWorldName(String group, String oldWorld, String newWorld) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".worlds." + oldWorld)) {
            for (Map.Entry entry : config.getConfigurationSection("groups." + group + ".worlds." + oldWorld).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("groups." + group + ".worlds." + newWorld + "." + entry.getKey(), entry.getValue());
            }

            config.set("groups." + group + ".worlds." + oldWorld, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removeGroupWorld(String group, String world) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group)) {
            if (config.isSet("groups." + group + ".worlds." + world)) {
                config.set("groups." + group + ".worlds." + world, null);

                try {
                    config.save(plugin.mIConfigF);
                } catch (IOException ignored) {}
            }
        }
    }

    public void addGroupWorldVar(String group, String world, String var, String value) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".worlds." + world)) {
            config.set("groups." + group + ".worlds." + world + "." + var, value);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editGroupWorldVar(String group, String world, String oldVar, String newVar) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".worlds." + world + "." + oldVar)) {
            for (Map.Entry entry : config.getConfigurationSection("groups." + group + ".worlds." + world + "." + oldVar).getValues(true).entrySet()) {
                if (entry.getValue() instanceof MemorySection)
                    continue;

                config.set("groups." + group + ".worlds." + world + "." + newVar + "." + entry.getKey(), entry.getValue());
            }

            config.set("groups." + group + ".worlds." + world + "." + oldVar, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void editGroupWorldValue(String group, String world, String var, String newValue) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".worlds." + world + "." + var)) {
            config.set("groups." + group + ".worlds." + world + "." + var, newValue);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void removeGroupWorldVar(String group, String world, String var) {
        YamlConfiguration config = plugin.mIConfig;

        if (config.isSet("groups." + group + ".worlds." + world + "." + var)) {
            config.set("groups." + group + ".worlds." + world + "." + var, null);

            try {
                 config.save(plugin.mIConfigF);
            } catch (IOException ignored) {}
        }
    }
}
