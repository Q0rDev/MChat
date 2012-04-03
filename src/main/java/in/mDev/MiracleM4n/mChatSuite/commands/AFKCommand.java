package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {
    mChatSuite plugin;

    public AFKCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatafk")) {
            String message = " Away From Keyboard";

            if (args.length > 0) {
                message = "";

                for (String arg : args)
                    message += " " + arg;
            }

            if (!(sender instanceof Player)) {
                Messanger.sendMessage(sender, "Console's can't be AFK.");
                return true;
            }

            Player player = (Player) sender;

            if (plugin.isAFK.get(player.getName()) != null)
                if (plugin.isAFK.get(player.getName())) {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + player.getName());
                    return true;
                }

            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.afk.self")) {
                Messanger.sendMessage(player, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.afk.self"));
                return true;
            }

            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + sender.getName() + message);
            return true;
        }

        return false;
    }
}
