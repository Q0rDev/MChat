package ca.q0r.mchat.api;

import ca.q0r.mchat.types.EventType;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.types.PluginType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.locale.LocaleType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
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
    @Deprecated
    public static String getRawInfo(UUID uuid, InfoType type, String world, String info) {
        return getRawInfo(uuid, world, info);
    }

    /**
     * Raw Info Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @param info  Info Variable being resolved.
     * @return Raw Info.
     */
    public static String getRawInfo(UUID uuid, String world, String info) {
        if (uuid == null) {
            return "";
        }

        if (world == null) {
            world = Bukkit.getServer().getWorlds().get(0).getName();
        }

        if (info == null) {
            info = "prefix";
        }

        if (API.isPluginEnabled(PluginType.VAULT_CHAT)) {
            return getVaultChatInfo(uuid, world, info);
        } else if (API.isPluginEnabled(PluginType.LEVELED_NODES)) {
            return getLeveledInfo(uuid, info);
        } else if (API.isPluginEnabled(PluginType.OLD_NODES)) {
            return getBukkitInfo(uuid, info);
        } else if (API.isPluginEnabled(PluginType.NEW_INFO)) {
            return getMChatInfo(uuid, world, info);
        }

        return getMChatInfo(uuid, world, info);
    }

    /**
     * Raw Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    @Deprecated
    public static String getRawPrefix(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, world, "prefix");
    }

    /**
     * Raw Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Raw Prefix.
     */
    public static String getRawPrefix(UUID uuid, String world) {
        return getRawInfo(uuid, world, "prefix");
    }

    /**
     * Raw Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Suffix.
     */
    @Deprecated
    public static String getRawSuffix(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, world, "suffix");
    }

    /**
     * Raw Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Raw Suffix.
     */
    public static String getRawSuffix(UUID uuid, String world) {
        return getRawInfo(uuid, world, "suffix");
    }

    /**
     * Raw Group Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Raw Group.
     */
    @Deprecated
    public static String getRawGroup(UUID uuid, InfoType type, String world) {
        return getRawInfo(uuid, world, "group");
    }

    /**
     * Raw Group Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Raw Group.
     */
    public static String getRawGroup(UUID uuid, String world) {
        return getRawInfo(uuid, world, "group");
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
    @Deprecated
    public static String getInfo(UUID uuid, InfoType type, String world, String info) {
        return MessageUtil.addColour(getRawInfo(uuid, world, info));
    }

    /**
     * Raw Info Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Player's World.
     * @param info  Info Variable being resolved.
     * @return Raw Info.
     */
    public static String getInfo(UUID uuid, String world, String info) {
        return MessageUtil.addColour(getRawInfo(uuid, world, info));
    }

    /**
     * Formatted Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Prefix.
     */
    @Deprecated
    public static String getPrefix(UUID uuid, InfoType type, String world) {
        return getInfo(uuid, world, "prefix");
    }

    /**
     * Formatted Prefix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Formatted Prefix.
     */
    public static String getPrefix(UUID uuid, String world) {
        return getInfo(uuid, world, "prefix");
    }

    /**
     * Formatted Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param type  InfoType being reflected upon.
     * @param world Name of the InfoType's World.
     * @return Formatted Suffix.
     */
    @Deprecated
    public static String getSuffix(UUID uuid, InfoType type, String world) {
        return getInfo(uuid, world, "suffix");
    }

    /**
     * Formatted Suffix Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Formatted Suffix.
     */
    public static String getSuffix(UUID uuid, String world) {
        return getInfo(uuid, world, "suffix");
    }


    /**
     * Formatted Group Resolving
     *
     * @param uuid  Defining value of the InfoType (Also known as name/uuid).
     * @param world Name of the InfoType's World.
     * @return Formatted Group.
     */
    public static String getGroup(UUID uuid, String world) {
        return getInfo(uuid, world, "group");
    }

    private static String getMChatInfo(UUID uuid, String world, String info) {
        if (info.equals("group")) {
            return getMChatGroup(uuid);
        }

        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (infoConfig.isSet("users." + uuid.toString() + ".info." + info)) {
            return infoConfig.getString("users." + uuid.toString() + ".info." + info);
        } else if (infoConfig.isSet("users." + uuid.toString() + ".worlds." + world + "." + info)) {
            return infoConfig.getString("users." + uuid.toString() + ".worlds." + world + "." + info);
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

    private static String getLeveledInfo(UUID uuid, String info) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();
        HashMap<Integer, String> iMap = new HashMap<Integer, String>();

        if (!infoConfig.isSet("mchat." + info)) {
            return "";
        }

        if (!infoConfig.isSet("rank." + info)) {
            return getBukkitInfo(uuid, info);
        }

        for (Map.Entry<String, Object> entry : infoConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(uuid, entry.getKey())) {
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

        return getBukkitInfo(uuid, info);
    }

    private static String getBukkitInfo(UUID uuid, String info) {
        YamlConfiguration infoConfig = YmlManager.getYml(YmlType.INFO_YML).getConfig();

        if (!infoConfig.isSet("mchat." + info)) {
            return "";
        }

        for (Map.Entry<String, Object> entry : infoConfig.getValues(true).entrySet()) {
            if (entry.getKey().contains("mchat." + info + ".")) {
                if (API.checkPermissions(uuid, entry.getKey())) {
                    String infoResolve = entry.getValue().toString();

                    if (!info.isEmpty()) {
                        return infoResolve;
                    }

                    break;
                }
            }
        }

        return "";
    }

    private static String getVaultChatInfo(UUID uuid, String world, String info) {
        OfflinePlayer player = Bukkit.getServer().getOfflinePlayer(uuid);
        String infoString = "";

        if (!API.vChat.getName().equals("MChat")) {
            if (info.equals("group")) {
                infoString = API.vChat.getPrimaryGroup(world, player);
            } else if (info.equals("prefix")) {
                infoString = API.vChat.getPlayerPrefix(world, player);
            } else if (info.equals("suffix")) {
                infoString = API.vChat.getPlayerSuffix(world, player);
            } else {
                infoString = API.vChat.getPlayerInfoString(world, player, info, "");
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

        if (group == null || group.isEmpty()) {
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

        if (world == null || world.isEmpty()) {
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