package com.miraclem4n.mchat.api;

import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.types.EventType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import net.krinsoft.privileges.Privileges;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.Bukkit;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {
    //Info Stuff

    /**
     * Raw Info Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param world Name of the InfoType's World.
     * @param type InfoType being reflected upon.
     * @param info Info Variable being resolved.
     * @return Raw Info.
     */
    public static Object getRawInfo(String name, InfoType type, String world, String info) {
        if (type == null) {
            type = InfoType.USER;
        }

        if (world == null) {
            world = Bukkit.getServer().getWorlds().get(0).getName();
        }

        if (info == null) {
            info = "prefix";
        }

        if (ConfigType.INFO_USE_LEVELED_NODES.getBoolean()) {
            return getLeveledInfo(name, world, info);
        }

        if (ConfigType.INFO_USE_OLD_NODES.getBoolean()) {
            return getBukkitInfo(name, world, info);
        }

        if (ConfigType.INFO_USE_NEW_INFO.getBoolean()) {
            return getMChatInfo(name, type, world, info);
        }

        if (API.gmB) {
            return getGroupManagerInfo(name, type, world, info);
        }

        if (API.pexB) {
            return getPEXInfo(name, type, world, info);
        }

        if (API.bPermB) {
            return getbPermInfo(name, type, world, info);
        }

        return getMChatInfo(name, type, world, info);
    }

    /**
     * Raw Prefix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    public static Object getRawPrefix(String name, InfoType type, String world) {
        return getRawInfo(name, type, world, "prefix");
    }

    /**
     * Raw Suffix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Suffix.
     */
    public static Object getRawSuffix(String name, InfoType type, String world) {
        return getRawInfo(name, type, world, "suffix");
    }

    /**
     * Raw Group Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Group.
     */
    public static Object getRawGroup(String name, InfoType type, String world) {
        return getRawInfo(name, type, world, "group");
    }

    /**
     * Raw Info Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Player's World.
     * @param info Info Variable being resolved.
     * @return Raw Info.
     */
    public static String getInfo(String name, InfoType type, String world, String info) {
        return MessageUtil.addColour(getRawInfo(name, type, world, info).toString());
    }

    /**
     * Formatted Prefix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Prefix.
     */
    public static String getPrefix(String name, InfoType type, String world) {
        return getInfo(name, type, world, "prefix");
    }

    /**
     * Formatted Suffix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Suffix.
     */
    public static String getSuffix(String name, InfoType type, String world) {
        return getInfo(name, type, world, "suffix");
    }

    /**
     * Formatted Group Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param world Name of the InfoType's World.
     * @return Formatted Group.
     */
    public static String getGroup(String name, String world) {
        return getInfo(name, InfoType.USER, world, "group");
    }

    /*
     * MChat Stuff
     */
    private static Object getMChatInfo(String name, InfoType type, String world, String info) {
        if (info.equals("group")) {
            return getMChatGroup(name);
        }

        String iType = type.getName();

        if (InfoUtil.getConfig().isSet(iType + "." + name + ".info." + info)) {
            return InfoUtil.getConfig().get(iType + "." + name + ".info." + info);
        } else if (InfoUtil.getConfig().isSet(iType + "." + name + ".worlds." + world + "." + info)) {
            return InfoUtil.getConfig().get(iType + "." + name + ".worlds." + world + "." + info);
        }

        return "";
    }

    private static Object getMChatGroup(String name) {
        if (InfoUtil.getConfig().isSet("users." + name + ".group")) {
            return InfoUtil.getConfig().get("users." + name + ".group");
        }

        return "";
    }

    /*
     * Leveled Nodes Stuff
     */
    private static Object getLeveledInfo(String name, String world, String info) {
        HashMap<Integer, String> iMap = new HashMap<Integer, String>();

        if (API.pBukkitB) {
            if (info.equals("group")) {
                return getPermBukkitGroup(name);
            }
        }

        if (!InfoUtil.getConfig().isSet("mchat." + info)) {
            return "";
        }

        if (!InfoUtil.getConfig().isSet("rank." + info)) {
            return getBukkitInfo(name, world, info);
        }

        for (Map.Entry<String, Object> entry : InfoUtil.getConfig().getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(name, world, entry.getKey())) {
                    String rVal = entry.getKey().replaceFirst("mchat\\.", "rank.");

                    if (!InfoUtil.getConfig().isSet(rVal)) {
                        continue;
                    }

                    try {
                        iMap.put(InfoUtil.getConfig().getInt(rVal), entry.getValue().toString());
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        for (int i = 0; i < 101; ++i) {
            if (iMap.get(i) != null && !iMap.get(i).isEmpty()) {
                return iMap.get(i);
            }
        }

        return getBukkitInfo(name, world, info);
    }

    /*
     * Old Nodes Stuff
     */
    private static Object getBukkitInfo(String name, String world, String info) {
        if (API.pBukkitB) {
            if (info.equals("group")) {
                return getPermBukkitGroup(name);
            }
        } else if (API.privB) {
            if (info.equals("group")) {
                return getPrivGroup(name);
            }
        }

        if (!InfoUtil.getConfig().isSet("mchat." + info)) {
            return "";
        }

        for (Map.Entry<String, Object> entry : InfoUtil.getConfig().getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(name, world, entry.getKey())) {
                    Object infoResolve = entry.getValue();

                    if (infoResolve != null && !info.isEmpty()) {
                        return infoResolve;
                    }

                    break;
                }
            }
        }

        return "";
    }

    private static String getPermBukkitGroup(String name) {
        PermissionsPlugin pBukkit =
                (PermissionsPlugin) Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit");

        List<Group> pGroups = pBukkit.getGroups(name);

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    private static String getPrivGroup(String name) {
        Privileges priv =
                (Privileges) Bukkit.getServer().getPluginManager().getPlugin("Privileges");

        net.krinsoft.privileges.groups.Group[] pGroups = priv.getPlayerManager().getPlayer(name).getGroups();

        try {
            return pGroups[0].getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    /*
     * GroupManager Stuff
     */
    private static Object getGroupManagerInfo(String name, InfoType type, String world, String info) {
        OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);

        if (info.equals("group")) {
            return getGroupManagerGroup(name, world);
        }

        String infoString = "";

        if (type == InfoType.USER) {
            infoString = gmPermissions.getUser(name).getVariables().getVarString(info);
        }

        if (type == InfoType.GROUP) {
            infoString = gmPermissions.getGroup(name).getVariables().getVarString(info);
        }

        return infoString;
    }

    private static String getGroupManagerGroup(String name, String world) {
        OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);

        String group = gmPermissions.getUser(name).getGroup().getName();

        if (group == null) {
            return "";
        }

        return group;
    }

    /*
     * PEX Stuff
     */
    private static Object getPEXInfo(String name, InfoType type, String world, String info) {
        Object infoString = "";

        if (name == null || name.isEmpty()) {
            return infoString;
        }

        if (info.equals("group")) {
            return getPEXGroup(name);
        }

        if (type == InfoType.USER) {
            PermissionUser user = API.pexPermissions.getUser(name);

            if (info.equals("prefix")) {
                infoString = user.getPrefix(world);
            } else if (info.equals("suffix")) {
                infoString = user.getSuffix(world);
            } else {
                infoString = user.getOption(info, world);
            }
        } else if (type == InfoType.GROUP) {
            PermissionGroup group = API.pexPermissions.getGroup(name);

            if (info.equals("prefix")) {
                infoString = group.getPrefix(world);
            } else if (info.equals("suffix")) {
                infoString = group.getSuffix(world);
            } else {
                infoString = group.getOption(info, world);
            }
        }

        return infoString;
    }

    private static Object getPEXGroup(String name) {
        String[] groupNames = API.pexPermissions.getUser(name).getGroupsNames();
        String group = "";

        if (groupNames.length > 0) {
            group = API.pexPermissions.getUser(name).getGroupsNames()[0];
        }

        return group;
    }

    /*
     * bPermissions Stuff
     */
    private static Object getbPermInfo(String name, InfoType type, String world, String info) {
        if (info.equals("group")) {
            return getbPermGroup(name, world);
        }

        Object userString = "";

        if (type == InfoType.USER) {
            userString = ApiLayer.getValue(world, CalculableType.USER, name, info);
        } else if (type == InfoType.GROUP) {
            userString = ApiLayer.getValue(world, CalculableType.GROUP, name, info);
        }

        return userString;
    }

    private static Object getbPermGroup(String name, String world) {
        String[] groupNames = ApiLayer.getGroups(world, CalculableType.USER, name);
        String group = "";

        if (groupNames.length > 0) {
            group = ApiLayer.getGroups(world, CalculableType.USER, name)[0];
        }

        return group;
    }

    // Misc

    /**
     * Group Name Resolver
     * @param group Group to be Resolved.
     * @return Group Name's Alias.
     */
    public static String getGroupName(String group) {
        if (group.isEmpty()) {
            return "";
        }

        if (InfoUtil.getConfig().isSet("groupnames." + group)) {
            return InfoUtil.getConfig().getString("groupnames." + group);
        }

        return group;
    }

    /**
     * World Name Resolver
     * @param world Group to be Resolved.
     * @return World Name's Alias.
     */
    public static String getWorldName(String world) {
        if (world.isEmpty()) {
            return "";
        }

        if (InfoUtil.getConfig().isSet("worldnames." + world)) {
            return InfoUtil.getConfig().getString("worldnames." + world);
        }

        return world;
    }

    /**
     * Player Name Resolver
     * @param name Name of Player to be Resolved.
     * @return Player Name's mChat Alias.
     */
    public static String getMName(String name) {
        if (InfoUtil.getConfig().isSet("mname." + name)) {
            if (!(InfoUtil.getConfig().getString("mname." + name).isEmpty())) {
                return InfoUtil.getConfig().getString("mname." + name);
            }
        }
        
        return name;
    }

    /**
     * Event Message Resolver.
     * @param type Type of Event you want to grab.
     * @return Event Message.
     */
    public static String getEventMessage(EventType type) {
        if (type.getName().equalsIgnoreCase("join")) {
            return LocaleType.MESSAGE_EVENT_JOIN.getRaw();
        } else if (type.getName().equalsIgnoreCase("kick")) {
            return LocaleType.MESSAGE_EVENT_KICK.getRaw();
        } else if (type.getName().equalsIgnoreCase("leave")) {
            return LocaleType.MESSAGE_EVENT_LEAVE.getRaw();
        }

        return "";
    }
}
