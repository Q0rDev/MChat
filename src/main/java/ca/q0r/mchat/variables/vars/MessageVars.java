package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.variables.ResolvePriority;
import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;

import java.util.UUID;

public class MessageVars {
    public static void addVars() {
        VariableManager.addVar(new MessageVar());
    }

    public static class MessageVar extends Var {
        @Keys(keys = {"message", "msg", "m"})
        @Meta(type = IndicatorType.MISC_VAR,
                priority = ResolvePriority.LAST)
        public String getValue(UUID uuid) {
            return null;
        }
    }
}