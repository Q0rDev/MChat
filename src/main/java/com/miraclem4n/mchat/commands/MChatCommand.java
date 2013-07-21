package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.MiscUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MChatCommand implements CommandExecutor {
    private MChat plugin;

    public MChatCommand(MChat instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchat")) {
            return true;
        }

        if (args.length == 0)
            return false;

        if (args[0].equalsIgnoreCase("version")) {
            if (!MiscUtil.hasCommandPerm(sender, "mchat.version")) {
                return true;
            }

            String[] vArray = plugin.pdfFile.getVersion().split("-");

            MessageUtil.sendMessage(sender, "&6Full Version: &1" + plugin.pdfFile.getVersion());
            MessageUtil.sendMessage(sender, "&6MineCraft Version: &2" + vArray[0]);
            MessageUtil.sendMessage(sender, "&6Release Version: &2" + vArray[1]);
            MessageUtil.sendMessage(sender, "&6Jenkins Build &5#&5: &2" + vArray[2]);

            return true;
        } else if (args[0].equalsIgnoreCase("reload")
                || args[0].equalsIgnoreCase("r")) {
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("config")
                        || args[1].equalsIgnoreCase("co")) {
                    if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.config")) {
                        return true;
                    }

                    MessageUtil.sendMessage(sender, "Config Reloaded.");
                    return true;
                } else if (args[1].equalsIgnoreCase("info")
                        || args[1].equalsIgnoreCase("i")) {
                    if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.info")) {
                        return true;
                    }

                    YmlManager.reloadYml(YmlType.INFO_YML);
                    MessageUtil.sendMessage(sender, "Info Reloaded.");
                    return true;
                } else if (args[1].equalsIgnoreCase("censor")
                        || args[1].equalsIgnoreCase("ce")) {
                    if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.censor")) {
                        return true;
                    }

                    YmlManager.reloadYml(YmlType.CENSOR_YML);
                    MessageUtil.sendMessage(sender, "Censor Reloaded.");
                    return true;
                } else if (args[1].equalsIgnoreCase("locale")
                        || args[1].equalsIgnoreCase("l")) {
                    if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.locale")) {
                        return true;
                    }

                    YmlManager.reloadYml(YmlType.LOCALE_YML);
                    MessageUtil.sendMessage(sender, "Locale Reloaded.");
                    return true;
                } else if (args[1].equalsIgnoreCase("all")
                        || args[1].equalsIgnoreCase("a")) {
                    if (!MiscUtil.hasCommandPerm(sender, "mchat.reload.all")) {
                        return true;
                    }

                    YmlManager.load();
                    MessageUtil.sendMessage(sender, "All Config's Reloaded.");
                    return true;
                }
            }
        }

        return false;
    }
}
