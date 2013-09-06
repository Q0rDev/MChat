package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Reader;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.variables.ResolvePriority;
import com.miraclem4n.mchat.variables.VariableManager;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerVars{
    public static void addVars(VariableManager mgr, Player player, String pName, String world) {
        Object prefix = Reader.getRawPrefix(pName, InfoType.USER, world);
        Object suffix = Reader.getRawSuffix(pName, InfoType.USER, world);
        Object group = Reader.getRawGroup(pName, InfoType.USER, world);
        List<Object> groups = Reader.getRawGroups(pName, InfoType.USER, world);

        Integer grpInt = 0;

        for (Object obj : groups) {
            mgr.addVars(new String[]{"groups" + grpInt,"gs" + grpInt}, obj, ResolvePriority.NORMAL);
            grpInt++;
        }
        
        mgr.addVars(new String[]{"group","g"}, group, ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"prefix","p"}, prefix, ResolvePriority.NORMAL);;
        mgr.addVars(new String[]{"suffix","s"}, suffix, ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"mname","mn"}, Reader.getMName(pName), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"pname","n"}, pName, ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"displayname","dname","dn"}, player.getDisplayName(), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"experience","exp"}, String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"experiencebar","expb","ebar"}, API.createBasicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"gamemode","gm"}, player.getGameMode() != null && player.getGameMode().name() != null ? player.getGameMode().name() : "", ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"health","h"}, String.valueOf(player.getHealth()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"healthbar","hb"}, API.createHealthBar(player), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"hunger"}, String.valueOf(player.getFoodLevel()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"hungerbar","hub"}, API.createBasicBar(player.getFoodLevel(), 20, 10), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"level","l"}, String.valueOf(player.getLevel()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"location","loc"}, "X: " + player.getLocation().getX() + ", " + "Y: " + player.getLocation().getY() + ", " + "Z: " + player.getLocation().getZ(), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"totalexp","texp","te"}, String.valueOf(player.getTotalExperience()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"world","w"}, player.getWorld().getName(), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"Groupname","Gname","G"}, Reader.getGroupName(group.toString()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"Worldname","Wname","W"}, Reader.getWorldName(player.getWorld().getName()), ResolvePriority.NORMAL);
    }
}
