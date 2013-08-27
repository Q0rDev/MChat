package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.variables.VariableManager;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class TownyVars {
    public static void addVars(VariableManager mgr, Resident resident) {
        try {
            Town town = resident.getTown();

            mgr.addVars(new String[]{"town"}, !resident.hasTown() ? "" : town.getName());
            mgr.addVars(new String[]{"townname"}, !resident.hasTown() ? "" : TownyFormatter.getFormattedTownName(town));
            mgr.addVars(new String[]{"townytitle"}, resident.getTitle());
            mgr.addVars(new String[]{"townysurname"}, resident.getSurname());
            mgr.addVars(new String[]{"townyresidentname"}, resident.getFormattedName());
            mgr.addVars(new String[]{"townyprefix"}, resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident));
            mgr.addVars(new String[]{"townynameprefix"}, TownyFormatter.getNamePrefix(resident));
            mgr.addVars(new String[]{"townypostfix"}, resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident));
            mgr.addVars(new String[]{"townynamepostfix"}, TownyFormatter.getNamePostfix(resident));
            mgr.addVars(new String[]{"townynation"}, resident.hasTown() && town.hasNation() ? town.getNation().getName() : "");
            mgr.addVars(new String[]{"townynationname"}, resident.hasTown() && town.hasNation() ? town.getNation().getFormattedName() : "");
            mgr.addVars(new String[]{"townynationtag"}, resident.hasTown() && town.hasNation() ? town.getNation().getTag() : "");
        } catch (Exception ignored) {}
    }
}
