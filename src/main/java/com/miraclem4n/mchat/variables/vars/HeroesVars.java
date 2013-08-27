package com.miraclem4n.mchat.variables.vars;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.util.Messaging;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.variables.VariableManager;

public class HeroesVars {
    public static void addVars(VariableManager mgr, Hero hero) {
        mgr.addVars(new String[]{"HClass","HClass"}, hero.getHeroClass().getName());
        mgr.addVars(new String[]{"HExp","HEx"}, String.valueOf(hero.getExperience(hero.getHeroClass())));
        mgr.addVars(new String[]{"HEBar","HEb"}, Messaging.createExperienceBar(hero, hero.getHeroClass()));
        mgr.addVars(new String[]{"HHealth","HH"}, String.valueOf(hero.getPlayer().getHealth()));
        mgr.addVars(new String[]{"HHBar","HHB"}, Messaging.createHealthBar(hero.getPlayer().getHealth(), hero.getPlayer().getMaxHealth()));
        mgr.addVars(new String[]{"HLevel","HL"}, String.valueOf(hero.getLevel()));
        mgr.addVars(new String[]{"HMana","HMn"}, String.valueOf(hero.getMana()));
        mgr.addVars(new String[]{"HMBar","HMb"}, Messaging.createManaBar(hero.getMana(), hero.getMaxMana()));
        mgr.addVars(new String[]{"HMastered","HMa"}, hero.isMaster(hero.getHeroClass()) && (hero.getSecondClass() == null || hero.isMaster(hero.getSecondClass()))
                ? LocaleType.MESSAGE_HEROES_TRUE.getVal() : LocaleType.MESSAGE_HEROES_FALSE.getVal());
        mgr.addVars(new String[]{"HParty","HPa"}, hero.getParty() != null ? hero.getParty().toString() : "");
        mgr.addVars(new String[]{"HSecClass","HSC"}, hero.getSecondClass() != null ? hero.getSecondClass().getName() : "");
        mgr.addVars(new String[]{"HSecExp","HSEx"}, hero.getSecondClass() != null ? String.valueOf(hero.getExperience(hero.getSecondClass())) : "");
        mgr.addVars(new String[]{"HSecEBar","HSEb"}, hero.getSecondClass() != null ? Messaging.createExperienceBar(hero, hero.getSecondClass()) : "");
        mgr.addVars(new String[]{"HSecLevel","HSL"}, hero.getSecondClass() != null ? String.valueOf(hero.getLevel(hero.getSecondClass())) : "");
    }
}
