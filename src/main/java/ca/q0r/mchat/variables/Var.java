package ca.q0r.mchat.variables;

import ca.q0r.mchat.types.IndicatorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Var {
    @Keys ( keys = {} )
    @Meta ( type = IndicatorType.MISC_VAR, priority = ResolvePriority.NORMAL )
    public abstract Object getValue(Object obj);

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Keys {
        String[] keys();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {
        IndicatorType type();
        ResolvePriority priority();
    }
}
