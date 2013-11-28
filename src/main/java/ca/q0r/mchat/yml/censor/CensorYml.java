package ca.q0r.mchat.yml.censor;

import ca.q0r.mchat.yml.Yml;

import java.io.File;

public class CensorYml extends Yml {
    public CensorYml() {
        super(new File("plugins/MChat/censor.yml"), "MChat Censor");
    }

    public void loadDefaults() {
        if (!file.exists()) {
            set("fuck", "fawg");
            set("cunt", "punt");
            set("shit", "feces");
            set("dick", "74RG3 P3N1S");
            set("miracleman", "MiracleM4n");
            set("dretax", "DreTax");

            save();
        }
    }
}