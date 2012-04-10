package in.mDev.MiracleM4n.mChatSuite.api;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class API {
    mChatSuite plugin;

    public API(mChatSuite instance) {
        plugin = instance;
    }

    /**
     * Global Variable Addition
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public void addGlobalVar(String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put("%^global^%|" + var, value);
    }

    /**
     * Player Variable Addition
     * @param pName Name of Player this Variable is being added for.
     * @param var Name of Variable being added.
     * @param value Value of Variable being added.
     */
    public void addPlayerVar(String pName, String var, String value) {
        if (var == null || var.isEmpty())
            return;

        if (value == null)
            value = "";

        plugin.cVarMap.put(pName + "|" +var, value);
    }

    /**
     * Health Bar Formatting
     * @param player Player the HealthBar is being rendered for.
     * @return Formatted Health Bar.
     */
    public String createHealthBar(Player player) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();

        return createBasicBar(health, maxHealth, barLength);
    }

    /**
     * Basic Bar Formatting
     * @param currentValue Current Value of Bar.
     * @param maxValue Max Value of Bar.
     * @param barLength Length of Bar.
     * @return Formatted Health Bar.
     */
    public String createBasicBar(float currentValue, float maxValue, float barLength) {
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
    public Boolean checkPermissions(Player player, World world, String node) {
        return checkPermissions(player.getName(), world.getName(), node)
                || player.hasPermission(node)
                || player.isOp();
    }

    /**
     * Permission Checking
     * @param pName Name of Player being checked.
     * @param world Name of Player's World.
     * @param node Permission Node being checked.
     * @return Player has Node.
     */
    public Boolean checkPermissions(String pName, String world, String node) {
        if (plugin.vaultB)
            if (plugin.vPerm.has(world, pName, node))
                return true;

        if (plugin.gmPermissionsB)
            if (plugin.gmPermissionsWH.getWorldPermissions(pName).getPermissionBoolean(pName, node))
                return true;

        if (plugin.PEXB)
            if (plugin.pexPermissions.has(pName, world, node))
                return true;

        if (plugin.getServer().getPlayer(pName) != null)
            if (plugin.getServer().getPlayer(pName).hasPermission(node))
                return true;

        return false;
    }

    /**
     * Permission Checking
     * @param sender CommandSender being checked.
     * @param node Permission Node being checked.
     * @return Sender has Node.
     */
    public Boolean checkPermissions(CommandSender sender, String node) {
        if (plugin.vaultB)
            if (plugin.vPerm.has(sender, node))
                return true;

        return sender.hasPermission(node);
    }
}
