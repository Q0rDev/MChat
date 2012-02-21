package in.mDev.MiracleM4n.mChatSuite;

import com.herocraftonline.dev.heroes.Heroes;

import com.massivecraft.factions.Conf;

import in.mDev.MiracleM4n.mChatSuite.api.MInfoReader;
import in.mDev.MiracleM4n.mChatSuite.api.MInfoWriter;
import in.mDev.MiracleM4n.mChatSuite.api.mChatAPI;

import in.mDev.MiracleM4n.mChatSuite.bukkit.GUI.*;
import in.mDev.MiracleM4n.mChatSuite.bukkit.commands.*;
import in.mDev.MiracleM4n.mChatSuite.bukkit.events.*;

import in.mDev.MiracleM4n.mChatSuite.configs.*;

import in.mDev.MiracleM4n.mChatSuite.external.BroadcastMessage;

import net.milkbowl.vault.permission.Permission;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
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
    public static BMPlayerListener pListener;
    public static BMEntityListener eListener;
    public static BMBlockListener bListener;
    public static BMCustomListener cusListener;

    // GUI
    public static Main mGUI;
    public static GUIEvent mGUIEvent;
    public static Pages mPages;

    // External Messaging
    public BroadcastMessage bMessage;

    // GroupManager
    public WorldsHolder gmPermissionsWH;
    public Boolean gmPermissionsB = false;

    // PermissionsEX
    public PermissionManager pexPermissions;
    public Boolean PEXB = false;

    // PermissionsBukkit
    public Boolean PermissionBuB = false;

    // bPermissions
    public Boolean bPermB = false;

    // MobDisguise
    public Boolean mobD = false;

    // Factions
    public Boolean factionsB = false;

    // Heroes
    public Heroes heroes;
    public Boolean heroesB = false;

    // Vault
    public Permission vPerm;
    public Boolean vaultB = false;

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
    public Boolean eBroadcast = false;
    public Boolean listB = true;

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
    public HashMap<String, Boolean> isShouting = new HashMap<String, Boolean>();
    public HashMap<String, Boolean> isSpying = new HashMap<String, Boolean>();

    public HashMap<String, String> lastPMd = new HashMap<String, String>();
    public HashMap<String, String> getInvite = new HashMap<String, String>();
    public HashMap<String, String> chatPartner = new HashMap<String, String>();

    public HashMap<String, Long> lastMove = new HashMap<String, Long>();

    public SortedMap<String, String> cVarMap = new TreeMap<String, String>();

    // Lists
    public ArrayList<Socket> queryList = new ArrayList<Socket>();

    public void onEnable() {
        // 1st Startup Timer
        sTime1 = new Date().getTime();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill Essentials Chat
        killEss();

        if (new File("plugins/mChat/").isDirectory()) {
            getServer().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] Please move the files in the mChat directory to");
            getServer().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] mChatSuite's than delete the mChat directory!");
        }

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

        // Setup Configs
        setupConfigs();

        // Setup Plugins
        setupPlugins();

        if (!mAPIOnly) {
            if (spoutB) {
                cusListener = new BMCustomListener(this);

                mGUI = new Main(this);
                mGUIEvent = new GUIEvent(this);
                mPages = new Pages(this);
            }

            pListener = new BMPlayerListener(this);
            bListener = new BMBlockListener(this);
            eListener = new BMEntityListener(this);
        }

        // Setup Permissions
        setupPerms();

        // Register Events
        registerEvents();

        // Setup Tasks
        setupTasks();

        // Setup Commands
        setupCommands();

        // External Messaging
        bMessage = new BroadcastMessage(this);

        if (eBroadcast)
            if (bMessage.connect())
                bMessage.startListeners();

        // Add All Players To Info.yml
        if (useAddDefault)
            for (Player players : getServer().getOnlinePlayers())
                if (mIConfig.get("users." + players.getName()) == null)
                    getInfoWriter().addBase(players.getName(), mIDefaultGroup);

        if (mChatEB) {
            for (Player players : getServer().getOnlinePlayers()) {
                isAFK.put(players.getName(), false);
                chatt.put(players.getName(), false);
                lastMove.put(players.getName(), new Date().getTime());

                if (spoutB) {
                    SpoutPlayer sPlayers = (SpoutPlayer) players;
                    sPlayers.setTitle(getAPI().ParsePlayerName(players.getName(), players.getWorld().getName()));
                }
            }
        }

        // Check for Automatic Factions Support
        setupFactions();

        // 2nd Startup Timer
        sTime2 = new Date().getTime();

        // Calculate Startup Timer
        sDiff = ((float) sTime2 - sTime1) / 1000;

        getAPI().log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + sDiff + "s]");
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

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

        getInfoConfig().load();

        getCensorConfig().load();

        getLocale().load();
    }

    public void reloadConfigs() {
        getMainConfig().reload();

        getInfoConfig().reload();

        getCensorConfig().reload();

        getLocale().reload();
    }

    void setupTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!mChatEB)
                    return;

                if (AFKTimer < 1)
                    return;

                getMainConfig().reload();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (isAFK.get(player.getName()) == null)
                        isAFK.put(player.getName(), false);

                    if (isAFK.get(player.getName()) ||
                            lastMove.get(player.getName()) == null ||
                            getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.afk"))
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

                if (AFKKickTimer < 1)
                    return;

                getMainConfig().reload();

                for (Player player : getServer().getOnlinePlayers()) {
                    if (getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.afkkick"))
                        continue;

                    if (!isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (AFKKickTimer * 1000) > lastMove.get(player.getName()))
                        player.kickPlayer("mAFK Kick");
                }
            }
        }, 20L * 10, 20L * 10);
    }

    void setupCommands() {
        regCommands("mchat", new BMChatCommand(this));
        regCommands("mchatafk", new BMChatAFKCommand(this));
        regCommands("mchatafkother", new BMChatAFKOtherCommand(this));

        if (listB)
            regCommands("mchatlist", new BMChatListCommand(this));

        regCommands("mchatme", new BMChatMeCommand(this));
        regCommands("mchatsay", new BMChatSayCommand(this));
        regCommands("mchatwho", new BMChatWhoCommand(this));
        regCommands("mchatshout", new BMChatShoutCommand(this));

        regCommands("pmchat", new BPMChatCommand(this));
        regCommands("pmchataccept", new BPMChatAcceptCommand(this));
        regCommands("pmchatdeny", new BPMChatDenyCommand(this));
        regCommands("pmchatinvite", new BPMChatInviteCommand(this));
        regCommands("pmchatleave", new BPMChatLeaveCommand(this));
        regCommands("pmchatreply", new BPMChatReplyCommand(this));
    }
    
    void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null)
            getCommand(command).setExecutor(executor);
    }

    // Main Config (config.yml)
    public MainConfig getMainConfig() {
        return new MainConfig(this);
    }

    // Info Config (info.yml)
    public InfoConfig getInfoConfig() {
        return new InfoConfig(this);
    }

    // Censor Config (censor.yml)
    public CensorConfig getCensorConfig() {
        return new CensorConfig(this);
    }

    // Language Config
    public LocaleConfig getLocale() {
        return new LocaleConfig(this);
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
