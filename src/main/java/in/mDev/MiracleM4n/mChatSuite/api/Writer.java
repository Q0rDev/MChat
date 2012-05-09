package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.configs.InfoUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.InfoType;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;

public class Writer {
    mChatSuite plugin;
    YamlConfiguration config;

    public Writer(mChatSuite instance) {
        plugin = instance;

        config = in.mDev.MiracleM4n.mChatSuite.configs.InfoUtil.getConfig();
    }

    /**
     * Used to set the Base values of an InfoType.
     * @param type Type of Base you want to set.
     * @param name Defining value of the base(Also known as Name).
     */
    public void addBase(String name, InfoType type) {
        String base = type.getName();

        if (type.equals(InfoType.USER))
            config.set(base + "." + name + ".group", ConfigType.INFO_DEFAULT_GROUP.getObject().toString());

        config.set(base + "." + name + ".info.prefix", "");
        config.set(base + "." + name + ".info.suffix", "");

        save();

        if (type.equals(InfoType.USER))
            setDGroup(ConfigType.INFO_DEFAULT_GROUP.getObject().toString());
    }

    /**
     * Used to add the Base for a Player with a custom DefaultGroup.
     * @param player Player's name.
     * @param group Default Group to set to the Base(Only needed if doing for InfoType.USER).
     */
    public void addBase(String player, String group) {


        config.set("users." + player + ".group", group);

        config.set("users." + player + ".info.prefix", "");
        config.set("users." + player + ".info.suffix", "");

        save();

        setDGroup(group);
    }

    /**
     * Used to add a World to a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to set.
     * @param world Name of the World you are trying to add.
     */
    public void addWorld(String name, InfoType type, String world) {

        String base = type.getName();

        if (!config.isSet(base + "." + name))
            addBase(name, type);

        config.set(base + "." + name + ".worlds." + world + "prefix", "");
        config.set(base + "." + name + ".worlds." + world + "suffix", "");

        save();
    }

    /**
     * Used to add an Info Variable to a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to set.
     * @param var Name of the Variable you are trying to add.
     * @param value Value of the Variable you are trying to add.
     */
    public void setInfoVar(String name, InfoType type, String var, Object value) {

        String base = type.getName();

        if (!config.isSet(base + "." + name))
            addBase(name, type);

        config.set(base + "." + name + ".info." + var, value);

        save();
    }

    /**
     * Used to add a World Variable to a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to set.
     * @param world Name of the World you are trying to add the Variable to.
     * @param var Name of the Variable you are trying to add.
     * @param value Value of the Variable you are trying to add.
     */
    public void setWorldVar(String name, InfoType type, String world, String var, Object value) {

        String base = type.getName();

        if (!config.isSet(base + "." + name + ".worlds." + world))
            addWorld(name, type, world);

        config.set(base + "." + name + ".worlds." + world + "." + var, value);

        save();
    }

    /**
     * Used to set the Group of a Player.
     * @param player Player's name.
     * @param group Group to be set to Player.
     */
    public void setGroup(String player, String group) {


        if (!config.isSet(player + "." + group))
            addBase(player, group);

        config.set("users." + player + ".group", group);

        save();
    }

    /**
     * Used to remove a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to remove.
     */
    public void removeBase(String name, InfoType type) {

        String base = type.getName();

        if (config.isSet(base + "." + name)) {
            config.set(base + "." + name, null);

            save();
        }
    }

    /**
     * Used to remove an Info Variable from a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to remove from.
     * @param var Name of the Variable you are trying to remove.
     */
    public void removeInfoVar(String name, InfoType type, String var) {
        setInfoVar(name, type, var, null);
    }

    /**
     * Used to remove a World from a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to remove from.
     * @param world Name of the World you are trying to remove.
     */
    public void removeWorld(String name, InfoType type, String world) {

        String base = type.getName();

        if (config.isSet(base + "." + name)) {
            if (config.isSet(base + "." + name + ".worlds." + world)) {
                config.set(base + "." + name + ".worlds." + world, null);

                save();
            }
        }
    }

    /**
     * Used to remove a World Variable from a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to remove from.
     * @param world Name of the World you are trying to remove from.
     * @param var Name of the Variable you are trying to remove.
     */
    public void removeWorldVar(String name, InfoType type, String world, String var) {
        setWorldVar(name, type, world, var, null);
    }

    void setDGroup(String group) {


        if (!config.isSet("groups." + group)) {
            config.set("groups." + group + ".info.prefix", "");
            config.set("groups." + group + ".info.suffix", "");

            save();
        }
    }

    void save() {
        InfoUtil.save();
    }
}
