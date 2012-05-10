package com.miraclem4n.mchat.channels;

import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.types.ChannelEditType;
import com.miraclem4n.mchat.types.ChannelType;

import java.util.HashSet;
import java.util.Set;

public class ChannelManager {
    static Set<Channel> channels = new HashSet<Channel>();

    public static void initialize() {
        channels = new HashSet<Channel>();

        loadChannels();
    }

    public static Set<Channel> getChannels() {
        loadChannels();

        return channels;
    }

    /**
     * Loads Channels from ConfigUtil to Memory.
     */
    public static void loadChannels() {
        for (String key : ConfigUtil.getConfig().getKeys(false)) {
            ChannelType type = ChannelType.fromName(ConfigUtil.getConfig().getString(key + ".type"));
            String prefix = ConfigUtil.getConfig().getString(key + ".prefix", "[");
            String suffix = ConfigUtil.getConfig().getString(key + ".suffix", "]");
            Boolean passworded = ConfigUtil.getConfig().getBoolean(key + ".passworded", false);
            String password = ConfigUtil.getConfig().getString(key + ".password");
            Integer distance = ConfigUtil.getConfig().getInt(key + ".distance", -1);
            Boolean defaulted = ConfigUtil.getConfig().getBoolean(key + ".default", false);

            if (type == null)
                type = ChannelType.GLOBAL;

            channels.add(new Channel(key.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));
        }
    }

    /**
     * Loads Channels from ConfigUtil to Memory.
     * @param name Name of Channel being created.
     * @param type Type of Channel being created.
     * @param prefix Prefix of Channel being created.
     * @param suffix Suffix of Channel being created.
     * @param passworded Is Channel passworded?
     * @param password Channel's Password.
     * @param distance Distance used if Type is local.
     * @param defaulted Is Channel the Default channel.
     */
    public static void createChannel(String name, ChannelType type, String prefix, String suffix, Boolean passworded, String password, Integer distance, Boolean defaulted) {
        channels.add(new Channel(name.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));

        ConfigUtil.getConfig().set(name.toLowerCase() + ".type", type.getName());
        ConfigUtil.getConfig().set(name.toLowerCase() + ".prefix", prefix);
        ConfigUtil.getConfig().set(name.toLowerCase() + ".suffix", suffix);
        ConfigUtil.getConfig().set(name.toLowerCase() + ".passworded", passworded);
        ConfigUtil.getConfig().set(name.toLowerCase() + ".password", password);
        ConfigUtil.getConfig().set(name.toLowerCase() + ".distance", distance);
        ConfigUtil.getConfig().set(name.toLowerCase() + ".default", defaulted);

        if (defaulted)
            setDefaultChannel(name);

        ConfigUtil.save();
    }

    /**
     * Removes a Channel from ConfigUtil/Memory.
     * @param name Name of Channel being removed.
     */
    public static void removeChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channels.remove(channel);

        ConfigUtil.getConfig().set(name, null);

        ConfigUtil.save();
    }

    /**
     * Looks for a Channel in Memory.
     * @param name Name of Channel being sought after.
     * @return Channel being sought after or null.
     */
    public static Channel getChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                return channel;

        return null;
    }

    /**
     * Saves all Channels in Memory to ConfigUtil.
     */
    public static void saveChannels() {
        for (Channel channel : channels)
            saveChannel(channel);
    }

    /**
     * Saves a Channel from Memory to config.
     * @param channel Channel being saved.
     */
    public static void saveChannel(Channel channel) {
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".type", channel.getType().getName().toLowerCase());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".prefix", channel.getPrefix());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".suffix", channel.getSuffix());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".passworded", channel.isPassworded());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".password", channel.getPassword());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".distance", channel.getDistance());
        ConfigUtil.getConfig().set(channel.getName().toLowerCase() + ".default", channel.isDefault());

        ConfigUtil.save();
    }

    /**
     * Makes a Channel the Default Channel.
     * @param name Name of Channel being defaulted.
     */
    public static void setDefaultChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name)) {
                channel.setDefault(true);
                saveChannel(channel);
            } else if (channel.isDefault()) {
                channel.setDefault(false);
                saveChannel(channel);
            }
    }

    /**
     * Reads Default Channel from Memory.
     * @return Default Channel or null.
     */
    public static Channel getDefaultChannel() {
        for (Channel channel : channels)
            if (channel.isDefault())
                return channel;

        return null;
    }

    /**
     * Reads Player's Active Channels
     * @param player Player's name being sought.
     * @return Set containing all Channels the Player is Active in.
     */
    public static Set<Channel> getPlayersActiveChannels(String player) {
        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channelz : getChannels())
            if (channelz.getActiveOccupants().contains(player))
                channels.add(channelz);

        return channels;
    }

    /**
     * Reads Player's Channels
     * @param player Player's name being sought.
     * @return Set containing all Channels the Player is in.
     */
    public static Set<Channel> getPlayersChannels(String player) {
        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channelz : getChannels())
            if (channelz.getOccupants().contains(player))
                channels.add(channelz);

        return channels;
    }

    /**
     * Loads Channels from ConfigUtil to Memory.
     * @param channel Name of Channel being edited.
     * @param type EditType being used.
     * @param option Option being used.
     */
    public static void editChannel(Channel channel, ChannelEditType type, Object option) {
        if (option.getClass() == type.getOptionClass()) {
            if (type.getName().equalsIgnoreCase("Name")) {
                ConfigUtil.getConfig().set(channel.getName(), null);

                channel.setName((String) option);
            } else if (type.getName().equalsIgnoreCase("Default"))
                setDefaultChannel(channel.getName());
            else if (type.getName().equalsIgnoreCase("Distance"))
                channel.setDistance((Integer) option);
            else if (type.getName().equalsIgnoreCase("Password"))
                channel.setPassword((String) option);
            else if (type.getName().equalsIgnoreCase("Passworded"))
                channel.setPassworded((Boolean) option, channel.getPassword());
            else if (type.getName().equalsIgnoreCase("Prefix"))
                channel.setPrefix((String) option);
            else if (type.getName().equalsIgnoreCase("Suffix"))
                channel.setSuffix((String) option);
            else if (type.getName().equalsIgnoreCase("Type"))
                channel.setType((ChannelType) option);

            saveChannel(channel);
        }
    }
}
