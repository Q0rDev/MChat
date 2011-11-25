package net.D3GN.MiracleM4n.mChatSuite;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import net.D3GN.MiracleM4n.mChatSuite.GUI.*;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import com.herocraftonline.dev.heroes.Heroes;

import com.massivecraft.factions.Conf;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import com.randomappdev.pluginstats.Ping;

import de.bananaco.permissions.info.InfoReader;
import de.bananaco.permissions.worlds.WorldPermissionsManager;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import org.getspout.spoutapi.SpoutManager;

import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class mChatSuite extends JavaPlugin {
    // Default Plugin Data
    PluginManager pm;
    PluginDescriptionFile pdfFile;

    // Listeners
    MPlayerListener pListener;
    MEntityListener eListener;
    MBlockListener bListener;
    MCommandSender mCSender;
    MECommandSender mECSender;
    MPCommandSender mPCSender;
    MConfigListener cListener;
    MIConfigListener mIListener;
    MCConfigListener mCListener;
    MLanguageListener lListener;
    MCustomListener cusListener;

    // GUI
    public static Main mGUI;
    public static GUIEvent mGUIEvent;
    public static Labels mLabels;
    public static Buttons mButtons;
    public static TextFields mTextFields;

    // API
    public static mChatAPI API;
    mChatAPI mAPI;

    // Info API
    public static MInfoReader IReader;
    MInfoReader mIReader;

    // Permissions
    PermissionHandler permissions;
    Boolean permissions3;
    Boolean permissionsB = false;

    // GroupManager
    AnjoPermissionsHandler gmPermissions;
    Boolean gmPermissionsB = false;

    // PermissionsEX
    PermissionManager pexPermissions;
    Boolean PEXB = false;

    // PermissionsBukkit
    Boolean PermissionBuB = false;

    // bPermissions
    WorldPermissionsManager bPermS;
    InfoReader bInfoR;
    Boolean bPermB = false;

    // mChannel
    Boolean mChanB = false;

    // MobDisguise
    Boolean mobD = false;

    // Register
    Boolean regB = false;

    // Factions
    Boolean factionsB = false;

    // Heroes
    Heroes heroes;
    Boolean heroesB = false;

    // SimpleClans
    SimpleClans sClan;
    Boolean sClanB = false;

    // Configuration
    public YamlConfiguration mConfig = null;
    public YamlConfiguration mIConfig = null;
    public YamlConfiguration mCConfig = null;
    public YamlConfiguration mELocale = null;

    // Configuration Files
    public File mConfigF = null;
    public File mIConfigF = null;
    public File mCConfigF = null;
    public File mELocaleF = null;

    // Optional mChatSuite only Info Support
    Boolean useNewInfo = false;

    // Optional Old Nodular Style Formatting
    Boolean useOldNodes = false;

    // Optional Leveled Nodes
    Boolean useLeveledNodes = false;

    // API Only Boolean
    Boolean mAPIOnly = false;

    // Fomatting Event Messages Boolean
    Boolean formatEvents = true;

    // Add New Players Boolean
    Boolean useAddDefault = false;

    // Info Related Variables
    String mIDefaultGroup = "default";

    // Formatting
    String varIndicator = "+";
    String tabbedListFormat = "+p+dn+s";
    String listCmdFormat = "+p+dn+s";
    String chatFormat = "+p+dn+s&f: +m";
    String nameFormat = "+p+dn+s&e";
    String eventFormat = "+p+dn+s&e";
    String dateFormat = "HH:mm:ss";

    // Messages
    String joinMessage = "has joined the game.";
    String leaveMessage = "has left the game.";
    String kickMessage = "has been kicked from the game +r.";
    String deathInFire = "went up in flames.";
    String deathOnFire = "burned to death.";
    String deathLava = "tried to swim in lava.";
    String deathInWall = "suffocated in a wall.";
    String deathDrown = "drowned.";
    String deathStarve = "starved to death.";
    String deathCactus = "was pricked to death.";
    String deathFall = "hit the ground too hard.";
    String deathOutOfWorld = "fell out of the world.";
    String deathGeneric = "died.";
    String deathExplosion = "blew up.";
    String deathMagic = "was killed by magic.";
    String deathEntity = "was slain by +CName.";
    String deathArrow = "was shot by +CName.";
    String deathFireball = "was fireballed by +CName.";
    String deathThrown = "was pummeled by +CName.";
    String hMasterT = "The Great";
    String hMasterF = "The Squire";

    // Booleans
    Boolean spoutEnabled = true;
    Boolean healthNotify = false;
    Boolean healthAchievement = true;
    Boolean spoutB = false;
    Boolean mAFKHQ = true;
    Boolean mChatEB = false;
    Boolean useAFKList = false;
    Boolean mChatPB = false;
    Boolean spoutPM = false;

    // Numbers
    Integer AFKTimer = 30;
    Integer AFKKickTimer = 120;

    // Other Config Stuff
    Double chatDistance = -1.0;
    String listVar = "group";

    // Timers
    long sTime1;
    long sTime2;
    float sDiff;

    // Maps
    HashMap<String, Long> lastMove = new HashMap<String, Long>();
    HashMap<String, Boolean> chatt = new HashMap<String, Boolean>();
    HashMap<String, Boolean> isAFK = new HashMap<String, Boolean>();
    HashMap<String, Location> AFKLoc = new HashMap<String, Location>();
    HashMap<String, String> lastPMd = new HashMap<String, String>();
    HashMap<String, Boolean> isConv = new HashMap<String, Boolean>();
    HashMap<String, String> getInvite = new HashMap<String, String>();
    HashMap<String, String> chatPartner = new HashMap<String, String>();

    public void onEnable() {
        // 1st Startup Timer
        sTime1 = new Date().getTime();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill Essentials Chat
        killEss();

        // Initialize Configs
        mConfigF = new File(getDataFolder(), "config.yml");
        mIConfigF = new File(getDataFolder(), "info.yml");
        mCConfigF = new File(getDataFolder(), "censor.yml");
        mELocaleF = new File(getDataFolder(), "locale.yml");

        mConfig = YamlConfiguration.loadConfiguration(mConfigF);
        mIConfig = YamlConfiguration.loadConfiguration(mIConfigF);
        mCConfig = YamlConfiguration.loadConfiguration(mCConfigF);
        mELocale = YamlConfiguration.loadConfiguration(mELocaleF);

        // Manage Config options
        mConfig.options().indent(4);
        mIConfig.options().indent(4);
        mCConfig.options().indent(4);

        // Initialize the API's
        API = new mChatAPI(this);
        mAPI = new mChatAPI(this);
        IReader = new MInfoReader(this);
        mIReader = new MInfoReader(this);

        // Initialize Listeners
        cListener = new MConfigListener(this);
        mIListener = new MIConfigListener(this);
        mCListener = new MCConfigListener(this);
        lListener = new MLanguageListener(this);

        // Setup Configs
        setupConfigs();

        // Setup Plugins
        setupPlugins();

        // Initialize Delayed Listeners
        mCSender = new MCommandSender(this);

        if (mChatEB)
            mECSender = new MECommandSender(this);

        if (mChatPB)
            mPCSender = new MPCommandSender(this);

        if (!mAPIOnly) {
            if (spoutB) {
                cusListener = new MCustomListener(this);

                mGUI = new Main(this);
                mGUIEvent = new GUIEvent(this);
                mLabels = new Labels(this);
                mTextFields = new TextFields(this);
                mButtons = new Buttons(this);
            }

            pListener = new MPlayerListener(this);
            bListener = new MBlockListener(this);
            eListener = new MEntityListener(this);
        }

        // Setup Permissions
        setupPerms();

        // Register Events
        registerEvents();

        // Setup Tasks
        setupTasks();

        // Register Commands
        getCommand("mchat").setExecutor(mCSender);

        if (mChatEB) {
            getCommand("mchatme").setExecutor(mECSender);
            getCommand("mchatwho").setExecutor(mECSender);
            getCommand("mchatlist").setExecutor(mECSender);
            getCommand("mchatafk").setExecutor(mECSender);
            getCommand("mchatafkother").setExecutor(mECSender);
        }

        if (mChatPB) {
            getCommand("pmchat").setExecutor(mPCSender);
            getCommand("pmchatreply").setExecutor(mPCSender);
            getCommand("pmchatinvite").setExecutor(mPCSender);
            getCommand("pmchataccept").setExecutor(mPCSender);
            getCommand("pmchatdeny").setExecutor(mPCSender);
            getCommand("pmchatleave").setExecutor(mPCSender);
        }

        // Ping Stats                                       `
        Ping.init(this);

        // Add All Players To Info.yml
        if (useAddDefault)
            for (Player players : getServer().getOnlinePlayers())
                if (mIConfig.get("users." + players.getName()) == null)
                    mIReader.addPlayer(players.getName(), mIDefaultGroup);

        if (mChatEB) {
            for (Player players : getServer().getOnlinePlayers()) {
                isAFK.put(players.getName(), false);
                chatt.put(players.getName(), false);
                lastMove.put(players.getName(), new Date().getTime());

                if (spoutB)
                    SpoutManager.getAppearanceManager().setGlobalTitle(players, mAPI.ParsePlayerName(players));
            }
        }
        // Check for Automatic Factions Support
        setupFactions();

        // 2nd Startup Timer
        sTime2 = new Date().getTime();

        // Calculate Startup Timer
        sDiff = new Float (sTime2 - sTime1) / 1000;

        mAPI.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled! Took " + sDiff + " seconds.");
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        mAPI.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    void registerEvents() {
        if (!mAPIOnly) {
            pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.SIGN_CHANGE, bListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Priority.Normal, this);

            if (mChatEB) {
                pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, pListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, Priority.Normal, this);
            }

            if (spoutB) {
                pm.registerEvent(Event.Type.CUSTOM_EVENT, cusListener, Event.Priority.Normal, this);
                pm.registerEvent(Event.Type.CUSTOM_EVENT, mGUIEvent, Event.Priority.Normal, this);
            }

            if (formatEvents) {
                pm.registerEvent(Event.Type.ENTITY_DEATH, eListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.PLAYER_KICK, pListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
            }
        }
    }

    void setupPerms() {
        Plugin permTest;

        permTest = pm.getPlugin("PermissionsBukkit");
        if (permTest != null) {
            PermissionBuB = true;
            mAPI.log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" +  (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("bPermissions");
        if (permTest != null) {
            bPermB = true;
            bInfoR = de.bananaco.permissions.Permissions.getInfoReader();
            bPermS = de.bananaco.permissions.Permissions.getWorldPermissionsManager();
            mAPI.log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" +  (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("PermissionsEx");
        if (permTest != null) {
            pexPermissions = PermissionsEx.getPermissionManager();
            PEXB = true;
            mAPI.log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" +  (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("Permissions");
        if (permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;
            permissions3 = permTest.getDescription().getVersion().startsWith("3");
            mAPI.log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" +  (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("GroupManager");
        if (permTest != null) {
            gmPermissionsB = true;
            mAPI.log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" +  (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        mAPI.log("[" + pdfFile.getName() + "] No Permissions plugins were found defaulting to permissions.yml/info.yml.");
    }

    Boolean setupPlugin(String pluginName) {
        Plugin plugin = pm.getPlugin(pluginName);

        if (plugin != null) {
            mAPI.log("[" + pdfFile.getName() + "] " +  plugin.getDescription().getName() + " " + (plugin.getDescription().getVersion()) + " found hooking in.");
            return true;
        }

        return false;
    }

    void setupPlugins() {
        // Setup mChannel
        mChanB = setupPlugin("mChannel");

        // Setup MobDisguise
        mobD = setupPlugin("MobDisguise");

        // Setup Register
        regB = setupPlugin("Register");

        // Setup Factions
        factionsB = setupPlugin("Factions");

        // Setup Heroes
        heroesB = setupPlugin("Heroes");

        if(heroesB)
            heroes = (Heroes) pm.getPlugin("Heroes");

        // Setup SimpleClans
        sClanB = setupPlugin("SimpleClans");

        if (sClanB)
            sClan = (SimpleClans) pm.getPlugin("SimpleClans");

        spoutB = setupPlugin("Spout");

        if (!spoutEnabled)
            spoutB = false;
    }

    void setupFactions() {
        if (factionsB)
            if (!(Conf.chatTagInsertIndex == 0))
                getServer().dispatchCommand(getServer().getConsoleSender(), "f config chatTagInsertIndex 0");
    }

    void killEss() {
        Plugin plugin = pm.getPlugin("EssentialsChat");

        if (plugin != null)
            pm.disablePlugin(plugin);
    }

    public void setupConfigs() {
        cListener.checkConfig();
        cListener.loadConfig();

        mIListener.checkConfig();

        mCListener.loadConfig();

        lListener.checkLocale();
        lListener.loadLocale();
    }

    public void loadConfigs() {
        cListener.load();

        mCListener.load();

        mIListener.load();

        lListener.load();
    }

    void setupTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (mChatEB)
                    return;

                if (AFKTimer < 0)
                    return;

                cListener.load();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (mAPI.checkPermissions(player, "mchat.afk.bypass"))
                        continue;

                    if (isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (AFKTimer * 1000) > lastMove.get(player.getName())) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "mchatafkother " + player.getName() + " AutoAfk");
                    } else
                        isAFK.put(player.getName(), false);
                }
            }
        }, 20L * 5, 20L * 5);

        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (mChatEB)
                    return;

                if (AFKKickTimer < 0)
                    return;

                cListener.load();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (mAPI.checkPermissions(player, "mchat.afkkick.bypass"))
                        continue;

                    if (!isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (AFKKickTimer * 1000) > lastMove.get(player.getName()))
                        player.kickPlayer("mAFK Kick");
                }
            }
        }, 20L * 10, 20L * 10);
    }
}
