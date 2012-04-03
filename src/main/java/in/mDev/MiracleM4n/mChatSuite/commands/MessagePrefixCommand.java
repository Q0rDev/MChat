package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MessagePrefixCommand implements CommandExecutor {
    mChatSuite plugin;

    public MessagePrefixCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatmessageprefix")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!plugin.getAPI().checkPermissions(sender, "mchat.messageprefix")) {
                        Messanger.sendMessage(sender, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.messageprefix"));
                        return true;
                    }

                    String message = "";

                    for (String arg : args)
                        message += " " + arg;

                    message = message.trim();

                    plugin.mPrefix.put(sender.getName(), message);

                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    plugin.mPrefix.put(sender.getName(), "");

                    return true;
                }
            }
        }

        return false;
    }
}
