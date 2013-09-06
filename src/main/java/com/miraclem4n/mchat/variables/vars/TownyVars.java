package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.variables.ResolvePriority;
import com.miraclem4n.mchat.variables.VariableManager;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

public class TownyVars {
    public static void addVars(VariableManager mgr, Resident resident) {
        try {
            Town town = resident.getTown();

            mgr.addVars(new String[]{"town"}, !resident.hasTown() ? "" : town.getName(), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townname"}, !resident.hasTown() ? "" : TownyFormatter.getFormattedTownName(town), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townytitle"}, resident.getTitle(), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townysurname"}, resident.getSurname(), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townyresidentname"}, resident.getFormattedName(), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townyprefix"}, resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townynameprefix"}, TownyFormatter.getNamePrefix(resident), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townypostfix"}, resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townynamepostfix"}, TownyFormatter.getNamePostfix(resident), ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townynation"}, resident.hasTown() && town.hasNation() ? town.getNation().getName() : "", ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townynationname"}, resident.hasTown() && town.hasNation() ? town.getNation().getFormattedName() : "", ResolvePriority.NORMAL);
            mgr.addVars(new String[]{"townynationtag"}, resident.hasTown() && town.hasNation() ? town.getNation().getTag() : "", ResolvePriority.NORMAL);
        } catch (Exception ignored) {}
    }
}
