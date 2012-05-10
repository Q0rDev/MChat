package in.mDev.MiracleM4n.mChatSuite.api;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class API {
    public API() {}

    public void addGlobalVar(String var, String value) {
        com.miraclem4n.mchat.api.API.addGlobalVar(var, value);
    }

    public void addPlayerVar(String pName, String var, String value) {
        com.miraclem4n.mchat.api.API.addPlayerVar(pName, var, value);
    }

    public String createHealthBar(Player player) {
        return com.miraclem4n.mchat.api.API.createHealthBar(player);
    }

    public String createBasicBar(float currentValue, float maxValue, float barLength) {
        return com.miraclem4n.mchat.api.API.createBasicBar(currentValue, maxValue, barLength);
    }

    public Boolean checkPermissions(Player player, World world, String node) {
        return com.miraclem4n.mchat.api.API.checkPermissions(player, world, node);
    }

    public Boolean checkPermissions(String pName, String world, String node) {
        return com.miraclem4n.mchat.api.API.checkPermissions(pName, world, node);
    }

    public Boolean checkPermissions(CommandSender sender, String node) {
        return com.miraclem4n.mchat.api.API.checkPermissions(sender, node);
    }
}
