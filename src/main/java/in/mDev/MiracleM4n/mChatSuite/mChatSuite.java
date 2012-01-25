package in.mDev.MiracleM4n.mChatSuite;

import com.herocraftonline.dev.heroes.Heroes;

import com.massivecraft.factions.Conf;

import com.smilingdevil.devilstats.api.DevilStats;

import de.bananaco.permissions.info.InfoReader;
import de.bananaco.permissions.worlds.WorldPermissionsManager;

import in.mDev.MiracleM4n.mChatSuite.bukkit.GUI.*;
import in.mDev.MiracleM4n.mChatSuite.bukkit.commands.*;
import in.mDev.MiracleM4n.mChatSuite.configs.*;
import in.mDev.MiracleM4n.mChatSuite.bukkit.events.*;

import in.mDev.MiracleM4n.mChatSuite.external.BroadcastMessage;

import net.milkbowl.vault.permission.Permission;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import org.getspout.spoutapi.player.SpoutPlayer;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.File;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

public class mChatSuite extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    // Listeners
    public static MBPlayerListener pListener;
    public static MBEntityListener eListener;
    public static MBBlockListener bListener;
    public static MBCommandSender mCSender;
    public static MBECommandSender mECSender;
    public static MBPCommandSender mPCSender;
    public static MBCustomListener cusListener;

    // GUI
    public static Main mGUI;
    public static GUIEvent mGUIEvent;
    public static Pages mPages;

    // External Messaging
    public BroadcastMessage bMessage;

    // GroupManager
    WorldsHolder gmPermissionsWH;
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

    // MobDisguise
    public Boolean mobD = false;

    // Factions
    Boolean factionsB = false;

    // Heroes
    Heroes heroes;
    Boolean heroesB = false;
    
    // Vault
    Permission vPerm = null;
    Boolean vaultB = false;

    // DevilStats
    DevilStats dStats = null;

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
    public Boolean useNewInfo = false;

    // Optional Old Nodular Style Formatting
    public Boolean useOldNodes = false;

    // Optional Leveled Nodes
    public Boolean useLeveledNodes = false;

    // API Only Boolean
    public Boolean mAPIOnly = false;

    // Alter Event Messages Boolean
    public Boolean alterEvents = true;
    public Boolean alterDMessages = true;

    // Add New Players Boolean
    public Boolean useAddDefault = false;

    // Info Related Variables
    public String mIDefaultGroup = "default";

    // Formatting
    public String varIndicator = "+";
    public String cusVarIndicator = "-";
    public String tabbedListFormat = "+p+dn+s";
    public String listCmdFormat = "+p+dn+s";
    public String chatFormat = "+p+dn+s&f: +m";
    public String nameFormat = "+p+dn+s&e";
    public String eventFormat = "+p+dn+s&e";
    public String meFormat = "* +p+dn+s&e +m";
    public String dateFormat = "HH:mm:ss";

    // Messages
    public String joinMessage = "has joined the game.";
    public String leaveMessage = "has left the game.";
    public String kickMessage = "has been kicked from the game. [ +r ]";
    public String deathInFire = "went up in flames.";
    public String deathOnFire = "burned to death.";
    public String deathLava = "tried to swim in lava.";
    public String deathInWall = "suffocated in a wall.";
    public String deathDrown = "drowned.";
    public String deathStarve = "starved to death.";
    public String deathCactus = "was pricked to death.";
    public String deathFall = "hit the ground too hard.";
    public String deathOutOfWorld = "fell out of the world.";
    public String deathGeneric = "died.";
    public String deathExplosion = "blew up.";
    public String deathMagic = "was killed by magic.";
    public String deathEntity = "was slain by +CName.";
    public String deathArrow = "was shot by +CName.";
    public String deathFireball = "was fireballed by +CName.";
    public String deathThrown = "was pummeled by +CName.";
    public String hMasterT = "The Great";
    public String hMasterF = "The Squire";

    // Strings
    public String eBroadcastIP = "ANY";

    // Booleans
    public Boolean spoutEnabled = true;
    public Boolean healthNotify = false;
    public Boolean healthAchievement = true;
    public Boolean spoutB = false;
    public Boolean mAFKHQ = true;
    public Boolean mChatEB = false;
    public Boolean useAFKList = false;
    public Boolean mChatPB = false;
    public Boolean spoutPM = false;
    public Boolean sJoinB = false;
    public Boolean sDeathB = false;
    public Boolean sQuitB = false;
    public Boolean sKickB = false;
    public Boolean useIPRestrict = true;
    public Boolean useGroupedList = true;
    public Boolean eBroadcast = true;
    public Boolean licenseB = false;

    // Numbers
    public Integer AFKTimer = 30;
    public Integer AFKKickTimer = 120;
    public Integer sJoinI = 30;
    public Integer sDeathI = 30;
    public Integer sQuitI = 30;
    public Integer sKickI = 30;
    public Integer eBroadcastPort = 1940;

    // Other Config Stuff
    public Double chatDistance = -1.0;
    public String cLVars = "default,Default";
    public String listVar = "group";

    // Timers
    long sTime1;
    long sTime2;
    float sDiff;

    // Maps
    public HashMap<String, Location> AFKLoc = new HashMap<String, Location>();

    public HashMap<String, Boolean> chatt = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isAFK = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isConv = new HashMap<String, Boolean>();

    public HashMap<String, String> lastPMd = new HashMap<String, String>();
    public HashMap<String, String> getInvite = new HashMap<String, String>();
    public HashMap<String, String> chatPartner = new HashMap<String, String>();

    public HashMap<String, Long> lastMove = new HashMap<String, Long>();

    public SortedMap<String, String> cVarMap = new TreeMap<String, String>();
    
    // Lists
    /*
    public ArrayList<String> meAliases = new ArrayList<String>();
    public ArrayList<String> whoAliases = new ArrayList<String>();
    public ArrayList<String> listAliases = new ArrayList<String>();
    public ArrayList<String> sayAliases = new ArrayList<String>();
    public ArrayList<String> afkAliases = new ArrayList<String>();
    public ArrayList<String> afkOtherAliases = new ArrayList<String>();
    public ArrayList<String> pmAliases = new ArrayList<String>();
    public ArrayList<String> replyAliases = new ArrayList<String>();
    public ArrayList<String> inviteAliases = new ArrayList<String>();
    public ArrayList<String> acceptAliases = new ArrayList<String>();
    public ArrayList<String> denyAliases = new ArrayList<String>();
    public ArrayList<String> leaveAliases = new ArrayList<String>();
    */

    public ArrayList<Socket> queryList = new ArrayList<Socket>();

    public void onEnable() {
        // 1st Startup Timer
        sTime1 = new Date().getTime();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill Essentials Chat
        killEss();

        // Initialize Configs
        if (new File("plugins/mChat/").isDirectory()) {
           getServer().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] Please move the files in the mChat directory to");
           getServer().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] mChatSuite's than delete the mChat directory!");
        }

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

        // Setup Configs
        setupConfigs();

        // Check License Boolean
        if (!licenseB) {
            getServer().getLogger().log(Level.SEVERE, "-------------------------[" + pdfFile.getName() + "]-----------------------");
            getServer().getLogger().log(Level.SEVERE, "|    You have not yet agreed to " + pdfFile.getName() + "'s License.      |");
            getServer().getLogger().log(Level.SEVERE, "|    Please read over the license.yml included in the      |");
            getServer().getLogger().log(Level.SEVERE, "|  plugins/mChatSuite directory than set \"mchat.license\"   |");
            getServer().getLogger().log(Level.SEVERE, "|         in the config.yml to true if you agree.          |");
            getServer().getLogger().log(Level.SEVERE, "|   This message will continue to appear until you do so.  |");
            getServer().getLogger().log(Level.SEVERE, "------------------------------------------------------------");
        }

        // Setup Plugins
        setupPlugins();

        // Initialize Delayed Listeners
        mCSender = new MBCommandSender(this);
        mECSender = new MBECommandSender(this);
        mPCSender = new MBPCommandSender(this);

        if (!mAPIOnly) {
            if (spoutB) {
                cusListener = new MBCustomListener(this);

                mGUI = new Main(this);
                mGUIEvent = new GUIEvent(this);
                mPages = new Pages(this);
            }

            pListener = new MBPlayerListener(this);
            bListener = new MBBlockListener(this);
            eListener = new MBEntityListener(this);
        }

        // Setup Permissions
        setupPerms();

        // Register Events
        registerEvents();

        // Setup Tasks
        setupTasks();

        // Register Commands
        getCommand("mchat").setExecutor(mCSender);

        getCommand("mchatme").setExecutor(mECSender);
        getCommand("mchatwho").setExecutor(mECSender);
        getCommand("mchatlist").setExecutor(mECSender);
        getCommand("mchatsay").setExecutor(mECSender);
        getCommand("mchatafk").setExecutor(mECSender);
        getCommand("mchatafkother").setExecutor(mECSender);

        getCommand("pmchat").setExecutor(mPCSender);
        getCommand("pmchatreply").setExecutor(mPCSender);
        getCommand("pmchatinvite").setExecutor(mPCSender);
        getCommand("pmchataccept").setExecutor(mPCSender);
        getCommand("pmchatdeny").setExecutor(mPCSender);
        getCommand("pmchatleave").setExecutor(mPCSender);

        // Ping Stats                                       `
        Stats.init(this);

        // Setup DevilStats
        try {
            dStats = new DevilStats(this);
            dStats.startup();
        } catch (Exception ignored) {}


        // External Messaging
        bMessage = new BroadcastMessage(this);

        if (eBroadcast)
            if (bMessage.connect())
                bMessage.startListener();

        // Add All Players To Info.yml
        if (useAddDefault)
            for (Player players : getServer().getOnlinePlayers())
                if (mIConfig.get("users." + players.getName()) == null)
                    getInfoWriter().addPlayer(players.getName(), mIDefaultGroup);

        if (mChatEB) {
            for (Player players : getServer().getOnlinePlayers()) {
                isAFK.put(players.getName(), false);
                chatt.put(players.getName(), false);
                lastMove.put(players.getName(), new Date().getTime());

                if (spoutB) {
                    SpoutPlayer sPlayers = (SpoutPlayer) players;
                    sPlayers.setTitle(getAPI().ParsePlayerName(players, players.getWorld()));
                }
            }
        }

        // Check for Automatic Factions Support
        setupFactions();

        // 2nd Startup Timer
        sTime2 = new Date().getTime();

        // Calculate Startup Timer
        sDiff = new Float(sTime2 - sTime1) / 1000;

        getAPI().log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + sDiff + "s]");
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        Stats.unload();

        if(dStats != null)
            dStats.shutdown();

        if (eBroadcast)
            bMessage.disconnect();

        getAPI().log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
    }

    void registerEvents() {
        if (!mAPIOnly) {
            pm.registerEvents(pListener, this);

            pm.registerEvents(bListener, this);

            pm.registerEvents(eListener, this);

            if (spoutB) {
                pm.registerEvents(cusListener, this);
                pm.registerEvents(mGUIEvent, this);
            }
        }
    }

    void setupPerms() {
        Plugin permTest;

        permTest = pm.getPlugin("PermissionsBukkit");
        if (permTest != null) {
            PermissionBuB = true;
            getAPI().log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" + (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("bPermissions");
        if (permTest != null) {
            bPermB = true;
            bInfoR = de.bananaco.permissions.Permissions.getInfoReader();
            bPermS = de.bananaco.permissions.Permissions.getWorldPermissionsManager();
            getAPI().log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" + (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("PermissionsEx");
        if (permTest != null) {
            pexPermissions = PermissionsEx.getPermissionManager();
            PEXB = true;
            getAPI().log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" + (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        permTest = pm.getPlugin("GroupManager");
        if (permTest != null) {
            gmPermissionsB = true;
            gmPermissionsWH = ((GroupManager) permTest).getWorldsHolder();
            getAPI().log("[" + pdfFile.getName() + "] " + permTest.getDescription().getName() + " v" + (permTest.getDescription().getVersion()) + " found hooking in.");
            return;
        }

        getAPI().log("[" + pdfFile.getName() + "] No Permissions plugins were found defaulting to permissions.yml/info.yml.");
    }

    public Boolean setupPlugin(String pluginName) {
        Plugin plugin = pm.getPlugin(pluginName);

        if (plugin != null) {
            getAPI().log("[" + pdfFile.getName() + "] " + plugin.getDescription().getName() + " " + (plugin.getDescription().getVersion()) + " found hooking in.");
            return true;
        }

        return false;
    }

    void setupPlugins() {
        // Setup MobDisguise
        mobD = setupPlugin("MobDisguise");

        // Setup Factions
        factionsB = setupPlugin("Factions");

        // Setup Heroes
        heroesB = setupPlugin("Heroes");

        if (heroesB)
            heroes = (Heroes) pm.getPlugin("Heroes");

        spoutB = setupPlugin("SpoutPlugin");

        vaultB = setupPlugin("Vault");

        if (vaultB) {
            RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);

            if (permissionProvider != null)
                vPerm = permissionProvider.getProvider();

            vaultB = vPerm != null;
        }

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
        getMainConfig().load();

        getInfoConfig().checkConfig();

        getCensorConfig().loadConfig();

        getLocale().checkLocale();

        getLicense().load();
    }

    public void loadConfigs() {
        getMainConfig().reload();

        getCensorConfig().load();

        getInfoConfig().load();

        getLicense().load();
    }

    void setupTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!mChatEB)
                    return;

                if (AFKTimer < 0)
                    return;

                getMainConfig().reload();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (isAFK.get(player.getName()) == null)
                            isAFK.put(player.getName(), false);

                    if (isAFK.get(player.getName()) ||
                            lastMove.get(player.getName()) == null ||
                            getAPI().checkPermissions(player, "mchat.bypass.afk"))
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
                if (!mChatEB)
                    return;

                if (AFKKickTimer < 0)
                    return;

                getMainConfig().reload();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (getAPI().checkPermissions(player, "mchat.bypass.afkkick"))
                        continue;

                    if (!isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (AFKKickTimer * 1000) > lastMove.get(player.getName()))
                        player.kickPlayer("mAFK Kick");
                }
            }
        }, 20L * 10, 20L * 10);
    }

    // Main Config (config.yml)
    public MConfigListener getMainConfig() {
        return new MConfigListener(this);
    }

    // Info Config (info.yml)
    public MIConfigListener getInfoConfig() {
        return new MIConfigListener(this);
    }

    // Censor Config (censor.yml)
    public MCConfigListener getCensorConfig() {
        return new MCConfigListener(this);
    }

    // Language Config
    public MLanguageListener getLocale() {
        return new MLanguageListener(this, mELocale);
    }

    // License Config
    public MLConfigListener getLicense() {
        return new MLConfigListener(this);
    }

    // API
    public mChatAPI getAPI() {
        return new mChatAPI(this);
    }

    // InfoReader
    public MInfoReader getInfoReader() {
        return new MInfoReader(this);
    }

    // InfoWriter
    public MInfoWriter getInfoWriter() {
        return new MInfoWriter(this);
    }
}
