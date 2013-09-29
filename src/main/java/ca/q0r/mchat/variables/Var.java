package ca.q0r.mchat.variables;

import ca.q0r.mchat.types.IndicatorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Var class used for all Variable processing.
 */
public abstract class Var {
    /**
     * Variable Processor.
     * @param obj Ojbect used to parse Variable. (Usually Bukkit Player object).
     * @return Variable's Value after processing.
     */
    @Keys ( keys = {} )
    @Meta ( type = IndicatorType.MISC_VAR, priority = ResolvePriority.NORMAL )
    public abstract Object getValue(Object obj);

    /**
     * Used to define Variable's Keys.
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Keys {
        /**
         * Variable's Keys.
         * @return Array of Keys used to define this Variable.
         */
        String[] keys();
    }

    /**
     * Used to define the Metadata used with this Variable.
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {
        /**
         * Indicator Type of Variable.
         * @return Indicator Type to be used with this Variable.
         */
        IndicatorType type();

        /**
         * Resolve Priority of Variable.
         * @return Resolve Priority to be used with this Variable.
         */
        ResolvePriority priority();
    }
}
