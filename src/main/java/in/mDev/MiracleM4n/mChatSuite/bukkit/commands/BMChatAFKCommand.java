package in.mDev.MiracleM4n.mChatSuite.bukkit.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BMChatAFKCommand implements CommandExecutor {
    mChatSuite plugin;

    public BMChatAFKCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatafk")) {
            String message = " Away From Keyboard";

            if (args.length > 0) {
                message = "";

                for (String arg : args)
                    message += " " + arg;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (plugin.isAFK.get(player.getName()) != null)
                    if (plugin.isAFK.get(player.getName())) {
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + player.getName());
                        return true;
                    }

                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.afk.self")) {
                    player.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }

                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + sender.getName() + message);
                return true;
            } else {
                plugin.getAPI().log(plugin.getAPI().formatMessage("Console's can't be AFK."));
                return true;
            }
        }

        return false;
    }
}
