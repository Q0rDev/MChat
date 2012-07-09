package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.types.InfoType;

public class Writer {
    public Writer() {}

    public void addBase(String name, InfoType type) {
        com.miraclem4n.mchat.api.Writer.addBase(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()));
    }

    public void addBase(String player, String group) {
        com.miraclem4n.mchat.api.Writer.addBase(player, group);
    }

    public void addWorld(String name, InfoType type, String world) {
        com.miraclem4n.mchat.api.Writer.addWorld(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
    }

    public void setInfoVar(String name, InfoType type, String var, Object value) {
        com.miraclem4n.mchat.api.Writer.setInfoVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), var, value);
    }

    public void setWorldVar(String name, InfoType type, String world, String var, Object value) {
        com.miraclem4n.mchat.api.Writer.setWorldVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, var, value);
    }

    public void setGroup(String player, String group) {
        com.miraclem4n.mchat.api.Writer.setGroup(player, group);
    }

    public void removeBase(String name, InfoType type) {
        com.miraclem4n.mchat.api.Writer.removeBase(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()));
    }

    public void removeInfoVar(String name, InfoType type, String var) {
        com.miraclem4n.mchat.api.Writer.removeInfoVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), var);
    }

    public void removeWorld(String name, InfoType type, String world) {
        com.miraclem4n.mchat.api.Writer.removeWorld(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world);
    }

    public void removeWorldVar(String name, InfoType type, String world, String var) {
        com.miraclem4n.mchat.api.Writer.removeWorldVar(name, com.miraclem4n.mchat.types.InfoType.fromName(type.getName()), world, var);
    }
}
