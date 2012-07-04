package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.channels.Channel;
import com.miraclem4n.mchat.channels.ChannelManager;
import com.miraclem4n.mchat.types.ChannelEditType;
import com.miraclem4n.mchat.types.ChannelType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MChannelCommand implements CommandExecutor {
    mChatSuite plugin;

    public MChannelCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchannel"))
            return true;

        if (!(args.length > 0)) {
            String[] message = new String[] {
                    MessageUtil.format("'/" + cmd + " types' for more information."),
                    MessageUtil.format("'/" + cmd + " edittypes' for more information."),
                    MessageUtil.format("'/" + cmd + " create' for more information."),
                    MessageUtil.format("'/" + cmd + " remove' for more information."),
                    MessageUtil.format("'/" + cmd + " edit' for more information."),
                    MessageUtil.format("'/" + cmd + " types' for more information."),
                    MessageUtil.format("'/" + cmd + " join' for more information."),
                    MessageUtil.format("'/" + cmd + " leave' for more information.")
            };

            sender.sendMessage(message);
            return true;
        }

        if (args[0].equalsIgnoreCase("types")) {
            if (!MiscUtil.hasCommandPerm(sender, "mchannel.types"))
                return true;

            String types = "";

            for (ChannelType type : ChannelType.values())
                types += " " + type.getName();

            types = types.trim();

            MessageUtil.sendMessage(sender, "All valid ChannelTypes: '" + types + "'.");

            return true;
        } else if (args[0].equalsIgnoreCase("editTypes")) {
            if (!MiscUtil.hasCommandPerm(sender, "mchannel.edittypes"))
                return true;

            String editTypes = "";

            for (ChannelEditType type : ChannelEditType.values())
                editTypes += " " + type.getName();

            editTypes = editTypes.trim();

            MessageUtil.sendMessage(sender, "All valid ChannelEditTypes: '" + editTypes + "'.");

            return true;
        } else if (args[0].equalsIgnoreCase("create")) {
            if (!(args.length > 2)) {
                MessageUtil.sendMessage(sender, "Please use'/" + cmd + " create [ChannelName] [ChannelType] <Password/Distance>'.");
                return true;
            }

            if (!MiscUtil.hasCommandPerm(sender, "mchannel.create." + args[1].toLowerCase()))
                return true;

            ChannelType type = ChannelType.fromName(args[2]);

            if (type == null) {
                MessageUtil.sendMessage(sender, "'" + args[2] + "' is not a valid ChannelType. Use '/" + cmd + " types' for more information.");
                return true;
            }

            Boolean passworded = false;
            String password = "";
            Integer distance = -1;

            if (type == ChannelType.PASSWORD) {
                if (args.length < 4) {
                    MessageUtil.sendMessage(sender, "'" + args[2] + "' ChannelType cannot be created with only 3 arguments. Use '/" + cmd + " create' for more information.");
                    return true;
                } else {
                    passworded = true;
                    password = args[3];
                }
            } else if (type == ChannelType.LOCAL) {
                if (args.length < 4) {
                    MessageUtil.sendMessage(sender, "'" + args[2] + "' ChannelType cannot be created with only 3 arguments. Use '/" + cmd + " create' for more information.");
                    return true;
                } else {
                    distance = Integer.parseInt(args[3]);
                }
            }

            if (ChannelManager.getChannel(args[1]) != null) {
                MessageUtil.sendMessage(sender, "'" + args[1] + "' has already been created.");
                return true;
            }

            ChannelManager.createChannel(args[1], type, "[", "]", passworded, password, distance, false);
            MessageUtil.sendMessage(sender, "You have successfully created Channel '" + args[1].toLowerCase() + "' of type '" + args[2] + "'.");

            return true;
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (!(args.length > 1)) {
                MessageUtil.sendMessage(sender, "Please use'/" + cmd + " remove [ChannelName]'.");
                return true;
            }

            if (!MiscUtil.hasCommandPerm(sender, "mchannel.remove." + args[1].toLowerCase()))
                return true;

            if (ChannelManager.getChannel(args[1]) == null) {
                MessageUtil.sendMessage(sender, "'" + args[1] + "' is not a valid channel.");
                return true;
            }

            ChannelManager.removeChannel(args[1]);
            MessageUtil.sendMessage(sender, "You have successfully removed Channel '" + args[1].toLowerCase() + "'.");

            return true;
        } else if (args[0].equalsIgnoreCase("edit")) {
            if (!(args.length > 3)) {
                MessageUtil.sendMessage(sender, "Please use'/" + cmd + " edit [ChannelName] [EditType] [Option]'.");
                return true;
            }

            if (!MiscUtil.hasCommandPerm(sender, "mchannel.edit." + args[1].toLowerCase()))
                return true;

            if (ChannelManager.getChannel(args[1]) == null) {
                MessageUtil.sendMessage(sender, "'" + args[1] + "' is not a valid channel.");
                return true;
            }

            if (ChannelEditType.fromName(args[2]) == null) {
                MessageUtil.sendMessage(sender, "'" + args[2] + "' is not a valid EditType.");
                return true;
            }

            ChannelEditType edit = ChannelEditType.fromName(args[2]);
            Channel channel = ChannelManager.getChannel(args[1]);
            Object option = null;

            try {
                if (edit.getName().equalsIgnoreCase("Default")) {
                    ChannelManager.setDefaultChannel(channel.getName());
                    MessageUtil.sendMessage(sender, "You have successfully edited '" + args[1].toLowerCase() + "'.");

                    return true;
                } else if (edit.getName().equalsIgnoreCase("Name"))
                    option = args[3];
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
                MessageUtil.sendMessage(sender, "Error when converting '" + args[3] + "' to an Object of type '" + ChannelEditType.fromName(args[2]).getOptionClass().getSimpleName() + "'.");
                return true;
            }

            if (option == null) {
                MessageUtil.sendMessage(sender, "The option '" + args[3] + "' seems to not be resolving properly.");
                return true;
            }

            ChannelManager.editChannel(channel, edit, option);
            MessageUtil.sendMessage(sender, "You have successfully edited '" + args[1].toLowerCase() + "'.");

            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtil.sendMessage(sender, "Console's can't interact with channels.");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            if (!(args.length > 1)) {
                MessageUtil.sendMessage(sender, "Please use'/" + cmd + " join [ChannelName]'.");
                return true;
            }

            if (!MiscUtil.hasCommandPerm(sender, "mchannel.join." + args[1].toLowerCase()))
                return true;

            Channel channel = ChannelManager.getChannel(args[1]);

            if (channel == null)
                MessageUtil.sendMessage(sender, "No Channel by the name of '" + args[1].toLowerCase() + "' could be found.");

            if (channel.getOccupants().contains(sender.getName())) {
                MessageUtil.sendMessage(sender, "You are already in channel '" + args[1] + "'.");
                return true;
            }

            if (channel.isPassworded()) {
                if (!(args.length > 2)) {
                    MessageUtil.sendMessage(sender, "'" + args[1] + "' is a Passworded channel. Please use '/" + cmd + " join [ChannelName] [Password]' to enter.");
                    return true;
                } else if (!args[2].equalsIgnoreCase(channel.getPassword()))
                    MessageUtil.sendMessage(sender, "Password entered for channel '" + args[1].toLowerCase() + "' is invalid.");
            }

            channel.addOccupant(sender.getName(), true);
            MessageUtil.sendMessage(sender, "You have successfully joined '" + args[1].toLowerCase() + "'.");

            return true;
        } else if (args[0].equalsIgnoreCase("leave")) {
            if (!(args.length > 1)) {
                MessageUtil.sendMessage(sender, "Please use'/" + cmd + " leave [ChannelName]'.");
                return true;
            }

            if (!MiscUtil.hasCommandPerm(sender, "mchannel.leave." + args[1].toLowerCase()))
                return true;

            Channel channel = ChannelManager.getChannel(args[1]);

            if (channel == null)
                MessageUtil.sendMessage(sender, "No Channel by the name of '" + args[1].toLowerCase() + "' could be found.");

            if (!channel.getOccupants().contains(sender.getName())) {
                MessageUtil.sendMessage(sender, "You are not in channel '" + args[1] + "'.");
                return true;
            }

            channel.removeOccupant(sender.getName());
            MessageUtil.sendMessage(sender, "You have successfully left '" + args[1].toLowerCase() + "'.");

            return true;
        }

        return true;
    }
}
