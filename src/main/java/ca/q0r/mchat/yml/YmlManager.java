package ca.q0r.mchat.yml;

import ca.q0r.mchat.yml.censor.CensorYml;
import ca.q0r.mchat.yml.config.ConfigYml;
import ca.q0r.mchat.yml.info.InfoYml;
import ca.q0r.mchat.yml.locale.LocaleYml;

/**
 * Used to Manage all YML Configs.
 */
public class YmlManager {
    static CensorYml censorYml;
    static ConfigYml configYml;
    static InfoYml infoYml;
    static LocaleYml localeYml;

    /**
     * Class Initializer.
     */
    public static void initialize() {
        censorYml = new CensorYml();
        censorYml.loadDefaults();

        configYml = new ConfigYml();
        configYml.loadDefaults();

        infoYml = new InfoYml();
        infoYml.loadDefaults();

        localeYml = new LocaleYml();
        localeYml.loadDefaults();
    }

    /**
     * YML retriever.
     * @param type Type of Config to get.
     * @return YML Config.
     */
    public static Yml getYml(YmlType type) {
        if (type == YmlType.CENSOR_YML) {
            return censorYml;
        } else if (type == YmlType.CONFIG_YML) {
            return configYml;
        } else if (type == YmlType.INFO_YML) {
            return infoYml;
        } else if (type == YmlType.LOCALE_YML) {
            return localeYml;
        }

        return null;
    }

    /**
     * YML Reloader.
     * @param type Type of Config to reload.
     */
    public static void reloadYml(YmlType type) {
        if (type == YmlType.CENSOR_YML) {
            censorYml = new CensorYml();
            censorYml.loadDefaults();
        } else if (type == YmlType.CONFIG_YML) {
            configYml = new ConfigYml();
            configYml.loadDefaults();
        } else if (type == YmlType.INFO_YML) {
            infoYml = new InfoYml();
            infoYml.loadDefaults();
        } else if (type == YmlType.LOCALE_YML) {
            localeYml = new LocaleYml();
            localeYml.loadDefaults();
        }
    }

    /**
     * YML Unloader. Unloads all configs.
     */
    public static void unload() {
        censorYml = null;
        configYml = null;
        infoYml = null;
        localeYml = null;
    }
}
