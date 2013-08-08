package com.miraclem4n.mchat.variables;

import java.util.ArrayList;

public abstract class MultiVariable {
   private ArrayList<Var> variables;

    public MultiVariable() {
        variables = new ArrayList<Var>();
    }

    public ArrayList<Var> getVars() {
        return variables;
    }

    public void addVar(Var var) {
        variables.add(var);
    }
}