package com.miraclem4n.mchat.types;

/**
 * Enum for Different Info Types.
 */
public enum InfoType {
    /** Group Info Type. */ GROUP("groups"),
    /** User Info Type. */ USER("users");

    private final String name;

    private InfoType(String name) {
        this.name = name;
    }

    /**
     * Config Value.
     * @return Config Value of Info Type.
     */
    public String getConfValue() {
        return name;
    }

    /*public static InfoType fromName(String name) {
        for (InfoType type : InfoType.values()) {
            if (type.name.contains(name)) {
                return type;
            }
        }

        return null;
    }*/
}
