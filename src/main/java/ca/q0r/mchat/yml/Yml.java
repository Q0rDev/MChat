package ca.q0r.mchat.yml;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Parent class for all YML configs.
 */
public abstract class Yml {
    /** YML Config */ public YamlConfiguration config;
    /** Config File */ public File file;
    /** Value is true when Config has been altered. */ public Boolean changed;

    /**
     * Used to instantiate Class.
     * @param filez YML Config File to be loaded.
     * @param header Header Comment.
     */
    public Yml(File filez, String header) {
        file = filez;
        changed = false;

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header(header);
    }

    /**
     * Used to load Default Config values.
     */
    public abstract void loadDefaults();

    /**
     * Sets key / value pair to config.
     * @param key Key to be set.
     * @param obj Value to be set.
     */
    public void set(String key, Object obj) {
        config.set(key, obj);

        changed = true;
    }

    /**
     * Writes config to disk.
     * @return Write result.
     */
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

    /**
     * YML Config.
     * @return YML File loaded from Disk.
     */
    public YamlConfiguration getConfig() {
        return config;
    }

    /**
     * Check if Option is Set, if not set Value.
     * @param option Key to check.
     * @param defValue Value to set if Key is not found.
     */
    public void checkOption(String option, Object defValue) {
        if (!config.isSet(option)) {
            set(option, defValue);
        }
    }

    /**
     * Edit Key.
     * @param oldOption Key to be changed.
     * @param newOption Key to change to if found.
     */
    public void editOption(String oldOption, String newOption) {
        if (config.isSet(oldOption)) {
            set(newOption, config.get(oldOption));
            set(oldOption, null);
        }
    }

    /**
     * Remove Key / Value.
     * @param option Key to remove if found.
     */
    public void removeOption(String option) {
        if (config.isSet(option)) {
            set(option, null);
        }
    }
}
