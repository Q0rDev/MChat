package com.miraclem4n.mchat.variables;

import com.miraclem4n.mchat.types.IndicatorType;

import java.util.ArrayList;
import java.util.List;

public abstract class Variable {
    public List<String> variables;

    public Variable() {
        variables = new ArrayList<String>();
    }

    public List<String> getVariables() {
        return variables;
    }

    public void addVar(String var) {
        variables.add(IndicatorType.MISC_VAR.getValue() + var);
    }

    public abstract Object getValue();
}
