package in.mDev.MiracleM4n.mChatSuite.types;

import java.util.HashMap;

public enum ChannelType {
    GLOBAL("Global"),
    LOCAL("Local"),
    PRIVATE("Private"),
    PASSWORD("Password"),
    WORLD("World"),
    CHUNK("Chunk");

    private final String name;

    private static final HashMap<String, ChannelType> nMap = new HashMap<String, ChannelType>();

    static {
        for (ChannelType type : values())
            nMap.put(type.name.toLowerCase(), type);
    }

    ChannelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static ChannelType fromName(String name) {
        if (name == null)
            return null;

        return nMap.get(name.toLowerCase());
    }
}
