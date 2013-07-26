package com.miraclem4n.mchat;

import com.herocraftonline.heroes.Heroes;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.commands.InfoAlterCommand;
import com.miraclem4n.mchat.commands.MChatCommand;
import com.miraclem4n.mchat.commands.MeCommand;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigType;
import com.miraclem4n.mchat.events.ChatListener;
import com.miraclem4n.mchat.events.CommandListener;
import com.miraclem4n.mchat.events.PlayerListener;
import com.miraclem4n.mchat.metrics.Metrics;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.TimerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import uk.org.whoami.geoip.GeoIPLookup;
import uk.org.whoami.geoip.GeoIPTools;

import java.io.IOException;
import java.util.HashMap;

public class MChat extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    // GeoIPTools
    public GeoIPLookup geoip;
    public Boolean geoipB = false;

    // Heroes
    public Heroes heroes;
    public Boolean heroesB = false;

    // Towny
    public Boolean towny = false;

    // MSocial
    public Boolean mSocial = false;

    // Maps
    public static HashMap<String, Boolean> shouting;
    public static HashMap<String, Boolean> spying;

    // Shout Format Type
    public static String shoutFormat;

    public void onEnable() {
        // Initialize and Start the Timer
        TimerUtil timer = new TimerUtil();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill EssentialsChat
        killEss();

        //Use a task, so we can wait for server to finish intializing.
        getServer().getScheduler().runTaskLater(this, new BukkitRunnable(){
				@Override
				public void run() {
					try {
						Metrics metrics = new Metrics(Bukkit.getPluginManager().getPlugin("MChat"));
			            metrics.findCustomData();
			            metrics.start();
			        } catch (IOException ignored) {}	
				}
		        			
			}, 200);
                
        // Setup Static Variables
        shouting = new HashMap<String, Boolean>();
        spying = new HashMap<String, Boolean>();
        shoutFormat = "";

        // Load Yml
        YmlManager.load();

        // Setup Plugins
        setupPlugins();

        // Initialize Classes
        initializeClasses();

        // Register Events
        registerEvents();

        // Setup Commands
        setupCommands();

        // Add All Players To Info.yml
        if (ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean()) {
            for (Player players : getServer().getOnlinePlayers()) {
                if (YmlManager.getYml(YmlType.INFO_YML).getConfig().get("users." + players.getName()) == null) {
                    Writer.addBase(players.getName(), ConfigType.INFO_DEFAULT_GROUP.getString(), false);
                }
            }
        }

        // Stop the Timer
        timer.stop();

        // Calculate Startup Timer
        long diff = timer.difference();

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + diff + "ms]");
    }

    public void onDisable() {
        // Initialize and Start the Timer
        TimerUtil timer = new TimerUtil();

        getServer().getScheduler().cancelTasks(this);

        // Kill Static Variables
        shouting = null;
        spying = null;
        shoutFormat = null;

        // Unload Yml
        YmlManager.unload();

        // Stop the Timer
        timer.stop();

        // Calculate Shutdown Timer
        long diff = timer.difference();

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
    }

    void registerEvents() {
        if (!ConfigType.MCHAT_API_ONLY.getBoolean()) {
            pm.registerEvents(new PlayerListener(this), this);

            pm.registerEvents(new ChatListener(this), this);

            pm.registerEvents(new CommandListener(), this);
        }
    }

    Boolean setupPlugin(String pluginName) {
        Plugin plugin = pm.getPlugin(pluginName);

        if (plugin != null) {
            MessageUtil.logFormatted("<Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }

    void setupPlugins() {
        // Setup GeoIPTools
        geoipB = setupPlugin("GeoIPTools");

        if (geoipB) {
            geoip = ((GeoIPTools) pm.getPlugin("GeoIPTools")).getGeoIPLookup();
        }

        // Setup Heroes
        heroesB = setupPlugin("Heroes");

        if (heroesB) {
            heroes = (Heroes) pm.getPlugin("Heroes");
        }

        // Setup MSocial
        mSocial = setupPlugin("MSocial");

        towny = setupPlugin("Towny");
    }

    void killEss() {
        Plugin plugin = pm.getPlugin("EssentialsChat");

        if (plugin != null) {
            pm.disablePlugin(plugin);
        }
    }

    void setupCommands() {
        regCommands("mchat", new MChatCommand(this));

        regCommands("mchatuser", new InfoAlterCommand("mchatuser", InfoType.USER));
        regCommands("mchatgroup", new InfoAlterCommand("mchatgroup", InfoType.GROUP));

        regCommands("mchatme", new MeCommand(this));
    }

    void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor(executor);
        }
    }

    void initializeClasses() {
        API.initialize();
        Parser.initialize(this);
    }
}
