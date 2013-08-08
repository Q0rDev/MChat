package com.miraclem4n.mchat.variables.vars;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Reader;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.variables.MultiVariable;
import com.miraclem4n.mchat.variables.Var;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerVars extends MultiVariable {
    public PlayerVars(Player player, String pName, String world) {
        Object prefix = Reader.getRawPrefix(pName, InfoType.USER, world);
        Object suffix = Reader.getRawSuffix(pName, InfoType.USER, world);
        Object group = Reader.getRawGroup(pName, InfoType.USER, world);
        List<Object> groups = Reader.getRawGroups(pName, InfoType.USER, world);

        Integer grpInt = 0;

        for (Object obj : groups) {
            addVar(new Var(new String[]{"groups" + grpInt,"gs" + grpInt}, obj));
            grpInt++;
        }
        
        addVar(new Var(new String[]{"group","g"}, group));
        addVar(new Var(new String[]{"prefix","p"}, prefix));
        addVar(new Var(new String[]{"suffix","s"}, suffix));
        addVar(new Var(new String[]{"mname","mn"}, Reader.getMName(pName)));
        addVar(new Var(new String[]{"pname","n"}, pName));
        addVar(new Var(new String[]{"displayname","dname","dn"}, player.getDisplayName()));
        addVar(new Var(new String[]{"experience","exp"}, String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10)));
        addVar(new Var(new String[]{"experiencebar","expb","ebar"}, API.createBasicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10)));
        addVar(new Var(new String[]{"gamemode","gm"}, player.getGameMode() != null && player.getGameMode().name() != null ? player.getGameMode().name() : ""));
        addVar(new Var(new String[]{"health","h"}, String.valueOf(player.getHealth())));
        addVar(new Var(new String[]{"healthbar","hb"}, API.createHealthBar(player)));
        addVar(new Var(new String[]{"hunger"}, String.valueOf(player.getFoodLevel())));
        addVar(new Var(new String[]{"hungerbar","hub"}, API.createBasicBar(player.getFoodLevel(), 20, 10)));
        addVar(new Var(new String[]{"level","l"}, String.valueOf(player.getLevel())));
        addVar(new Var(new String[]{"location","loc"}, "X: " + player.getLocation().getX() + ", " + "Y: " + player.getLocation().getY() + ", " + "Z: " + player.getLocation().getZ()));
        addVar(new Var(new String[]{"totalexp","texp","te"}, String.valueOf(player.getTotalExperience())));
        addVar(new Var(new String[]{"world","w"}, player.getWorld().getName()));
        addVar(new Var(new String[]{"Groupname","Gname","G"}, Reader.getGroupName(group.toString())));
        addVar(new Var(new String[]{"Worldname","Wname","W"}, Reader.getWorldName(player.getWorld().getName())));
    }
}
