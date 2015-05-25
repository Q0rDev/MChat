package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Reader;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.variables.ResolvePriority;
import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.config.ConfigType;
import ca.q0r.mchat.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PlayerVars {
    public static void addVars() {
        VariableManager.addVars(new Var[]{new GroupVar(), new PrefixVar(), new SuffixVar(), new MNameVar(), new PNameVar(),
                new DNameVar(), new ExpVar(), new ExpBarVar(), new GameModeVar(), new HealthVar(), new HealthBarVar(),
                new HungerVar(), new HungerBarVar(), new LevelVar(), new LocVar(), new TotalExpVar(), new WorldVar(),
                new WorldNameVar(), new GroupNameVar(), new MNameFormat(), new TimeFormat(), new SpyFormat(), new DistanceType()});
    }

    private static class GroupVar extends Var {
        @Keys(keys = {"group", "g"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getRawGroup(uuid, player.getWorld().getName());
            }

            return "";
        }
    }

    private static class PrefixVar extends Var {
        @Keys(keys = {"prefix", "p"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getRawPrefix(uuid, player.getWorld().getName());
            }

            return "";
        }
    }

    private static class SuffixVar extends Var {
        @Keys(keys = {"suffix", "s"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getRawSuffix(uuid, player.getWorld().getName());
            }

            return "";
        }
    }

    private static class MNameVar extends Var {
        @Keys(keys = {"mname", "mn"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getMName(uuid);
            }

            return "";
        }
    }

    private static class PNameVar extends Var {
        @Keys(keys = {"pname", "n"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return player.getName();
            }

            return "";
        }
    }

    private static class DNameVar extends Var {
        @Keys(keys = {"displayname", "dname", "dn"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return player.getDisplayName();
            }

            return "";
        }
    }

    private static class ExpVar extends Var {
        @Keys(keys = {"experience", "exp"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10);
            }

            return "";
        }
    }

    private static class ExpBarVar extends Var {
        @Keys(keys = {"experiencebar", "expb", "ebar"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return API.createBasicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10);
            }

            return "";
        }
    }

    private static class GameModeVar extends Var {
        @Keys(keys = {"gamemode", "gm"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return player.getGameMode() != null && player.getGameMode().name() != null ? player.getGameMode().name() : "";
            }

            return "";
        }
    }

    private static class HealthVar extends Var {
        @Keys(keys = {"health", "h"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return String.valueOf(player.getHealth());
            }

            return "";
        }
    }

    private static class HealthBarVar extends Var {
        @Keys(keys = {"healthbar", "hb"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return API.createHealthBar(player);
            }

            return "";
        }
    }

    private static class HungerVar extends Var {
        @Keys(keys = {"hunger"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return String.valueOf(player.getFoodLevel());
            }

            return "";
        }
    }

    private static class HungerBarVar extends Var {
        @Keys(keys = {"hungerbar", "hub"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return API.createBasicBar(player.getFoodLevel(), 20, 10);
            }

            return "";
        }
    }

    private static class LevelVar extends Var {
        @Keys(keys = {"level", "l"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return String.valueOf(player.getLevel());
            }

            return "";
        }
    }

    private static class LocVar extends Var {
        @Keys(keys = {"location", "loc"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return "X: " + player.getLocation().getX() + ", " + "Y: " + player.getLocation().getY() + ", " + "Z: " + player.getLocation().getZ();
            }

            return "";
        }
    }

    private static class TotalExpVar extends Var {
        @Keys(keys = {"totalexp", "texp", "te"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return String.valueOf(player.getTotalExperience());
            }

            return "";
        }
    }

    private static class WorldVar extends Var {
        @Keys(keys = {"world", "w"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return player.getWorld().getName();
            }

            return "";
        }
    }

    private static class GroupNameVar extends Var {
        @Keys(keys = {"Groupname", "Gname", "G"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getGroupName(Reader.getRawGroup(uuid, player.getWorld().getName()));
            }

            return "";
        }
    }

    private static class WorldNameVar extends Var {
        @Keys(keys = {"Worldname", "Wname", "W"})
        public String getValue(UUID uuid) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return Reader.getWorldName(player.getWorld().getName());
            }

            return "";
        }
    }

    private static class MNameFormat extends Var {
        @Keys(keys = {"mnameformat", "mnf"})
        @Meta(type = IndicatorType.MISC_VAR,
                priority = ResolvePriority.FIRST)
        public String getValue(UUID uuid) {
            return LocaleType.FORMAT_NAME.getVal();
        }
    }

    private static class TimeFormat extends Var {
        @Keys(keys = {"time", "t"})
        public String getValue(UUID uuid) {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(LocaleType.FORMAT_DATE.getRaw());

            return dateFormat.format(now);
        }
    }

    private static class SpyFormat extends Var {
        @Keys(keys = {"spying", "spy"})
        public String getValue(UUID uuid) {
            String sType = "";

            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                String pName = player.getName();

                if (API.getSpying().get(pName) != null
                        && API.getSpying().get(pName)) {
                    sType = LocaleType.FORMAT_SPY.getVal();
                }
            }

            return sType;
        }
    }

    private static class DistanceType extends Var {
        @Keys(keys = {"distancetype", "dtype"})
        public String getValue(UUID uuid) {
            String dType = "";

            if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
                dType = LocaleType.FORMAT_LOCAL.getVal();
            }

            return dType;
        }
    }
}