package ca.q0r.mchat.variables;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.variables.vars.*;
import com.herocraftonline.heroes.Heroes;
import com.p000ison.dev.simpleclans2.chat.SimpleClansChat;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
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

    // SimpleClansChat
    private static SimpleClansChat clans;
    private static Boolean clansB;

    /**
     * Initializes Manager.
     */
    public static void initialize() {
        varSet = new HashSet<>();

        setupPlugins();

        // Initialize GeoIP Vars
        if (geoipB) {
            GeoIpVars.addVars(geoip);
        }

        // Initialize Player Vars
        PlayerVars.addVars();

        // Initialize Heroes Vars
        if (heroesB) {
            HeroesVars.addVars(heroes.getCharacterManager());
        }

        // Initialize Towny Vars
        if (townyB) {
            try {
                TownyVars.addVars(TownyUniverse.getDataSource());
            } catch (Exception ignored) {
            }
        }

        // Initialize SimpleClansChat Vars
        if (clansB) {
            SimpleClansVars.addVars(clans);
        }

        MessageVars.addVars();
    }

    /**
     * Adds Var to VarSet.
     *
     * @param var Variable processor.
     */
    public static void addVar(Var var) {
        if (var != null) {
            varSet.add(var);
        }
    }

    /**
     * Adds Vars to VarSet.
     *
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
     *
     * @param format   String to be replaced.
     * @param uuid     Name of Player being formatted against.
     * @param msg      Message being relayed.
     * @param doColour Whether or not to colour replacement value.
     * @return String with Variables replaced.
     */
    public static String replaceVars(String format, UUID uuid, String msg, Boolean doColour) {
        NavigableMap<String, String> fVarMap = new TreeMap<>();
        NavigableMap<String, String> nVarMap = new TreeMap<>();
        NavigableMap<String, String> lVarMap = new TreeMap<>();

        for (Var var : varSet) {
            Method[] methods = var.getClass().getMethods();

            for (Method method : methods) {
                ResolvePriority priority = ResolvePriority.DEFAULT;
                IndicatorType type = IndicatorType.MISC_VAR;
                String[] keys = {};
                String value = "";

                if (method.isAnnotationPresent(Var.Keys.class)) {
                    Var.Keys vKeys = method.getAnnotation(Var.Keys.class);

                    keys = vKeys.keys();

                    if (msg != null && var instanceof MessageVars.MessageVar) {
                        value = msg;
                    } else {
                        value = var.getValue(uuid);
                    }
                }

                if (method.isAnnotationPresent(Var.Meta.class)) {
                    Var.Meta vMeta = method.getAnnotation(Var.Meta.class);

                    priority = vMeta.priority();
                    type = vMeta.type();
                }

                switch (priority) {
                    case FIRST:
                        for (String key : keys) {
                            fVarMap.put(type.getValue() + key, value);
                        }

                        break;
                    case DEFAULT:
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
        format = replacer(format, lVarMap, false);

        return format;
    }

    /**
     * Custom Variable Replacer.
     *
     * @param uuid   Player's UUID.
     * @param format String to be replaced.
     * @return String with Custom Variables replaced.
     */
    public static String replaceCustVars(UUID uuid, String format) {
        for (Map.Entry<String, String> entry : API.getUuidVarMap().entrySet()) {
            String pName = Bukkit.getPlayer(uuid).getName();
            String pKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue();

            if (format.contains(pKey)) {
                format = format.replace(pKey, MessageUtil.addColour(value));
            }
        }

        for (Map.Entry<String, Object> entry : API.getGlobalVarMap().entrySet()) {
            String gKey = IndicatorType.CUS_VAR.getValue() + entry.getKey();
            String value = entry.getValue().toString();

            if (format.contains(gKey)) {
                format = format.replace(gKey, MessageUtil.addColour(value));
            }
        }

        return format;
    }

    private static String replacer(String format, Map<String, String> map, Boolean doColour) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

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

        townyB = setupPlugin("Towny");

        clansB = setupPlugin("SimpleClansChat") && setupPlugin("SimpleClans2");

        if (clansB) {
            clans = (SimpleClansChat) pm.getPlugin("SimpleClansChat");
        }
    }

    private static Boolean setupPlugin(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        if (plugin != null && plugin.isEnabled()) {
            MessageUtil.logFormatted("<Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }
}