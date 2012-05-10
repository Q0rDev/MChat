package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.types.InfoType;

public class Writer {
    public Writer() {}

    public void addBase(String name, InfoType type) {
        com.miraclem4n.mchat.api.Writer.addBase(name, type);
    }

    public void addBase(String player, String group) {
        com.miraclem4n.mchat.api.Writer.addBase(player, group);
    }

    public void addWorld(String name, InfoType type, String world) {
        com.miraclem4n.mchat.api.Writer.addWorld(name, type, world);
    }

    public void setInfoVar(String name, InfoType type, String var, Object value) {
        com.miraclem4n.mchat.api.Writer.setInfoVar(name, type, var, value);
    }

    public void setWorldVar(String name, InfoType type, String world, String var, Object value) {
        com.miraclem4n.mchat.api.Writer.setWorldVar(name, type, world, var, value);
    }

    public void setGroup(String player, String group) {
        com.miraclem4n.mchat.api.Writer.setGroup(player, group);
    }

    public void removeBase(String name, InfoType type) {
        com.miraclem4n.mchat.api.Writer.removeBase(name, type);
    }

    public void removeInfoVar(String name, InfoType type, String var) {
        com.miraclem4n.mchat.api.Writer.removeInfoVar(name, type, var);
    }

    public void removeWorld(String name, InfoType type, String world) {
        com.miraclem4n.mchat.api.Writer.removeWorld(name, type, world);
    }

    public void removeWorldVar(String name, InfoType type, String world, String var) {
        com.miraclem4n.mchat.api.Writer.removeWorldVar(name, type, world, var);
    }
}
