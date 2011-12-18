package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.player.SpoutPlayer;

public class MECommandSender implements CommandExecutor {
    mChatSuite plugin;

    public MECommandSender(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (!plugin.mChatEB) {
            sender.sendMessage(formatMessage(plugin.getAPI().addColour("mChatEssentials' functions are currently disabled.")));
            return true;
        }

        if (commandName.equalsIgnoreCase("mchatme")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (plugin.getAPI().checkPermissions(player, "mchat.me"))
                        plugin.getServer().broadcastMessage(plugin.getAPI().ParseMe(player, message));
                    else
                        sender.sendMessage(formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));

                    return true;
                } else {
                    String senderName = "Console";
                    plugin.getServer().broadcastMessage("* " + senderName + " " + message);
                    plugin.getAPI().log("* " + senderName + " " + message);
                    return true;
                }
            }
        } else if (commandName.equalsIgnoreCase("mchatwho")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!plugin.getAPI().checkPermissions(player, "mchat.who")) {
                        sender.sendMessage(formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                        return true;
                    }
                }

                if (plugin.getServer().getPlayer(args[0]) == null) {
                    sender.sendMessage(formatPNF(args[0]));
                    return true;
                } else {
                    Player receiver = plugin.getServer().getPlayer(args[0]);
                    formatWho(sender, receiver);
                    return true;
                }
            }
        } else if (commandName.equalsIgnoreCase("mchatafk")) {
            String message = " Away From Keyboard";

            if (args.length > 0) {
                message = "";

                for (String arg : args)
                    message += " " + arg;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                
                if (plugin.isAFK.get(player.getName()) != null)
                    if (plugin.isAFK.get(player.getName()))
                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + player.getName());

                if (!plugin.getAPI().checkPermissions(player, "mchat.afk")) {
                    player.sendMessage(formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }

                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + sender.getName() + message);
                return true;
            } else {
                plugin.getAPI().log(formatMessage("Console's can't be AFK."));
                return true;
            }
        } else if (commandName.equalsIgnoreCase("mchatafkother")) {
            String message = " Away From Keyboard";

            if (args.length > 1) {
                message = "";

                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getAPI().checkPermissions(player, "mchat.afkother")) {
                    sender.sendMessage(formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }
            }

            if (plugin.getServer().getPlayer(args[0]) == null) {
                sender.sendMessage(formatPNF(args[0]));
                return true;
            }

            Player afkTarget = plugin.getServer().getPlayer(args[0]);

            if (plugin.isAFK.get(afkTarget.getName()) != null &&
                    plugin.isAFK.get(afkTarget.getName())) {
                if (plugin.spoutB) {
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption("noLongerAFK"), Material.PAPER);
                        else
                            players.sendMessage(plugin.getAPI().ParsePlayerName(afkTarget) + " " + plugin.getLocale().getOption("notAFK"));
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getLocale().getOption("AFK") + " -" + '\n' + plugin.getAPI().ParsePlayerName(afkTarget));
                } else
                    plugin.getServer().broadcastMessage(plugin.getAPI().ParsePlayerName(afkTarget) + " " + plugin.getLocale().getOption("notAFK"));

                afkTarget.setSleepingIgnored(false);
                plugin.isAFK.put(afkTarget.getName(), false);

                if (plugin.useAFKList)
                    if (plugin.getAPI().ParseTabbedList(afkTarget).length() > 15) {
                        String pLName = plugin.getAPI().ParseTabbedList(afkTarget);
                        pLName = pLName.substring(0, 16);
                        afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(plugin.getAPI().ParseTabbedList(afkTarget));

                return true;
            } else {
                if (plugin.spoutB) {
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption("isAFK"), Material.PAPER);
                        else
                            players.sendMessage(plugin.getAPI().ParsePlayerName(afkTarget) + " " + plugin.getLocale().getOption("isAFK") + " [" + message + " ]");
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getLocale().getOption("AFK") + " -" + '\n' + plugin.getAPI().ParsePlayerName(afkTarget));
                } else
                    plugin.getServer().broadcastMessage(plugin.getAPI().ParsePlayerName(afkTarget) + " " + plugin.getLocale().getOption("isAFK") + " [" + message + " ]");

                afkTarget.setSleepingIgnored(true);
                plugin.isAFK.put(afkTarget.getName(), true);
                plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

                if (plugin.useAFKList)
                    if (("[" + plugin.getLocale().getOption("AFK") + "] " + plugin.getAPI().ParseTabbedList(afkTarget)).length() > 15) {
                        String pLName = plugin.getAPI().addColour("[<gold>" + plugin.getLocale().getOption("AFK") + "] " + afkTarget);
                        pLName = pLName.substring(0, 16);
                        afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(plugin.getAPI().addColour("<gold>[" + plugin.getLocale().getOption("AFK") + "] " + afkTarget));

                return true;
            }
        } else if (commandName.equalsIgnoreCase("mchatlist")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getAPI().checkPermissions(player, "mchat.list")) {
                    player.sendMessage(formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }
            }

            // My Way
            // sender.sendMessage(plugin.getAPI().addColour("&4" + plugin.getLocale().pOffline + ": &8" + plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers()));

            // Waxdt's Way
            sender.sendMessage(plugin.getAPI().addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --"));
            formatList(sender);
            return true;
        }

        return false;
    }

    void formatList(CommandSender sender) {
        String msg = "";
        String[] msgS;

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            String iVar = plugin.getInfoReader().getInfo(players, plugin.listVar);
            String mName = plugin.getAPI().ParseListCmd(players.getName());

            if (iVar.isEmpty())
                continue;

            if (plugin.isAFK.get(players.getName())) {
                if (msg.contains(iVar + ": &f")) {
                    msg = msg.replace(iVar + ": &f", iVar + ": &f&4[" + plugin.getLocale().getOption("AFK") + "]" + mName + "&f, &f");
                } else {
                    msg += (iVar + ": &f&4[" + plugin.getLocale().getOption("AFK") + "]" + mName + "&f, &f" + '\n');
                }
            } else {
                if (msg.contains(iVar + ": &f")) {
                    msg = msg.replace(iVar + ": &f", iVar + ": &f" + mName + "&f, &f");
                } else {
                    msg += (iVar + ": &f" + mName + "&f, &f" + '\n');
                }
            }

            if (!msg.contains("" + '\n'))
                msg += '\n';
        }

        if (msg.contains("" + '\n')) {
            msgS = msg.split("" + '\n');

            for (String arg : msgS) {
                sender.sendMessage(plugin.getAPI().addColour(arg));
            }

            sender.sendMessage(plugin.getAPI().addColour("&6-------------------------"));
            return;
        }

        sender.sendMessage(plugin.getAPI().addColour(msg));
    }

    void formatWho(CommandSender sender, Player recipient) {
        String recipientName = plugin.getAPI().ParsePlayerName(recipient);
        Integer locX = (int) recipient.getLocation().getX();
        Integer locY = (int) recipient.getLocation().getY();
        Integer locZ = (int) recipient.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);
        String world = recipient.getWorld().getName();

        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + " Name: " + recipient.getName()));
        sender.sendMessage(plugin.getAPI().addColour("Display Name: " + recipient.getDisplayName()));
        sender.sendMessage(plugin.getAPI().addColour("Formatted Name: " + recipientName));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s Location: [ " + loc + " ]"));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s World: " + world));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s Health: " + plugin.getAPI().healthBar(recipient) + " " + recipient.getHealth() + "/20"));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s IP: " + recipient.getAddress().toString().replace("/", "")));
        sender.sendMessage(plugin.getAPI().addColour("Current Item: " + recipient.getItemInHand().getType().name()));
        sender.sendMessage(plugin.getAPI().addColour("Entity ID: #" + recipient.getEntityId()));
    }

    String formatMessage(String message) {
        return (plugin.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String formatPNF(String playerNotFound) {
        return (plugin.getAPI().addColour(formatMessage("") + " " + plugin.getLocale().getOption("player") + " &e" + playerNotFound + " &4" + plugin.getLocale().getOption("notFound")));
    }
}

