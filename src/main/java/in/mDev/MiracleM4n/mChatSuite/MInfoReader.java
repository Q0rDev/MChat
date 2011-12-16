package in.mDev.MiracleM4n.mChatSuite;

import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MInfoReader {
    mChatSuite plugin;

    public MInfoReader(mChatSuite plugin) {
        this.plugin = plugin;
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

    public String getmName(Player player) {
        if (plugin.mIConfig.isSet("mname." + player.getName()))
            if (!(plugin.mIConfig.getString("mname." + player.getName()).isEmpty()))
                return plugin.mIConfig.getString("mname." + player.getName());

        return player.getName();
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
        return plugin.getAPI().addColour(getRawInfo(player, info));
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
            return plugin.mIConfig.getString("users." + pName + ".info." + info);

        else if (plugin.mIConfig.isSet("users." + pName + ".worlds." + world + "." + info))
            return plugin.mIConfig.getString("users." + pName + ".worlds." + world + "." + info);

        return "";
    }


    String getmChatGroupInfo(Player player, String info) {
        String world = player.getWorld().getName();
        String group = getmChatGroup(player);

        if (plugin.mIConfig.isSet("groups." + group + ".info." + info))
            return plugin.mIConfig.getString("groups." + group + ".info." + info);

        else if (plugin.mIConfig.isSet("groups." + group + ".worlds." + world + "." + info))
            return plugin.mIConfig.getString("groups." + group + ".worlds." + world + "." + info);

        return "";
    }

    String getmChatGroup(Player player) {
        String pName = player.getName();
        if (plugin.mIConfig.isSet("users." + pName + ".group"))
            return plugin.mIConfig.getString("users." + pName + ".group");

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
            return getBukkitInfo(player, info);

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(player, entry.getKey(), false)) {
                    String rVal = entry.getKey().replaceFirst("mchat\\.", "rank.");

                    if (!plugin.mIConfig.isSet(rVal))
                        continue;

                    try {
                        iMap.put(plugin.mIConfig.getInt(rVal), entry.getValue().toString());
                    } catch (NumberFormatException ignored) {
                    }
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

        for (Map.Entry<String, Object> entry : plugin.mIConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + "."))
                if (plugin.getAPI().checkPermissions(player, entry.getKey(), false)) {
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
        PermissionsPlugin pBukkit = (PermissionsPlugin) pPlugin;
        List<Group> pGroups = pBukkit.getGroups(player.getName());

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    /*
     * GroupManager Stuff
     */
    String getGroupManagerInfo(Player player, String info) {
        AnjoPermissionsHandler gmPermissions = plugin.gmPermissionsWH.getWorldPermissions(player);

        if (info.equals("group"))
            return getGroupManagerGroups(player);

        String pName = player.getName();
        String group = gmPermissions.getGroup(pName);
        String userString = gmPermissions.getUserPermissionString(pName, info);

        if (userString != null && !userString.isEmpty())
            return userString;

        if (group == null)
            return "";

        return gmPermissions.getGroupPermissionString(group, info);
    }

    String getGroupManagerGroups(Player player) {
        AnjoPermissionsHandler gmPermissions = plugin.gmPermissionsWH.getWorldPermissions(player);

        String pName = player.getName();
        String group = gmPermissions.getGroup(pName);

        if (group == null)
            return "";

        return group;
    }

    /*
     * PEX Stuff
     */
    String getPEXInfo(Player player, String info) {
        String world = player.getWorld().getName();
        String userString;

        if (info.equals("group"))
            return getPEXGroup(player);

        else if (info.equals("prefix"))
            userString = plugin.pexPermissions.getUser(player).getPrefix(world);

        else if (info.equals("suffix"))
            userString = plugin.pexPermissions.getUser(player).getSuffix(world);

        else
            userString = plugin.pexPermissions.getUser(player).getOption(info, world);

        if (userString != null)
            return userString;

        return "";
    }

    String getPEXGroup(Player player) {
        String group = plugin.pexPermissions.getUser(player).getGroupsNames()[0];

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
    * Misc
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
