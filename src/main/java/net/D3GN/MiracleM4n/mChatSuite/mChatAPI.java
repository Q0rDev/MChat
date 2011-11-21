package net.D3GN.MiracleM4n.mChatSuite;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dev.heroes.hero.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;

import com.nijikokun.register.payment.Methods;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import net.D3GN.MiracleM4n.mChannel.mChannel;

import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

@SuppressWarnings("unused")
public class mChatAPI {
    mChatSuite plugin;

    public mChatAPI(mChatSuite plugin) {
        this.plugin = plugin;
    }

    /*
     * Format Stuff
     */
    public String ParseChatMessage(String pName, String msg, String formatAll) {
        Player player = plugin.getServer().getPlayer(pName);

        if (player == null)
            return addColour(formatAll);

        String prefix = getRawPrefix(player);
        String suffix = getRawSuffix(player);
        String group = getRawGroup(player);

        String vI = plugin.varIndicator;

        if (prefix == null)
            prefix = "";

        if (suffix == null)
            suffix = "";

        if (group == null)
            group = "";

        // Location
        Integer locX = (int) player.getLocation().getX();
        Integer locY = (int) player.getLocation().getY();
        Integer locZ = (int) player.getLocation().getZ();
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
        String exp = String.valueOf(player.getExperience()) + "/" + ((player.getLevel() + 1) * 10);
        String expBar = basicBar(player.getExperience(), ((player.getLevel() + 1) * 10), 10);
        String tExp = String.valueOf(player.getTotalExperience());
        String gMode = player.getGameMode().name();

        // Time Var
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(plugin.dateFormat);
        String time = dateFormat.format(now);

        // mChannel Vars
        String mCName = "";
        String mCPref = "";
        String mCSuf = "";
        String mCType = "";

        // Money Var/s
        String money = "";

        // Heroes Vars
        String hClass = "";
        String hHealth = "";
        String hHBar = "";
        String hMana = "";
        String hMBar = "";
        String hParty = "";
        String hMastered = "";
        String hLevel = "";
        String hExp = "";
        String hEBar = "";

        // SimpleClans Vars


        // Initialize mChannel Vars
        if (plugin.mChanB) {
            mCName = mChannel.API.getPlayersChannel(player);
            mCPref = mChannel.API.getChannelPrefix(mCName);
            mCSuf = mChannel.API.getChannelSuffix(mCName);
            mCType = mChannel.API.getChannelType(mCName);
        }

        // Initialize Money Var/s
        if (plugin.regB)
            money = "$" + Methods.getMethod().getAccount(pName).balance();

        // Initialize Heroes Vars
        if (plugin.heroesB) {
            Hero hero = plugin.heroes.getHeroManager().getHero(player);

            int hL = Properties.getLevel(hero.getExperience());
            double hE = Properties.getExperience(hL);

            hClass = hero.getHeroClass().getName();
            hHealth = String.valueOf(hero.getHealth());
            hHBar = Messaging.createHealthBar((float) hero.getHealth(), (float) hero.getMaxHealth());
            hMana = String.valueOf(hero.getMana());
            hMBar = Messaging.createManaBar(hero.getMana());
            hLevel = String.valueOf(hL);
            hExp = String.valueOf(hE);
            hEBar = Messaging.createExperienceBar(hero, hero.getHeroClass());

            if (hero.getParty() != null)
                hParty = hero.getParty().toString();

            if (hero.isMaster())
                hMastered = plugin.hMasterT;
            else
                hMastered = plugin.hMasterF;
        }

        // Initialize SimpleClans Vars
        if (plugin.sClanB) {
            ClanPlayer cPlayer = plugin.sClan.getClanManager().getClanPlayer(player);
        }

        String format = parseVars(formatAll, player);
        String[] search;
        String[] replace;

        msg = msg.replaceAll("%", "%%");
        format = format.replaceAll("%", "%%");

        if (format == null)
            return msg;

        if (!checkPermissions(player, "mchat.coloredchat", false))
            msg = removeColour(msg);

        if (!checkPermissions(player, "mchat.censorbypass", false))
            msg = replaceCensoredWords(msg);

        search = new String[] {
                vI+"mname,"+vI+"mn",
                vI+"displayname,"+vI+"dname,"+vI+"dn",
                vI+"experiencebar,"+vI+"expb,"+vI+"ebar,"+vI+"eb",
                vI+"experience,"+vI+"exp",
                vI+"gamemode,"+vI+"gm",
                vI+"group,"+vI+"g",
                vI+"hungerbar,"+vI+"hub",
                vI+"hunger",
                vI+"healthbar,"+vI+"hb",
                vI+"health,"+vI+"h",
                vI+"location,"+vI+"loc",
                vI+"level,"+vI+"l",
                vI+"money,"+vI+"mon",
                vI+"name,"+vI+"n",
                vI+"prefix,"+vI+"p",
                vI+"suffix,"+vI+"s",
                vI+"totalexp,"+vI+"texp,"+vI+"te",
                vI+"time,"+vI+"t",
                vI+"world,"+vI+"w",
                vI+"Cname,"+vI+"Cn",
                vI+"Cprefix,"+vI+"Cp",
                vI+"Csuffix,"+vI+"Cs",
                vI+"Ctype,"+vI+"Ct",
                vI+"Groupname,"+vI+"Gname,"+vI+"G",
                vI+"HClass,"+vI+"HC",
                vI+"HExp,"+vI+"HEx",
                vI+"HEBar,"+vI+"HEb",
                vI+"HHBar,"+vI+"HHB",
                vI+"HHealth,"+vI+"HH",
                vI+"HLevel,"+vI+"HL",
                vI+"HMastered,"+vI+"HMa",
                vI+"HMana,"+vI+"HMn",
                vI+"HMBar,"+vI+"HMb",
                vI+"HParty,"+vI+"HPa",
                vI+"Worldname,"+vI+"Wname,"+vI+"W",
                vI+"message,"+vI+"msg,"+vI+"m"
            };

        replace = new String[] {
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
                money,
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
                getGroupName(group),
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
                getWorldName(world),
                msg
            };

        return replaceVars(format, search, replace);
    }

    public String ParseChatMessage(Player player, String msg) {
        return ParseChatMessage(player.getName(), msg, plugin.chatFormat);
    }

    public String ParsePlayerName(Player player) {
        return ParseChatMessage(player.getName(), "", plugin.nameFormat);
    }

    public String ParseEventName(Player player) {
        return ParseChatMessage(player.getName(), "", plugin.eventFormat);
    }

    public String ParsePlayerList(Player player) {
        return ParseChatMessage(player.getName(), "", plugin.listFormat).replaceAll("(§([a-z0-9]))", "");
    }

    public String ParseChatMessage(String pName, String msg) {
        return ParseChatMessage(pName, msg, plugin.chatFormat);
    }

    public String ParsePlayerName(String pName) {
        return ParseChatMessage(pName, "", plugin.nameFormat);
    }

    public String ParseEventName(String pName) {
        if (plugin.getServer().getPlayer(pName) == null)
            return pName;

        return ParseChatMessage(pName, "", plugin.eventFormat);
    }

    public String ParsePlayerList(String pName) {
        return ParseChatMessage(pName, "", plugin.listFormat).replaceAll("(§([a-z0-9]))", "");
    }

    public String getGroupName(String group) {
        if (group.isEmpty())
            return "";

        if (plugin.mIConfig.isSet("groupnames." + group))
            return plugin.mIConfig.get("groupnames." + group).toString();

        return group;
    }

    public String getWorldName(String world) {
        if (world.isEmpty())
            return "";

        if (plugin.mIConfig.isSet("worldnames." + world))
            return plugin.mIConfig.get("worldnames." + world).toString();

        return world;
    }

    /*
     * Info Stuff
     */
    public String getRawInfo(Player player, String info) {
        if (plugin.useLeveledNodes)
            return getLeveledInfo(player, info);

        if (plugin.useOldNodes)
            return getBukkitInfo(player, info);

        if (plugin.useNewInfo)
            return getmChatInfo(player, info);

        if (plugin.permissionsB)
            return getPermissionsInfo(player, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(player, info);

        if (plugin.PEXB)
            return getPEXInfo(player, info);

        if (plugin.bPermB)
            return getbPermInfo(player, info);

        return getmChatInfo(player, info);
    }

    public String getRawPrefix(Player player) {
        return getRawInfo(player, "prefix");
    }

    public String getRawSuffix(Player player) {
        return getRawInfo(player, "suffix");
    }

    public String getRawGroup(Player player) {
        return getRawInfo(player, "group");
    }

    public String getInfo(Player player, String info) {
        return addColour(getRawInfo(player, info));
    }

    public String getPrefix(Player player) {
        return getInfo(player, "prefix");
    }

    public String getSuffix(Player player) {
        return getInfo(player, "suffix");
    }

    public String getGroup(Player player) {
        return getInfo(player, "group");
    }

    /*
     * mChatSuite Stuff
     */
    String getmChatInfo(Player player, String info) {
        if (info.equals("group"))
            if (getmChatGroup(player) != null)
                return getmChatGroup(player);

        if (getmChatPlayerInfo(player, info).isEmpty())
            return getmChatGroupInfo(player, info);

        return getmChatPlayerInfo(player, info);
    }

    String getmChatPlayerInfo(Player player, String info) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.mIConfig.isSet("users." + pName + ".info." + info))
            return plugin.mIConfig.get("users." + pName + ".info." + info).toString();

        else if (plugin.mIConfig.isSet("users." + pName + ".worlds." + world + "." + info))
            return plugin.mIConfig.get("users." + pName + ".worlds." + world + "." + info).toString();

        return "";
    }


    String getmChatGroupInfo(Player player, String info) {
        String world = player.getWorld().getName();
        String group = getmChatGroup(player);

        if (plugin.mIConfig.isSet("groups." + group + ".info." + info))
            return plugin.mIConfig.get("groups." + group + ".info." + info).toString();

        else if (plugin.mIConfig.isSet("groups." + group + ".worlds." + world + "." + info))
            return plugin.mIConfig.get("groups." + group + ".worlds." + world + "." + info).toString();

        return "";
    }

    String getmChatGroup(Player player) {
        String pName = player.getName();
        if (plugin.mIConfig.isSet("users." + pName + ".group"))
            return plugin.mIConfig.get("users." + pName + ".group").toString();

        return "";
    }

    /*
     * Leveled Nodes Stuff
     */
    String getLeveledInfo(Player player, String info) {
        HashMap<Integer, String> iMap = new HashMap<Integer, String>();

        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(player);

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        if (!plugin.mIConfig.isSet("rank." + info))
            return "";

        for (Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (checkPermissions(player, entry.getKey(), false)) {
                    String rVal = entry.getKey().replaceFirst("mchat\\.", "rank.");

                    if (!plugin.mIConfig.isSet(rVal))
                        continue;

                    try {
                        iMap.put(plugin.mIConfig.getInt(rVal), entry.getValue().toString());
                    } catch (NumberFormatException ignored) {}
                }
        }

        for (int i = 0; i < 101; ++i) {
            if (iMap.get(i) != null && !iMap.get(i).isEmpty())
                return iMap.get(i);
        }

        return getBukkitInfo(player, info);
    }

    /*
     * Old Nodes Stuff
     */
    String getBukkitInfo(Player player, String info) {
        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(player);

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        for (Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (checkPermissions(player, entry.getKey(), false)) {
                    String infoResolve = entry.getValue().toString();

                    if (infoResolve != null && !info.isEmpty())
                        return infoResolve;

                    break;
                }
        }

        return "";
    }

    String getPermBukkitGroup(Player player) {
        Plugin pPlugin = plugin.pm.getPlugin("PermissionsBukkit");
        PermissionsPlugin pBukkit = (PermissionsPlugin)pPlugin;
        List<Group> pGroups = pBukkit.getGroups(player.getName());

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    /*
     * Permissions Stuff
     */
    @SuppressWarnings("deprecation")
    String getPermissionsInfo(Player player, String info) {
        if (info.equals("group"))
            return getPermissionsGroup(player);

        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.permissions3) {
            String userString = plugin.permissions.getInfoString(world, pName, info, false);
            String group = plugin.permissions.getPrimaryGroup(world, pName);

            if (userString != null && !userString.isEmpty())
                return userString;

            if (group == null)
                return "";

            String groupString = plugin.permissions.getInfoString(world, group, info, true);
            if (groupString == null)
                return "";

            return groupString;
        } else {
            String group = plugin.permissions.getGroup(world, pName);
            String userString = plugin.permissions.getUserPermissionString(world, pName, info);
            if (userString != null && !userString.isEmpty())
                return userString;

            if (group == null)
                return "";

            return plugin.permissions.getGroupPermissionString(world, group, info);
        }
    }

    @SuppressWarnings("deprecation")
    String getPermissionsGroup(Player player) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (plugin.permissions3) {
            String group = plugin.permissions.getPrimaryGroup(world, pName);

            if (group == null)
                return "";

            return group;
        } else {
            String group = plugin.permissions.getGroup(world, pName);

            if (group == null)
                return "";

            return group;
        }
    }

    /*
     * GroupManager Stuff
     */
    String getGroupManagerInfo(Player player, String info) {
        if (info.equals("group"))
            return getGroupManagerGroup(player);

        String pName = player.getName();
        String group = plugin.gmPermissions.getGroup(pName);
        String userString = plugin.gmPermissions.getUserPermissionString(pName, info);

        if (userString != null && !userString.isEmpty())
            return userString;

        if (group == null)
            return "";

        return plugin.gmPermissions.getGroupPermissionString(group, info);
    }

    String getGroupManagerGroup(Player player) {
        String pName = player.getName();
        String group = plugin.gmPermissions.getGroup(pName);

        if (group == null)
            return "";

        return group;
    }

    /*
     * PEX Stuff
     */
    String getPEXInfo(Player player, String info) {
        if (info.equals("group"))
            return getPEXGroup(player);

        String pName = player.getName();
        String world = player.getWorld().getName();

        String userString = plugin.pexPermissions.getUser(pName).getOption(info, world);
        if (userString != null && !userString.isEmpty())
            return userString;

        return "";
    }

    String getPEXGroup(Player player) {
        String pName = player.getName();
        String world = player.getWorld().getName();

        String group = plugin.pexPermissions.getUser(pName).getGroupsNames(world)[0];

        if (group == null)
            return "";

        return group;
    }

    /*
     * bPermissions Stuff
     */
    String getbPermInfo(Player player, String info) {
        if (info.equals("group"))
            return getbPermGroup(player);

        String userString = plugin.bInfoR.getValue(player, info);
        if (userString != null && !userString.isEmpty())
            return userString;

        return "";
    }

    String getbPermGroup(Player player) {
        String group = plugin.bPermS.getPermissionSet(player.getWorld()).getGroups(player).get(0);

        if (group == null)
            return "";

        return group;
    }

    /*
     * Misc Stuff
     */
    public String healthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();

        return basicBar(health, maxHealth, barLength);
    }

    public String basicBar(float currentValue, float maxValue, float barLength) {
        int fill = Math.round((currentValue / maxValue) * barLength);

        String barColor = (fill <= (barLength/4)) ? "&4" : (fill <= (barLength/7)) ? "&e" : "&2";

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
                        .replace("`r", ChatColor.RED.toString())             .replace("`R", ChatColor.DARK_RED.toString())
                        .replace("`y", ChatColor.YELLOW.toString())          .replace("`Y", ChatColor.GOLD.toString())
                        .replace("`g", ChatColor.GREEN.toString())           .replace("`G", ChatColor.DARK_GREEN.toString())
                        .replace("`a", ChatColor.AQUA.toString())            .replace("`A", ChatColor.DARK_AQUA.toString())
                        .replace("`b", ChatColor.BLUE.toString())            .replace("`B", ChatColor.DARK_BLUE.toString())
                        .replace("`p", ChatColor.LIGHT_PURPLE.toString())    .replace("`P", ChatColor.DARK_PURPLE.toString())
                        .replace("`k", ChatColor.BLACK.toString())           .replace("`s", ChatColor.GRAY.toString())
                        .replace("`S", ChatColor.DARK_GRAY.toString())       .replace("`w", ChatColor.WHITE.toString());

        string = string.replace("<r>", "")
                        .replace("<black>", "\u00A70")                       .replace("<navy>", "\u00A71")
                        .replace("<green>", "\u00A72")                       .replace("<teal>", "\u00A73")
                        .replace("<red>", "\u00A74")                         .replace("<purple>", "\u00A75")
                        .replace("<gold>", "\u00A76")                        .replace("<silver>", "\u00A77")
                        .replace("<gray>", "\u00A78")                        .replace("<blue>", "\u00A79")
                        .replace("<lime>", "\u00A7a")                        .replace("<aqua>", "\u00A7b")
                        .replace("<rose>", "\u00A7c")                        .replace("<pink>", "\u00A7d")
                        .replace("<yellow>", "\u00A7e")                      .replace("<white>", "\u00A7f");

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
        if (plugin.permissionsB)
            if (plugin.permissions.has(player, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissions.has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        return player.hasPermission(node) || player.isOp();

    }

    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (plugin.permissionsB)
            if (plugin.permissions.has(player, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissions.has(player, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(player, node))
                return true;

        if (useOp)
            if (player.isOp())
                return true;

        return player.hasPermission(node);

    }

    public String getEventMessage(String eventName) {
        if (eventName.equalsIgnoreCase("join"))
            eventName = plugin.joinMessage;

        if (eventName.equalsIgnoreCase("enter"))
            eventName = plugin.joinMessage;

        if (eventName.equalsIgnoreCase("kick"))
            eventName = plugin.kickMessage;

        if (eventName.equalsIgnoreCase("quit"))
            eventName = plugin.leaveMessage;

        if (eventName.equalsIgnoreCase("leave"))
            eventName = plugin.leaveMessage;

        return plugin.mAPI.addColour(eventName);
    }

    String parseVars(String format, Player player) {
        String vI = "\\" + plugin.varIndicator;
        Pattern pattern = Pattern.compile(vI + "<(.*?)>");
        Matcher matcher = pattern.matcher(format);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String var = getRawInfo(player, matcher.group(1));
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

    String replaceCensoredWords(String msg) {
        for (Entry<String, Object> entry : plugin.mCConfig.getValues(false).entrySet()) {
            Pattern pattern = Pattern.compile("(?i)" + entry.getKey());
            Matcher matcher = pattern.matcher(msg);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                String var = entry.getValue().toString();
                matcher.appendReplacement(sb, Matcher.quoteReplacement(var));
            }

            matcher.appendTail(sb);

            msg = sb.toString();
        }

        return msg;
    }

    public void log(String loggedString) {
        plugin.getServer().getConsoleSender().sendMessage(loggedString);
    }
}
