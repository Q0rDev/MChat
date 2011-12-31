package in.mDev.MiracleM4n.mChatSuite;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.hero.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;

import in.mDev.MiracleM4n.mChannel.mChannel;

import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
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
    public String ParseMessage(String name, String msg, String format) {
        Player player = plugin.getServer().getPlayer(name);

        if (player == null)
            return addColour(format);

        String prefix = plugin.getInfoReader().getRawPrefix(player);
        String suffix = plugin.getInfoReader().getRawSuffix(player);
        String group = plugin.getInfoReader().getRawGroup(player);

        String vI = plugin.varIndicator;

        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (group == null)
            group = "";

        // Location
        Double locX = player.getLocation().getX();
        Double locY = player.getLocation().getY();
        Double locZ = player.getLocation().getZ();

        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);

        // Health
        String healthbar = healthBar(player);
        String health = String.valueOf(player.getHealth());

        // World
        String world = player.getWorld().getName();

        // 1.8 Vars
        String hungerLevel = String.valueOf(player.getFoodLevel());
        String hungerBar = basicBar(player.getFoodLevel(), 20, 10);
        String level = String.valueOf(player.getLevel());
        String exp = String.valueOf(player.getExp()) + "/" + ((player.getLevel() + 1) * 10);
        String expBar = basicBar(player.getExp(), ((player.getLevel() + 1) * 10), 10);
        String tExp = String.valueOf(player.getTotalExperience());
        String gMode = "";

        if (player.getGameMode() != null && player.getGameMode().name() != null)
            gMode = player.getGameMode().name();

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);

        // mChannel Vars
        String mCName = "";
        String mCPref = "";
        String mCSuf = "";
        String mCType = "";

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

        // Initialize mChannel Vars
        if (plugin.mChanB) {
            if (mChannel.API.getPlayersChannels(player)[0] != null)
                mCName = mChannel.API.getPlayersChannels(player)[0];

            mCPref = mChannel.API.getChannelPrefix(mCName);
            mCSuf = mChannel.API.getChannelSuffix(mCName);
            mCType = mChannel.API.getChannelType(mCName);
        }

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

        String formatAll = parseVars(format, player);
        String[] search;
        String[] replace;

        msg = msg.replaceAll("%", "%%");
        formatAll = formatAll.replaceAll("%", "%%");

        if (formatAll == null)
            return msg;

        if (!checkPermissions(player, "mchat.coloredchat", false))
            msg = removeColour(msg);

        if (!checkPermissions(player, "mchat.censorbypass", false))
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
                vI + "name," + vI + "n",
                vI + "prefix," + vI + "p",
                vI + "suffix," + vI + "s",
                vI + "totalexp," + vI + "texp," + vI + "te",
                vI + "time," + vI + "t",
                vI + "world," + vI + "w",
                vI + "Cname," + vI + "Cn",
                vI + "Cprefix," + vI + "Cp",
                vI + "Csuffix," + vI + "Cs",
                vI + "Ctype," + vI + "Ct",
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
                player.getDisplayName(),
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
                plugin.getInfoReader().getmName(player),
                player.getName(),
                prefix,
                suffix,
                tExp,
                time,
                world,
                mCName,
                mCPref,
                mCSuf,
                mCType,
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

        formatAll = replaceCustVars(player, formatAll);

        return replaceVars(formatAll, search, replace);
    }

    public String ParseChatMessage(Player player, String msg) {
        return ParseMessage(player.getName(), msg, plugin.chatFormat);
    }

    public String ParsePlayerName(Player player) {
        return ParseMessage(player.getName(), "", plugin.nameFormat);
    }

    public String ParseEventName(Player player) {
        return ParseMessage(player.getName(), "", plugin.eventFormat);
    }

    public String ParseTabbedList(Player player) {
        return ParseMessage(player.getName(), "", plugin.tabbedListFormat);
    }

    public String ParseListCmd(Player player) {
        return ParseMessage(player.getName(), "", plugin.listCmdFormat);
    }

    public String ParseMe(Player player, String msg) {
        return ParseMessage(player.getName(), msg, plugin.meFormat);
    }

    public String ParseChatMessage(String pName, String msg) {
        return ParseMessage(pName, msg, plugin.chatFormat);
    }

    public String ParsePlayerName(String pName) {
        return ParseMessage(pName, "", plugin.nameFormat);
    }

    public String ParseEventName(String pName) {
        if (plugin.getServer().getPlayer(pName) == null)
            return pName;

        return ParseMessage(pName, "", plugin.eventFormat);
    }

    public String ParseTabbedList(String pName) {
        return ParseMessage(pName, "", plugin.tabbedListFormat);
    }

    public String ParseListCmd(String pName) {
        return ParseMessage(pName, "", plugin.listCmdFormat);
    }

    public String ParseMe(String pName, String msg) {
        return ParseMessage(pName, msg, plugin.meFormat);
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

    public void addPlayerVar(Player player, String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put(player.getName() + "|" +var, value);
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

    public Boolean checkPermissions(Player player, String node) {
        if (plugin.gmPermissionsB)
            if (plugin.gmPermissionsWH.getWorldPermissions(player).has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        return player.hasPermission(node) || player.isOp();

    }

    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (plugin.gmPermissionsB)
            if (plugin.gmPermissionsWH.getWorldPermissions(player).has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);

    }

    String parseVars(String format, Player player) {
        String vI = "\\" + plugin.varIndicator;
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = plugin.getInfoReader().getRawInfo(player, matcher.group(1));
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

    String replaceCustVars(Player player, String format) {
        SortedMap<String, String> cVarMap = plugin.cVarMap;

        for (Entry<String, String> entry : cVarMap.entrySet()) {
            String pKey = plugin.cusVarIndicator + entry.getKey().replace(player.getName() + "|", "");
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
        for (Entry<String, Object> entry : plugin.mCConfig.getValues(false).entrySet()) {
            String val = entry.getValue().toString();

            msg = replacer(msg, "(?i)" + entry.getKey(), val);
        }

        if (plugin.useIPRestrict)
            msg = replacer(msg, "([0-9]{1,3}\\.){3}([0-9]{1,3})", "*.*.*.*");

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

    public void log(Object loggedObject) {
        try {
            plugin.getServer().getConsoleSender().sendMessage(loggedObject.toString());
        } catch (IncompatibleClassChangeError ignored) {
            System.out.println(loggedObject);
        }
    }
}
