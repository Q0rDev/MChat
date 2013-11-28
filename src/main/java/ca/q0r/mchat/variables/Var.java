package ca.q0r.mchat.variables;

import ca.q0r.mchat.types.IndicatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Var class used for all Variable processing.
 */
public abstract class Var {
    /**
     * Variable Processor.
     *
     * @param obj Object used to parse Variable. (Usually Bukkit Player object).
     * @return Variable's Value after processing.
     */
    @Keys
    @Meta
    public abstract Object getValue(Object obj);

    /**
     * Used to define Variable's Keys.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Keys {
        /**
         * Variable's Keys.
         *
         * @return Array of Keys used to define this Variable.
         */
        String[] keys() default {};
    }

    /**
     * Used to define the Metadata used with this Variable.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {
        /**
         * Indicator Type of Variable.
         *
         * @return Indicator Type to be used with this Variable.
         */
        IndicatorType type() default IndicatorType.MISC_VAR;

        /**
         * Resolve Priority of Variable.
         *
         * @return Resolve Priority to be used with this Variable.
         */
        ResolvePriority priority() default ResolvePriority.NORMAL;
    }
}