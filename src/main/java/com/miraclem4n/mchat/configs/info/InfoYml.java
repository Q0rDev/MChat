package com.miraclem4n.mchat.configs.info;

import com.miraclem4n.mchat.configs.Yml;

import java.io.File;

public class InfoYml extends Yml {
    public InfoYml() {
        super(new File("plugins/MChat/info.yml"), "MChat Info");
    }

    public void loadDefaults() {
        if (config.get("users") == null) {
            set("users.MiracleM4n.group", "admin");
            set("users.MiracleM4n.worlds.DtK.prefix", "");
            set("users.MiracleM4n.info.suffix", "");
            set("users.MiracleM4n.info.prefix", "");
        }

        if (config.get("groups") == null) {
            set("groups.admin.worlds.DtK.prefix", "");
            set("groups.admin.info.prefix", "");
            set("groups.admin.info.suffix", "");
            set("groups.admin.info.custVar", "");
        }

        if (config.get("groupnames") == null) {
            set("groupnames.admin", "[a]");
            set("groupnames.sadmin", "[sa]");
            set("groupnames.jadmin", "[ja]");
            set("groupnames.member", "[m]");
        }

        if (config.get("worldnames") == null) {
            set("worldnames.D3GN", "[D]");
            set("worldnames.DtK", "[DtK]");
            set("worldnames.Nether", "[N]");
            set("worldnames.Hello", "[H]");
        }

        if (config.get("mname") == null) {
            set("mname.MiracleM4n", "M1r4c13M4n");
            set("mname.Jessica_RS", "M1r4c13M4n's Woman");
        }

        save();
    }
}
