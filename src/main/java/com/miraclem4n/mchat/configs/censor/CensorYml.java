package com.miraclem4n.mchat.configs.censor;

import com.miraclem4n.mchat.configs.Yml;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CensorYml extends Yml {
    public CensorYml() {
        file = new File("plugins/MChat/censor.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("MChat Censor");

        if (!file.exists()) {
            loadDefaults();
        }
    }

    public void loadDefaults() {
        set("fuck", "fawg");
        set("cunt", "punt");
        set("shit", "feces");
        set("dick", "74RG3 P3N1S");
        set("miracleman", "MiracleM4n");
        set("dretax", "DreTax");

        save();
    }
}
