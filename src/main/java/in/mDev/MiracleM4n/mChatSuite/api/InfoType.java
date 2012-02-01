package in.mDev.MiracleM4n.mChatSuite.api;

public enum InfoType {
    GROUP("groups"),
    USER("users");

    private final String name;

    InfoType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
