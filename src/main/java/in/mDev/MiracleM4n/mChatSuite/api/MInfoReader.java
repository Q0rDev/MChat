package in.mDev.MiracleM4n.mChatSuite.api;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

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
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @param info Info Variable being resolved.
     * @return Raw Info.
     */
    public String getRawInfo(String pName, String world, String info) {
        if (plugin.useLeveledNodes)
            return getLeveledInfo(pName, world, info);

        if (plugin.useOldNodes)
            return getBukkitInfo(pName, world, info);

        if (plugin.useNewInfo)
            return getmChatInfo(pName, world, info);

        if (plugin.gmPermissionsB)
            return getGroupManagerInfo(pName, info);

        if (plugin.PEXB)
            return getPEXInfo(pName, world, info);

        if (plugin.bPermB)
            return getbPermInfo(pName, world, info);

        return getmChatInfo(pName, world, info);
    }

    /**
     * Raw Prefix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Prefix.
     */
    @Deprecated
    public String getRawPrefix(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "prefix");
    }

    /**
     * Raw Suffix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Suffix.
     */
    @Deprecated
    public String getRawSuffix(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "suffix");
    }

    /**
     * Raw Group Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Raw Group.
     */
    @Deprecated
    public String getRawGroup(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "group");
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
        return plugin.getAPI().addColour(getRawInfo(player.getName(), world.getName(), info));
    }

    /**
     * Formatted Prefix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Prefix.
     */
    @Deprecated
    public String getPrefix(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "prefix");
    }

    /**
     * Formatted Suffix Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Suffix.
     */
    @Deprecated
    public String getSuffix(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "suffix");
    }

    /**
     * Formatted Group Resolving
     * @param player Player being reflected upon.
     * @param world Player's World.
     * @return Formatted Group.
     */
    @Deprecated
    public String getGroup(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "group");
    }

    /**
     * Raw Prefix Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Raw Prefix.
     */
    public String getRawPrefix(String pName, String world) {
        return getRawInfo(pName, world, "prefix");
    }

    /**
     * Raw Suffix Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Raw Suffix.
     */
    public String getRawSuffix(String pName, String world) {
        return getRawInfo(pName, world, "suffix");
    }

    /**
     * Raw Group Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Raw Group.
     */
    public String getRawGroup(String pName, String world) {
        return getRawInfo(pName, world, "group");
    }

    /**
     * Raw Info Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Player's World.
     * @param info Info Variable being resolved.
     * @return Raw Info.
     */
    public String getInfo(String pName, String world, String info) {
        return plugin.getAPI().addColour(getRawInfo(pName, world, info));
    }

    /**
     * Formatted Prefix Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Prefix.
     */
    public String getPrefix(String pName, String world) {
        return getInfo(pName, world, "prefix");
    }

    /**
     * Formatted Suffix Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Suffix.
     */
    public String getSuffix(String pName, String world) {
        return getInfo(pName, world, "suffix");
    }

    /**
     * Formatted Group Resolving
     * @param pName Name of Player being reflected upon.
     * @param world Name of Player's World.
     * @return Formatted Group.
     */
    public String getGroup(String pName, String world) {
        return getInfo(pName, world, "group");
    }

    /*
     * mChatSuite Stuff
     */
    String getmChatInfo(String pName, String world, String info) {
        if (info.equals("group"))
            if (getmChatGroup(pName) != null)
                return getmChatGroup(pName);

        if (getmChatPlayerInfo(pName, world, info).isEmpty())
            return getmChatGroupInfo(pName, world, info);

        return getmChatPlayerInfo(pName, world, info);
    }

    String getmChatPlayerInfo(String pName, String world, String info) {
        if (plugin.mIConfig.isSet("users." + pName + ".info." + info))
            return plugin.mIConfig.getString("users." + pName + ".info." + info);

        else if (plugin.mIConfig.isSet("users." + pName + ".worlds." + world + "." + info))
            return plugin.mIConfig.getString("users." + pName + ".worlds." + world + "." + info);

        return "";
    }


    String getmChatGroupInfo(String pName, String world, String info) {
        String group = getmChatGroup(pName);

        if (plugin.mIConfig.isSet("groups." + group + ".info." + info))
            return plugin.mIConfig.getString("groups." + group + ".info." + info);

        else if (plugin.mIConfig.isSet("groups." + group + ".worlds." + world + "." + info))
            return plugin.mIConfig.getString("groups." + group + ".worlds." + world + "." + info);

        return "";
    }

    String getmChatGroup(String pName) {
        if (plugin.mIConfig.isSet("users." + pName + ".group"))
            return plugin.mIConfig.getString("users." + pName + ".group");

        return "";
    }

    /*
     * Leveled Nodes Stuff
     */
    String getLeveledInfo(String pName, String world, String info) {
        HashMap<Integer, String> iMap = new HashMap<Integer, String>();

        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(pName);

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        if (!plugin.mIConfig.isSet("rank." + info))
            return getBukkitInfo(pName, world, info);

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(pName, world, entry.getKey())) {
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

        return getBukkitInfo(pName, world, info);
    }

    /*
     * Old Nodes Stuff
     */
    String getBukkitInfo(String pName, String world, String info) {
        if (plugin.PermissionBuB)
            if (info.equals("group"))
                return getPermBukkitGroup(pName);

        if (!plugin.mIConfig.isSet("mchat." + info))
            return "";

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(pName, world, entry.getKey())) {
                    String infoResolve = entry.getValue().toString();

                    if (infoResolve != null && !info.isEmpty())
                        return infoResolve;

                    break;
                }
        }

        return "";
    }

    String getPermBukkitGroup(String pName) {
        PermissionsPlugin pBukkit =
                (PermissionsPlugin) plugin.pm.getPlugin("PermissionsBukkit");

        List<Group> pGroups = pBukkit.getGroups(pName);

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    /*
     * GroupManager Stuff
     */
    String getGroupManagerInfo(String pName, String info) {
        AnjoPermissionsHandler gmPermissions = plugin.gmPermissionsWH.getWorldPermissions(pName);

        if (info.equals("group"))
            return getGroupManagerGroups(pName);

        String group = gmPermissions.getGroup(pName);
        String userString = gmPermissions.getUserPermissionString(pName, info);

        if (userString != null && !userString.isEmpty())
            return userString;

        if (group == null)
            return "";

        return gmPermissions.getGroupPermissionString(group, info);
    }

    String getGroupManagerGroups(String pName) {
        AnjoPermissionsHandler gmPermissions = plugin.gmPermissionsWH.getWorldPermissions(pName);

        String group = gmPermissions.getGroup(pName);

        if (group == null)
            return "";

        return group;
    }

    /*
     * PEX Stuff
     */
    String getPEXInfo(String pName, String world, String info) {
        String userString;

        if (info.equals("group"))
            return getPEXGroup(pName);

        else if (info.equals("prefix"))
            userString = plugin.pexPermissions.getUser(pName).getPrefix(world);

        else if (info.equals("suffix"))
            userString = plugin.pexPermissions.getUser(pName).getSuffix(world);

        else
            userString = plugin.pexPermissions.getUser(pName).getOption(info, world);

        if (userString != null)
            return userString;

        return "";
    }

    String getPEXGroup(String pName) {
        String group = plugin.pexPermissions.getUser(pName).getGroupsNames()[0];

        if (group == null)
            return "";

        return group;
    }

    /*
     * bPermissions Stuff
     */
    String getbPermInfo(String pName, String world, String info) {
        if (info.equals("group"))
            return getbPermGroup(pName, world);

        String userString = ApiLayer.getValue(world, CalculableType.USER, pName, info);

        if (userString != null)
            if (!userString.isEmpty())
                return userString;

        return "";
    }

    String getbPermGroup(String pName, String world) {
        List<String> groupS = new ArrayList<String>();

        Collections.addAll(groupS, ApiLayer.getGroups(world, CalculableType.GROUP, pName));

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
     * @param pName Name of Player to be Resolved.
     * @return Player Name's mChat Alias.
     */
    public String getmName(String pName) {
        if (plugin.mIConfig.isSet("mname." + pName))
            if (!(plugin.mIConfig.getString("mname." + pName).isEmpty()))
                return plugin.mIConfig.getString("mname." + pName);

        return pName;
    }

    /**
     * Event Message Resolver.
     * @param eventName Type of Base you want to set.
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
}
