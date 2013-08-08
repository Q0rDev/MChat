package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.variables.MultiVariable;
import com.miraclem4n.mchat.variables.Var;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class TownyVars extends MultiVariable {
    public TownyVars(Resident resident) {
        try {
            Town town = resident.getTown();

            addVar(new Var(new String[]{"town"}, !resident.hasTown() ? "" : town.getName()));
            addVar(new Var(new String[]{"townname"}, !resident.hasTown() ? "" : TownyFormatter.getFormattedTownName(town)));
            addVar(new Var(new String[]{"townytitle"}, resident.getTitle()));
            addVar(new Var(new String[]{"townysurname"}, resident.getSurname()));
            addVar(new Var(new String[]{"townyresidentname"}, resident.getFormattedName()));
            addVar(new Var(new String[]{"townyprefix"}, resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident)));
            addVar(new Var(new String[]{"townynameprefix"}, TownyFormatter.getNamePrefix(resident)));
            addVar(new Var(new String[]{"townypostfix"}, resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident)));
            addVar(new Var(new String[]{"townynamepostfix"}, TownyFormatter.getNamePostfix(resident)));
            addVar(new Var(new String[]{"townynation"}, resident.hasTown() && town.hasNation() ? town.getNation().getName() : ""));
            addVar(new Var(new String[]{"townynationname"}, resident.hasTown() && town.hasNation() ? town.getNation().getFormattedName() : ""));
            addVar(new Var(new String[]{"townynationtag"}, resident.hasTown() && town.hasNation() ? town.getNation().getTag() : ""));
        } catch (Exception ignored) {}
    }
}
