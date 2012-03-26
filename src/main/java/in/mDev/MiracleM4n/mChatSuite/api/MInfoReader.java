package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class MInfoReader {
    mChatSuite plugin;

    public MInfoReader(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public Object getRawInfo(String name, InfoType type, String world, String info) {
		 return plugin.getReader().getRawInfo(name, type, world, info);
    }

    public Object getRawPrefix(Player player, World world) {
		 return plugin.getReader().getRawPrefix(player, world);
    }

    public Object getRawSuffix(Player player, World world) {
		 return plugin.getReader().getRawSuffix(player, world);
    }

    public Object getRawGroup(Player player, World world) {
		 return plugin.getReader().getRawGroup(player, world);
    }

    public String getInfo(Player player, World world, String info) {
		 return plugin.getReader().getInfo(player, world, info);
    }

    public String getPrefix(Player player, World world) {
		 return plugin.getReader().getPrefix(player, world);
    }

    public String getSuffix(Player player, World world) {
		 return plugin.getReader().getSuffix(player, world);
    }

    public String getGroup(Player player, World world) {
		 return plugin.getReader().getGroup(player, world);
    }

    public Object getRawPrefix(String name, InfoType type, String world) {
		 return plugin.getReader().getRawPrefix(name, type, world);
    }

    public Object getRawSuffix(String name, InfoType type, String world) {
		 return plugin.getReader().getRawSuffix(name, type, world);
    }

    public Object getRawGroup(String name, InfoType type, String world) {
		 return plugin.getReader().getRawGroup(name, type, world);
    }

    public String getInfo(String name, InfoType type, String world, String info) {
		 return plugin.getReader().getInfo(name, type, world, info);
    }

    public String getPrefix(String name, InfoType type, String world) {
		 return plugin.getReader().getPrefix(name, type, world);
    }

    public String getSuffix(String name, InfoType type, String world) {
		 return plugin.getReader().getSuffix(name, type, world);
    }

    public String getGroup(String name, String world) {
		 return plugin.getReader().getGroup(name, world);
    }

    public String getGroupName(String group) {
		 return plugin.getReader().getGroupName(group);
    }

    public String getWorldName(String world) {
		 return plugin.getReader().getWorldName(world);
    }

    public String getmName(String name) {
		 return plugin.getReader().getmName(name);
    }

    public String getEventMessage(EventType type) {
		 return plugin.getReader().getEventMessage(type);
    }

    public String getEventMessage(String type) {
		 return plugin.getReader().getEventMessage(type);
    }

    public String getRawPrefix(String name, String world) {
		 return plugin.getReader().getRawPrefix(name, world);
    }

    public String getRawSuffix(String name, String world) {
		 return plugin.getReader().getRawSuffix(name, world);
    }
}
