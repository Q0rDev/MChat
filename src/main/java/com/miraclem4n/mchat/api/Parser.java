package com.miraclem4n.mchat.api;

import com.herocraftonline.heroes.Heroes;
import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigType;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.variables.VariableManager;
import com.miraclem4n.mchat.variables.vars.GeoIpVars;
import com.miraclem4n.mchat.variables.vars.HeroesVars;
import com.miraclem4n.mchat.variables.vars.PlayerVars;
import com.miraclem4n.mchat.variables.vars.TownyVars;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.org.whoami.geoip.GeoIPLookup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // GeoIP
    public static GeoIPLookup geoip;
    public static Boolean geoipB;

    // Heroes
    public static Boolean heroesB;
    public static Heroes heroes;

    // Towny
    public static Boolean townyB;

    // MSocial
    public static Boolean mSocialB;

    public static void initialize(MChat instance) {
        geoip = instance.geoip;
        geoipB = instance.geoipB;
        heroesB = instance.heroesB;
        heroes = instance.heroes;
        townyB = instance.towny;
        mSocialB = instance.mSocial;
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

        if (!API.checkPermissions(pName, world, "mchat.bypass.clock")
                && ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger() > 0) {
            msg = fixCaps(msg, ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger());
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

        VariableManager varMgr = new VariableManager();

        varMgr.addVars(new String[]{"mnameformat","mnf"}, LocaleType.FORMAT_NAME.getVal());

        varMgr.sortVars();
        formatAll = varMgr.replaceCustVars(pName, formatAll);
        formatAll = varMgr.replaceVars(formatAll, true);
        varMgr = new VariableManager();

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(LocaleType.FORMAT_DATE.getRaw());
        String time = dateFormat.format(now);

        // Chat Distance Type
        String dType = "";

        if (mSocialB
                && MChat.shouting.get(pName) != null
                && MChat.shouting.get(pName)) {
            dType = MChat.shoutFormat;
        } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
            dType = LocaleType.FORMAT_LOCAL.getVal();
        }

        // Spy Type
        String sType = "";

        if (MChat.spying.get(pName) != null
                && MChat.spying.get(pName)) {
            sType = LocaleType.FORMAT_SPY.getVal();
        }

        varMgr.addVars(new String[]{"distancetype","dtype"}, dType);
        varMgr.addVars(new String[]{"spying","spy"}, sType);
        varMgr.addVars(new String[]{"time","t"}, time);

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

        varMgr.sortVars();
        formatAll = varMgr.replaceVars(formatAll, true);
        varMgr = new VariableManager();

        varMgr.addVars(new String[]{"message","msg","m"}, msg);

        varMgr.sortVars();
        formatAll = varMgr.replaceVars(formatAll, false);

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
     * ListCommand Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted ListCommand Name.
     */
    public static String parseListCmd(String pName, String world) {
        return parseMessage(pName, world, "", LocaleType.FORMAT_LIST_CMD.getRaw());
    }

    /**
     * Me Formatting
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
}
