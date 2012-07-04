package com.miraclem4n.mchat.channels;

import com.miraclem4n.mchat.configs.ChannelUtil;
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
     * Loads Channels from ChannelUtil to Memory.
     */
    public static void loadChannels() {
        for (String key : ChannelUtil.getConfig().getKeys(false)) {
            ChannelType type = ChannelType.fromName(ChannelUtil.getConfig().getString(key + ".type"));
            String prefix = ChannelUtil.getConfig().getString(key + ".prefix", "[");
            String suffix = ChannelUtil.getConfig().getString(key + ".suffix", "]");
            Boolean passworded = ChannelUtil.getConfig().getBoolean(key + ".passworded", false);
            String password = ChannelUtil.getConfig().getString(key + ".password");
            Integer distance = ChannelUtil.getConfig().getInt(key + ".distance", -1);
            Boolean defaulted = ChannelUtil.getConfig().getBoolean(key + ".default", false);

            if (type == null)
                type = ChannelType.GLOBAL;

            channels.add(new Channel(key.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));
        }
    }

    /**
     * Loads a Channel from the config.
     */
    public static void loadChannel(String name) {
        ChannelType type = ChannelType.fromName(ChannelUtil.getConfig().getString(name + ".type"));
        String prefix = ChannelUtil.getConfig().getString(name + ".prefix", "[");
        String suffix = ChannelUtil.getConfig().getString(name + ".suffix", "]");
        Boolean passworded = ChannelUtil.getConfig().getBoolean(name + ".passworded", false);
        String password = ChannelUtil.getConfig().getString(name + ".password");
        Integer distance = ChannelUtil.getConfig().getInt(name + ".distance", -1);
        Boolean defaulted = ChannelUtil.getConfig().getBoolean(name + ".default", false);

        channels.add(new Channel(name.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));
    }


    /**
     * Reloads Channels from ChannelUtil to Memory.
     */
    public static void reloadChannels() {
        for (String key : ChannelUtil.getConfig().getKeys(false)) {
            if (getChannel(key) != null) {
                Channel channel = getChannel(key);

                channel.setType(ChannelType.fromName(ChannelUtil.getConfig().getString(key + ".type")));
                channel.setPrefix(ChannelUtil.getConfig().getString(key + ".prefix", "["));
                channel.setSuffix(ChannelUtil.getConfig().getString(key + ".suffix", "]"));
                channel.setPassword(ChannelUtil.getConfig().getString(key + ".password"));
                channel.setPassworded(ChannelUtil.getConfig().getBoolean(key + ".passworded", false), channel.getPassword());
                channel.setDistance(ChannelUtil.getConfig().getInt(key + ".distance", -1));
                channel.setDefault(ChannelUtil.getConfig().getBoolean(key + ".default", false));
            } else {
                loadChannel(key);
            }
        }
    }

    /**
     * Loads Channels from ChannelUtil to Memory.
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

        ChannelUtil.getConfig().set(name.toLowerCase() + ".type", type.getName());
        ChannelUtil.getConfig().set(name.toLowerCase() + ".prefix", prefix);
        ChannelUtil.getConfig().set(name.toLowerCase() + ".suffix", suffix);
        ChannelUtil.getConfig().set(name.toLowerCase() + ".passworded", passworded);
        ChannelUtil.getConfig().set(name.toLowerCase() + ".password", password);
        ChannelUtil.getConfig().set(name.toLowerCase() + ".distance", distance);
        ChannelUtil.getConfig().set(name.toLowerCase() + ".default", defaulted);

        if (defaulted)
            setDefaultChannel(name);

        ChannelUtil.save();
    }

    /**
     * Removes a Channel from ChannelUtil/Memory.
     * @param name Name of Channel being removed.
     */
    public static void removeChannel(String name) {
        Boolean isReal = false;

        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name)) {
                isReal = true;
                break;
            }

        if (isReal) {
            channels.remove(getChannel(name));

            ChannelUtil.getConfig().set(name, null);

            ChannelUtil.save();
        }
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
     * Saves all Channels in Memory to ChannelUtil.
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
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".type", channel.getType().getName().toLowerCase());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".prefix", channel.getPrefix());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".suffix", channel.getSuffix());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".passworded", channel.isPassworded());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".password", channel.getPassword());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".distance", channel.getDistance());
        ChannelUtil.getConfig().set(channel.getName().toLowerCase() + ".default", channel.isDefault());

        ChannelUtil.save();
    }

    /**
     * Makes a Channel the Default Channel.
     * @param name Name of Channel being defaulted.
     */
    public static void setDefaultChannel(String name) {
        Boolean hasDefaulted = false;
        for (Channel channel : channels) {
            if (channel.getName().equalsIgnoreCase(name)) {
                channel.setDefault(true);
                saveChannel(channel);
                hasDefaulted = true;
            } else if (channel.isDefault() && hasDefaulted) {
                channel.setDefault(false);
                saveChannel(channel);
            }
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
     * Loads Channels from ChannelUtil to Memory.
     * @param channel Name of Channel being edited.
     * @param type EditType being used.
     * @param option Option being used.
     */
    public static void editChannel(Channel channel, ChannelEditType type, Object option) {
        if (option.getClass() == type.getOptionClass()) {
            if (type.getName().equalsIgnoreCase("Name")) {
                ChannelUtil.getConfig().set(channel.getName(), null);

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
