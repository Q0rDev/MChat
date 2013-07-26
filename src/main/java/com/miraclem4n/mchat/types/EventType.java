package com.miraclem4n.mchat.types;

public enum EventType {
    JOIN("join"),
    QUIT("leave"),
    LEAVE("leave"),
    KICK("kick");

    private final String name;

    EventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*public static EventType fromName(String name) {
        for (EventType type : EventType.values()) {
            if (type.name.contains(name)) {
                return type;
            }
        }

        return null;
    }*/
}
