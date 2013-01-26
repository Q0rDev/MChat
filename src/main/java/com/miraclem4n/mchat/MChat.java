package com.miraclem4n.mchat;

import com.herocraftonline.heroes.Heroes;
import com.massivecraft.factions.Conf;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.commands.InfoAlterCommand;
import com.miraclem4n.mchat.commands.MChatCommand;
import com.miraclem4n.mchat.commands.MeCommand;
import com.miraclem4n.mchat.configs.CensorUtil;
import com.miraclem4n.mchat.configs.ConfigUtil;
import com.miraclem4n.mchat.configs.InfoUtil;
import com.miraclem4n.mchat.configs.LocaleUtil;
import com.miraclem4n.mchat.events.ChatListener;
import com.miraclem4n.mchat.events.CommandListener;
import com.miraclem4n.mchat.events.EntityListener;
import com.miraclem4n.mchat.events.PlayerListener;
import com.miraclem4n.mchat.metrics.Metrics;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.TimerUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ignored) {}


        // Setup Static Variables
        shouting = new HashMap<String, Boolean>();
        spying = new HashMap<String, Boolean>();
        shoutFormat = "";

        // Initialize Configs
        initializeConfigs();

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
                if (InfoUtil.getConfig().get("users." + players.getName()) == null) {
                    Writer.addBase(players.getName(), ConfigType.INFO_DEFAULT_GROUP.getString());
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

        // Kill Configs
        unloadConfigs();

        // Stop the Timer
        timer.stop();

        // Calculate Shutdown Timer
        long diff = timer.difference();

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
    }

    void registerEvents() {
        if (!ConfigType.MCHAT_API_ONLY.getBoolean()) {
            pm.registerEvents(new PlayerListener(this), this);

            pm.registerEvents(new EntityListener(this), this);

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

        // Setup Factions
        Boolean factions = setupPlugin("Factions");

        if (factions) {
            setupFactions();
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

    void setupFactions() {
        try {
            if (!(Conf.chatTagInsertIndex == 0)) {
                getServer().dispatchCommand(getServer().getConsoleSender(), "f config chatTagInsertIndex 0");
            }
        } catch (NoSuchFieldError ignored) {}
    }

    void killEss() {
        Plugin plugin = pm.getPlugin("EssentialsChat");

        if (plugin != null) {
            pm.disablePlugin(plugin);
        }
    }

    public void initializeConfigs() {
        ConfigUtil.initialize();
        InfoUtil.initialize();
        CensorUtil.initialize();
        LocaleUtil.initialize();
    }

    public void reloadConfigs() {
        ConfigUtil.initialize();
        InfoUtil.initialize();
        CensorUtil.initialize();
        LocaleUtil.initialize();
    }

    private void unloadConfigs() {
        ConfigUtil.dispose();
        InfoUtil.dispose();
        CensorUtil.dispose();
        LocaleUtil.dispose();
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
