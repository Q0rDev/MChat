package in.mDev.MiracleM4n.mChatSuite;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.hero.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class mChatAPI {
    static mChatSuite plugin;

    public mChatAPI(mChatSuite plugin) {
        mChatAPI.plugin = plugin;
    }

    /*
     * Format Stuff
     */
    public String ParseMessage(String pName, String world, String msg, String format) {
        String prefix = plugin.getInfoReader().getRawPrefix(pName, world);
        String suffix = plugin.getInfoReader().getRawSuffix(pName, world);
        String group = plugin.getInfoReader().getRawGroup(pName, world);

        String vI = plugin.varIndicator;

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
                Hero hero = plugin.heroes.getHeroManager().getHero(player);
                HeroClass heroClass = hero.getHeroClass();
                HeroClass heroSClass = hero.getSecondClass();

                int hL = Properties.getLevel(hero.getExperience());
                int hSL = hero.getLevel(heroSClass);
                double hE = Properties.getExperience(hL);
                double hSE = hero.getExperience(heroSClass);

                hClass = hero.getHeroClass().getName();
                hHealth = String.valueOf(hero.getHealth());
                hHBar = Messaging.createHealthBar((float) hero.getHealth(), (float) hero.getMaxHealth());
                hMana = String.valueOf(hero.getMana());
                hMBar = Messaging.createManaBar(hero.getMana());
                hLevel = String.valueOf(hL);
                hExp = String.valueOf(hE);
                hEBar = Messaging.createExperienceBar(hero, heroClass);

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
        String[] search;
        String[] replace;

        msg = msg.replaceAll("%", "%%");
        formatAll = formatAll.replaceAll("%", "%%");

        if (formatAll == null)
            return msg;

        if (!checkPermissions(pName, world, "mchat.coloredchat"))
            msg = removeColour(msg);

        if (!checkPermissions(pName, world, "mchat.censorbypass"))
            msg = replaceCensoredWords(msg);

        search = new String[]{
                vI + "mnameformat," + vI + "mnf",
                vI + "displayname," + vI + "dname," + vI + "dn",
                vI + "experiencebar," + vI + "expb," + vI + "ebar," + vI + "eb",
                vI + "experience," + vI + "exp",
                vI + "gamemode," + vI + "gm",
                vI + "group," + vI + "g",
                vI + "hungerbar," + vI + "hub",
                vI + "hunger",
                vI + "healthbar," + vI + "hb",
                vI + "health," + vI + "h",
                vI + "location," + vI + "loc",
                vI + "level," + vI + "l",
                vI + "mname," + vI + "mn",
                vI + "pName," + vI + "n",
                vI + "prefix," + vI + "p",
                vI + "suffix," + vI + "s",
                vI + "totalexp," + vI + "texp," + vI + "te",
                vI + "time," + vI + "t",
                vI + "world," + vI + "w",
                vI + "Groupname," + vI + "Gname," + vI + "G",
                vI + "HClass," + vI + "HC",
                vI + "HExp," + vI + "HEx",
                vI + "HEBar," + vI + "HEb",
                vI + "HHBar," + vI + "HHB",
                vI + "HHealth," + vI + "HH",
                vI + "HLevel," + vI + "HL",
                vI + "HMastered," + vI + "HMa",
                vI + "HMana," + vI + "HMn",
                vI + "HMBar," + vI + "HMb",
                vI + "HParty," + vI + "HPa",
                vI + "HSecClass," + vI + "HSC",
                vI + "HSecExp," + vI + "HSEx",
                vI + "HSecEBar," + vI + "HSEb",
                vI + "HSecLevel," + vI + "HSL",
                vI + "Worldname," + vI + "Wname," + vI + "W",
                vI + "message," + vI + "msg," + vI + "m"
        };

        replace = new String[]{
                plugin.nameFormat,
                dName,
                expBar,
                exp,
                gMode,
                group,
                hungerBar,
                hungerLevel,
                healthbar,
                health,
                loc,
                level,
                plugin.getInfoReader().getmName(pName),
                pName,
                prefix,
                suffix,
                tExp,
                time,
                pWorld,
                plugin.getInfoReader().getGroupName(group),
                hClass,
                hExp,
                hEBar,
                hHBar,
                hHealth,
                hLevel,
                hMastered,
                hMana,
                hMBar,
                hParty,
                hSClass,
                hSExp,
                hSEBar,
                hSLevel,
                plugin.getInfoReader().getWorldName(world),
                msg
        };

        formatAll = replaceCustVars(pName, formatAll);

        return replaceVars(formatAll, search, replace);
    }

    @Deprecated
    public String ParseChatMessage(Player player, World world, String msg) {
        return ParseMessage(player.getName(), world.getName(), msg, plugin.chatFormat);
    }

    @Deprecated
    public String ParsePlayerName(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.nameFormat);
    }

    @Deprecated
    public String ParseEventName(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.eventFormat);
    }

    @Deprecated
    public String ParseTabbedList(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.tabbedListFormat);
    }

    @Deprecated
    public String ParseListCmd(Player player, World world) {
        return ParseMessage(player.getName(), world.getName(), "", plugin.listCmdFormat);
    }

    @Deprecated
    public String ParseMe(Player player, World world, String msg) {
        return ParseMessage(player.getName(), world.getName(), msg, plugin.meFormat);
    }

    public String ParseChatMessage(String pName, String world, String msg) {
        return ParseMessage(pName, world, msg, plugin.chatFormat);
    }

    public String ParsePlayerName(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.nameFormat);
    }

    public String ParseEventName(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.eventFormat);
    }

    public String ParseTabbedList(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.tabbedListFormat);
    }

    public String ParseListCmd(String pName, String world) {
        return ParseMessage(pName, world, "", plugin.listCmdFormat);
    }

    public String ParseMe(String pName, String world, String msg) {
        return ParseMessage(pName, world, msg, plugin.meFormat);
    }

    /*
     * Misc Stuff
     */
    public void addGlobalVar(String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put("%^global^%|" + var, value);
    }

    public void addPlayerVar(String pName, String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put(pName + "|" +var, value);
    }

    public String healthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();

        return basicBar(health, maxHealth, barLength);
    }

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

    public String addColour(String string) {
        string = string.replace("`e", "")
                .replace("`r", "\u00A7c").replace("`R", "\u00A74")
                .replace("`y", "\u00A7e").replace("`Y", "\u00A76")
                .replace("`g", "\u00A7a").replace("`G", "\u00A72")
                .replace("`a", "\u00A7b").replace("`A", "\u00A73")
                .replace("`b", "\u00A79").replace("`B", "\u00A71")
                .replace("`p", "\u00A7d").replace("`P", "\u00A75")
                .replace("`k", "\u00A70").replace("`s", "\u00A77")
                .replace("`S", "\u00A78").replace("`w", "\u00A7f");

        string = string.replace("<r>", "")
                .replace("<black>", "\u00A70").replace("<navy>", "\u00A71")
                .replace("<green>", "\u00A72").replace("<teal>", "\u00A73")
                .replace("<red>", "\u00A74").replace("<purple>", "\u00A75")
                .replace("<gold>", "\u00A76").replace("<silver>", "\u00A77")
                .replace("<gray>", "\u00A78").replace("<blue>", "\u00A79")
                .replace("<lime>", "\u00A7a").replace("<aqua>", "\u00A7b")
                .replace("<rose>", "\u00A7c").replace("<pink>", "\u00A7d")
                .replace("<yellow>", "\u00A7e").replace("<white>", "\u00A7f");

        string = string.replaceAll("(§([a-fA-F0-9]))", "\u00A7$2");

        string = string.replaceAll("(&([a-fA-F0-9]))", "\u00A7$2");

        return string.replace("&&", "&");
    }

    public String removeColour(String string) {
        addColour(string);

        string = string.replaceAll("(§([a-fA-F0-9]))", "& $2");

        string = string.replaceAll("(&([a-fA-F0-9]))", "& $2");

        return string.replace("&&", "&");
    }

    @Deprecated
    public Boolean checkPermissions(Player player, String node) {
        return checkPermissions(player.getName(), player.getWorld().getName(), node)
                || player.hasPermission(node)
                || player.isOp();

    }

    public Boolean checkPermissions(Player player, World world, String node) {
        return checkPermissions(player.getName(), world.getName(), node)
                || player.hasPermission(node)
                || player.isOp();
    }

    @Deprecated
    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), player.getWorld().getName(), node))
                return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

    @Deprecated
    public Boolean checkPermissions(Player player, World world, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), world.getName(), node))
                return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

    public Boolean checkPermissions(Player player, String world, String node, Boolean useOp) {
        if (checkPermissions(player.getName(), world, node))
            return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);
    }

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

    String parseVars(String format, String pName, String world) {
        String vI = "\\" + plugin.varIndicator;
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = plugin.getInfoReader().getRawInfo(pName, world, matcher.group(1));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    String replaceVars(String format, String[] search, String[] replace) {
        if (search.length != replace.length)
            return format;

        for (int i = 0; i < search.length; i++) {
            if (search[i].contains(","))
                for (String s : search[i].split(",")) {
                    if (s == null || replace[i] == null)
                        continue;

                    format = format.replace(s, replace[i]);
                }
            else
                format = format.replace(search[i], replace[i]);
        }

        return addColour(format);
    }

    String replaceCustVars(String pName, String format) {
        SortedMap<String, String> cVarMap = plugin.cVarMap;

        for (Entry<String, String> entry : cVarMap.entrySet()) {
            String pKey = plugin.cusVarIndicator + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue();

            if (format.contains(pKey))
                format = format.replace(pKey, value);
        }

        for (Entry<String, String> entry : cVarMap.entrySet()) {
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

    public void log(Object loggedObject) {
        try {
            plugin.getServer().getConsoleSender().sendMessage(loggedObject.toString());
        } catch (IncompatibleClassChangeError ignored) {}
    }
}
