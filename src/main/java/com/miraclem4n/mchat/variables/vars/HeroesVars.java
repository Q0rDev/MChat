package com.miraclem4n.mchat.variables.vars;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.util.Messaging;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.variables.ResolvePriority;
import com.miraclem4n.mchat.variables.VariableManager;

public class HeroesVars {
    public static void addVars(VariableManager mgr, Hero hero) {
        mgr.addVars(new String[]{"HClass","HClass"}, hero.getHeroClass().getName(), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HExp","HEx"}, String.valueOf(hero.getExperience(hero.getHeroClass())), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HEBar","HEb"}, Messaging.createExperienceBar(hero, hero.getHeroClass()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HHealth","HH"}, String.valueOf(hero.getPlayer().getHealth()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HHBar","HHB"}, Messaging.createHealthBar(hero.getPlayer().getHealth(), hero.getPlayer().getMaxHealth()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HLevel","HL"}, String.valueOf(hero.getLevel()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HMana","HMn"}, String.valueOf(hero.getMana()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HMBar","HMb"}, Messaging.createManaBar(hero.getMana(), hero.getMaxMana()), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HMastered","HMa"}, hero.isMaster(hero.getHeroClass()) && (hero.getSecondClass() == null || hero.isMaster(hero.getSecondClass()))
                ? LocaleType.MESSAGE_HEROES_TRUE.getVal() : LocaleType.MESSAGE_HEROES_FALSE.getVal(), ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HParty","HPa"}, hero.getParty() != null ? hero.getParty().toString() : "", ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HSecClass","HSC"}, hero.getSecondClass() != null ? hero.getSecondClass().getName() : "", ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HSecExp","HSEx"}, hero.getSecondClass() != null ? String.valueOf(hero.getExperience(hero.getSecondClass())) : "", ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HSecEBar","HSEb"}, hero.getSecondClass() != null ? Messaging.createExperienceBar(hero, hero.getSecondClass()) : "", ResolvePriority.NORMAL);
        mgr.addVars(new String[]{"HSecLevel","HSL"}, hero.getSecondClass() != null ? String.valueOf(hero.getLevel(hero.getSecondClass())) : "", ResolvePriority.NORMAL);
    }
}
