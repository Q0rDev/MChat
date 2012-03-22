package in.mDev.MiracleM4n.mChatSuite.channel;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ChannelManager {
    mChatSuite plugin;
    Set<Channel> channels;

    public ChannelManager(mChatSuite plugin) {
        this.plugin = plugin;

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
        for (String key : plugin.mChConfig.getKeys(false)) {
            ChannelType type = ChannelType.fromName(plugin.mChConfig.getString(key + ".type"));
            String prefix = plugin.mChConfig.getString(key + ".prefix", "[");
            String suffix = plugin.mChConfig.getString(key + ".suffix", "]");
            Boolean passworded = plugin.mChConfig.getBoolean(key + ".passworded", false);
            String password = plugin.mChConfig.getString(key + ".password");
            Integer distance = plugin.mChConfig.getInt(key + ".distance", -1);
            Boolean defaulted = plugin.mChConfig.getBoolean(key + ".default", false);

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

        plugin.mChConfig.set(name.toLowerCase() + ".type", type.getName());
        plugin.mChConfig.set(name.toLowerCase() + ".prefix", prefix);
        plugin.mChConfig.set(name.toLowerCase() + ".suffix", suffix);
        plugin.mChConfig.set(name.toLowerCase() + ".passworded", passworded);
        plugin.mChConfig.set(name.toLowerCase() + ".password", password);
        plugin.mChConfig.set(name.toLowerCase() + ".distance", distance);
        plugin.mChConfig.set(name.toLowerCase() + ".default", defaulted);

        if (defaulted)
           setDefaultChannel(name);

        try {
            plugin.mChConfig.save(plugin.mChConfigF);
        } catch (IOException ignored) {}
    }

    /**
     * Removes a Channel from Config/Memory.
     * @param name Name of Channel being removed.
     */
    public void removeChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channels.remove(channel);

        plugin.mChConfig.set(name, null);

        try {
            plugin.mChConfig.save(plugin.mChConfigF);
        } catch (IOException ignored) {}
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
     * Makes a Channel the Default Channel.
     * @param name Name of Channel being defaulted.
     */
    public void setDefaultChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channel.setDefault(true);
            else
                channel.setDefault(false);
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
            if (type.getName().equalsIgnoreCase("Name"))
                channel.setName((String) option);
            else if (type.getName().equalsIgnoreCase("Default"))
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
        }
    }
}
