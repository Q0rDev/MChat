package com.miraclem4n.mchat.variables;

public class Var extends Variable {
    private Object value;

    public Var(String[] vars, Object obj) {
        for (String var : vars) {
            addVar(var);
        }

        value = obj != null ? obj : "";
    }

    public Object getValue() {
        return value;
    }
}
