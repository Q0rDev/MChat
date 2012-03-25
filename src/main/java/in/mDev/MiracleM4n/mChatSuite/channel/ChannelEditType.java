package in.mDev.MiracleM4n.mChatSuite.channel;

import java.util.HashMap;

public enum ChannelEditType {
    DEFAULT("Default", Boolean.class),
    PASSWORD("Password", String.class),
    PASSWORDED("Passworded", Boolean.class),
    TYPE("Type", ChannelType.class),
    NAME("Name", String.class),
    PREFIX("Prefix", String.class),
    SUFFIX("Suffix", String.class),
    DISTANCE("Distance", Integer.class);

    private final String name;
    private final Class<?> clazz;

    private static final HashMap<String, ChannelEditType> nMap = new HashMap<String, ChannelEditType>();

    static {
        for (ChannelEditType type : values())
            nMap.put(type.name.toLowerCase(), type);
    }

    ChannelEditType(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<?> getOptionClass() {
        return clazz;
    }

    public static ChannelEditType fromName(String name) {
        if (name == null)
            return null;

        return nMap.get(name.toLowerCase());
    }
}
