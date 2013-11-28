package ca.q0r.mchat.types;

import ca.q0r.mchat.yml.config.ConfigType;

/**
 * Enum for Different Indicator Types.
 */
public enum IndicatorType {
    /** Miscellaneous Variable Type. */ MISC_VAR(ConfigType.MCHAT_VAR_INDICATOR),
    /** Custom Variable Type. */ CUS_VAR(ConfigType.MCHAT_CUS_VAR_INDICATOR),
    /** Locale Variable Type. */ LOCALE_VAR(ConfigType.MCHAT_LOCALE_VAR_INDICATOR);

    private ConfigType type;

    private IndicatorType(ConfigType type) {
        this.type = type;
    }

    /**
     * Indicator Value.
     *
     * @return Value of Indicator Type.
     */
    public String getValue() {
        return type.getString();
    }
}