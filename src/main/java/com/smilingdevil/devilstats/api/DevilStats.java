package com.smilingdevil.devilstats.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class DevilStats {

    private HashMap<String, String> values = new HashMap<String, String>();

    /**
     *
     * @param plugin Reference to the parent class
     * @throws Exception In the case that the parent class didn't give a value
     * or a null value
     */
    public DevilStats(JavaPlugin plugin) throws Exception {
        if (plugin != null) {
            values.put("name", plugin.getDescription().getName());
            ArrayList<String> authors = plugin.getDescription().getAuthors();
            values.put("author", authors.get(0));
            values.put("version", plugin.getDescription().getVersion());
        } else {
            throw new Exception("Missing required value (JavaPlugin plugin)");
        }
    }

    protected void log(String s) {
        Logger log = Logger.getLogger("Minecraft");
        log.log(Level.INFO, "[DevilStats] "+s);
    }

    private String genURL(String action) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://stats.smilingdevil.com/api?").append("action=").append(action).append("&plugin=").append(values.get("name")).append("&version=").append(values.get("version")).append("&author=").append(values.get("author"));
        return sb.toString();
    }

    public void startup() {
        String url = genURL("startup");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    public void shutdown() {
        String url = genURL("shutdown");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    public String getPlugin() {
        return values.get("name");
    }

    public String getVersion() {
        return values.get("version");
    }
}
