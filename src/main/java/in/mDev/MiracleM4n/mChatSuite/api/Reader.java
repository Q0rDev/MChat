package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.types.EventType;
import in.mDev.MiracleM4n.mChatSuite.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;

public class Reader {
    public Reader() {}

    public Object getRawInfo(String name, InfoType type, String world, String info) {
        return com.miraclem4n.mchat.api.Reader.getRawInfo(name, type, world, info);
    }

    public Object getRawPrefix(String name, InfoType type, String world) {
        return com.miraclem4n.mchat.api.Reader.getRawPrefix(name, type, world);
    }

    public Object getRawSuffix(String name, InfoType type, String world) {
        return com.miraclem4n.mchat.api.Reader.getRawSuffix(name, type, world);
    }

    public Object getRawGroup(String name, InfoType type, String world) {
        return com.miraclem4n.mchat.api.Reader.getRawGroup(name, type, world);
    }

    public String getInfo(String name, InfoType type, String world, String info) {
        return com.miraclem4n.mchat.api.Reader.getInfo(name, type, world, info);
    }

    public String getPrefix(String name, InfoType type, String world) {
        return com.miraclem4n.mchat.api.Reader.getPrefix(name, type, world);
    }

    public String getSuffix(String name, InfoType type, String world) {
        return com.miraclem4n.mchat.api.Reader.getSuffix(name, type, world);
    }

    public String getGroup(String name, String world) {
        return com.miraclem4n.mchat.api.Reader.getGroup(name, world);
    }

    public String getGroupName(String group) {
        return com.miraclem4n.mchat.api.Reader.getGroupName(group);
    }

    public String getWorldName(String world) {
        return com.miraclem4n.mchat.api.Reader.getWorldName(world);
    }

    public String getMName(String name) {
        return com.miraclem4n.mchat.api.Reader.getMName(name);
    }

    public String getEventMessage(EventType type) {
        return com.miraclem4n.mchat.api.Reader.getEventMessage(type);
    }
}
