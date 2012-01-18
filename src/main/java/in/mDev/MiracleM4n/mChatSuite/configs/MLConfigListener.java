package in.mDev.MiracleM4n.mChatSuite.configs;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MLConfigListener {
    mChatSuite plugin;
    YamlConfiguration config;

    public MLConfigListener(mChatSuite plugin) {
        this.plugin = plugin;
        this.config = YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder(), "license.yml"));
    }

    void save() {
        try {
            config.save(new File(this.plugin.getDataFolder(), "license.yml"));
            config.options().header("BananaCode Minecraft License v1.1");
        } catch (Exception ignored) {}
    }

    public void load() {
        defaultConfig();
    }

    void defaultConfig() {
        wipeConfig();

        ArrayList<String> aList = new ArrayList<String>();
        ArrayList<String> bList = new ArrayList<String>();
        ArrayList<String> cList = new ArrayList<String>();
        ArrayList<String> dList = new ArrayList<String>();
        ArrayList<String> eList = new ArrayList<String>();
        ArrayList<String> fList = new ArrayList<String>();
        ArrayList<String> gList = new ArrayList<String>();

        aList.add("This license grants the software and its creator unrestricted rights to broadcast messages or download content onto or");
        aList.add("access any Minecraft instance on which it is activated and running with the express purpose of enhancing the Minecraft experience.");

        bList.add("This may include occasionally broadcasting promotional messages or downloading additional software for use on the server,");
        bList.add("as well as sending any amount of data back to the creator of the software and related bodies.");

        cList.add("This license begins from the time you install the software and extends until such a time as you uninstall it.");

        dList.add("This license is subject to change without notice at any time, and you are responsible for being aware of the change,");
        dList.add("no notification has to be made on the part of the creator of this software.");

        eList.add("The creator of this software, nor any related bodies cannot be held responsible for any damage done by or during the use of this software.");

        fList.add("By accepting this license you are completely accepting full responsibility for any damage or injury which may occur during the use of this software.");

        gList.add("By accepting this license you are agreeing that you and you alone are solely responsible for how you use this software,");
        gList.add("and that no-one other than yourself can be liable for prosecution for its use.");

        config.set("1", aList);
        config.set("2", bList);
        config.set("3", cList);
        config.set("4", dList);
        config.set("5", eList);
        config.set("6", fList);
        config.set("7", gList);

        save();
    }
    
    void wipeConfig() {
        for (Map.Entry<String, Object> key : config.getValues(true).entrySet())
            config.set(key.getKey(), null);

        save();
    }
}

