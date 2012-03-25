package in.mDev.MiracleM4n.mChatSuite.api;

public enum EventType {
    JOIN("join"),
    QUIT("quit"),
    KICK("kick");

    private final String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
