package com.miraclem4n.mchat.types.config;

public enum DeathType {
    DEATH_IN_FIRE("went up in flames", LocaleType.MESSAGE_DEATH_IN_FIRE),
    DEATH_ON_FIRE("burned to death", LocaleType.MESSAGE_DEATH_ON_FIRE),
    DEATH_LAVA("tried to swim in lava", LocaleType.MESSAGE_DEATH_LAVA),
    DEATH_IN_WALL("suffocated in a wall", LocaleType.MESSAGE_DEATH_IN_WALL),
    DEATH_DROWN("drowned", LocaleType.MESSAGE_DEATH_DROWN),
    DEATH_STARVE("starved to death", LocaleType.MESSAGE_DEATH_STARVE),
    DEATH_CACTUS("was pricked to death", LocaleType.MESSAGE_DEATH_CACTUS),
    DEATH_FALL("hit the ground too hard", LocaleType.MESSAGE_DEATH_FALL),
    DEATH_OUT_OF_WORLD("fell out of the world", LocaleType.MESSAGE_DEATH_OUT_OF_WORLD),
    DEATH_GENERIC("died", LocaleType.MESSAGE_DEATH_GENERIC),
    DEATH_EXPLOSION("blew up", LocaleType.MESSAGE_DEATH_EXPLOSION),
    DEATH_MAGIC("was killed by", LocaleType.MESSAGE_DEATH_MAGIC),
    DEATH_ENTITY("was slain by", LocaleType.MESSAGE_DEATH_ENTITY),
    DEATH_ARROW("was shot by", LocaleType.MESSAGE_DEATH_ARROW),
    DEATH_FIREBALL("was fireballed by", LocaleType.MESSAGE_DEATH_FIREBALL),
    DEATH_THROWN("was pummeled by", LocaleType.MESSAGE_DEATH_THROWN);

    private final String name;
    private LocaleType type;

    DeathType(String name, LocaleType type) {
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return type.getVal();
    }

    public static DeathType fromMsg(String msg) {
        for (DeathType type : DeathType.values()) {
            if (msg.contains(type.name)) {
                return type;
            }
        }

        return DEATH_GENERIC;
    }
}
