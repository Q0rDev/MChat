/*
Copyright (C) 2011 jblaske

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

NOTE: The modification of this code could result in improper tracking of
statistics, stability of the server running your plugins or the statistics
website. Please do not modify this file without verification of the author.
*/
package com.randomappdev.pluginstats;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ping {
    private static final File configFile = new File("plugins/mChat/stats.yml");
    private static final String logFile = "plugins/mChat/statsLog.txt";
    private static final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    private static Logger logger = null;

    public static void init(Plugin plugin) {
        if (configExists(plugin) && logExists() && !config.getBoolean("opt-out")) {
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Pinger(plugin, config.getString("guid"), logger), 10L, 20L * 60L * 60 * 24);
            System.out.println("[" + plugin.getDescription().getName() + "] Stats are being kept for this plugin. To opt-out check stats.yml.");
        }
    }

    private static Boolean configExists(Plugin plugin) {
        config.addDefault("opt-out", false);
        config.addDefault("guid", UUID.randomUUID().toString());
        if (!configFile.exists() || config.get("hash", null) == null) {
            System.out.println("[" + plugin.getDescription().getName() + "] PluginStats is initializing for the first time. To opt-out check stats.yml.");
            try {
                config.options().copyDefaults(true);
                config.save(configFile);
            } catch (Exception ignored) {
                System.out.println("[" + plugin.getDescription().getName() + "] Error creating PluginStats configuration file.");
                return false;
            }
        }
        return true;
    }

    private static Boolean logExists() {
        try {
            FileHandler handler = new FileHandler(logFile, true);
            logger = Logger.getLogger("com.randomappdev");
            logger.setUseParentHandlers(false);
            logger.addHandler(handler);
        } catch (Exception ignored) {
            System.out.println("Error creating PluginStats log file.");
            return false;
        }
        return true;
    }
}

class Pinger implements Runnable {
    private Plugin plugin;
    private String guid;
    private Logger logger;

    public Pinger(Plugin plugin, String guid, Logger theLogger) {
        this.plugin = plugin;
        this.guid = guid;
        this.logger = theLogger;
    }

    public void run()  {
        pingServer();
    }

    private void pingServer() {
        String authors = "";

        try {
            for (String auth : plugin.getDescription().getAuthors())
                authors = authors + " " + auth;

            authors = authors.trim();

            String url = String.format("http://pluginstats.randomappdev.com/ping.aspx?snam=%s&sprt=%s&shsh=%s&sver=%s&spcnt=%s&pnam=%s&pmcla=%s&paut=%s&pweb=%s&pver=%s",
                    URLEncoder.encode(plugin.getServer().getServerName(), "UTF-8"),
                    plugin.getServer().getPort(),
                    guid,
                    URLEncoder.encode(Bukkit.getVersion(), "UTF-8"),
                    plugin.getServer().getOnlinePlayers().length,
                    URLEncoder.encode(plugin.getDescription().getName(), "UTF-8"),
                    URLEncoder.encode(plugin.getDescription().getMain(), "UTF-8"),
                    URLEncoder.encode(authors, "UTF-8"),
                    URLEncoder.encode(plugin.getDescription().getWebsite(), "UTF-8"),
                    URLEncoder.encode(plugin.getDescription().getVersion(), "UTF-8"));

            new URL(url).openConnection().getInputStream();

            logger.log(Level.INFO, "PluginStats pinged the central server.");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.toString());
        }
    }
}
