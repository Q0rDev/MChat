package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.locale.LocaleType;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.util.Messaging;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HeroesVars {
    private static CharacterManager manager;

    public static void addVars(CharacterManager mgr) {
        manager = mgr;

        VariableManager.addVars(new Var[]{new ClassVar(), new ExpVar(), new ExpBarVar(), new HealthVar(),
                new HealthBarVar(), new LevelVar(), new ManaVar(), new ManaBarVar(), new MasteredVar(),
                new PartyVar(), new SecClassVar(), new SecExpVar(), new SecExpBarVar(), new SecLevelVar()});
    }

    private static class ClassVar extends Var {
        @Keys(keys = {"HClass", "HClass"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getHeroClass().getName();
            }

            return "";
        }
    }

    private static class ExpVar extends Var {
        @Keys(keys = {"HExp", "HEx"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return String.valueOf(hero.getExperience(hero.getHeroClass()));
            }

            return "";
        }
    }

    private static class ExpBarVar extends Var {
        @Keys(keys = {"HEBar", "HEb"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return Messaging.createExperienceBar(hero, hero.getHeroClass());
            }

            return "";
        }
    }

    private static class HealthVar extends Var {
        @Keys(keys = {"HHealth", "HH"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return String.valueOf(hero.getPlayer().getHealth());
            }

            return "";
        }
    }

    private static class HealthBarVar extends Var {
        @Keys(keys = {"HHBar", "HHB"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return Messaging.createHealthBar(hero.getPlayer().getHealth(), hero.getPlayer().getMaxHealth());
            }

            return "";
        }
    }

    private static class LevelVar extends Var {
        @Keys(keys = {"HLevel", "HL"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return String.valueOf(hero.getLevel());
            }

            return "";
        }
    }

    private static class ManaVar extends Var {
        @Keys(keys = {"HMana", "HMn"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return String.valueOf(hero.getMana());
            }

            return "";
        }
    }

    private static class ManaBarVar extends Var {
        @Keys(keys = {"HMBar", "HMb"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return Messaging.createManaBar(hero.getMana(), hero.getMaxMana());
            }

            return "";
        }
    }

    private static class MasteredVar extends Var {
        @Keys(keys = {"HMastered", "HMa"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.isMaster(hero.getHeroClass()) && (hero.getSecondClass() == null || hero.isMaster(hero.getSecondClass()))
                        ? LocaleType.MESSAGE_HEROES_TRUE.getVal() : LocaleType.MESSAGE_HEROES_FALSE.getVal();
            }

            return "";
        }
    }

    private static class PartyVar extends Var {
        @Keys(keys = {"HParty", "HPa"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getParty() != null ? hero.getParty().toString() : "";
            }

            return "";
        }
    }

    private static class SecClassVar extends Var {
        @Keys(keys = {"HSecClass", "HSC"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getSecondClass() != null ? hero.getSecondClass().getName() : "";
            }

            return "";
        }
    }

    private static class SecExpVar extends Var {
        @Keys(keys = {"HSecExp", "HSEx"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getSecondClass() != null ? String.valueOf(hero.getExperience(hero.getSecondClass())) : "";
            }

            return "";
        }
    }

    private static class SecExpBarVar extends Var {
        @Keys(keys = {"HSecEBar", "HSEb"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getSecondClass() != null ? Messaging.createExperienceBar(hero, hero.getSecondClass()) : "";
            }

            return "";
        }
    }

    private static class SecLevelVar extends Var {
        @Keys(keys = {"HSecLevel", "HSL"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                Hero hero = manager.getHero(player);
                return hero.getSecondClass() != null ? String.valueOf(hero.getLevel(hero.getSecondClass())) : "";
            }

            return "";
        }
    }
}