package in.mDev.MiracleM4n.mChatSuite.bukkit.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BMChatListCommand implements CommandExecutor {
    mChatSuite plugin;

    public BMChatListCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatlist")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.list")) {
                    player.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }
            }

            // My Way
            // sender.sendMessage(plugin.getAPI().addColour("&4" + plugin.getLocale().pOffline + ": &8" + plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers()));

            // Waxdt's Way
            sender.sendMessage(plugin.getAPI().addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --"));
            formatList(sender);
            return true;
        }

        return false;
    }

    void formatList(CommandSender sender) {
        HashMap<String, Integer> cLMap = new HashMap<String, Integer>();

        String msg = "";
        String line = "";
        String[] msgS;

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            String iVar = plugin.getAPI().ParseMessage(players.getName(), players.getWorld().getName(), "", plugin.varIndicator + plugin.listVar);
            String mName = plugin.getAPI().ParseListCmd(players.getName(), players.getWorld().getName());

            Boolean collapsed = false;

            if (iVar.isEmpty())
                iVar = "Default";

            for (String string : plugin.cLVars.split(",")) {
                if (!iVar.equals(string))
                    continue;

                collapsed = true;

                if (cLMap.get(string) != null) {
                    Integer sVal = cLMap.get(string);

                    cLMap.put(string, sVal+1);
                } else
                    cLMap.put(string, 1);
            }

            if (collapsed)
                continue;

            if (plugin.isAFK.get(players.getName()) != null && plugin.isAFK.get(players.getName()))
                if (msg.contains(iVar + ": &f"))
                    msg = msg.replace(iVar + ": &f", iVar + ": &f&4[" + plugin.getLocale().getOption("AFK") + "]" + mName + "&f, &f");
                else
                    msg += (iVar + ": &f&4[" + plugin.getLocale().getOption("AFK") + "]" + mName + "&f, &f" + '\n');
            else
            if (msg.contains(iVar + ": &f"))
                msg = msg.replace(iVar + ": &f", iVar + ": &f" + mName + "&f, &f");
            else
                msg += (iVar + ": &f" + mName + "&f, &f" + '\n');

            if (!msg.contains("" + '\n'))
                msg += '\n';
        }

        for (Map.Entry entry : cLMap.entrySet())
            msg += (entry.getKey() + ": &f" + entry.getValue() + '\n');

        if (msg.contains("" + '\n')) {
            if (plugin.useGroupedList) {
                msgS = msg.split("" + '\n');


                for (String arg : msgS)
                    sender.sendMessage(plugin.getAPI().addColour(arg));
            } else {
                msg = plugin.getAPI().addColour(msg.replace("" + '\n', "&5 | &f"));

                sender.sendMessage(msg);
            }
        } else
            sender.sendMessage(plugin.getAPI().addColour(msg));

        for (int i = 20; i < plugin.getAPI().addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --").length(); i++)
            line += "-";

        sender.sendMessage(plugin.getAPI().addColour("&6" + line));
    }
}
