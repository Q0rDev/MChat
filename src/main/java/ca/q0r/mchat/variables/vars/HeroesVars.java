package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.configs.locale.LocaleType;
import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.util.Messaging;
import org.bukkit.entity.Player;

public class HeroesVars {
    private static CharacterManager manager;
    
    public static void addVars(CharacterManager mgr) {
         manager = mgr;
        
        VariableManager.addVars(new Var[]{new ClassVar(), new ExpVar(), new ExpBarVar(), new HealthVar(),
                new HealthBarVar(), new LevelVar(), new ManaVar(), new ManaBarVar(), new MasteredVar(),
                new PartyVar(), new SecClassVar(), new SecExpVar(), new SecExpBarVar(), new SecLevelVar()});
    }

    private static class ClassVar extends Var {
        @Keys ( keys = {"HClass","HClass"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getHeroClass().getName();
            }

            return "";
        }
    }

    private static class ExpVar extends Var {
        @Keys ( keys = {"HExp","HEx"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return String.valueOf(hero.getExperience(hero.getHeroClass()));
            }

            return "";
        }
    }

    private static class ExpBarVar extends Var {
        @Keys ( keys = {"HEBar","HEb"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return Messaging.createExperienceBar(hero, hero.getHeroClass());
            }

            return "";
        }
    }

    private static class HealthVar extends Var {
        @Keys ( keys = {"HHealth","HH"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return String.valueOf(hero.getPlayer().getHealth());
            }

            return "";
        }
    }

    private static class HealthBarVar extends Var {
        @Keys ( keys = {"HHBar","HHB"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return Messaging.createHealthBar(hero.getPlayer().getHealth(), hero.getPlayer().getMaxHealth());
            }

            return "";
        }
    }

    private static class LevelVar extends Var {
        @Keys ( keys = {"HLevel","HL"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return String.valueOf(hero.getLevel());
            }

            return "";
        }
    }

    private static class ManaVar extends Var {
        @Keys ( keys = {"HMana","HMn"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return String.valueOf(hero.getMana());
            }

            return "";
        }
    }

    private static class ManaBarVar extends Var {
        @Keys ( keys = {"HMBar","HMb"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return Messaging.createManaBar(hero.getMana(), hero.getMaxMana());
            }

            return "";
        }
    }

    private static class MasteredVar extends Var {
        @Keys ( keys = {"HMastered","HMa"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.isMaster(hero.getHeroClass()) && (hero.getSecondClass() == null || hero.isMaster(hero.getSecondClass()))
                        ? LocaleType.MESSAGE_HEROES_TRUE.getVal() : LocaleType.MESSAGE_HEROES_FALSE.getVal();
            }

            return "";
        }
    }

    private static class PartyVar extends Var {
        @Keys ( keys = {"HParty","HPa"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getParty() != null ? hero.getParty().toString() : "";
            }

            return "";
        }
    }

    private static class SecClassVar extends Var {
        @Keys ( keys = {"HSecClass","HSC"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getSecondClass() != null ? hero.getSecondClass().getName() : "";
            }

            return "";
        }
    }

    private static class SecExpVar extends Var {
        @Keys ( keys = {"HSecExp","HSEx"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getSecondClass() != null ? String.valueOf(hero.getExperience(hero.getSecondClass())) : "";
            }

            return "";
        }
    }

    private static class SecExpBarVar extends Var {
        @Keys ( keys = {"HSecEBar","HSEb"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getSecondClass() != null ? Messaging.createExperienceBar(hero, hero.getSecondClass()) : "";
            }

            return "";
        }
    }

    private static class SecLevelVar extends Var {
        @Keys ( keys = {"HSecLevel","HSL"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                Hero hero = manager.getHero((Player) obj);
                return hero.getSecondClass() != null ? String.valueOf(hero.getLevel(hero.getSecondClass())) : "";
            }

            return "";
        }
    }
}
