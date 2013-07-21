package com.miraclem4n.mchat.types;

public enum InfoType {
    GROUP("groups"),
    USER("users");

    private final String name;

    InfoType(String name) {
        this.name = name;
    }

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
