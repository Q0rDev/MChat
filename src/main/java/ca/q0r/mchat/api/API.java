package ca.q0r.mchat.api;

import ca.q0r.mchat.types.IndicatorType;
import ca.q0r.mchat.types.PluginType;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.yml.config.ConfigType;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;

/**
 * Class used for various tasks not covered by other classes.
 */
public class API {
    // Vault
    static Permission vPerm;
    static Boolean vPermB;

    static Chat vChat = null;
    static Boolean vChatB = false;

    // Var Map
    private static final SortedMap<String, String> gVarMap = Collections.synchronizedSortedMap(new TreeMap<String, String>());
    private static final SortedMap<String, String> pVarMap = Collections.synchronizedSortedMap(new TreeMap<String, String>());

    // Maps
    private static HashMap<String, Boolean> spying;

    /**
     * Class Initializer
     */
    public static void initialize() {
        setupPlugins();

        spying = new HashMap<>();
    }

    /**
     * Global Variable Addition
     *
     * @param var   Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public static void addGlobalVar(String var, String value) {
        if (var == null || var.isEmpty()) {
            return;
        }

        if (value == null) {
            value = "";
        }

        synchronized (gVarMap) {
            gVarMap.put(var, value);
        }
    }

    /**
     * Global Variable Removal
     *
     * @param var Name of Variable being removed.
     */
    public static void removeGlobalVar(String var) {
        synchronized (gVarMap) {
            if (gVarMap.get(var) != null) {
                gVarMap.remove(var);
            }
        }
    }

    /**
     * Global Variable TreeMap.
     *
     * @return Map of Custom Variables.
     */
    public static SortedMap<String, Object> getGlobalVarMap() {
        SortedMap<String, Object> map = new TreeMap<>();

        synchronized (gVarMap) {
            map.putAll(Collections.unmodifiableSortedMap(gVarMap));

            return map;
        }
    }

    /**
     * Player Variable Addition
     *
     * @param uuid  UUID of Player this Variable is being added for.
     * @param var   Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public static void addUuidVar(UUID uuid, String var, String value) {
        if (var == null || var.isEmpty()) {
            return;
        }

        if (value == null) {
            value = "";
        }

        synchronized (pVarMap) {
            pVarMap.put(uuid.toString() + "|" + var, value);
        }
    }

    /**
     * Player Variable Removal
     *
     * @param uuid UUID of Player this Variable is being removed from.
     * @param var  Name of Variable being removed.
     */
    public static void removeUuidVar(UUID uuid, String var) {
        synchronized (pVarMap) {
            if (pVarMap.get(uuid.toString() + "|" + var) != null) {
                pVarMap.remove(uuid.toString() + "|" + var);
            }
        }
    }

    /**
     * Player Variable TreeMap.
     *
     * @return Map of Custom Variables.
     */
    public static SortedMap<String, String> getUuidVarMap() {
        SortedMap<String, String> map = new TreeMap<>();

        synchronized (pVarMap) {
            map.putAll(Collections.unmodifiableSortedMap(pVarMap));

            return map;
        }
    }

    /**
     * Health Bar Formatting
     *
     * @param player Player the HealthBar is being rendered for.
     * @return Formatted Health Bar.
     */
    public static String createHealthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = new Float(player.getHealth());

        return createBasicBar(health, maxHealth, barLength);
    }

    /**
     * Food Bar Formatting
     *
     * @param player Player the FoodBar is being rendered for.
     * @return Formatted Health Bar.
     */
    public static String createFoodBar(Player player) {
        float maxFood = 20;
        float barLength = 10;
        float food = player.getFoodLevel();

        return createBasicBar(food, maxFood, barLength);
    }

    /**
     * Basic Bar Formatting
     *
     * @param currentValue Current Value of Bar.
     * @param maxValue     Max Value of Bar.
     * @param barLength    Length of Bar.
     * @return Formatted Health Bar.
     */
    public static String createBasicBar(float currentValue, float maxValue, float barLength) {
        int fill = Math.round((currentValue / maxValue) * barLength);

        String barColor = (fill <= (barLength / 4)) ? "&4" : (fill <= (barLength / 7)) ? "&e" : "&2";

        StringBuilder out = new StringBuilder();
        out.append(barColor);

        for (int i = 0; i < barLength; i++) {
            if (i == fill)
                out.append("&8");

            out.append("|");
        }

        out.append("&f");

        return out.toString();
    }

    /**
     * Permission Checking
     *
     * @param player Player being checked.
     * @param node   Permission Node being checked.
     * @return Player has Node.
     */
    public static Boolean checkPermissions(Player player, String node) {
        return vPermB && vPerm.has(player, node)
                || player.hasPermission(node) || player.isOp();
    }

    /**
     * Permission Checking
     *
     * @param pName Name of Player being checked.
     * @param world Name of Player's World.
     * @param node  Permission Node being checked.
     * @return Player has Node.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static Boolean checkPermissions(String pName, String world, String node) {
        return vPermB && vPerm.has(world, pName, node)
                || Bukkit.getServer().getPlayer(pName) != null && Bukkit.getServer().getPlayer(pName).hasPermission(node);
    }

    /**
     * Permission Checking
     *
     * @param uuid  UUID of Player being checked.
     * @param node  Permission Node being checked.
     * @return Player has Node.
     */
    public static Boolean checkPermissions(UUID uuid, String node) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null && checkPermissions(Bukkit.getPlayer(uuid), node);
    }

    /**
     * Permission Checking
     *
     * @param sender CommandSender being checked.
     * @param node   Permission Node being checked.
     * @return Sender has Node.
     */
    public static Boolean checkPermissions(CommandSender sender, String node) {
        return vPermB && vPerm.has(sender, node)
                || sender.hasPermission(node);
    }

    /**
     * Variable Replacer
     *
     * @param source  String being modified.
     * @param changes Map of Search / Replace pairs.
     * @param type    Type of Variable.
     * @return Source with variables replaced.
     */
    public static String replace(String source, TreeMap<String, String> changes, IndicatorType type) {
        NavigableMap<String, String> changed = changes.descendingMap();

        for (NavigableMap.Entry<String, String> entry : changed.entrySet()) {
            source = source.replace(type.getValue() + entry.getKey(), entry.getValue());
        }

        return source;
    }

    /**
     * Variable Replacer
     *
     * @param source  String being modified.
     * @param search  String being searched for.
     * @param replace String search term is to be replaced with.
     * @param type    Type of Variable.
     * @return Source with variable replaced.
     */
    public static String replace(String source, String search, String replace, IndicatorType type) {
        return source.replace(type.getValue() + search, replace);
    }

    /**
     * Used to check if Plugin is enabled.
     *
     * @param type Plugin to be checked.
     * @return <code>true</code> if plugin is enabled <code>false</code> if not.
     */
    public static Boolean isPluginEnabled(PluginType type) {
        if (type == null) {
            return false;
        }

        switch (type) {
            case VAULT_PERM:
                return vPermB;
            case VAULT_CHAT:
                return vChatB;
            case LEVELED_NODES:
                return ConfigType.INFO_USE_LEVELED_NODES.getBoolean();
            case OLD_NODES:
                return ConfigType.INFO_USE_OLD_NODES.getBoolean();
            case NEW_INFO:
                return ConfigType.INFO_USE_NEW_INFO.getBoolean();
            default:
                return false;
        }
    }

    /**
     * Spying HashMap.
     *
     * @return Map of Spying statuses.
     */
    public static HashMap<String, Boolean> getSpying() {
        return spying;
    }

    /**
     * Gets Player based on partial UUID/Name.
     *
     * @param find Partial or complete UUID/Name to lookup.
     * @return Player object or null if not found.
     */
    public static Player getPlayer(String find) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getUniqueId().toString().toLowerCase().startsWith(find.toLowerCase())) {
                return player;
            } else if (player.getName().toLowerCase().startsWith(find.toLowerCase())) {
                return player;
            }
        }

        return null;
    }


    private static void setupPlugins() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        Plugin pluginTest;

        pluginTest = pm.getPlugin("Vault");
        if (pluginTest != null) {
            RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
            RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

            if (chatProvider != null) {
                MessageUtil.logFormatted("<Chat> " + pluginTest.getDescription().getName() + " v" + pluginTest.getDescription().getVersion() + " hooked!.");
                vChat = chatProvider.getProvider();
            }

            if (permissionProvider != null) {
                MessageUtil.logFormatted("<Permissions> " + pluginTest.getDescription().getName() + " v" + pluginTest.getDescription().getVersion() + " hooked!.");
                vPerm = permissionProvider.getProvider();
            }

            vPermB = vPerm != null;
            vChatB = vChat != null;
        }

        if (pluginTest == null) {
            MessageUtil.logFormatted("<Permissions> SuperPerms hooked!.");
        }
    }
}