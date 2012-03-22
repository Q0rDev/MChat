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

    public void createChannel(String name, ChannelType type, String prefix, String suffix, Boolean passworded, String password, Integer distance, Boolean defaulted) {
        channels.add(new Channel(name.toLowerCase(), type, prefix, suffix, passworded, password, distance, defaulted));

        plugin.mChConfig.set(name.toLowerCase() + ".type", type.getName());
        plugin.mChConfig.set(name.toLowerCase() + ".prefix", prefix);
        plugin.mChConfig.set(name.toLowerCase() + ".suffix", suffix);
        plugin.mChConfig.set(name.toLowerCase() + ".passworded", passworded);
        plugin.mChConfig.set(name.toLowerCase() + ".password", password);
        plugin.mChConfig.set(name.toLowerCase() + ".distance", distance);
        plugin.mChConfig.set(name.toLowerCase() + ".default", defaulted);

        try {
            plugin.mChConfig.save(plugin.mChConfigF);
        } catch (IOException ignored) {}
    }

    public void removeChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channels.remove(channel);

        plugin.mChConfig.set(name, null);

        try {
            plugin.mChConfig.save(plugin.mChConfigF);
        } catch (IOException ignored) {}
    }

    public Channel getChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                return channel;

        return null;
    }

    public void setDefaultChannel(String name) {
        for (Channel channel : channels)
            if (channel.getName().equalsIgnoreCase(name))
                channel.setDefault(true);
            else
                channel.setDefault(false);
    }

    public Channel getDefaultChannel() {
        for (Channel channel : channels)
            if (channel.isDefault())
                return channel;

        return null;
    }

    public Set<Channel> getPlayersChannels(String player) {
        Set<Channel> channels = new HashSet<Channel>();

        for (Channel channelz : plugin.getChannelManager().getChannels())
            if (channelz.getActiveOccupants().contains(player))
                channels.add(channelz);

        return channels;
    }

    public void editChannel(Channel channel, ChannelEditType edit, Object option) {
        if (option.getClass() == edit.getOptionClass()) {
            if (edit.getName().equalsIgnoreCase("Name"))
                channel.setName((String) option);
            else if (edit.getName().equalsIgnoreCase("Default"))
                setDefaultChannel(channel.getName());
            else if (edit.getName().equalsIgnoreCase("Distance"))
                channel.setDistance((Integer) option);
            else if (edit.getName().equalsIgnoreCase("Password"))
                channel.setPassword((String) option);
            else if (edit.getName().equalsIgnoreCase("Passworded"))
                channel.setPassworded((Boolean) option, channel.getPassword());
            else if (edit.getName().equalsIgnoreCase("Prefix"))
                channel.setPrefix((String) option);
            else if (edit.getName().equalsIgnoreCase("Suffix"))
                channel.setSuffix((String) option);
            else if (edit.getName().equalsIgnoreCase("Type"))
                channel.setType((ChannelType) option);
        }
    }
}
