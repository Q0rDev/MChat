package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.configs.locale.LocaleType;
import com.miraclem4n.mchat.types.InfoEditType;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoAlterCommand implements CommandExecutor {
    private InfoType type;
    private String cmd;

    public InfoAlterCommand(String command, InfoType infoType) {
        type = infoType;
        cmd = command;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(cmd)) {
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendMessage(sender, "Use '/" + cmd + " add/edit/remove' for more info.");
            return true;
        }

        InfoEditType editType;

        String p = "user";
        String t = "player";
        String T = "Player";

        if (type == InfoType.GROUP) {
            p = "group";
            t = "group";
            T = "Group";
        }

        if (args[0].equalsIgnoreCase("a")
                || args[0].equalsIgnoreCase("add")) {
            if (args.length == 1) {
                MessageUtil.sendMessage(sender, "Usage for '/" + cmd + " add':\n" +
                        "    - /" + cmd + " add " + t + " <" + T + ">\n" +
                        "    - /" + cmd + " add ivar <" + T + "> <Variable> [Value]\n" +
                        "    - /" + cmd + " add world <" + T + "> <World>\n" +
                        "    - /" + cmd + " add wvar <" + T + "> <World> <Variable> [Value]");
                return true;
            } else if (args[1].equalsIgnoreCase(t.substring(0, 1))
                    || args[1].equalsIgnoreCase(t)) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add." + t)) {
                    return true;
                }

                editType = InfoEditType.ADD_BASE;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.addBase(args[2], type);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("iVar")
                    || args[1].equalsIgnoreCase("infoVariable")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.ivar")) {
                    return true;
                }

                editType = InfoEditType.ADD_INFO_VAR;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.setInfoVar(args[2], type, args[3], combineArgs(args, 4));
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("w")
                    || args[1].equalsIgnoreCase("world")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.world")) {
                    return true;
                }

                editType = InfoEditType.ADD_WORLD;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.addWorld(args[2], type, args[3]);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("wVar")
                    || args[1].equalsIgnoreCase("worldVariable")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".add.wvar")) {
                    return true;
                }

                editType = InfoEditType.ADD_WORLD_VAR;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.setWorldVar(args[2], type, args[3], args[4], combineArgs(args, 5));
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            }
        } else if (args[0].equalsIgnoreCase("r")
                || args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                MessageUtil.sendMessage(sender, "Usage for '/" + cmd + " remove':\n" +
                        "    - /" + cmd + " remove " + t + " <" + T + ">\n" +
                        "    - /" + cmd + " remove ivar <" + T + "> <Variable>\n" +
                        "    - /" + cmd + " remove world <" + T + "> <World>\n" +
                        "    - /" + cmd + " remove wvar <" + T + "> <World> <Variable>");
                return true;
            } else if (args[1].equalsIgnoreCase(t.substring(0, 1))
                    || args[1].equalsIgnoreCase(t)) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove." + t)) {
                    return true;
                }

                editType = InfoEditType.REMOVE_BASE;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.removeBase(args[2], type);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("iVar")
                    || args[1].equalsIgnoreCase("infoVariable")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.ivar")) {
                    return true;
                }

                editType = InfoEditType.REMOVE_INFO_VAR;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.removeInfoVar(args[2], type, args[3]);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("w")
                    || args[1].equalsIgnoreCase("world")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.world")) {
                    return true;
                }

                editType = InfoEditType.REMOVE_WORLD;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.removeWorld(args[2], type, args[3]);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            } else if (args[1].equalsIgnoreCase("wVar")
                    || args[1].equalsIgnoreCase("worldVariable")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat." + p + ".remove.wvar")) {
                    return true;
                }

                editType = InfoEditType.REMOVE_WORLD_VAR;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.removeWorldVar(args[2], type, args[3], args[4]);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            }
        } else if (type == InfoType.USER
                && (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("set"))) {
            if (args.length == 1) {
                MessageUtil.sendMessage(sender, "Usage for '/mchat user set':\n" +
                        "    - /" + cmd + " set group <Player> <" + T + ">");
                return true;
            } else if (args[1].equalsIgnoreCase("g")
                    || args[1].equalsIgnoreCase("group")) {
                if (!MiscUtil.hasCommandPerm(sender, "mchat.user.set.group")) {
                    return true;
                }

                editType = InfoEditType.SET_GROUP;

                if (args.length < editType.getLength()) {
                    editType.sendMsg(sender, cmd, type);
                    return true;
                }

                Writer.setGroup(args[2], args[3]);
                MessageUtil.sendMessage(sender, LocaleType.MESSAGE_INFO_ALTERATION.getVal());
                return true;
            }
        }

        return false;
    }

    private String combineArgs(String[] args, Integer startingPoint) {
        String argString = "";

        for (int i = startingPoint; i < args.length; ++i)
            argString += args[i] + " ";

        return argString.trim();
    }
}
