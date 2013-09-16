package com.miraclem4n.mchat.api;

import com.herocraftonline.heroes.Heroes;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigType;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.variables.ResolvePriority;
import com.miraclem4n.mchat.variables.VariableManager;
import com.miraclem4n.mchat.variables.vars.GeoIpVars;
import com.miraclem4n.mchat.variables.vars.HeroesVars;
import com.miraclem4n.mchat.variables.vars.PlayerVars;
import com.miraclem4n.mchat.variables.vars.TownyVars;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import uk.org.whoami.geoip.GeoIPLookup;
import uk.org.whoami.geoip.GeoIPTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class used to parse messages / events / misc.
 */
public class Parser {
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

    /**
     * Class Initializer
     */
    public static void initialize() {
        setupPlugins();
    }

    /**
     * Core Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Player's World.
     * @param msg Message being displayed.
     * @param format Resulting Format.
     * @return Formatted Message.
     */
    public static String parseMessage(String pName, String world, String msg, String format) {
        msg = msg != null ? msg : "";

        String formatAll = parseVars(format, pName, world);

        msg = msg.replaceAll("%", "%%");

        formatAll = formatAll.replaceAll("%", "%%");

        formatAll = MessageUtil.addColour(formatAll);

        Integer cInt = ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger();

        if (!API.checkPermissions(pName, world, "mchat.bypass.clock") && cInt > 0) {
            msg = fixCaps(msg, cInt);
        }

        if (formatAll == null) {
            return msg;
        }

        if (API.checkPermissions(pName, world, "mchat.coloredchat")) {
            msg = MessageUtil.addColour(msg);
        }

        if (!API.checkPermissions(pName, world, "mchat.censorbypass")) {
            msg = replaceCensoredWords(msg);
        }

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(LocaleType.FORMAT_DATE.getRaw());
        String time = dateFormat.format(now);

        // Chat Distance Type
        String dType = "";

        if (mSocialB
                && API.isShouting().get(pName) != null
                && API.isShouting().get(pName)) {
            dType = API.getShoutFormat();
        } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
            dType = LocaleType.FORMAT_LOCAL.getVal();
        }

        // Spy Type
        String sType = "";

        if (API.isSpying().get(pName) != null
                && API.isSpying().get(pName)) {
            sType = LocaleType.FORMAT_SPY.getVal();
        }

        VariableManager varMgr = new VariableManager();

        // Player Object Stuff
        Player player = Bukkit.getServer().getPlayer(pName);
        if (player != null) {
            // Initialize Player Vars
            PlayerVars.addVars(varMgr, player, pName, world);

            // Initialize GeoIP Vars
            if (geoipB) {
                GeoIpVars.addVars(varMgr, geoip.getCountry(player.getAddress().getAddress()), geoip.getLocation(player.getAddress().getAddress()));
            }

            // Initialize Heroes Vars
            if (heroesB) {
                HeroesVars.addVars(varMgr, heroes.getCharacterManager().getHero(player));
            }

            // Initialize Towny Vars
            if (townyB) {
                try {
                    Resident resident = TownyUniverse.getDataSource().getResident(pName);
                    TownyVars.addVars(varMgr, resident);
                } catch (Exception ignored) {}
            }
        }

        varMgr.addVars(new String[]{"mnameformat", "mnf"}, LocaleType.FORMAT_NAME.getVal(), ResolvePriority.FIRST);

        varMgr.addVars(new String[]{"distancetype","dtype"}, dType, ResolvePriority.NORMAL);
        varMgr.addVars(new String[]{"API.isSpying()","spy"}, sType, ResolvePriority.NORMAL);
        varMgr.addVars(new String[]{"time","t"}, time, ResolvePriority.NORMAL);

        varMgr.addVars(new String[]{"message","msg","m"}, msg, ResolvePriority.LAST);

        varMgr.sortVars(ResolvePriority.ALL);

        formatAll = varMgr.replaceCustVars(pName, formatAll);
        formatAll = varMgr.replaceVars(formatAll, true, ResolvePriority.ALL);

        return formatAll;
    }

    /**
     * Chat Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg Message being displayed.
     * @return Formatted Chat Message.
     */
    public static String parseChatMessage(String pName, String world, String msg) {
        return parseMessage(pName, world, msg, LocaleType.FORMAT_CHAT.getRaw());
    }

    /**
     * Player Name Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Player Name.
     */
    public static String parsePlayerName(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_NAME.getRaw());
    }

    /**
     * Event Message Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param type Event Type being formatted.
     * @return Formatted Event Message.
     */
    public static String parseEvent(String pName, String world, EventType type) {
        return parseMessage(pName, world, "", API.replace(Reader.getEventMessage(type), "player", parsePlayerName(pName, world), IndicatorType.LOCALE_VAR));
    }

    /**
     * TabbedList Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted TabbedList Name.
     */
    public static String parseTabbedList(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_TABBED_LIST.getRaw());
    }

    /**
     * ListCommand Formatting.
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted ListCommand Name.
     */
    public static String parseListCmd(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_LIST_CMD.getRaw());
    }

    /**
     * Me Formatting.
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg Message being displayed.
     * @return Formatted Me Message.
     */
    public static String parseMe(String pName, String world, String msg) {
        return parseMessage(pName, world, msg, LocaleType.FORMAT_ME.getRaw());
    }

    private static String fixCaps(String format, Integer range) {
        if (range < 1) {
            return format;
        }

        Pattern pattern = Pattern.compile("([A-Z]{" + range + ",300})");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group().toLowerCase()));
        }

        matcher.appendTail(sb);

        format = sb.toString();

        return format;
    }

    private static String parseVars(String format, String pName, String world) {
        String vI = "\\" + IndicatorType.MISC_VAR.getValue();
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = Reader.getRawInfo(pName, InfoType.USER, world, matcher.group(1)).toString();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String replaceCensoredWords(String msg) {
        if (ConfigType.MCHAT_IP_CENSOR.getBoolean()) {
            msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");
        }

        for (Map.Entry<String, Object> entry : YmlManager.getYml(YmlType.CENSOR_YML).getConfig().getValues(true).entrySet()) {
            if (!(entry.getValue() instanceof String)) {
                continue;
            }

            msg = replacer(msg, "(?i)" + entry.getKey(), entry.getValue().toString());
        }

        return msg;
    }

    private static String replacer(String msg, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);

        msg = sb.toString();

        return msg;
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
