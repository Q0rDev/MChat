package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MuteCommand implements CommandExecutor {
    mChatSuite plugin;

    public MuteCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatmute")) {
            if (args.length > 0) {
                if (!plugin.getAPI().checkPermissions(sender, "mchat.mute")) {
                    Messanger.sendMessage(sender, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.mute"));
                    return true;
                }
                
                String target = args[0];
                
                if (plugin.getServer().getPlayer(args[0]) != null) {
                    target = plugin.getServer().getPlayer(args[0]).getName();
                }

                plugin.isMuted.put(target, true);

                Messanger.sendMessage(sender, "Target '" + target  + "' successfully muted. To unmute use this command again.");

                return true;
            }
        }

        return false;
    }
}