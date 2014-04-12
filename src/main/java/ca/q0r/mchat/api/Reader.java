package ca.q0r.mchat.api;

import ca.q0r.mchat.types.EventType;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.types.PluginType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.locale.LocaleType;
import com.platymuus.bukkit.permissions.Group;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.CalculableType;
import net.krinsoft.privileges.Privileges;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class used to read from <b>info.yml</b> and various other Plugins.
 */
public class Reader {
    /**
     * Raw Info Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @param type  InfoType being reflected upon.
     * @param info  Info Variable being resolved.
     * @return Raw Info.
     */
    public static String getRawInfo(UUID uuid, InfoType type, String world, String info) {
        if (uuid == null) {
            return "";
        }

        if (type == null) {
            type = InfoType.USER;
        }

        if (world == null) {
            world = Bukkit.getServer().getWorlds().get(0).getName();
        }

        if (info == null) {
            info = "prefix";
        }

        if (API.isPluginEnabled(PluginType.LEVELED_NODES)) {
            return getLeveledInfo(uuid, world, info);
        } else if (API.isPluginEnabled(PluginType.OLD_NODES)) {
            return getBukkitInfo(uuid, world, info);
        } else if (API.isPluginEnabled(PluginType.NEW_INFO)) {
            return getMChatInfo(uuid, type, world, info);
        } else if (API.isPluginEnabled(PluginType.GROUP_MANAGER)) {
            return getGroupManagerInfo(uuid, type, world, info);
        } else if (API.isPluginEnabled(PluginType.PERMISSIONS_EX)) {
            return getPEXInfo(uuid, type, world, info);
        } else if (API.isPluginEnabled(PluginType.BPERMISSIONS)) {
            return getbPermInfo(uuid, type, world, info);
        } else if (API.isPluginEnabled(PluginType.VAULT_CHAT)) {
            return getVaultInfo(uuid, type, world, info);
        }

        return getMChatInfo(uuid, type, world, info);
    }

    /**
     * Raw Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    public static String getRawPrefix(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, type, world, "prefix");
    }

    /**
     * Raw Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Suffix.
     */
    public static String getRawSuffix(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, type, world, "suffix");
    }

    /**
     * Raw Group Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Group.
     */
    public static String getRawGroup(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, type, world, "group");
    }

    /**
     * Raw Info Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Player's World.
     * @param info  Info Variable being resolved.
     * @return Raw Info.
     */
    public static String getInfo(UUID uuid, InfoType type, String world, String info) {
        return MessageUtil.addColour(getRawInfo(uuid, type, world, info));
    }

    /**
     * Formatted Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Prefix.
     */
    public static String getPrefix(UUID uuid, InfoType type, String world) {
        return getInfo(uuid, type, world, "prefix");
    }

    /**
     * Formatted Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Suffix.
     */
    public static String getSuffix(UUID uuid, InfoType type, String world) {
        return getInfo(uuid, type, world, "suffix");
    }

    /**
     * Formatted Group Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Formatted Group.
     */
    public static String getGroup(UUID uuid, String world) {
        return getInfo(uuid, InfoType.USER, world, "group");
    }

    private static String getMChatInfo(UUID uuid, InfoType type, String world, String info) {
        if (info.equals("group")) {
            return getMChatGroup(uuid);
        }

        String iType = type.getConfValue();

        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (infoConfig.isSet(iType + "." + uuid.toString() + ".info." + info)) {
            return infoConfig.getString(iType + "." + uuid.toString() + ".info." + info);
        } else if (infoConfig.isSet(iType + "." + uuid.toString() + ".worlds." + world + "." + info)) {
            return infoConfig.getString(iType + "." + uuid.toString() + ".worlds." + world + "." + info);
        } else if (infoConfig.isSet("users." + uuid.toString() + ".group")) {
            String group = infoConfig.getString("users." + uuid.toString() + ".group");

            if (infoConfig.isSet("groups." + group + ".info." + info)) {
                return infoConfig.getString("groups." + group + ".info." + info);
            } else if (infoConfig.isSet("groups." + group + ".worlds." + world + "." + info)) {
                return infoConfig.getString("groups." + group + ".worlds." + world + "." + info);
            }
        }

        return "";
    }

    private static String getMChatGroup(UUID uuid) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (infoConfig.isSet("users." + uuid.toString() + ".group")) {
            return infoConfig.getString("users." + uuid.toString() + ".group");
        }

        return "";
    }

    private static String getLeveledInfo(UUID uuid, String world, String info) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();
        HashMap<Integer, String> iMap = new HashMap<>();

        if (API.isPluginEnabled(PluginType.PERMISSIONS_BUKKIT)) {
            if (info.equals("group")) {
                return getPermBukkitGroup(uuid);
            }
        }

        if (!infoConfig.isSet("mchat." + info)) {
            return "";
        }

        if (!infoConfig.isSet("rank." + info)) {
            return getBukkitInfo(uuid, world, info);
        }

        for (Map.Entry<String, Object> entry : infoConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(uuid, world, entry.getKey())) {
                    String rVal = entry.getKey().replaceFirst("mchat\\.", "rank.");

                    if (!infoConfig.isSet(rVal)) {
                        continue;
                    }

                    try {
                        iMap.put(infoConfig.getInt(rVal), entry.getValue().toString());
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        for (int i = 0; i < 101; ++i) {
            if (iMap.get(i) != null && !iMap.get(i).isEmpty()) {
                return iMap.get(i);
            }
        }

        return getBukkitInfo(uuid, world, info);
    }

    private static String getBukkitInfo(UUID uuid, String world, String info) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (info.equals("group")) {
            if (API.isPluginEnabled(PluginType.PERMISSIONS_BUKKIT)) {
                return getPermBukkitGroup(uuid);
            } else if (API.isPluginEnabled(PluginType.PRIVILEGES)) {
                return getPrivGroup(uuid);
            }
        }

        if (!infoConfig.isSet("mchat." + info)) {
            return "";
        }

        for (Map.Entry<String, Object> entry : infoConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(uuid, world, entry.getKey())) {
                    String infoResolve = entry.getValue().toString();

                    if (infoResolve != null && !info.isEmpty()) {
                        return infoResolve;
                    }

                    break;
                }
            }
        }

        return "";
    }

    private static String getPermBukkitGroup(UUID uuid) {
        PermissionsPlugin pBukkit =
                (PermissionsPlugin) Bukkit.getServer().getPluginManager().getPlugin("PermissionsBukkit");

        List<Group> pGroups = pBukkit.getGroups(Bukkit.getServer().getPlayer(uuid).getName());

        try {
            return pGroups.get(0).getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    private static String getPrivGroup(UUID uuid) {
        Privileges priv =
                (Privileges) Bukkit.getServer().getPluginManager().getPlugin("Privileges");

        net.krinsoft.privileges.groups.Group[] pGroups = priv.getPlayerManager()
                .getPlayer(Bukkit.getServer().getPlayer(uuid).getName()).getGroups();

        try {
            return pGroups[0].getName();
        } catch (Exception ignored) {
            return "";
        }
    }

    private static String getGroupManagerInfo(UUID uuid, InfoType type, String world, String info) {
        OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);
        String name = Bukkit.getServer().getPlayer(uuid).getName();

        if (info.equals("group")) {
            return getGroupManagerGroup(uuid, world);
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

    private static String getGroupManagerGroup(UUID uuid, String world) {
        OverloadedWorldHolder gmPermissions = API.gmWH.getWorldData(world);
        String name = Bukkit.getServer().getPlayer(uuid).getName();
        String group = gmPermissions.getUser(name).getGroup().getName();

        if (group == null) {
            return "";
        }

        return group;
    }

    private static String getPEXInfo(UUID uuid, InfoType type, String world, String info) {
        String infoString = "";

        if (uuid == null) {
            return infoString;
        }

        if (info.equals("group") || info.equals("groups")) {
            return getPEXGroup(uuid);
        }

        if (type == InfoType.USER) {
            PermissionUser user = API.pexPermissions.getUser(Bukkit.getServer().getPlayer(uuid).getName());

            switch (info) {
                case "prefix":
                    infoString = user.getPrefix(world);
                    break;
                case "suffix":
                    infoString = user.getSuffix(world);
                    break;
                default:
                    infoString = user.getOption(info, world);
                    break;
            }
        } else if (type == InfoType.GROUP) {
            PermissionGroup group = API.pexPermissions.getGroup(Bukkit.getServer().getPlayer(uuid).getName());

            switch (info) {
                case "prefix":
                    infoString = group.getPrefix(world);
                    break;
                case "suffix":
                    infoString = group.getSuffix(world);
                    break;
                default:
                    infoString = group.getOption(info, world);
                    break;
            }
        }

        return infoString;
    }

    private static String getPEXGroup(UUID uuid) {
        String name = Bukkit.getServer().getPlayer(uuid).getName();
        String[] groupNames = API.pexPermissions.getUser(name).getGroupsNames();

        String group = "";

        if (groupNames.length > 0) {
            group = API.pexPermissions.getUser(name).getGroupsNames()[0];
        }

        return group;
    }

    private static String getbPermInfo(UUID uuid, InfoType type, String world, String info) {
        String name = Bukkit.getServer().getPlayer(uuid).getName();

        if (info.equals("group")) {
            return getbPermGroup(uuid, world);
        }

        String userString = "";

        if (type == InfoType.USER) {
            userString = ApiLayer.getValue(world, CalculableType.USER, name, info);
        } else if (type == InfoType.GROUP) {
            userString = ApiLayer.getValue(world, CalculableType.GROUP, name, info);
        }

        return userString;
    }

    private static String getbPermGroup(UUID uuid, String world) {
        String name = Bukkit.getServer().getPlayer(uuid).getName();
        String[] groupNames = ApiLayer.getGroups(world, CalculableType.USER, name);

        String group = "";

        if (groupNames.length > 0) {
            group = ApiLayer.getGroups(world, CalculableType.USER, name)[0];
        }

        return group;
    }

    private static String getVaultInfo(UUID uuid, InfoType type, String world, String info) {
        String infoString = "";

        if (type == InfoType.USER) {
            infoString = getVaultUserInfo(uuid, world, info);
        }

        if (type == InfoType.GROUP || infoString.equals("")) {
            getVaultGroupInfo(uuid, world, info);
        }

        return infoString;
    }

    private static String getVaultUserInfo(UUID uuid, String world, String info) {
        String name = Bukkit.getServer().getPlayer(uuid).getName();
        String infoString = "";

        if (!API.vChat.getName().equals("MChat")) {
            switch (info) {
                case "group":
                    infoString = API.vChat.getPrimaryGroup(world, name);
                    break;
                case "prefix":
                    infoString = API.vChat.getPlayerPrefix(world, name);
                    break;
                case "suffix":
                    infoString = API.vChat.getPlayerSuffix(world, name);
                    break;
                default:
                    infoString = API.vChat.getPlayerInfoString(world, name, info, "");
                    break;
            }
        }

        return infoString;
    }

    private static String getVaultGroupInfo(UUID uuid, String world, String info) {
        String name = Bukkit.getServer().getPlayer(uuid).getName();
        String infoString = "";

        if (!API.vChat.getName().equals("MChat")) {
            switch (info) {
                case "prefix":
                    infoString = API.vChat.getGroupPrefix(world, name);
                    break;
                case "suffix":
                    infoString = API.vChat.getGroupSuffix(world, name);
                    break;
                default:
                    infoString = API.vChat.getGroupInfoString(world, name, info, "");
                    break;
            }
        }

        return infoString;
    }

    /**
     * Group Name Resolver
     *
     * @param group Group to be Resolved.
     * @return Group Name's Alias.
     */
    public static String getGroupName(String group) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (group.isEmpty()) {
            return "";
        }

        if (infoConfig.isSet("groupnames." + group)) {
            return infoConfig.getString("groupnames." + group);
        }

        return group;
    }

    /**
     * World Name Resolver
     *
     * @param world Group to be Resolved.
     * @return World Name's Alias.
     */
    public static String getWorldName(String world) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (world.isEmpty()) {
            return "";
        }

        if (infoConfig.isSet("worldnames." + world)) {
            return infoConfig.getString("worldnames." + world);
        }

        return world;
    }

    /**
     * Player Name Resolver
     *
     * @param uuid UUID of Player to be Resolved.
     * @return Player Name's MChat Alias.
     */
    public static String getMName(UUID uuid) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (infoConfig.isSet("mname." + uuid)) {
            if (!(infoConfig.getString("mname." + uuid).isEmpty())) {
                return infoConfig.getString("mname." + uuid);
            }
        }

        return Bukkit.getServer().getPlayer(uuid).getName();
    }

    /**
     * Event Message Resolver.
     *
     * @param type Type of Event you want to grab.
     * @return Event Message.
     */
    public static String getEventMessage(EventType type) {
        switch (type) {
            case JOIN:
                return LocaleType.MESSAGE_EVENT_JOIN.getRaw();
            case KICK:
                return LocaleType.MESSAGE_EVENT_KICK.getRaw();
            case LEAVE:
                return LocaleType.MESSAGE_EVENT_LEAVE.getRaw();
            case QUIT:
                return LocaleType.MESSAGE_EVENT_LEAVE.getRaw();
        }

        return "";
    }
}