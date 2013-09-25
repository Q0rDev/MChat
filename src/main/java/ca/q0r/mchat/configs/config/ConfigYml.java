package ca.q0r.mchat.configs.config;

import ca.q0r.mchat.configs.Yml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConfigYml extends Yml {
    private ArrayList<String> meAliases = new ArrayList<String>();
    //private ArrayList<String> userAliases = new ArrayList<String>();
    //private ArrayList<String> groupAliases = new ArrayList<String>();

    private HashMap<String, List<String>> aliasMap = new HashMap<String, List<String>>();

    public ConfigYml() {
        super(new File("plugins/MChat/config.yml"), "MChat Config");
    }

    public void loadDefaults() {
        removeOption("auto-Changed");
        removeOption("mchat.suppressMessages");

        removeOption("mchat.enableList");

        editOption("mchat-message-format", "format.chat");
        editOption("mchat-API-only", "mchat.apiOnly");
        editOption("mchat-format-events", "mchat.formatEvents");
        editOption("mchat-chat-distance", "mchat.chatDistance");
        editOption("mchat-info-only", "info.useNewInfo");
        editOption("mchat-oldNodes-only", "info.useOldNodes");
        editOption("mchat-add-info-players", "info.addNewPlayers");

        editOption("mchat.formatEvents", "mchat.alter.events");

        editOption("mchat.alterEvents", "mchat.alter.events");

        checkOption("mchat.apiOnly", false);
        checkOption("mchat.updateCheck", true);
        checkOption("mchat.alter.events", true);
        checkOption("mchat.chatDistance", -1.0);
        checkOption("mchat.varIndicator", "+");
        checkOption("mchat.cusVarIndicator", "-");
        checkOption("mchat.localeVarIndicator", "%");
        checkOption("mchat.spout", true);
        checkOption("mchat.IPCensor", true);
        checkOption("mchat.cLockRange", 3);

        checkOption("suppress.useJoin", false);
        checkOption("suppress.useKick", false);
        checkOption("suppress.useQuit", false);
        checkOption("suppress.maxJoin", 30);
        checkOption("suppress.maxKick", 30);
        checkOption("suppress.maxQuit", 30);

        checkOption("info.useNewInfo", false);
        checkOption("info.useLeveledNodes", false);
        checkOption("info.useOldNodes", false);
        checkOption("info.addNewPlayers", false);
        checkOption("info.defaultGroup", "default");

        loadAliases();

        checkOption("aliases.mchatme", meAliases);
        //checkOption("aliases.mchatuser", userAliases);
        //checkOption("aliases.mchatgroup", groupAliases);

        setupAliasMap();

        save();
    }

    public HashMap<String, List<String>> getAliasMap() {
        return aliasMap;
    }

    private void loadAliases() {
        meAliases.add("me");

        //userAliases.add("muser");
        //groupAliases.add("mgroup");
    }

    private void setupAliasMap() {
        Set<String> keys = config.getConfigurationSection("aliases").getKeys(false);

        for (String key : keys) {
            aliasMap.put(key, config.getStringList("aliases." + key));
        }
    }
}
