package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.channel.Channel;
import in.mDev.MiracleM4n.mChatSuite.channel.ChannelEditType;
import in.mDev.MiracleM4n.mChatSuite.channel.ChannelType;
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
                        Messanger.format("'/" + cmd + " types' for more information."),
                        Messanger.format("'/" + cmd + " edittypes' for more information."),
                        Messanger.format("'/" + cmd + " create' for more information."),
                        Messanger.format("'/" + cmd + " remove' for more information."),
                        Messanger.format("'/" + cmd + " edit' for more information."),
                        Messanger.format("'/" + cmd + " types' for more information."),
                        Messanger.format("'/" + cmd + " join' for more information."),
                        Messanger.format("'/" + cmd + " leave' for more information.")
                };

                sender.sendMessage(message);
                return true;
            }

            if (args[0].equalsIgnoreCase("types")) {
                if (!plugin.getAPI().checkPermissions(sender, "mchannel.types")) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: 'mchannel.types'.");
                    return true;
                }

                String types = "";

                for (ChannelType type : ChannelType.values())
                    types += " " + type.getName();

                types.trim();

                Messanger.sendMessage(sender, "All valid ChannelTypes: '" + types + "'.");

                return true;
            } else if (args[0].equalsIgnoreCase("editTypes")) {
                if (!plugin.getAPI().checkPermissions(sender, "mchannel.edittypes")) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: 'mchannel.edittypes'.");
                    return true;
                }

                String editTypes = "";

                for (ChannelEditType type : ChannelEditType.values())
                    editTypes += " " + type.getName();

                editTypes.trim();

                Messanger.sendMessage(sender, "All valid ChannelEditTypes: '" + editTypes + "'.");

                return true;
            } else if (args[0].equalsIgnoreCase("create")) {
                if (!(args.length > 2)) {
                    Messanger.sendMessage(sender, "Please use'/" + cmd + " create [ChannelName] [ChannelType]'.");
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.create." + args[1].toLowerCase())) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: '" + "mchannel.create." + args[1].toLowerCase() + "'.");
                    return true;
                }

                if (ChannelType.fromName(args[2]) == null) {
                    Messanger.sendMessage(sender, "'" + args[2] + "' is not a valid ChannelType. Use '/" + cmd + " types' for more information.");
                    return true;
                }

                plugin.getChannelManager().createChannel(args[1], ChannelType.fromName(args[2]), "[", "]", false, "", -1, false);
                Messanger.sendMessage(sender, "You have successfully created Channel '" + args[1].toLowerCase() + "'.");

                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (!(args.length > 1)) {
                    Messanger.sendMessage(sender, "Please use'/" + cmd + " remove [ChannelName]'.");
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.remove." + args[1].toLowerCase())) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: '" + "mchannel.remove." + args[1].toLowerCase() + "'.");
                    return true;
                }

                plugin.getChannelManager().removeChannel(args[1]);
                Messanger.sendMessage(sender, "You have successfully removed Channel '" + args[1].toLowerCase() + "'.");

                return true;
            } else if (args[0].equalsIgnoreCase("edit")) {
                if (!(args.length > 3)) {
                    Messanger.sendMessage(sender, "Please use'/" + cmd + " edit [ChannelName] [EditType] [Option]'.");
                    return true;
                }

                if (plugin.getChannelManager().getChannel(args[1]) == null) {
                    Messanger.sendMessage(sender, "'" + args[1] + "' is not a valid channel.");
                    return true;
                }

                if (ChannelEditType.fromName(args[2]) == null) {
                    Messanger.sendMessage(sender, "'" + args[2] + "' is not a valid EditType.");
                    return true;
                }
                
                ChannelEditType edit = ChannelEditType.fromName(args[2]);
                Channel channel = plugin.getChannelManager().getChannel(args[1]);
                Object option = null;
                
                try {
                    if (edit.getName().equalsIgnoreCase("Name"))
                        option = args[3];
                    else if (edit.getName().equalsIgnoreCase("Default"))
                        plugin.getChannelManager().setDefaultChannel(channel.getName());
                    else if (edit.getName().equalsIgnoreCase("Distance"))
                        option = Integer.valueOf(args[3]);
                    else if (edit.getName().equalsIgnoreCase("Password"))
                        option = args[3];
                    else if (edit.getName().equalsIgnoreCase("Passworded"))
                        option = Boolean.parseBoolean(args[3]);
                    else if (edit.getName().equalsIgnoreCase("Prefix"))
                        option = args[3];
                    else if (edit.getName().equalsIgnoreCase("Suffix"))
                        option = args[3];
                    else if (edit.getName().equalsIgnoreCase("Type"))
                        option = ChannelType.fromName(args[3]);
                } catch (Exception ignored) {
                    Messanger.sendMessage(sender, "Error when converting '" + args[3] + "' to an Object of type '" + ChannelEditType.fromName(args[2]).getOptionClass().getSimpleName() + "'.");
                    return true;
                }

                if (option == null) {
                    Messanger.sendMessage(sender, "The option '" + args[3] + "' seems to not be resolving properly.");
                    return true;
                }

                plugin.getChannelManager().editChannel(channel, edit, option);
                Messanger.sendMessage(sender, "You have successfully edited '" + args[1].toLowerCase() + "'.");

                return true;
            }

            if (!(sender instanceof Player)) {
                Messanger.sendMessage(sender, "Console's can't interact with channels.");
                return true;
            }

            if (args[0].equalsIgnoreCase("join")) {
                if (!(args.length > 1)) {
                    Messanger.sendMessage(sender, "Please use'/" + cmd + " join [ChannelName]'.");
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.join." + args[1].toLowerCase())) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: '" + "mchannel.join." + args[1].toLowerCase() + "'.");
                    return true;
                }
                
                Channel channel = plugin.getChannelManager().getChannel(args[1]);

                if (channel == null)
                    Messanger.sendMessage(sender, "No Channel by the name of '" + args[1].toLowerCase() + "' could be found.");

                if (channel.isPassworded() && !(args.length > 2)) {
                    if (!(args.length > 2))
                        Messanger.sendMessage(sender, "'" + args[1] + "' is a Passworded channel. Please use'/" + cmd + " join [ChannelName] [Password]' to enter.");
                    else if (!args[2].equalsIgnoreCase(channel.getPassword()))
                        Messanger.sendMessage(sender, "Password entered for channel '" + args[1] + "' is invalid.");

                    return true;
                }

                plugin.getChannelManager().getChannel(args[1]).addOccupant(sender.getName(), true);
                Messanger.sendMessage(sender, "You have successfully joined '" + args[1].toLowerCase() + "'.");

                return true;
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!(args.length > 1)) {
                    Messanger.sendMessage(sender, "Please use'/" + cmd + " leave [ChannelName]'.");
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(sender, "mchannel.leave." + args[1].toLowerCase())) {
                    Messanger.sendMessage(sender, "You don't have Permissions for: '" + args[1].toLowerCase() + "'.");
                    return true;
                }

                if (plugin.getChannelManager().getChannel(args[1]) == null)
                    Messanger.sendMessage(sender, "No Channel by the name of '" + "mchannel.leave." + args[1].toLowerCase() + "' could be found.");

                plugin.getChannelManager().getChannel(args[1]).removeOccupant(sender.getName());
                Messanger.sendMessage(sender, "You have successfully left '" + args[1].toLowerCase() + "'.");

                return true;
            }
        }

        return true;
    }
}
