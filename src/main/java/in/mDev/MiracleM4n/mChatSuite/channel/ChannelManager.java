package in.mDev.MiracleM4n.mChatSuite.channel;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.ChannelEditType;
import in.mDev.MiracleM4n.mChatSuite.types.ChannelType;

import java.util.*;

public class ChannelManager {
    mChatSuite plugin;
    Set<Channel> channels;

    public ChannelManager(mChatSuite instance) {
        plugin = instance;

        this.channels = new HashSet<Channel>();
    }

    public Set<Channel> getChannels() {
        loadChannels();

        return channels;
    }

    /**
     * Loads Channels from Config to Memory.
     */
    public void loadChannels() {
        for (String key : plugin.channels.getKeys(false)) {
            ChannelType type = ChannelType.fromName(plugin.channels.getString(key + ".type"));
            String prefix = plugin.channels.getString(key + ".prefix", "[");
            String suffix = plugin.channels.getString(key + ".suffix", "]");
            Boolean passworded = plugin.channels.getBoolean(key + ".passworded", false);
            String password = plugin.channels.getString(key + ".password");
            Integer distance = plugin.channels.getInt(key + ".distance", -1);
            Boolean defaulted = plugin.channels.getBoolean(key + ".default", false);

            if (type == null)
                type = ChannelType.GLOBAL;

            channels.add(new Channel(key.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));
        }
    }

    /**
     * Loads Channels from Config to Memory.
     * @param name Name of Channel being created.
     * @param type Type of Channel being created.
     * @param prefix Prefix of Channel being created.
     * @param suffix Suffix of Channel being created.
     * @param passworded Is Channel passworded?
     * @param password Channel's Password.
     * @param distance Distance used if Type is local.
     * @param defaulted Is Channel the Default channel.
     */
    public void createChannel(String name, ChannelType type, String prefix, String suffix, Boolean passworded, String password, Integer distance, Boolean defaulted) {
        channels.add(new Channel(name.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));

        plugin.channels.set(name.toLowerCase() + ".type", type.getName());
        plugin.channels.set(name.toLowerCase() + ".prefix", prefix);
        plugin.channels.set(name.toLowerCase() + ".suffix", suffix);
        plugin.channels.set(name.toLowerCase() + ".passworded", passworded);
        plugin.channels.set(name.toLowerCase() + ".password", password);
        plugin.channels.set(name.toLowerCase() + ".distance", distance);
        plugin.channels.set(name.toLowerCase() + ".default", defaulted);

        if (defaulted)
            setDefaultChannel(name);

        try {
            plugin.channels.save(plugin.channelsF);
        } catch (Exception ignored) {}
    }

    /**
     * Removes a Channel from Config/Memory.
     * @param name Name of Channel being removed.
     */
    public void removeChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channels.remove(channel);

        plugin.channels.set(name, null);

        try {
            plugin.channels.save(plugin.channelsF);
        } catch (Exception ignored) {}
    }

    /**
     * Looks for a Channel in Memory.
     * @param name Name of Channel being sought after.
     * @return Channel being sought after or null.
     */
    public Channel getChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                return channel;

        return null;
    }

    /**
     * Saves all Channels in Memory to Config.
     */
    public void saveChannels() {
        for (Channel channel : channels)
            saveChannel(channel);
    }

    /**
     * Saves a Channel from Memory to config.
     * @param channel Channel being saved.
     */
    public void saveChannel(Channel channel) {
        plugin.channels.set(channel.getName().toLowerCase() + ".type", channel.getType().getName().toLowerCase());
        plugin.channels.set(channel.getName().toLowerCase() + ".prefix", channel.getPrefix());
        plugin.channels.set(channel.getName().toLowerCase() + ".suffix", channel.getSuffix());
        plugin.channels.set(channel.getName().toLowerCase() + ".passworded", channel.isPassworded());
        plugin.channels.set(channel.getName().toLowerCase() + ".password", channel.getPassword());
        plugin.channels.set(channel.getName().toLowerCase() + ".distance", channel.getDistance());
        plugin.channels.set(channel.getName().toLowerCase() + ".default", channel.isDefault());

        try {
            plugin.channels.save(plugin.channelsF);
        } catch (Exception ignored) {}
    }

    /**
     * Makes a Channel the Default Channel.
     * @param name Name of Channel being defaulted.
     */
    public void setDefaultChannel(String name) {
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
    public Channel getDefaultChannel() {
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
    public Set<Channel> getPlayersActiveChannels(String player) {
        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channelz : plugin.getChannelManager().getChannels())
            if (channelz.getActiveOccupants().contains(player))
                channels.add(channelz);

        return channels;
    }

    /**
     * Reads Player's Channels
     * @param player Player's name being sought.
     * @return Set containing all Channels the Player is in.
     */
    public Set<Channel> getPlayersChannels(String player) {
        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channelz : plugin.getChannelManager().getChannels())
            if (channelz.getOccupants().contains(player))
                channels.add(channelz);

        return channels;
    }

    /**
     * Loads Channels from Config to Memory.
     * @param channel Name of Channel being edited.
     * @param type EditType being used.
     * @param option Option being used.
     */
    public void editChannel(Channel channel, ChannelEditType type, Object option) {
        if (option.getClass() == type.getOptionClass()) {
            if (type.getName().equalsIgnoreCase("Name")) {
                plugin.channels.set(channel.getName(), null);

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
