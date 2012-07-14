package com.miraclem4n.mchat;

import com.herocraftonline.heroes.Heroes;
import com.massivecraft.factions.Conf;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.channels.ChannelManager;
import com.miraclem4n.mchat.commands.*;
import com.miraclem4n.mchat.configs.*;
import com.miraclem4n.mchat.events.*;
import com.miraclem4n.mchat.metrics.Metrics;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.player.SpoutPlayer;

import java.util.Date;
import java.util.HashMap;

public class MChat extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    // Factions
    public Boolean factionsB = false;

    // Heroes
    public Heroes heroes;
    public Boolean heroesB = false;

    // Towny
    public Boolean townyB = false;

    // Spout
    public Boolean spoutB = false;

    // Debug
    Boolean debug = false;

    // Metrics
    public Metrics metrics;

    // Timers
    long sTime1;
    long sTime2;
    long sDiff;

    // Maps
    public HashMap<String, Location> AFKLoc = new HashMap<String, Location>();

    public HashMap<String, Boolean> chatt = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isAFK = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isConv = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isMuted = new HashMap<String, Boolean>();

    public static HashMap<String, Boolean> isShouting;
    public static HashMap<String, Boolean> isSpying;

    public HashMap<String, String> lastPMd = new HashMap<String, String>();
    public HashMap<String, String> getInvite = new HashMap<String, String>();
    public HashMap<String, String> chatPartner = new HashMap<String, String>();
    public HashMap<String, String> mPrefix = new HashMap<String, String>();

    public HashMap<String, Long> lastMove = new HashMap<String, Long>();

    public void onEnable() {
        // 1st Startup Timer
        sTime1 = new Date().getTime();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill EssentialsChat
        killEss();

        // Setup Static Variables
        isShouting = new HashMap<String, Boolean>();
        isSpying = new HashMap<String, Boolean>();

        // Initialize Configs
        initializeConfigs();

        // Setup Plugins
        setupPlugins();

        // Initialize Classes
        initializeClasses();

        // Register Events
        registerEvents();

        // Setup Tasks
        setupTasks();

        // Setup Commands
        setupCommands();

        // Add All Players To Info.yml
        if (ConfigType.INFO_ADD_NEW_PLAYERS.getObject().toBoolean())
            for (Player players : getServer().getOnlinePlayers())
                if (InfoUtil.getConfig().get("users." + players.getName()) == null)
                    com.miraclem4n.mchat.api.Writer.addBase(players.getName(), ConfigType.INFO_DEFAULT_GROUP.getObject().toString());

        if (ConfigType.MCHATE_ENABLE.getObject().toBoolean()) {
            for (Player players : getServer().getOnlinePlayers()) {
                isAFK.put(players.getName(), false);
                chatt.put(players.getName(), false);
                lastMove.put(players.getName(), new Date().getTime());

                if (spoutB) {
                    SpoutPlayer sPlayers = (SpoutPlayer) players;
                    sPlayers.setTitle(com.miraclem4n.mchat.api.Parser.parsePlayerName(players.getName(), players.getWorld().getName()));
                }
            }
        }

        // 2nd Startup Timer
        sTime2 = new Date().getTime();

        // Calculate Startup Timer
        sDiff = (sTime2 - sTime1);

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + sDiff + "ms]");
    }

    public void onDisable() {
        // Shutdown Timer
        String shutdown;
        sTime1 = new Date().getTime();

        getServer().getScheduler().cancelTasks(this);

        // Kill Static Variables
        isShouting = null;
        isSpying = null;

        // 2nd Shutdown Timer
        sTime2 = new Date().getTime();
        sDiff = (sTime2 - sTime1);
        shutdown = "[Sched: " + sDiff + "ms]";

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!" + shutdown);
    }

    void registerEvents() {
        if (!ConfigType.MCHAT_API_ONLY.getObject().toBoolean()) {
            pm.registerEvents(new PlayerListener(this), this);

            pm.registerEvents(new EntityListener(this), this);

            pm.registerEvents(new ChatListener(this), this);

            pm.registerEvents(new ChannelListener(this), this);

            pm.registerEvents(new CommandListener(this), this);

            if (spoutB)
                pm.registerEvents(new CustomListener(this), this);
        }

        if (debug)
            pm.registerEvents(new DebugListener(), this);
    }

    Boolean setupPlugin(String pluginName) {
        Plugin plugin = pm.getPlugin(pluginName);

        if (plugin != null) {
            MessageUtil.log("[" + pdfFile.getName() + "] <Plugin> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }

    void setupPlugins() {
       // Setup Factions
        factionsB = setupPlugin("Factions");

        if (factionsB)
            setupFactions();

        // Setup Heroes
        heroesB = setupPlugin("Heroes");

        if (heroesB)
            heroes = (Heroes) pm.getPlugin("Heroes");

        spoutB = setupPlugin("Spout");

        townyB = setupPlugin("Towny");

        if (!ConfigType.MCHAT_SPOUT.getObject().toBoolean())
            spoutB = false;
    }

    void setupFactions() {
        if (!(Conf.chatTagInsertIndex == 0))
            getServer().dispatchCommand(getServer().getConsoleSender(), "f config chatTagInsertIndex 0");
    }

    void killEss() {
        Plugin plugin = pm.getPlugin("EssentialsChat");

        if (plugin != null)
            pm.disablePlugin(plugin);
    }

    public void initializeConfigs() {
        ConfigUtil.initialize();
        InfoUtil.initialize();
        CensorUtil.initialize();
        LocaleUtil.initialize();
        ChannelUtil.initialize();
    }

    public void reloadConfigs() {
        ConfigUtil.initialize();
        InfoUtil.initialize();
        CensorUtil.initialize();
        LocaleUtil.initialize();
        ChannelUtil.initialize();
    }

    void setupTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!ConfigType.MCHATE_ENABLE.getObject().toBoolean())
                    return;

                if (ConfigType.MCHATE_AFK_TIMER.getObject().toInteger() < 1)
                    return;

                ConfigUtil.initialize();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (isAFK.get(player.getName()) == null)
                        isAFK.put(player.getName(), false);

                    if (isAFK.get(player.getName()) ||
                            lastMove.get(player.getName()) == null ||
                            com.miraclem4n.mchat.api.API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.afk"))
                        continue;

                    if (new Date().getTime() - (ConfigType.MCHATE_AFK_TIMER.getObject().toInteger() * 1000) > lastMove.get(player.getName())) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "mchatafkother " + player.getName() + " " + LocaleType.MESSAGE_AFK_DEFAULT.getVal());
                    } else
                        isAFK.put(player.getName(), false);
                }
            }
        }, 20L * 5, 20L * 5);

        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!ConfigType.MCHATE_ENABLE.getObject().toBoolean())
                    return;

                if (ConfigType.MCHATE_AFK_KICK_TIMER.getObject().toInteger() < 1)
                    return;

                ConfigUtil.initialize();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (com.miraclem4n.mchat.api.API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.afkkick"))
                        continue;

                    if (!isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (ConfigType.MCHATE_AFK_KICK_TIMER.getObject().toInteger() * 1000) > lastMove.get(player.getName()))
                        player.kickPlayer(LocaleType.MESSAGE_AFK_DEFAULT.getVal());
                }
            }
        }, 20L * 10, 20L * 10);
    }

    void setupCommands() {
        regCommands("mchat", new MChatCommand(this));
        regCommands("mchatafk", new AFKCommand(this));
        regCommands("mchatafkother", new AFKOtherCommand(this));

        regCommands("mchatlist", new ListCommand(this));

        regCommands("mchatme", new MeCommand(this));
        regCommands("mchatsay", new SayCommand(this));
        regCommands("mchatwho", new WhoCommand(this));
        regCommands("mchatshout", new ShoutCommand(this));
        regCommands("mchatmute", new MuteCommand(this));
        regCommands("mchatmessageprefix", new MessagePrefixCommand(this));

        regCommands("pmchat", new PMCommand(this));
        regCommands("pmchataccept", new AcceptCommand(this));
        regCommands("pmchatdeny", new DenyCommand(this));
        regCommands("pmchatinvite", new InviteCommand(this));
        regCommands("pmchatleave", new LeaveCommand(this));
        regCommands("pmchatreply", new ReplyCommand(this));

        regCommands("mchannel", new MChannelCommand(this));
    }

    void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null)
            getCommand(command).setExecutor(executor);
    }

    void initializeClasses() {
        ChannelManager.initialize();

        API.initialize();
        Parser.initialize(this);
    }
}
