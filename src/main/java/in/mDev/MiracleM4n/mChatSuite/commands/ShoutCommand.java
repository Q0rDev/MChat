package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShoutCommand implements CommandExecutor {
    mChatSuite plugin;

    public ShoutCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatshout")) {
            String message = "";

            for (String arg : args)
                message += " " + arg;

            if (!(sender instanceof Player)) {
                Messanger.log(Messanger.format("Console's can't shout."));
                return true;
            }

            Player player = (Player) sender;

            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.shout")) {
                Messanger.sendMessage(player, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.shout"));
                return true;
            }

            plugin.isShouting.put(sender.getName(), true);

            plugin.getServer().broadcastMessage(plugin.getParser().parseChatMessage(player.getName(), player.getWorld().getName(), message));

            plugin.isShouting.put(sender.getName(), false);

            return true;
        }

        return false;
    }
}
