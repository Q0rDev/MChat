package net.D3GN.MiracleM4n.mChatSuite;

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

public class Stats {
    static final File configFile = new File("plugins/mChatSuite/stats.yml");
    static final String logFile = "plugins/mChatSuite/statsLog/log.txt";
    static final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    static Logger logger = null;

    public static void init(Plugin plugin) {
        if (configExists(plugin) && logExists() && !config.getBoolean("opt-out")) {
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Pinger(plugin, config.getString("guid"), logger), 10L, 20L * 60L * 60 * 24);
            System.out.println("[" + plugin.getDescription().getName() + "] Stats are being kept for this plugin. To opt-out check stats.yml.");
        }
    }

    static Boolean configExists(Plugin plugin) {
        config.addDefault("opt-out", false);
        config.addDefault("guid", UUID.randomUUID().toString());
        if (!configFile.exists() || config.get("hash", null) == null) {
            System.out.println("[" + plugin.getDescription().getName() + "] Stats is initializing for the first time. To opt-out check stats.yml.");
            try {
                config.options().copyDefaults(true);
                config.save(configFile);
            } catch (Exception ignored) {
                System.out.println("[" + plugin.getDescription().getName() + "] Error creating Stats configuration file.");
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    static Boolean logExists() {
        try {
            File log = new File("plugins/mChatSuite/statsLog/");
            log.mkdir();

            FileHandler handler = new FileHandler(logFile, true);
            logger = Logger.getLogger("com.randomappdev");
            logger.setUseParentHandlers(false);
            logger.addHandler(handler);
        } catch (Exception ex) {
            System.out.println("Error creating Stats log file.");
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

class Pinger implements Runnable {
    Plugin plugin;
    String guid;
    Logger logger;

    public Pinger(Plugin plugin, String guid, Logger theLogger) {
        this.plugin = plugin;
        this.guid = guid;
        this.logger = theLogger;
    }

    public void run() {
        pingServer();
    }

    void pingServer() {
        String authors = "";
        String plugins = "";

        try {
            for (Plugin pluginn : plugin.getServer().getPluginManager().getPlugins())
                plugins += " " + pluginn.getDescription().getName() + " " + pluginn.getDescription().getVersion() + ",";

            for (String auth : plugin.getDescription().getAuthors())
                authors += " " + auth;

            plugins = plugins.trim();
            authors = authors.trim();

            String url = String.format("http://mdev.in/update.php?server=%s&ip=%s&port=%s&hash=%s&bukkit=%s&players=%s&name=%s&authors=%s&plugins=%s&version=%s",
                    URLEncoder.encode(plugin.getServer().getServerName(), "UTF-8"),
                    URLEncoder.encode(plugin.getServer().getIp(), "UTF-8"),
                    plugin.getServer().getPort(),
                    URLEncoder.encode(guid, "UTF-8"),
                    URLEncoder.encode(Bukkit.getVersion(), "UTF-8"),
                    plugin.getServer().getOnlinePlayers().length,
                    URLEncoder.encode(plugin.getDescription().getName(), "UTF-8"),
                    URLEncoder.encode(authors, "UTF-8"),
                    URLEncoder.encode(plugins, "UTF-8"),
                    URLEncoder.encode(plugin.getDescription().getVersion(), "UTF-8"));

            new URL(url).openConnection().getInputStream();
            logger.log(Level.INFO, "Stats pinged the central server.");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.toString());
        }
    }
}