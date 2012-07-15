package in.mDev.MiracleM4n.mChatSuite.api;

public class Parser {
    public Parser() {}

    public String parseMessage(String pName, String world, String msg, String format) {
        return com.miraclem4n.mchat.api.Parser.parseMessage(pName, world, msg, format);
    }

    public String parseChatMessage(String pName, String world, String msg) {
        return com.miraclem4n.mchat.api.Parser.parseChatMessage(pName, world, msg);
    }

    public String parsePlayerName(String pName, String world) {
        return com.miraclem4n.mchat.api.Parser.parsePlayerName(pName, world);
    }

    public String parseEventName(String pName, String world) {
        return null; //com.miraclem4n.mchat.api.Parser.parseEventName(pName, world);
    }

    public String parseTabbedList(String pName, String world) {
        return com.miraclem4n.mchat.api.Parser.parseTabbedList(pName, world);
    }

    public String parseListCmd(String pName, String world) {
        return com.miraclem4n.mchat.api.Parser.parseListCmd(pName, world);
    }

    public String parseMe(String pName, String world, String msg) {
        return com.miraclem4n.mchat.api.Parser.parseMe(pName, world, msg);
    }
}
