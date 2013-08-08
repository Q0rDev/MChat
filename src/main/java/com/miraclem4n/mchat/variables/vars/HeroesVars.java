package com.miraclem4n.mchat.variables.vars;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.util.Messaging;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.variables.MultiVariable;
import com.miraclem4n.mchat.variables.Var;

public class HeroesVars extends MultiVariable {
    public HeroesVars(Hero hero) {
        addVar(new Var(new String[]{"HClass","HClass"}, hero.getHeroClass().getName()));
        addVar(new Var(new String[]{"HExp","HEx"}, String.valueOf(hero.getExperience(hero.getHeroClass()))));
        addVar(new Var(new String[]{"HEBar","HEb"}, Messaging.createExperienceBar(hero, hero.getHeroClass())));
        addVar(new Var(new String[]{"HHealth","HH"}, String.valueOf(hero.getPlayer().getHealth())));
        addVar(new Var(new String[]{"HHBar","HHB"}, Messaging.createHealthBar(hero.getPlayer().getHealth(), hero.getPlayer().getMaxHealth())));
        addVar(new Var(new String[]{"HLevel","HL"}, String.valueOf(hero.getLevel())));
        addVar(new Var(new String[]{"HMana","HMn"}, String.valueOf(hero.getMana())));
        addVar(new Var(new String[]{"HMBar","HMb"}, Messaging.createManaBar(hero.getMana(), hero.getMaxMana())));
        addVar(new Var(new String[]{"HMastered","HMa"}, hero.isMaster(hero.getHeroClass()) && (hero.getSecondClass() == null || hero.isMaster(hero.getSecondClass()))
                ? LocaleType.MESSAGE_HEROES_TRUE.getVal() : LocaleType.MESSAGE_HEROES_FALSE.getVal()));
        addVar(new Var(new String[]{"HParty","HPa"}, hero.getParty() != null ? hero.getParty().toString() : ""));
        addVar(new Var(new String[]{"HSecClass","HSC"}, hero.getSecondClass() != null ? hero.getSecondClass().getName() : ""));
        addVar(new Var(new String[]{"HSecExp","HSEx"}, hero.getSecondClass() != null ? String.valueOf(hero.getExperience(hero.getSecondClass())) : ""));
        addVar(new Var(new String[]{"HSecEBar","HSEb"}, hero.getSecondClass() != null ? Messaging.createExperienceBar(hero, hero.getSecondClass()) : ""));
        addVar(new Var(new String[]{"HSecLevel","HSL"}, hero.getSecondClass() != null ? String.valueOf(hero.getLevel(hero.getSecondClass())) : ""));
    }
}
