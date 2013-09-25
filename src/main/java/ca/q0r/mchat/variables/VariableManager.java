package ca.q0r.mchat.variables;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.variables.vars.*;
import com.herocraftonline.heroes.Heroes;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import uk.org.whoami.geoip.GeoIPLookup;
import uk.org.whoami.geoip.GeoIPTools;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Manager for Variables.
 */
public class VariableManager {
    private static Set<Var> varSet;

    // GeoIP
    private static GeoIPLookup geoip;
    private static Boolean geoipB;

    // Heroes
    private static Boolean heroesB;
    private static Heroes heroes;

    // Towny
    private static Boolean townyB;

    // MSocial
    private static Boolean mSocialB;

    public static void initialize() {
        varSet = new HashSet<Var>();

        setupPlugins();

        // Initialize GeoIP Vars
        if (geoipB) {
            GeoIpVars.addVars(geoip);
        }

        // Initialize Player Vars
        PlayerVars.addVars();

        // Initialize MSocial Vars
        MSocialVars.addVars(mSocialB);

        // Initialize Heroes Vars
        if (heroesB) {
            HeroesVars.addVars(heroes.getCharacterManager());
        }

        // Initialize Towny Vars
        if (townyB) {
            try {
                TownyVars.addVars(TownyUniverse.getDataSource());
            } catch (Exception ignored) {}
        }

        MessageVars.addVars();
    }

    /**
     * Adds Var to VarSet.
     * @param var Variable processor.
     */
    public static void addVar(Var var) {
        if (var != null) {
            varSet.add(var);
        }
    }

    /**
     * Adds Vars to VarSet.
     * @param vars Variable processor Array.
     */
    public static void addVars(Var[] vars) {
        if (vars != null) {
            for (Var var : vars) {
                addVar(var);
            }
        }
    }


    /**
     * Variable Replacer.
     * @param format String to be replaced.
     * @param player Player being formatted against.
     * @param msg Message being relayed.
     * @param doColour Whether or not to colour replacement value.
     * @return String with Variables replaced.
     */
    public static String replaceVars(String format, Player player, String msg, Boolean doColour) {
        NavigableMap<String, Object> fVarMap = new TreeMap<String, Object>();
        NavigableMap<String, Object> nVarMap = new TreeMap<String, Object>();
        NavigableMap<String, Object> lVarMap = new TreeMap<String, Object>();

        for (Var var : varSet) {
            Method[] methods = var.getClass().getMethods();

            for (Method method : methods) {
                ResolvePriority priority = ResolvePriority.NORMAL;
                IndicatorType type = IndicatorType.MISC_VAR;
                String[] keys = {};
                String value = "";

                if (method.isAnnotationPresent(Var.Keys.class)) {
                    Var.Keys vKeys = method.getAnnotation(Var.Keys.class);

                    keys = vKeys.keys();

                    if (msg != null && var instanceof MessageVars.MessageVar) {
                        value = var.getValue(msg).toString();
                    } else {
                        value = var.getValue(player).toString();
                    }
                }

                if (method.isAnnotationPresent(Var.Meta.class)) {
                    Var.Meta vMeta = method.getAnnotation(Var.Meta.class);

                    priority = vMeta.priority();
                    type = vMeta.type();
                }

                switch(priority) {
                    case FIRST:
                        for (String key : keys) {
                            fVarMap.put(type.getValue() + key, value);
                        }

                        break;
                    case NORMAL:
                        for (String key : keys) {
                            nVarMap.put(type.getValue() + key, value);
                        }

                        break;
                    case LAST:
                        for (String key : keys) {
                            lVarMap.put(type.getValue() + key, value);
                        }

                        break;
                    default:
                        for (String key : keys) {
                            nVarMap.put(type + key, value);
                        }

                        break;
                }
            }
        }

        fVarMap = fVarMap.descendingMap();
        nVarMap = nVarMap.descendingMap();
        lVarMap = lVarMap.descendingMap();

        format = replacer(format, fVarMap, doColour);
        format = replacer(format, nVarMap, doColour);
        format = replacer(format, lVarMap, doColour);

        return format;
    }

    /**
     * Custom Variable Replacer.
     * @param pName Player's Name.
     * @param format String to be replaced.
     * @return String with Custom Variables replaced.
     */
    public static String replaceCustVars(String pName, String format) {
        if (!API.getVarMapQueue().isEmpty()) {
            API.getVarMap().putAll(API.getVarMapQueue());
            API.getVarMapQueue().clear();
        }

        Set<Map.Entry<String, Object>> entrySet = API.getVarMap().entrySet();

        for (Map.Entry<String, Object> entry : entrySet) {
            String pKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue().toString();

            if (format.contains(pKey)) {
                format = format.replace(pKey, MessageUtil.addColour(value));
            }
        }

        for (Map.Entry<String, Object> entry : entrySet) {
            String gKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace("%^global^%|", "");
            String value = entry.getValue().toString();

            if (format.contains(gKey)) {
                format = format.replace(gKey, MessageUtil.addColour(value));
            }
        }

        return format;
    }

    private static String replacer(String format, Map<String, Object> map, Boolean doColour) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();

            if (doColour) {
                value = MessageUtil.addColour(value);
            }

            format = format.replace(key, value);
        }

        return format;
    }

    private static void setupPlugins() {
        PluginManager pm = Bukkit.getPluginManager();

        // Setup GeoIPTools
        geoipB = setupPlugin("GeoIPTools");

        if (geoipB) {
            geoip = ((GeoIPTools) pm.getPlugin("GeoIPTools")).getGeoIPLookup();
        }

        // Setup Heroes
        heroesB = setupPlugin("Heroes");

        if (heroesB) {
            heroes = (Heroes) pm.getPlugin("Heroes");
        }

        // Setup MSocial
        mSocialB = setupPlugin("MSocial");

        townyB = setupPlugin("Towny");
    }

    private static Boolean setupPlugin(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        if (plugin != null) {
            MessageUtil.logFormatted("<Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }
}
