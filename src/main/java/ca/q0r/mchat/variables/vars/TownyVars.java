package ca.q0r.mchat.variables.vars;

import ca.q0r.mchat.variables.Var;
import ca.q0r.mchat.variables.VariableManager;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.entity.Player;

public class TownyVars {
    private static TownyDataSource dataSource;

    public static void addVars(TownyDataSource townyDataSource) {
        dataSource = townyDataSource;

        VariableManager.addVars(new Var[]{new TownVar(), new TownNameVar(), new TitleVar(), new SurnameVar(),
                new ResidentNameVar(), new PrefixVar(), new NamePrefixVar(), new PostfixVar(), new NamePostfixVar(),
                new NationVar(), new NationNameVar(), new NationTagVar()});
    }

    private static class TownVar extends Var {
        @Keys ( keys = {"town"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());
                    return !resident.hasTown() ? "" : resident.getTown().getName();
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class TownNameVar extends Var {
        @Keys ( keys = {"townname"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());
                    return !resident.hasTown() ? "" : TownyFormatter.getFormattedTownName(resident.getTown());
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class TitleVar extends Var {
        @Keys ( keys = {"townytitle"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.getTitle();
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class SurnameVar extends Var {
        @Keys ( keys = {"townysurname"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.getSurname();
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class ResidentNameVar extends Var {
        @Keys ( keys = {"townyresidentname"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.getFormattedName();
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class PrefixVar extends Var {
        @Keys ( keys = {"townyprefix"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident);
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class NamePrefixVar extends Var {
        @Keys ( keys = {"townynameprefix"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return TownyFormatter.getNamePrefix(resident);
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class PostfixVar extends Var {
        @Keys ( keys = {"townypostfix"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident);
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class NamePostfixVar extends Var {
        @Keys ( keys = {"townynamepostfix"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return TownyFormatter.getNamePostfix(resident);
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class NationVar extends Var {
        @Keys ( keys = {"townynation"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.hasTown() && resident.getTown().hasNation() ? resident.getTown().getNation().getName() : "";
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class NationNameVar extends Var {
        @Keys ( keys = {"townynationname"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.hasTown() && resident.getTown().hasNation() ? resident.getTown().getNation().getFormattedName() : "";
                } catch (Exception ignored) { }
            }

            return "";
        }
    }

    private static class NationTagVar extends Var {
        @Keys ( keys = {"townynationtag"} )
        public Object getValue(Object obj) {
            if (obj != null && obj instanceof Player) {
                try {
                    Resident resident = dataSource.getResident(((Player) obj).getName());

                    return resident.hasTown() && resident.getTown().hasNation() ? resident.getTown().getNation().getTag() : "";
                } catch (Exception ignored) { }
            }

            return "";
        }
    }
}
