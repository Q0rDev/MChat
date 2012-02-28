package in.mDev.MiracleM4n.mChatSuite.api;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

@SuppressWarnings("unused")
public class MInfoReader {
    mChatSuite plugin;

    public MInfoReader(mChatSuite plugin) {
        this.plugin = plugin;
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
        if (plugin.useLeveledNodes)
            return getLeveledInfo(name, world, info);

        if (plugin.useOldNodes)
            return getBukkitInfo(name, world, info);

        if (plugin.useNewInfo)
            return getmChatInfo(name, type, world, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(name, type, world, info);

        if (plugin.PEXB)
            return getPEXInfo(name, type, world, info);

        if (plugin.bPermB)
            return getbPermInfo(name, type, world, info);

        return getmChatInfo(name, type, world, info);
    }

    /**
     * Raw Prefix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Prefix.
     */
    @Deprecated
    public Object getRawPrefix(Player player, World world) {
        return getRawInfo(player.getName(), InfoType.USER, world.getName(), "prefix");
    }

    /**
     * Raw Suffix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Suffix.
     */
    @Deprecated
    public Object getRawSuffix(Player player, World world) {
        return getRawInfo(player.getName(), InfoType.USER, world.getName(), "suffix");
    }

    /**
     * Raw Group Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Group.
     */
    @Deprecated
    public Object getRawGroup(Player player, World world) {
        return getRawInfo(player.getName(), InfoType.USER, world.getName(), "group");
    }

    /**
     * Formatted Info Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @param info Info Variable being resolved.
     * @return Formatted Info.
     */
    @Deprecated
    public String getInfo(Player player, World world, String info) {
        return plugin.getAPI().addColour(getRawInfo(player.getName(), InfoType.USER, world.getName(), info).toString());
    }

    /**
     * Formatted Prefix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Prefix.
     */
    @Deprecated
    public String getPrefix(Player player, World world) {
        return getInfo(player.getName(), InfoType.USER, world.getName(), "prefix");
    }

    /**
     * Formatted Suffix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Suffix.
     */
    @Deprecated
    public String getSuffix(Player player, World world) {
        return getInfo(player.getName(), InfoType.USER, world.getName(), "suffix");
    }

    /**
     * Formatted Group Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Group.
     */
    @Deprecated
    public String getGroup(Player player, World world) {
        return getInfo(player.getName(), InfoType.USER, world.getName(), "group");
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
        return plugin.getAPI().addColour(getRawInfo(name, type, world, info).toString());
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
     * mChatSuite Stuff
     */
    Object getmChatInfo(String name, InfoType type, String world, String info) {
        if (info.equals("group"))
            return getmChatGroup(name);

        String iType = type.getName();

        if (plugin.mIConfig.isSet(iType + "." + name + ".info." + info))
            return plugin.mIConfig.get(iType + "." + name + ".info." + info);

        else if (plugin.mIConfig.isSet(iType + "." + name + ".worlds." + world + "." + info))
            return plugin.mIConfig.get(iType + "." + name + ".worlds." + world + "." + info);

        return "";
    }

    Object getmChatGroup(String name) {
        if (plugin.mIConfig.isSet("users." + name + ".group"))
            return plugin.mIConfig.get("users." + name + ".group");

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

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        if (!plugin.mIConfig.isSet("rank." + info))
            return getBukkitInfo(name, world, info);

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(name, world, entry.getKey())) {
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

        return getBukkitInfo(name, world, info);
    }

    /*
     * Old Nodes Stuff
     */
    Object getBukkitInfo(String name, String world, String info) {
        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(name);

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
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

    String getPEXGroup(String name) {
        String group = plugin.pexPermissions.getUser(name).getGroupsNames()[0];

        if (group == null)
            return "";

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
        List<String> groupS = new ArrayList<String>();

        Collections.addAll(groupS, ApiLayer.getGroups(world, CalculableType.GROUP, name));

        if (groupS.size() == 0)
            return "";

        String group = "";

        try {
            group = groupS.get(0);
        } catch(Exception ignored) {}

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

        if (plugin.mIConfig.isSet("groupnames." + group))
            return plugin.mIConfig.get("groupnames." + group).toString();

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

        if (plugin.mIConfig.isSet("worldnames." + world))
            return plugin.mIConfig.get("worldnames." + world).toString();

        return world;
    }

    /**
     * Player Name Resolver
     * @param name Name of Player to be Resolved.
     * @return Player Name's mChat Alias.
     */
    public String getmName(String name) {
        if (plugin.mIConfig.isSet("mname." + name))
            if (!(plugin.mIConfig.getString("mname." + name).isEmpty()))
                return plugin.mIConfig.getString("mname." + name);

        return name;
    }

    /**
     * Event Message Resolver.
     * @param eventName Name of Event you want to grab.
     * @return Event Message.
     */
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

        return plugin.getAPI().addColour(eventName);
    }

    /**
     * Raw Prefix Resolving
     * @param name Defining value of the InfoType(Also known as Name).
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    @Deprecated
    public String getRawPrefix(String name, String world) {
        return getRawInfo(name, InfoType.USER, world, "prefix").toString();
    }
}
