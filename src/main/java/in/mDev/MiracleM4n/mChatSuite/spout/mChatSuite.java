package in.mDev.MiracleM4n.mChatSuite.spout;

import com.herocraftonline.dev.heroes.Heroes;

import in.mDev.MiracleM4n.mChatSuite.spout.api.MInfoReader;
import in.mDev.MiracleM4n.mChatSuite.spout.api.MInfoWriter;
import in.mDev.MiracleM4n.mChatSuite.spout.api.mChatAPI;
import in.mDev.MiracleM4n.mChatSuite.spout.GUI.GUIEvent;
import in.mDev.MiracleM4n.mChatSuite.spout.GUI.Main;
import in.mDev.MiracleM4n.mChatSuite.spout.GUI.Pages;
import in.mDev.MiracleM4n.mChatSuite.spout.commands.*;
import in.mDev.MiracleM4n.mChatSuite.spout.events.BlockListener;
import in.mDev.MiracleM4n.mChatSuite.spout.events.CustomListener;
import in.mDev.MiracleM4n.mChatSuite.spout.events.EntityListener;
import in.mDev.MiracleM4n.mChatSuite.spout.events.PlayerListener;
import in.mDev.MiracleM4n.mChatSuite.spout.configs.CensorConfig;
import in.mDev.MiracleM4n.mChatSuite.spout.configs.InfoConfig;
import in.mDev.MiracleM4n.mChatSuite.spout.configs.LocaleConfig;
import in.mDev.MiracleM4n.mChatSuite.spout.configs.MainConfig;
import in.mDev.MiracleM4n.mChatSuite.spout.external.BroadcastMessage;

import net.milkbowl.vault.permission.Permission;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;

import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.event.EventManager;
import org.spout.api.geo.discrete.Point;
import org.spout.api.player.Player;
import org.spout.api.plugin.*;

import org.spout.api.util.config.Configuration;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.File;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

public class mChatSuite extends CommonPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public EventManager em;
    public PluginDescriptionFile pdfFile;

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
    public Configuration mConfig = null;
    public Configuration mIConfig = null;
    public Configuration mCConfig = null;
    public Configuration mELocale = null;

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
    public HashMap<String, Point> AFKLoc = new HashMap<String, Point>();

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
        pm = getGame().getPluginManager();
        pdfFile = getDescription();
        em = getGame().getEventManager();

        // First we kill Essentials Chat
        killEss();

        if (new File("plugins/mChat/").isDirectory()) {
            getGame().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] Please move the files in the mChat directory to");
            getGame().getLogger().log(Level.SEVERE, "[" + pdfFile.getName() + "] mChatSuite's than delete the mChat directory!");
        }

        // Initialize Configs
        mConfigF = new File(getDataFolder(), "config.yml");
        mIConfigF = new File(getDataFolder(), "info.yml");
        mCConfigF = new File(getDataFolder(), "censor.yml");
        mELocaleF = new File(getDataFolder(), "locale.yml");

        mConfig = new Configuration(mConfigF);
        mIConfig = new Configuration(mIConfigF);
        mCConfig = new Configuration(mCConfigF);
        mELocale = new Configuration(mELocaleF);

        mConfig.load();
        mIConfig.load();
        mCConfig.load();
        mELocale.load();

        // Setup Configs
        setupConfigs();

        // Setup Plugins
        setupPlugins();

        if (!mAPIOnly)
            if (spoutB) {
                mGUI = new Main(this);
                mGUIEvent = new GUIEvent(this);
                mPages = new Pages(this);
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
            for (Player players : getGame().getOnlinePlayers())
                if (mIConfig.getValue("users." + players.getName()) == null)
                    getInfoWriter().addBase(players.getName(), mIDefaultGroup);

        if (mChatEB) {
            for (Player players : getGame().getOnlinePlayers()) {
                isAFK.put(players.getName(), false);
                chatt.put(players.getName(), false);
                lastMove.put(players.getName(), new Date().getTime());

                //TODO Find/Fix PlayerManager Functions
                //if (spoutB)
                //players.setTitle(getAPI().ParsePlayerName(players.getName(), players.getEntity().getWorld().getName()));
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
        getGame().getScheduler().cancelTasks(this);

        if (eBroadcast)
            bMessage.disconnect();

        getAPI().log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled!");
    }

    void registerEvents() {
        if (!mAPIOnly) {
            em.registerEvents(new PlayerListener(this), this);

            em.registerEvents(new BlockListener(this), this);

            em.registerEvents(new EntityListener(this), this);

            if (spoutB) {
                em.registerEvents(new CustomListener(this), this);
                em.registerEvents(mGUIEvent, this);
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
            ServiceProvider<Permission> permissionProvider = getGame().getServiceManager().getRegistration(Permission.class);

            if (permissionProvider != null)
                vPerm = permissionProvider.getProvider();

            vaultB = vPerm != null;
        }

        if (!spoutEnabled)
            spoutB = false;
    }

    void setupFactions() {
        //TODO Fix Factions Command
        //if (factionsB)
        //if (!(Conf.chatTagInsertIndex == 0))
        //getGame().processCommand(getGame().get, "f config chatTagInsertIndex 0");
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
        getGame().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!mChatEB)
                    return;

                if (AFKTimer < 1)
                    return;

                getMainConfig().reload();

                for (Player player : getGame().getOnlinePlayers()) {
                    if (isAFK.get(player.getName()) == null)
                        isAFK.put(player.getName(), false);

                    if (isAFK.get(player.getName()) ||
                            lastMove.get(player.getName()) == null ||
                            getAPI().checkPermissions(player.getName(), player.getEntity().getWorld().getName(), "mchat.bypass.afk"))
                        continue;

                    //TODO Fix AFKOther Command
                    //if (new Date().getTime() - (AFKTimer * 1000) > lastMove.get(player.getName())) {
                    //getGame().processCommand(getGame().getConsoleSender(), "mchatafkother " + player.getName() + " AutoAfk");
                    //} else
                    isAFK.put(player.getName(), false);
                }
            }
        }, 20L * 5, 20L * 5);

        getGame().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                if (!mChatEB)
                    return;

                if (AFKKickTimer < 1)
                    return;

                getMainConfig().reload();

                for (Player player : getGame().getOnlinePlayers()) {
                    if (getAPI().checkPermissions(player.getName(), player.getEntity().getWorld().getName(), "mchat.bypass.afkkick"))
                        continue;

                    if (!isAFK.get(player.getName()))
                        continue;

                    if (new Date().getTime() - (AFKKickTimer * 1000) > lastMove.get(player.getName()))
                        player.kick("mAFK Kick");
                }
            }
        }, 20L * 10, 20L * 10);
    }

    void setupCommands() {
        regCommands("mchat", MChatCommand.class);
        regCommands("mchatafk", MChatAFKCommand.class);
        regCommands("mchatafkother", MChatAFKOtherCommand.class);

        regCommands("mchatlist", MChatListCommand.class);

        regCommands("mchatme", MChatMeCommand.class);
        regCommands("mchatsay", MChatSayCommand.class);
        regCommands("mchatwho", MChatWhoCommand.class);
        regCommands("mchatshout", MChatShoutCommand.class);

        regCommands("pmchat", MChatCommand.class);
        regCommands("pmchataccept", PMChatAcceptCommand.class);
        regCommands("pmchatdeny", PMChatDenyCommand.class);
        regCommands("pmchatinvite", PMChatInviteCommand.class);
        regCommands("pmchatleave", PMChatLeaveCommand.class);
        regCommands("pmchatreply", PMChatReplyCommand.class);
    }

    void regCommands(String command, Class executor) {
        //TODO Wait for implementation
        /*
        CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(this), new SimpleAnnotatedCommandExecutorFactory());

        if (!command.startsWith("${"))
            getGame().getRootCommand().addSubCommands(getGame(), executor, commandRegFactory);
        */
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

