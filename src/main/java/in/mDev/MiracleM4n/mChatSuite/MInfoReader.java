package in.mDev.MiracleM4n.mChatSuite;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class MInfoReader {
    mChatSuite plugin;

    public MInfoReader(mChatSuite plugin) {
        this.plugin = plugin;
    }

    /*
     * Info Stuff
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

    public String getRawPrefix(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "prefix");
    }

    public String getRawSuffix(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "suffix");
    }

    public String getRawGroup(Player player, World world) {
        return getRawInfo(player.getName(), world.getName(), "group");
    }

    public String getInfo(Player player, World world, String info) {
        return plugin.getAPI().addColour(getRawInfo(player.getName(), world.getName(), info));
    }

    public String getPrefix(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "prefix");
    }

    public String getSuffix(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "suffix");
    }

    public String getGroup(Player player, World world) {
        return getInfo(player.getName(), world.getName(), "group");
    }

    public String getRawPrefix(String pName, String world) {
        return getRawInfo(pName, world, "prefix");
    }

    public String getRawSuffix(String pName, String world) {
        return getRawInfo(pName, world, "suffix");
    }

    public String getRawGroup(String pName, String world) {
        return getRawInfo(pName, world, "group");
    }

    public String getInfo(String pName, String world, String info) {
        return plugin.getAPI().addColour(getRawInfo(pName, world, info));
    }

    public String getPrefix(String pName, String world) {
        return getInfo(pName, world, "prefix");
    }

    public String getSuffix(String pName, String world) {
        return getInfo(pName, world, "suffix");
    }

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
        Plugin pPlugin = plugin.pm.getPlugin("PermissionsBukkit");
        PermissionsPlugin pBukkit = (PermissionsPlugin) pPlugin;
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

        String userString = plugin.bInfoR.getValue(pName, world, info);
        if (userString != null && !userString.isEmpty())
            return userString;

        return "";
    }

    String getbPermGroup(String pName, String world) {
        String group = plugin.bPermS.getPermissionSet(world).getGroups(pName).get(0);

        if (group == null)
            return "";

        return group;
    }

    /*
    * Misc
    */
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

    public String getmName(String pName) {
        if (plugin.mIConfig.isSet("mname." + pName))
            if (!(plugin.mIConfig.getString("mname." + pName).isEmpty()))
                return plugin.mIConfig.getString("mname." + pName);

        return pName;
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

        return plugin.getAPI().addColour(eventName);
    }
}
