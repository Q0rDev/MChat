package com.miraclem4n.mchat.configs;

import com.miraclem4n.mchat.configs.censor.CensorYml;
import com.miraclem4n.mchat.configs.config.ConfigYml;
import com.miraclem4n.mchat.configs.info.InfoYml;
import com.miraclem4n.mchat.configs.locale.LocaleYml;

public class YmlManager {
    private static CensorYml censorYml;
    private static ConfigYml configYml;
    private static InfoYml infoYml;
    private static LocaleYml localeYml;

    public static void load() {
        censorYml = new CensorYml();
        configYml = new ConfigYml();
        infoYml = new InfoYml();
        localeYml = new LocaleYml();
    }

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

    public static void reloadYml(YmlType type) {
        if (type == YmlType.CENSOR_YML) {
            censorYml = new CensorYml();
        } else if (type == YmlType.CONFIG_YML) {
            configYml = new ConfigYml();
        } else if (type == YmlType.INFO_YML) {
            infoYml = new InfoYml();
        } else if (type == YmlType.LOCALE_YML) {
            localeYml = new LocaleYml();
        }
    }

    public static void unload() {
        censorYml = null;
        configYml = null;
        infoYml = null;
        localeYml = null;
    }
}
