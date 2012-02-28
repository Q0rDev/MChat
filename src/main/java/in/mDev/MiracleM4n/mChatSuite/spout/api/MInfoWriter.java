package in.mDev.MiracleM4n.mChatSuite.spout.api;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;
import org.spout.api.util.config.Configuration;

@SuppressWarnings("unused")
public class MInfoWriter {
    mChatSuite plugin;

    public MInfoWriter(mChatSuite plugin) {
        this.plugin = plugin;
    }

    void save() {
        plugin.mIConfig.save();
        plugin.getInfoConfig().reload();
    }

    /**
     * Used to set the Base values of an InfoType.
     * @param type Type of Base you want to set.
     * @param name Defining value of the base(Also known as Name).
     */
    public void addBase(String name, InfoType type) {
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (type.equals(InfoType.USER))
            config.setValue(base + "." + name + ".group", plugin.mIDefaultGroup);

        config.setValue(base + "." + name + ".info.prefix", "");
        config.setValue(base + "." + name + ".info.suffix", "");

        save();

        if (type.equals(InfoType.USER))
            setDGroup(plugin.mIDefaultGroup);
    }

    /**
     * Used to add the Base for a Player with a custom DefaultGroup.
     * @param player Player's name.
     * @param group Default Group to set to the Base(Only needed if doing for InfoType.USER).
     */
    public void addBase(String player, String group) {
        Configuration config = plugin.mIConfig;

        config.setValue("users." + player + ".group", group);

        config.setValue("users." + player + ".info.prefix", "");
        config.setValue("users." + player + ".info.suffix", "");

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
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (config.getValue(base + "." + name) == null)
            addBase(name, type);

        config.setValue(base + "." + name + ".worlds." + world + "prefix", "");
        config.setValue(base + "." + name + ".worlds." + world + "suffix", "");

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
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (config.getValue(base + "." + name) == null)
            addBase(name, type);

        config.setValue(base + "." + name + ".info." + var, value);

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
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (config.getValue(base + "." + name + ".worlds." + world) == null)
            addWorld(name, type, world);

        config.setValue(base + "." + name + ".worlds." + world + "." + var, value);

        save();
    }

    /**
     * Used to set the Group of a Player.
     * @param player Player's name.
     * @param group Group to be set to Player.
     */
    public void setGroup(String player, String group) {
        Configuration config = plugin.mIConfig;

        if (config.getValue(player + "." + group) == null)
            addBase(player, group);

        config.setValue("users." + player + ".group", group);

        save();
    }

    /**
     * Used to remove a Base.
     * @param name Defining value of the Base(Also known as name).
     * @param type Type of Base you want to remove.
     */
    public void removeBase(String name, InfoType type) {
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (config.getValue(base + "." + name) != null) {
            config.setValue(base + "." + name, null);

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
        Configuration config = plugin.mIConfig;
        String base = type.getName();

        if (config.getValue(base + "." + name) != null) {
            if (config.getValue(base + "." + name + ".worlds." + world) != null) {
                config.setValue(base + "." + name + ".worlds." + world, null);

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
        Configuration config = plugin.mIConfig;

        if (config.getValue("groups." + group) == null) {
            config.setValue("groups." + group + ".info.prefix", "");
            config.setValue("groups." + group + ".info.suffix", "");

            save();
        }
    }
}
