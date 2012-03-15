package in.mDev.MiracleM4n.mChatSuite.api;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import com.herocraftonline.heroes.util.Messaging;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;

@SuppressWarnings("unused")
public class mChatAPI {
    mChatSuite plugin;
    SortedMap<String, String> varMap;

    public mChatAPI(mChatSuite plugin) {
        this.plugin = plugin;

        varMap = new TreeMap<String, String>();

        if (plugin.cVarMap != null)
            varMap.putAll(plugin.cVarMap);
    }

    //Format Stuff

    /**
     * Core Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Player's World.
     * @param msg Message being displayed.
     * @param format Resulting Format.
     * @return Formatted Message.
     */
    public String ParseMessage(String pName, String world, String msg, String format) {
        Object prefix = plugin.getInfoReader().getRawPrefix(pName, InfoType.USER, world);
        Object suffix = plugin.getInfoReader().getRawSuffix(pName, InfoType.USER, world);
        Object group = plugin.getInfoReader().getRawGroup(pName, InfoType.USER, world);

        String vI = plugin.varIndicator;

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
        String hungerBar = basicBar(randomNumber(0, 20), 20, 10);
        String level = String.valueOf(randomNumber(1, 2));
        String exp = String.valueOf(randomNumber(0, 200))+ "/" + ((randomNumber(1, 2) + 1) * 10);
        String expBar = basicBar(randomNumber(0, 200), ((randomNumber(1, 2) + 1) * 10), 10);
        String tExp = String.valueOf(randomNumber(0, 300));
        String gMode = String.valueOf(randomNumber(0, 1));

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);

        // Display Name
        String dName = pName;

        // Chat Distance Type
        String dType = "";

        if (plugin.isShouting.get(pName) != null
                && plugin.isShouting.get(pName)) {
            dType = plugin.getLocale().getOption("format.shout");
        } else if (plugin.chatDistance > 0) {
            dType = plugin.getLocale().getOption("format.local");
        }

        // Chat Distance Type
        String sType = "";

        if (plugin.isSpying.get(pName) != null
                && plugin.isSpying.get(pName))
            sType = plugin.getLocale().getOption("format.spy");

        // Player Object Stuff
        if (plugin.getServer().getPlayer(pName) != null)  {
            Player player = plugin.getServer().getPlayer(pName);

            // Location
            locX = player.getLocation().getX();
            locY = player.getLocation().getY();
            locZ = player.getLocation().getZ();

            loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

            // Health
            healthbar = healthBar(player);
            health = String.valueOf(player.getHealth());

            // World
            pWorld = player.getWorld().getName();

            // 1.8 Vars
            hungerLevel = String.valueOf(player.getFoodLevel());
            hungerBar = basicBar(player.getFoodLevel(), 20, 10);
            level = String.valueOf(player.getLevel());
            exp = String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10);
            expBar = basicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10);
            tExp = String.valueOf(player.getTotalExperience());
            gMode = "";

            if (player.getGameMode() != null && player.getGameMode().name() != null)
                gMode = player.getGameMode().name();

            // Display Name
            dName = player.getDisplayName();

            // Initialize Heroes Vars
            if (plugin.heroesB) {
                Hero hero = plugin.heroes.getCharacterManager().getHero(player);
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
                    hMastered = plugin.hMasterT;
                else
                    hMastered = plugin.hMasterF;
            }
        }

        String formatAll = parseVars(format, pName, world);

        msg = msg.replaceAll("%", "%%");
        formatAll = formatAll.replaceAll("%", "%%");

        if (formatAll == null)
            return msg;

        if (!checkPermissions(pName, world, "mchat.coloredchat"))
            msg = Messanger.removeColour(msg);

        if (!checkPermissions(pName, world, "mchat.censorbypass"))
            msg = replaceCensoredWords(msg);


        TreeMap<String, Object> fVarMap = new TreeMap<String, Object>();
        TreeMap<String, Object> rVarMap = new TreeMap<String, Object>();
        TreeMap<String, Object> lVarMap = new TreeMap<String, Object>();

        addVar(fVarMap, vI + "mnameformat," + vI + "mnf", plugin.nameFormat);
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
        addVar(rVarMap, vI + "mname," + vI + "mn", plugin.getInfoReader().getmName(pName));
        addVar(rVarMap, vI + "pname," + vI + "n", pName);
        addVar(rVarMap, vI + "prefix," + vI + "p", prefix);
        addVar(rVarMap, vI + "spying," + vI + "spy", sType);
        addVar(rVarMap, vI + "suffix," + vI + "s", suffix);
        addVar(rVarMap, vI + "totalexp," + vI + "texp," + vI + "te", tExp);
        addVar(rVarMap, vI + "time," + vI + "t", time);
        addVar(rVarMap, vI + "world," + vI + "w", pWorld);
        addVar(rVarMap, vI + "Groupname," + vI + "Gname," + vI + "G", plugin.getInfoReader().getGroupName(group.toString()));
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
        addVar(rVarMap, vI + "Worldname," + vI + "Wname," + vI + "W", plugin.getInfoReader().getWorldName(pWorld));

        addVar(lVarMap, vI + "message," + vI + "msg," + vI + "m", msg);

        formatAll = replaceCustVars(pName, formatAll);
        
        formatAll = replaceVars(formatAll, fVarMap.descendingMap());
        formatAll = replaceVars(formatAll, rVarMap.descendingMap());

        return replaceVars(formatAll, lVarMap.descendingMap());
    }

    /**
     * Chat Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @param msg Message being displayed.
     * @return Formatted Chat Message.
     */
    @Deprecated
    public String ParseChatMessage(Player player, World world, String msg) {
        return ParseMessage(player.getName(), world.getName(), msg, plugin.chatFormat);
    }

    /**
     * Player Name Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Player Name.
     */
    @Deprecated
    public String ParsePlayerName(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.nameFormat);
    }

    /**
     * Event Message Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Event Message.
     */
    @Deprecated
    public String ParseEventName(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.eventFormat);
    }

    /**
     * TabbedList Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted TabbedList Name.
     */
    @Deprecated
    public String ParseTabbedList(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.tabbedListFormat);
    }

    /**
     * ListCommand Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted ListCommand Name.
     */
    @Deprecated
    public String ParseListCmd(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.listCmdFormat);
    }

    /**
     * Me Formatting
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @param msg Message being displayed.
     * @return Formatted Me Message.
     */
    @Deprecated
    public String ParseMe(Player player, World world, String msg) {
        return ParseMessage(player.getName(), world.getName(), msg, plugin.meFormat);
    }

    /**
     * Chat Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg Message being displayed.
     * @return Formatted Chat Message.
     */
    public String ParseChatMessage(String pName, String world, String msg) {
        return ParseMessage(pName, world, msg, plugin.chatFormat);
    }

    /**
     * Player Name Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Player Name.
     */
    public String ParsePlayerName(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.nameFormat);
    }

    /**
     * Event Message Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Event Message.
     */
    public String ParseEventName(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.eventFormat);
    }

    /**
     * TabbedList Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted TabbedList Name.
     */
    public String ParseTabbedList(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.tabbedListFormat);
    }

    /**
     * ListCommand Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted ListCommand Name.
     */
    public String ParseListCmd(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.listCmdFormat);
    }

    /**
     * Me Formatting
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param msg Message being displayed.
     * @return Formatted Me Message.
     */
    public String ParseMe(String pName, String world, String msg) {
        return ParseMessage(pName, world, msg, plugin.meFormat);
    }

    // Misc Stuff


    /**
     * Global Variable Addition
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public void addGlobalVar(String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put("%^global^%|" + var, value);
    }

    /**
     * Player Variable Addition
     * @param pName Name of Player this Variable is being added for.
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public void addPlayerVar(String pName, String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put(pName + "|" +var, value);
    }

    /**
     * Health Bar Formatting
     * @param player Player the HealthBar is being rendered for.
     * @return Formatted Health Bar.
     */
    public String healthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();

        return basicBar(health, maxHealth, barLength);
    }

    /**
     * Basic Bar Formatting
     * @param currentValue Current Value of Bar.
     * @param maxValue Max Value of Bar.
     * @param barLength Length of Bar.
     * @return Formatted Health Bar.
     */
    public String basicBar(float currentValue, float maxValue, float barLength) {
        int fill = Math.round((currentValue / maxValue) * barLength);

        String barColor = (fill <= (barLength / 4)) ? "&4" : (fill <= (barLength / 7)) ? "&e" : "&2";

        StringBuilder out = new StringBuilder();
        out.append(barColor);

        for (int i = 0; i < barLength; i++) {
            if (i == fill)
                out.append("&8");

            out.append("|");
        }

        out.append("&f");

        return out.toString();
    }

    /**
     * Permission Checking
     * @param player Player being checked.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    @Deprecated
    public Boolean checkPermissions(Player player, String node) {
        return checkPermissions(player.getName(), player.getWorld().getName(), node)
                || player.hasPermission(node)
                || player.isOp();
    }

    /**
     * Permission Checking
     * @param player Player being checked.
     * @param world Player's World.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    public Boolean checkPermissions(Player player, World world, String node) {
        return checkPermissions(player.getName(), world.getName(), node)
                || player.hasPermission(node)
                || player.isOp();
    }

    /**
     * Permission Checking
     * @param player Player being checked.
     * @param node Permission Node being checked.
     * @param useOp Whether to take Op Status into account.
     * @return Player has Node.
     */
    @Deprecated
    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), player.getWorld().getName(), node))
            return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

    /**
     * Permission Checking
     * @param player Player being checked.
     * @param world Player's World.
     * @param node Permission Node being checked.
     * @param useOp Whether to take Op Status into account.
     * @return Player has Node.
     */
    public Boolean checkPermissions(Player player, World world, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), world.getName(), node))
            return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

    /**
     * Permission Checking
     * @param player Player being checked.
     * @param world Name of Player's World.
     * @param node Permission Node being checked.
     * @param useOp Whether to take Op Status into account.
     * @return Player has Node.
     */
    @Deprecated
    public Boolean checkPermissions(Player player, String world, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), world, node))
            return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

    /**
     * Permission Checking
     * @param pName Name of Player being checked.
     * @param world Name of Player's World.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    public Boolean checkPermissions(String pName, String world, String node) {
        if (plugin.vaultB)
            if (plugin.vPerm.has(world, pName, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissionsWH.getWorldPermissions(pName).getPermissionBoolean(pName, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(pName, world, node))
                return true;

        if (plugin.getServer().getPlayer(pName) != null)
            if (plugin.getServer().getPlayer(pName).hasPermission(node))
                return true;

        return false;
    }

    /**
     * Permission Checking
     * @param sender CommandSender being checked.
     * @param node Permission Node being checked.
     * @return Sender has Node.
     */
    public Boolean checkPermissions(CommandSender sender, String node) {
        if (plugin.vaultB)
            if (plugin.vPerm.has(sender, node))
                return true;

        return sender.hasPermission(node);
    }

    TreeMap<String, Object> addVar(TreeMap<String, Object> map, String keys, Object value) {
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

    String parseVars(String format, String pName, String world) {
        String vI = "\\" + plugin.varIndicator;
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = plugin.getInfoReader().getRawInfo(pName, InfoType.USER, world, matcher.group(1)).toString();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    String replaceVars(String format, Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet())
            format = format.replace(entry.getKey(), entry.getValue().toString());

        return Messanger.addColour(format);
    }

    String replaceCustVars(String pName, String format) {
        SortedMap<String, String> gVarMap = varMap;
        SortedMap<String, String> pVarMap = varMap;

        for (Entry<String, String> entry : pVarMap.entrySet()) {
            String pKey = plugin.cusVarIndicator + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue();

            if (format.contains(pKey))
                format = format.replace(pKey, value);
        }

        for (Entry<String, String> entry : gVarMap.entrySet()) {
            String gKey = plugin.cusVarIndicator + entry.getKey().replace("%^global^%|", "");
            String value = entry.getValue();

            if (format.contains(gKey))
                format = format.replace(gKey, value);
        }

        return format;
    }

    String replaceCensoredWords(String msg) {
        if (plugin.useIPRestrict)
            msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");

        for (Entry<String, Object> entry : plugin.mCConfig.getValues(false).entrySet()) {
            String val = entry.getValue().toString();

            msg = replacer(msg, "(?i)" + entry.getKey(), val);
        }

        return msg;
    }

    String replacer(String msg, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer sb = new StringBuffer();

        while (matcher.find())
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));

        matcher.appendTail(sb);

        msg = sb.toString();

        return msg;
    }

    Integer randomNumber(Integer minValue, Integer maxValue) {
        Random random = new Random();

        return random.nextInt(maxValue - minValue + 1) + minValue;
    }
}
