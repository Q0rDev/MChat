package com.miraclem4n.mchat.configs;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class Yml {
    public YamlConfiguration config;
    public File file;
    public Boolean changed;

    public Yml(File filez, String header) {
        file = filez;
        changed = false;

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header(header);
    }

    public abstract void loadDefaults();

    public void set(String key, Object obj) {
        config.set(key, obj);

        changed = true;
    }

    public Boolean save() {
        if (!changed) {
            return false;
        }

        try {
            config.save(file);
            changed = false;
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void checkOption(String option, Object defValue) {
        if (!config.isSet(option)) {
            set(option, defValue);
        }
    }

    public void editOption(String oldOption, String newOption) {
        if (config.isSet(oldOption)) {
            set(newOption, config.get(oldOption));
            set(oldOption, null);
        }
    }

    public void removeOption(String option) {
        if (config.isSet(option)) {
            set(option, null);
        }
    }
}
