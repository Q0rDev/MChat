package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Reader;
import com.miraclem4n.mchat.types.InfoType;
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
            mgr.addVars(new String[]{"groups" + grpInt,"gs" + grpInt}, obj);
            grpInt++;
        }
        
        mgr.addVars(new String[]{"group","g"}, group);
        mgr.addVars(new String[]{"prefix","p"}, prefix);
        mgr.addVars(new String[]{"suffix","s"}, suffix);
        mgr.addVars(new String[]{"mname","mn"}, Reader.getMName(pName));
        mgr.addVars(new String[]{"pname","n"}, pName);
        mgr.addVars(new String[]{"displayname","dname","dn"}, player.getDisplayName());
        mgr.addVars(new String[]{"experience","exp"}, String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10));
        mgr.addVars(new String[]{"experiencebar","expb","ebar"}, API.createBasicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10));
        mgr.addVars(new String[]{"gamemode","gm"}, player.getGameMode() != null && player.getGameMode().name() != null ? player.getGameMode().name() : "");
        mgr.addVars(new String[]{"health","h"}, String.valueOf(player.getHealth()));
        mgr.addVars(new String[]{"healthbar","hb"}, API.createHealthBar(player));
        mgr.addVars(new String[]{"hunger"}, String.valueOf(player.getFoodLevel()));
        mgr.addVars(new String[]{"hungerbar","hub"}, API.createBasicBar(player.getFoodLevel(), 20, 10));
        mgr.addVars(new String[]{"level","l"}, String.valueOf(player.getLevel()));
        mgr.addVars(new String[]{"location","loc"}, "X: " + player.getLocation().getX() + ", " + "Y: " + player.getLocation().getY() + ", " + "Z: " + player.getLocation().getZ());
        mgr.addVars(new String[]{"totalexp","texp","te"}, String.valueOf(player.getTotalExperience()));
        mgr.addVars(new String[]{"world","w"}, player.getWorld().getName());
        mgr.addVars(new String[]{"Groupname","Gname","G"}, Reader.getGroupName(group.toString()));
        mgr.addVars(new String[]{"Worldname","Wname","W"}, Reader.getWorldName(player.getWorld().getName()));
    }
}
