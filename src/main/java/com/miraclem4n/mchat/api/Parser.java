package com.miraclem4n.mchat.api;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import com.herocraftonline.heroes.util.Messaging;
import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.configs.CensorUtil;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    // Heroes
    public static Boolean heroesB;
    public static Heroes heroes;

    // Towny
    public static Boolean townyB;

    // MSocial
    public static Boolean mSocialB;

    public static void initialize(MChat instance) {
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
        Object prefix = Reader.getRawPrefix(pName, InfoType.USER, world);
        Object suffix = Reader.getRawSuffix(pName, InfoType.USER, world);
        Object group = Reader.getRawGroup(pName, InfoType.USER, world);

        String vI = ConfigType.MCHAT_VAR_INDICATOR.getString();

        if (msg == null)
            msg = "";

        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (group == null)
            group = "";

        // Heroes Vars
        String hSClass = "";
        String hClass = "";
        String hHealth = "";
        String hHBar = "";
        String hMana = "";
        String hMBar = "";
        String hParty = "";
        String hMastered = "";
        String hLevel = "";
        String hSLevel = "";
        String hExp = "";
        String hSExp = "";
        String hEBar = "";
        String hSEBar = "";

        // Towny Vars
        String tTown = "";
        String tTownName = "";
        String tTitle = "";
        String tSurname = "";
        String tResidentName = "";
        String tPrefix = "";
        String tNamePrefix = "";
        String tPostfix = "";
        String tNamePostfix = "";
        String tNation = "";
        String tNationName = "";
        String tNationTag = "";

        // Location
        Double locX = (double) randomNumber(-100, 100);
        Double locY = (double) randomNumber(-100, 100);
        Double locZ = (double) randomNumber(-100, 100);

        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

        // Health
        String healthbar = "";
        String health = String.valueOf(randomNumber(1, 20));

        // World
        String pWorld = "";

        // 1.8 Vars
        String hungerLevel = String.valueOf(randomNumber(0, 20));
        String hungerBar = API.createBasicBar(randomNumber(0, 20), 20, 10);
        String level = String.valueOf(randomNumber(1, 2));
        String exp = String.valueOf(randomNumber(0, 200))+ "/" + ((randomNumber(1, 2) + 1) * 10);
        String expBar = API.createBasicBar(randomNumber(0, 200), ((randomNumber(1, 2) + 1) * 10), 10);
        String tExp = String.valueOf(randomNumber(0, 300));
        String gMode = String.valueOf(randomNumber(0, 1));

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(LocaleType.FORMAT_DATE.getRaw());
        String time = dateFormat.format(now);

        // Display Name
        String dName = pName;

        // Chat Distance Type
        String dType = "";

        if (mSocialB
                && MChat.shouting.get(pName) != null
                && MChat.shouting.get(pName)) {
            dType = MChat.shoutFormat;
        } else if (ConfigType.MCHAT_CHAT_DISTANCE.getDouble() > 0) {
            dType = LocaleType.FORMAT_LOCAL.getVal();
        }

        // Chat Distance Type
        String sType = "";

        if (MChat.spying.get(pName) != null
                && MChat.spying.get(pName))
            sType = LocaleType.FORMAT_SPY.getVal();

        // Player Object Stuff
        if (Bukkit.getServer().getPlayer(pName) != null)  {
            Player player = Bukkit.getServer().getPlayer(pName);

            // Location
            locX = player.getLocation().getX();
            locY = player.getLocation().getY();
            locZ = player.getLocation().getZ();

            loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

            // Health
            healthbar = API.createHealthBar(player);
            health = String.valueOf(player.getHealth());

            // World
            pWorld = player.getWorld().getName();

            // 1.8 Vars
            hungerLevel = String.valueOf(player.getFoodLevel());
            hungerBar = API.createBasicBar(player.getFoodLevel(), 20, 10);
            level = String.valueOf(player.getLevel());
            exp = String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10);
            expBar = API.createBasicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10);
            tExp = String.valueOf(player.getTotalExperience());
            gMode = "";

            if (player.getGameMode() != null && player.getGameMode().name() != null)
                gMode = player.getGameMode().name();

            // Display Name
            dName = player.getDisplayName();

            // Initialize Heroes Vars
            if (heroesB)             {
                Hero hero = heroes.getCharacterManager().getHero(player);
                HeroClass heroClass = hero.getHeroClass();
                HeroClass heroSClass = hero.getSecondClass();

                int hL = hero.getLevel();
                int hSL = hero.getLevel(heroSClass);
                double hE = hero.getExperience(heroClass);
                double hSE = hero.getExperience(heroSClass);

                hClass = hero.getHeroClass().getName();
                hHealth = String.valueOf(hero.getHealth());
                hHBar = Messaging.createHealthBar(hero.getHealth(), hero.getMaxHealth());
                hMana = String.valueOf(hero.getMana());
                hLevel = String.valueOf(hL);
                hExp = String.valueOf(hE);
                hEBar = Messaging.createExperienceBar(hero, heroClass);

                Integer hMMana = hero.getMaxMana();

                if (hMMana != null)
                    hMBar = Messaging.createManaBar(hero.getMana(), hero.getMaxMana());

                if (hero.getParty() != null)
                    hParty = hero.getParty().toString();

                if (heroSClass != null) {
                    hSClass = heroSClass.getName();
                    hSLevel = String.valueOf(hSL);
                    hSExp = String.valueOf(hSE);
                    hSEBar = Messaging.createExperienceBar(hero, heroSClass);
                }

                if ((hero.isMaster(heroClass))
                        && (heroSClass == null || hero.isMaster(heroSClass)))
                    hMastered = LocaleType.MESSAGE_HEROES_TRUE.getVal();
                else
                    hMastered = LocaleType.MESSAGE_HEROES_FALSE.getVal();
            }

            if (townyB) {
                try {
                    Resident resident = TownyUniverse.getDataSource().getResident(pName);

                    if (resident.hasTown()) {
                        Town town = resident.getTown();

                        tTown = town.getName();
                        tTownName = TownyFormatter.getFormattedTownName(town);

                        tTitle = resident.getTitle();
                        tSurname = resident.getSurname();
                        tResidentName = resident.getFormattedName();

                        tPrefix = resident.hasTitle() ? resident.getTitle() : TownyFormatter.getNamePrefix(resident);
                        tNamePrefix = TownyFormatter.getNamePrefix(resident);
                        tPostfix = resident.hasSurname() ? resident.getSurname() : TownyFormatter.getNamePostfix(resident);
                        tNamePostfix = TownyFormatter.getNamePostfix(resident);

                        if (resident.hasNation()) {
                            Nation nation = town.getNation();

                            tNation = nation.getName();
                            tNationName = nation.getFormattedName();
                            tNationTag = nation.getTag();
                        }
                    }
                } catch (Exception ignored) {}
            }
        }

        String formatAll = parseVars(format, pName, world);

        msg = msg.replaceAll("%", "%%");

        formatAll = formatAll.replaceAll("%", "%%");

        formatAll = MessageUtil.addColour(formatAll);

        if (!API.checkPermissions(pName, world, "mchat.bypass.clock")
                && ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger() > 0)
            msg = fixCaps(msg, ConfigType.MCHAT_CAPS_LOCK_RANGE.getInteger());

        if (formatAll == null)
            return msg;

        if (API.checkPermissions(pName, world, "mchat.coloredchat"))
            msg = MessageUtil.addColour(msg);

        if (!API.checkPermissions(pName, world, "mchat.censorbypass"))
            msg = replaceCensoredWords(msg);


        TreeMap<String, Object> fVarMap = new TreeMap<String, Object>();
        TreeMap<String, Object> rVarMap = new TreeMap<String, Object>();
        TreeMap<String, Object> lVarMap = new TreeMap<String, Object>();

        addVar(fVarMap, vI + "mnameformat," + vI + "mnf", LocaleType.FORMAT_NAME.getVal());
        addVar(fVarMap, vI + "healthbar," + vI + "hb", healthbar);

        addVar(rVarMap, vI + "distancetype," + vI + "dtype", dType);
        addVar(rVarMap, vI + "displayname," + vI + "dname," + vI + "dn", dName);
        addVar(rVarMap, vI + "experiencebar," + vI + "expb," + vI + "ebar," + vI + "eb", expBar);
        addVar(rVarMap, vI + "experience," + vI + "exp", exp);
        addVar(rVarMap, vI + "gamemode," + vI + "gm", gMode);
        addVar(rVarMap, vI + "group," + vI + "g", group);
        addVar(rVarMap, vI + "hungerbar," + vI + "hub", hungerBar);
        addVar(rVarMap, vI + "hunger", hungerLevel);
        addVar(rVarMap, vI + "health," + vI + "h", health);
        addVar(rVarMap, vI + "location," + vI + "loc", loc);
        addVar(rVarMap, vI + "level," + vI + "l", level);
        addVar(rVarMap, vI + "mname," + vI + "mn", Reader.getMName(pName));
        addVar(rVarMap, vI + "pname," + vI + "n", pName);
        addVar(rVarMap, vI + "prefix," + vI + "p", prefix);
        addVar(rVarMap, vI + "spying," + vI + "spy", sType);
        addVar(rVarMap, vI + "suffix," + vI + "s", suffix);
        addVar(rVarMap, vI + "totalexp," + vI + "texp," + vI + "te", tExp);
        addVar(rVarMap, vI + "time," + vI + "t", time);
        addVar(rVarMap, vI + "world," + vI + "w", pWorld);
        addVar(rVarMap, vI + "Groupname," + vI + "Gname," + vI + "G", Reader.getGroupName(group.toString()));
        addVar(rVarMap, vI + "HClass," + vI + "HC", hClass);
        addVar(rVarMap, vI + "HExp," + vI + "HEx", hExp);
        addVar(rVarMap, vI + "HEBar," + vI + "HEb", hEBar);
        addVar(rVarMap, vI + "HHBar," + vI + "HHB", hHBar);
        addVar(rVarMap, vI + "HHealth," + vI + "HH", hHealth);
        addVar(rVarMap, vI + "HLevel," + vI + "HL", hLevel);
        addVar(rVarMap, vI + "HMastered," + vI + "HMa", hMastered);
        addVar(rVarMap, vI + "HMana," + vI + "HMn", hMana);
        addVar(rVarMap, vI + "HMBar," + vI + "HMb", hMBar);
        addVar(rVarMap, vI + "HParty," + vI + "HPa", hParty);
        addVar(rVarMap, vI + "HSecClass," + vI + "HSC", hSClass);
        addVar(rVarMap, vI + "HSecExp," + vI + "HSEx", hSExp);
        addVar(rVarMap, vI + "HSecEBar," + vI + "HSEb", hSEBar);
        addVar(rVarMap, vI + "HSecLevel," + vI + "HSL", hSLevel);

        addVar(rVarMap, vI + "town", tTown);
        addVar(rVarMap, vI + "townname", tTownName);
        addVar(rVarMap, vI + "townysurname", tSurname);
        addVar(rVarMap, vI + "townytitle", tTitle);
        addVar(rVarMap, vI + "townyresidentname", tResidentName);
        addVar(rVarMap, vI + "townyprefix", tPrefix);
        addVar(rVarMap, vI + "townynameprefix", tNamePrefix);
        addVar(rVarMap, vI + "townypostfix", tPostfix);
        addVar(rVarMap, vI + "townynamepostfix", tNamePostfix);
        addVar(rVarMap, vI + "townynation", tNation);
        addVar(rVarMap, vI + "townynationname", tNationName);
        addVar(rVarMap, vI + "townynationtag", tNationTag);

        addVar(rVarMap, vI + "Worldname," + vI + "Wname," + vI + "W", Reader.getWorldName(pWorld));

        addVar(lVarMap, vI + "message," + vI + "msg," + vI + "m", msg);

        formatAll = replaceCustVars(pName, formatAll);

        formatAll = replaceVars(formatAll, fVarMap.descendingMap(), true);
        formatAll = replaceVars(formatAll, rVarMap.descendingMap(), true);
        formatAll = replaceVars(formatAll, lVarMap.descendingMap(), false);

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
        return parseMessage(pName, world, msg, ConfigType.FORMAT_CHAT.getString());
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


    // Misc Stuff

    private static TreeMap<String, Object> addVar(TreeMap<String, Object> map, String keys, Object value) {
        if (keys.contains(","))
            for (String s : keys.split(",")) {
                if (s == null || value == null)
                    continue;

                map.put(s, value);
            }
        else if (value != null)
            map.put(keys, value);

        return map;
    }

    private static String fixCaps(String format, Integer range) {
        if (range < 1)
            return format;

        Pattern pattern = Pattern.compile("([A-Z]{" + range + ",300})");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find())
            matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group().toLowerCase()));

        matcher.appendTail(sb);

        format = sb.toString();

        return format;
    }

    private static String parseVars(String format, String pName, String world) {
        String vI = "\\" + ConfigType.MCHAT_VAR_INDICATOR.getString();
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

    private static String replaceVars(String format, Map<String, Object> map, Boolean doColour) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String value = entry.getValue().toString();

            if (doColour)
                value = MessageUtil.addColour(value);

            format = format.replace(entry.getKey(), value);
        }

        return format;
    }

    private static String replaceCustVars(String pName, String format) {
        Set<Map.Entry<String, String>> varSet = API.varMap.entrySet();

        for (Map.Entry<String, String> entry : varSet) {
            String pKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue();

            if (format.contains(pKey))
                format = format.replace(pKey, MessageUtil.addColour(value));
        }

        for (Map.Entry<String, String> entry : varSet) {
            String gKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace("%^global^%|", "");
            String value = entry.getValue();

            if (format.contains(gKey))
                format = format.replace(gKey, MessageUtil.addColour(value));
        }

        return format;
    }

    private static String replaceCensoredWords(String msg) {
        if (ConfigType.MCHAT_IP_CENSOR.getBoolean())
            msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");

        for (Map.Entry<String, Object> entry : CensorUtil.getConfig().getValues(false).entrySet()) {
            String val = entry.getValue().toString();

            msg = replacer(msg, "(?i)" + entry.getKey(), val);
        }

        return msg;
    }

    private static String replacer(String msg, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer sb = new StringBuffer();

        while (matcher.find())
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));

        matcher.appendTail(sb);

        msg = sb.toString();

        return msg;
    }

    private static Integer randomNumber(Integer minValue, Integer maxValue) {
        Random random = new Random();

        return random.nextInt(maxValue - minValue + 1) + minValue;
    }
}
