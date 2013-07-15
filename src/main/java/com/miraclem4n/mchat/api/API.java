package com.miraclem4n.mchat.api;

import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.NavigableMap;
import java.util.TreeMap;

@SuppressWarnings("unused")
public class API {
    // Vault
    private static Permission vPerm;
    private static Boolean vaultB;

    public static Chat vChat = null;
    public static Boolean vChatB = false;

    // GroupManager
    public static WorldsHolder gmWH;
    public static Boolean gmB;

    //PEX
    public static PermissionManager pexPermissions;
    public static Boolean pexB;

    // bPerms
    public static Boolean bPermB;

    // Privileges
    public static Boolean privB;

    // PermissionsBukkit
    public static Boolean pBukkitB;

    // Var Map
    public static TreeMap<String, String> varMap;
    public static TreeMap<String, String> varMapQueue;

    public static void initialize() {
        setupPlugins();

        varMap = new TreeMap<String, String>();
        varMapQueue = new TreeMap<String, String>();
    }

    /**
     * Global Variable Addition
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public static void addGlobalVar(String var, String value) {
        if (var == null || var.isEmpty()) {
            return;
        }

        if (value == null) {
            value = "";
        }

        varMapQueue.put("%^global^%|" + var, value);
    }

    /**
     * Player Variable Addition
     * @param pName Name of Player this Variable is being added for.
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public static void addPlayerVar(String pName, String var, String value) {
        if (var == null || var.isEmpty()) {
            return;
        }

        if (value == null) {
            value = "";
        }

        varMapQueue.put(pName + "|" + var, value);
    }

    /**
     * Health Bar Formatting
     * @param player Player the HealthBar is being rendered for.
     * @return Formatted Health Bar.
     */
    public static String createHealthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();

        return createBasicBar(health, maxHealth, barLength);
    }

    /**
     * Food Bar Formatting
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
     * @param currentValue Current Value of Bar.
     * @param maxValue Max Value of Bar.
     * @param barLength Length of Bar.
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
     * @param player Player being checked.
     * @param world Player's World.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    public static Boolean checkPermissions(Player player, World world, String node) {
        return checkPermissions(player.getName(), world.getName(), node) || player.hasPermission(node) || player.isOp();
    }

    /**
     * Permission Checking
     * @param pName Name of Player being checked.
     * @param world Name of Player's World.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    public static Boolean checkPermissions(String pName, String world, String node) {
        return vaultB && vPerm.has(world, pName, node)
                || gmB && gmWH.getWorldPermissions(pName).getPermissionBoolean(pName, node)
                || pexB && pexPermissions.has(pName, world, node)
                || Bukkit.getServer().getPlayer(pName) != null && Bukkit.getServer().getPlayer(pName).hasPermission(node);
    }

    /**
     * Permission Checking
     * @param sender CommandSender being checked.
     * @param node Permission Node being checked.
     * @return Sender has Node.
     */
    public static Boolean checkPermissions(CommandSender sender, String node) {
        return vaultB && vPerm.has(sender, node)
                || sender.hasPermission(node);
    }

    /**
     * Variable Replacer
     * @param source String being modified.
     * @param changes Map of Search / Replace pairs.
     * @param type Type of Variable.
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
     * @param source String being modified.
     * @param search String being searched for.
     * @param replace String search term is to be replaced with.
     * @param type Type of Variable.
     * @return Source with variable replaced.
     */
    public static String replace(String source, String search, String replace, IndicatorType type) {
        return source.replace(type.getValue() + search, replace);
    }

    private static void setupPlugins() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        Plugin pluginTest;

        pluginTest = pm.getPlugin("Vault");
        vaultB = pluginTest != null;
        if (vaultB) {
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

            vaultB = vPerm != null;
            vChatB = vChat != null;
        }

        pBukkitB = setupPermPlugin(pm.getPlugin("PermissionsBukkit"));
        bPermB = setupPermPlugin(pm.getPlugin("bPermissions"));
        pexB = setupPermPlugin(pm.getPlugin("PermissionsEx"));

        if (pexB) {
            pexPermissions = PermissionsEx.getPermissionManager();
        }

        pluginTest = pm.getPlugin("GroupManager");
        gmB = pluginTest != null;
        if (gmB) {
            gmWH = ((GroupManager) pluginTest).getWorldsHolder();
            MessageUtil.logFormatted("<Permissions> " + pluginTest.getDescription().getName() + " v" + pluginTest.getDescription().getVersion() + " hooked!.");

        }

        privB = setupPermPlugin(pm.getPlugin("Privileges"));

        if (!(vaultB || pBukkitB || bPermB || pexB || gmB)) {
            MessageUtil.logFormatted("<Permissions> SuperPerms hooked!.");
        }
    }

    private static Boolean setupPermPlugin(Plugin plugin) {
        if (plugin != null) {
            MessageUtil.logFormatted("<Permissions> " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " hooked!.");
            return true;
        }

        return false;
    }
}
