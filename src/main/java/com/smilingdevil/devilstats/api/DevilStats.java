package com.smilingdevil.devilstats.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class DevilStats {

    private JavaPlugin plugin;
    protected HashMap<String, String> values = new HashMap<String, String>();

    // Disabled by default
    // Sends numbers of players to server
    private boolean sendPlayers = false;

    // Sends usernames of players to server
    private boolean sendPlayerNames = false;

    // Disables hooked message
    protected boolean showHookedMessage = true;

    /**
     *
     * @param plugin
     *            Reference to the parent class
     * @throws Exception
     *             In the case that the parent class didn't give a value or a
     *             null value
     */
    public DevilStats(JavaPlugin plugin) throws Exception {
        if (plugin != null) {
            // Valid plugin
            this.plugin = plugin;
            values.put("name", plugin.getDescription().getName());
            ArrayList<String> authors = plugin.getDescription().getAuthors();
            values.put("author", authors.get(0));
            values.put("version", plugin.getDescription().getVersion());
        } else {
            throw new Exception("Missing required value (JavaPlugin plugin)");
        }
    }

    /**
     * Logs a string to the console
     *
     * @param s
     *            String to log
     */
    protected void log(String s) {
        Logger log = Logger.getLogger("Minecraft");
        log.log(Level.INFO, "[DevilStats] " + s);
    }

    /**
     * Generate a URL for internal use only.
     *
     * @param action
     *            Action to input to the URL
     * @return
     * 			  Returns the URL
     */
    private String genURL(String action) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://stats.smilingdevil.com/api?").append("action=")
                .append(action).append("&plugin=").append(values.get("name"))
                .append("&version=").append(values.get("version"))
                .append("&author=").append(values.get("author"));
        if (sendPlayers) {
            if (sendPlayerNames) {
                sb.append("&data=players:");
                Player[] players = plugin.getServer().getOnlinePlayers();
                for (int i = 0; i < players.length; i++) {
                    if ((i + 1) > players.length)
                        sb.append("," + players[i]);
                    else
                        sb.append("," + players[i] + ",");
                }
            } else {
                sb.append("&data=players-length:"+plugin.getServer().getOnlinePlayers().length);
            }
        }
        return sb.toString();
    }

    /**
     * Enables / Disables the hook message on startup
     *
     * @param value
     *            Enables / Disables the message
     */
    public void showHookedMessage(boolean value) {
        this.showHookedMessage = value;
    }

    /**
     *
     * @param value
     *            Change value if we want to send player statistics about the
     *            server.
     * @param detailed
     *            Send usernames of players to statistics server?
     */
    public void sendPlayers(boolean value, boolean detailed) {
        this.sendPlayers = value;
        this.sendPlayerNames = detailed;
    }

    /**
     * Sends startup signal to the stats server
     */
    public void startup() {
        String url = genURL("startup");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    /**
     * Sends shutdown signal to the stats server
     */
    public void shutdown() {
        String url = genURL("shutdown");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    /**
     * Gets currently running count from the stats server
     */
    public void getCount() {
        String url = genURL("get-count");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    /**
     * Gets rank from the stats server
     */
    public void getRank() {
        String url = genURL("get-rank");
        DevilThread thread = new DevilThread(url, this);
        new Thread(thread).start();
    }

    /**
     * Gets the plugin's version
     *
     * @return Returns the plugin's version
     */
    protected String getVersion() {
        return values.get("version");
    }

    /**
     * Gets the author's name
     *
     * @return Returns the author's name
     */
    protected String getAuthor() {
        return values.get("author");
    }

    /**
     * Gets the plugin's name
     *
     * @return Returns the name of the plugin
     */
    protected String getPlugin() {
        return values.get("name");
    }
}
