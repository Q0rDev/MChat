package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MChatSayCommand implements CommandExecutor {
    mChatSuite plugin;

    public MChatSayCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatsay")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.say")) {
                        sender.sendMessage(Messanger.format(plugin.getLocale().getOption("noPermissions") + " " + cmd + "."));
                        return true;
                    }
                }

                plugin.getServer().broadcastMessage(plugin.getLocale().getOption("sayName") + " " +  message);
                return true;
            }
        }

        return false;
    }
}
