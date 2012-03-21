package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MChannelCommand implements CommandExecutor {
    mChatSuite plugin;

    public MChannelCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchannel")) {
            if (!(args.length > 0)) {
                String[] message = new String[] {
                        Messanger.format("'/" + cmd + " join' for more information."),
                        Messanger.format("'/" + cmd + " leave' for more information."),
                        Messanger.format("'/" + cmd + " create' for more information."),
                        Messanger.format("'/" + cmd + " remove' for more information."),
                        Messanger.format("'/" + cmd + " edit' for more information.")
                    };

                sender.sendMessage(message);
                return true;
            }

            if (args[0].equalsIgnoreCase("create")) {
                if (!(args.length > 1)) {
                    sender.sendMessage(Messanger.format("Please use'/" + cmd + " create [ChannelName]' for more information."));
                    return true;
                }



                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (!(args.length > 1)) {
                    sender.sendMessage(Messanger.format("Please use'/" + cmd + " remove [ChannelName]'."));
                    return true;
                }



                return true;
            } else if (args[0].equalsIgnoreCase("edit")) {
                if (!(args.length > 1)) {
                    sender.sendMessage(Messanger.format("Please use'/" + cmd + " edit [ChannelName]' for more information."));
                    return true;
                }



                return true;
            }

            if (!(sender instanceof Player)) {
                Messanger.sendMessage(sender, "Console's can't interact with channels.");
                return true;
            }

            if (args[0].equalsIgnoreCase("join")) {
                if (!(args.length > 1)) {
                    sender.sendMessage(Messanger.format("Please use'/" + cmd + " join [ChannelName]'."));
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.join." + args[1].toLowerCase())) {
                    sender.sendMessage(Messanger.format("You don't have Permissions for: '" + "mchannel.join." + args[1].toLowerCase() + "'."));
                    return true;
                }

                if (plugin.getChannelManager().getChannel(args[1]) == null)
                    sender.sendMessage(Messanger.format("No Channel by the name of '" + args[1].toLowerCase() + "' could be found."));

                plugin.getChannelManager().getChannel(args[1]).addOccupant(sender.getName(), true);
                sender.sendMessage(Messanger.format("You have successfully joined '" + args[1].toLowerCase() + "'."));

                return true;
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!(args.length > 1)) {
                    sender.sendMessage(Messanger.format("Please use'/" + cmd + " leave [ChannelName]'."));
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.leave." + args[1].toLowerCase())) {
                    sender.sendMessage(Messanger.format("You don't have Permissions for: '" + args[1].toLowerCase() + "'."));
                    return true;
                }

                if (plugin.getChannelManager().getChannel(args[1]) == null)
                    sender.sendMessage(Messanger.format("No Channel by the name of '" + "mchannel.leave." + args[1].toLowerCase() + "' could be found."));

                plugin.getChannelManager().getChannel(args[1]).removeOccupant(sender.getName());
                sender.sendMessage(Messanger.format("You have successfully left '" + args[1].toLowerCase() + "'."));

                return true;
            }
        }

        return true;
    }
}
