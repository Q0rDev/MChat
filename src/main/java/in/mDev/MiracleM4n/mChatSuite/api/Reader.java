package in.mDev.MiracleM4n.mChatSuite.api;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import in.mDev.MiracleM4n.mChatSuite.configs.ConfigUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.EventType;
import in.mDev.MiracleM4n.mChatSuite.types.InfoType;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {
    mChatSuite plugin;
    YamlConfiguration config;

    public Reader(mChatSuite instance) {
        plugin = instance;

        config = ConfigUtil.getConfig();
    }

    //Info Stuff

    /**
     * Raw Info Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param world Name of the InfoType's World.
     * @param type InfoType being reflected upon.
     * @param info Info Variable being resolved.
     * @return Raw Info.
     */
    public Object getRawInfo(String name, InfoType type, String world, String info) {
        if (ConfigType.INFO_USE_LEVELED_NODES.getObject().toBoolean())
            return getLeveledInfo(name, world, info);

        if (ConfigType.INFO_USE_OLD_NODES.getObject().toBoolean())
            return getBukkitInfo(name, world, info);

        if (ConfigType.INFO_USE_NEW_INFO.getObject().toBoolean())
            return getMChatInfo(name, type, world, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(name, type, world, info);

        if (plugin.PEXB)
            return getPEXInfo(name, type, world, info);

        if (plugin.bPermB)
            return getbPermInfo(name, type, world, info);

        return getMChatInfo(name, type, world, info);
    }

    /**
     * Raw Prefix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    public Object getRawPrefix(String name, InfoType type, String world) {
        return getRawInfo(name, type, world, "prefix");
    }

    /**
     * Raw Suffix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Suffix.
     */
    public Object getRawSuffix(String name, InfoType type, String world) {
        return getRawInfo(name, type, world, "suffix");
    }

    /**
     * Raw Group Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Group.
     */
    public Object getRawGroup(String name, InfoType type, String world) {
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
    public String getInfo(String name, InfoType type, String world, String info) {
        return MessageUtil.addColour(getRawInfo(name, type, world, info).toString());
    }

    /**
     * Formatted Prefix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Prefix.
     */
    public String getPrefix(String name, InfoType type, String world) {
        return getInfo(name, type, world, "prefix");
    }

    /**
     * Formatted Suffix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param type InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Suffix.
     */
    public String getSuffix(String name, InfoType type, String world) {
        return getInfo(name, type, world, "suffix");
    }

    /**
     * Formatted Group Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param world Name of the InfoType's World.
     * @return Formatted Group.
     */
    public String getGroup(String name, String world) {
        return getInfo(name, InfoType.USER, world, "group");
    }

    /*
     * MChat Stuff
     */
    Object getMChatInfo(String name, InfoType type, String world, String info) {
        if (info.equals("group"))
            return getMChatGroup(name);

        String iType = type.getName();

        if (config.isSet(iType + "." + name + ".info." + info))
            return config.get(iType + "." + name + ".info." + info);

        else if (config.isSet(iType + "." + name + ".worlds." + world + "." + info))
            return config.get(iType + "." + name + ".worlds." + world + "." + info);

        return "";
    }

    Object getMChatGroup(String name) {
        if (config.isSet("users." + name + ".group"))
            return config.get("users." + name + ".group");

        return "";
    }

    /*
     * Leveled Nodes Stuff
     */
    Object getLeveledInfo(String name, String world, String info) {
        HashMap<Integer, String> iMap = new HashMap<Integer, String>();

        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(name);

        if (!config.isSet("mchat." + info))
            return "";

        if (!config.isSet("rank." + info))
            return getBukkitInfo(name, world, info);

        for (Map.Entry<String, Object> entry : config.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(name, world, entry.getKey())) {
                    String rVal = entry.getKey().replaceFirst("mchat\\.", "rank.");

                    if (!config.isSet(rVal))
                        continue;

                    try {
                        iMap.put(config.getInt(rVal), entry.getValue().toString());
                    } catch (NumberFormatException ignored) {}
                }
        }

        for (int i = 0; i < 101; ++i) {
            if (iMap.get(i) != null && !iMap.get(i).isEmpty())
                return iMap.get(i);
        }

        return getBukkitInfo(name, world, info);
    }

    /*
     * Old Nodes Stuff
     */
    Object getBukkitInfo(String name, String world, String info) {
        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(name);

        if (!config.isSet("mchat." + info))
            return "";

        for (Map.Entry<String, Object> entry : config.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(name, world, entry.getKey())) {
                    Object infoResolve = entry.getValue();

                    if (infoResolve != null && !info.isEmpty())
                        return infoResolve;

                    break;
                }
        }

        return "";
    }

    String getPermBukkitGroup(String name) {
        PermissionsPlugin pBukkit =
                (PermissionsPlugin) plugin.pm.getPlugin("PermissionsBukkit");

        List<Group> pGroups = pBukkit.getGroups(name);

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    /*
     * GroupManager Stuff
     */
    Object getGroupManagerInfo(String name, InfoType type, String world, String info) {
        OverloadedWorldHolder gmPermissions = plugin.gmPermissionsWH.getWorldData(world);

        if (info.equals("group"))
            return getGroupManagerGroup(name, world);

        String infoString = "";

        if (type == InfoType.USER)
            infoString = gmPermissions.getUser(name).getVariables().getVarString(info);

        if (type == InfoType.GROUP)
            infoString = gmPermissions.getGroup(name).getVariables().getVarString(info);

        return infoString;
    }

    String getGroupManagerGroup(String name, String world) {
        OverloadedWorldHolder gmPermissions = plugin.gmPermissionsWH.getWorldData(world);

        String group = gmPermissions.getUser(name).getGroup().getName();

        if (group == null)
            return "";

        return group;
    }

    /*
     * PEX Stuff
     */
    Object getPEXInfo(String name, InfoType type, String world, String info) {
        Object infoString = "";

        if (info.equals("group"))
            return getPEXGroup(name);

        if (type == InfoType.USER) {
            if (info.equals("prefix"))
                infoString = plugin.pexPermissions.getUser(name).getPrefix(world);

            else if (info.equals("suffix"))
                infoString = plugin.pexPermissions.getUser(name).getSuffix(world);

            else
                infoString = plugin.pexPermissions.getUser(name).getOption(info, world);
        } else if (type == InfoType.GROUP) {
            if (info.equals("prefix"))
                infoString = plugin.pexPermissions.getGroup(name).getPrefix(world);

            else if (info.equals("suffix"))
                infoString = plugin.pexPermissions.getGroup(name).getSuffix(world);

            else
                infoString = plugin.pexPermissions.getGroup(name).getOption(info, world);
        }

        return infoString;
    }

    Object getPEXGroup(String name) {
        String[] groupNames = plugin.pexPermissions.getUser(name).getGroupsNames();
        String group = "";

        if (groupNames.length > 0)
            group = plugin.pexPermissions.getUser(name).getGroupsNames()[0];

        return group;
    }

    /*
     * bPermissions Stuff
     */
    Object getbPermInfo(String name, InfoType type, String world, String info) {
        if (info.equals("group"))
            return getbPermGroup(name, world);

        Object userString = "";

        if (type == InfoType.USER)
            userString = ApiLayer.getValue(world, CalculableType.USER, name, info);

        else if (type == InfoType.GROUP)
            userString = ApiLayer.getValue(world, CalculableType.GROUP, name, info);

        return userString;
    }

    Object getbPermGroup(String name, String world) {
        String[] groupNames = ApiLayer.getGroups(world, CalculableType.USER, name);
        String group = "";

        if (groupNames.length > 0)
            group = ApiLayer.getGroups(world, CalculableType.USER, name)[0];

        return group;
    }

    // Misc

    /**
     * Group Name Resolver
     * @param group Group to be Resolved.
     * @return Group Name's Alias.
     */
    public String getGroupName(String group) {
        if (group.isEmpty())
            return "";

        if (config.isSet("groupnames." + group))
            return config.getString("groupnames." + group);

        return group;
    }

    /**
     * World Name Resolver
     * @param world Group to be Resolved.
     * @return World Name's Alias.
     */
    public String getWorldName(String world) {
        if (world.isEmpty())
            return "";

        if (config.isSet("worldnames." + world))
            return config.getString("worldnames." + world);

        return world;
    }

    /**
     * Player Name Resolver
     * @param name Name of Player to be Resolved.
     * @return Player Name's MChat Alias.
     */
    public String getMName(String name) {
        if (config.isSet("mname." + name))
            if (!(config.getString("mname." + name).isEmpty()))
                return config.getString("mname." + name);

        return name;
    }

    /**
     * Event Message Resolver.
     * @param type Type of Event you want to grab.
     * @return Event Message.
     */
    public String getEventMessage(EventType type) {
        String event = "";

        if (type.getName().equalsIgnoreCase("join"))
            event = ConfigType.MESSAGE_JOIN.getObject().toString();

        else if (type.getName().equalsIgnoreCase("kick"))
            event = ConfigType.MESSAGE_KICK.getObject().toString();

        else if (type.getName().equalsIgnoreCase("leave"))
            event = ConfigType.MESSAGE_LEAVE.getObject().toString();

        return MessageUtil.addColour(event);
    }
}
