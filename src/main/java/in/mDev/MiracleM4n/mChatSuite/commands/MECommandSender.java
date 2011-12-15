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

    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (!plugin.mChatEB)
            return true;

        if (commandName.equalsIgnoreCase("mchatme")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (mChatSuite.getAPI().checkPermissions(player, "mchat.me"))
                        plugin.getServer().broadcastMessage(mChatSuite.getAPI().ParseMe(player, message));
                    else
                        sender.sendMessage(formatMessage(mChatSuite.getLocale().getOption("noPermissions") + " " + commandName + "."));

                    return true;
                } else {
                    String senderName = "Console";
                    plugin.getServer().broadcastMessage("* " + senderName + " " + message);
                    mChatSuite.getAPI().log("* " + senderName + " " + message);
                    return true;
                }
            }
        } else if (commandName.equalsIgnoreCase("mchatwho")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                     Player player = (Player) sender;
                     if (!mChatSuite.getAPI().checkPermissions(player, "mchat.who")) {
                        sender.sendMessage(formatMessage(mChatSuite.getLocale().getOption("noPermissions") + " " + commandName + "."));
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
        } else if(commandName.equalsIgnoreCase("mchatafk")) {
            String message = " Away From Keyboard";

            if (args.length > 0) {
                message = "";

                for (String arg : args)
                    message += " " + arg;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!mChatSuite.getAPI().checkPermissions(player, "mchat.afk")) {
                    player.sendMessage(formatMessage(mChatSuite.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }

                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "mchatafkother " + sender.getName() + message);
                return true;
            } else {
                mChatSuite.getAPI().log(formatMessage("Console's can't be AFK."));
                return true;
            }
        } else if(commandName.equalsIgnoreCase("mchatafkother")) {
            String message = " Away From Keyboard";

            if (args.length > 1) {
                message = "";

                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];
            }

            if (sender instanceof Player) {
                 Player player = (Player) sender;
                 if (!mChatSuite.getAPI().checkPermissions(player, "mchat.afkother")) {
                    sender.sendMessage(formatMessage(mChatSuite.getLocale().getOption("noPermissions") + " " + commandName + "."));
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
                            sPlayers.sendNotification(afkTarget.getName(), mChatSuite.getLocale().getOption("noLongerAFK"), Material.PAPER);
                        else
                            players.sendMessage(mChatSuite.getAPI().ParsePlayerName(afkTarget) + " " + mChatSuite.getLocale().getOption("notAFK"));
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(mChatSuite.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + mChatSuite.getLocale().getOption("AFK") + " -" + '\n' + mChatSuite.getAPI().ParsePlayerName(afkTarget));
                } else
                    plugin.getServer().broadcastMessage(mChatSuite.getAPI().ParsePlayerName(afkTarget) + " " + mChatSuite.getLocale().getOption("notAFK"));

                afkTarget.setSleepingIgnored(false);
                plugin.isAFK.put(afkTarget.getName(), false);

                if (plugin.useAFKList)
                    if (mChatSuite.getAPI().ParseTabbedList(afkTarget).length() > 15) {
                            String pLName = mChatSuite.getAPI().ParseTabbedList(afkTarget);
                            pLName = pLName.substring(0, 16);
                            afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(mChatSuite.getAPI().ParseTabbedList(afkTarget));

                return true;
            } else {
                if (plugin.spoutB) {
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), mChatSuite.getLocale().getOption("isAFK"), Material.PAPER);
                        else
                            players.sendMessage(mChatSuite.getAPI().ParsePlayerName(afkTarget) + " " + mChatSuite.getLocale().getOption("isAFK") + " [" + message + " ]");
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(mChatSuite.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + mChatSuite.getLocale().getOption("AFK") + " -" + '\n' + mChatSuite.getAPI().ParsePlayerName(afkTarget));
                } else
                    plugin.getServer().broadcastMessage(mChatSuite.getAPI().ParsePlayerName(afkTarget) + " " + mChatSuite.getLocale().getOption("isAFK") + " [" + message + " ]");

                afkTarget.setSleepingIgnored(true);
                plugin.isAFK.put(afkTarget.getName(), true);
                plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

                if (plugin.useAFKList)
                    if (("[" + mChatSuite.getLocale().getOption("AFK") + "] " + mChatSuite.getAPI().ParseTabbedList(afkTarget)).length() > 15) {
                            String pLName = mChatSuite.getAPI().addColour("[<gold>" + mChatSuite.getLocale().getOption("AFK") + "] " + afkTarget);
                            pLName = pLName.substring(0, 16);
                            afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(mChatSuite.getAPI().addColour("<gold>[" + mChatSuite.getLocale().getOption("AFK") + "] " + afkTarget));

                return true;
            }
        } else if(commandName.equalsIgnoreCase("mchatlist")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!mChatSuite.getAPI().checkPermissions(player, "mchat.list")) {
                    player.sendMessage(formatMessage(mChatSuite.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }
            }

            // My Way
            // sender.sendMessage(mChatSuite.getAPI().addColour("&4" + mChatSuite.getLocale().pOffline + ": &8" + plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers()));

            // Waxdt's Way
            sender.sendMessage(mChatSuite.getAPI().addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --"));
            formatList(sender);
            return true;
        }

        return false;
    }

    void formatList(CommandSender sender) {
        String msg = "";
        String[] msgS;

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            String iVar = mChatSuite.getAPI().getInfo(players, plugin.listVar);
            String mName = mChatSuite.getAPI().ParseListCmd(players.getName());

            if (iVar.isEmpty())
                continue;

            if (plugin.isAFK.get(players.getName())) {
                if (msg.contains(iVar + ": &f")) {
                    msg = msg.replace(iVar + ": &f", iVar + ": &f&4[" + mChatSuite.getLocale().getOption("AFK") + "]" + mName + "&f, &f");
                } else {
                    msg += (iVar + ": &f&4[" + mChatSuite.getLocale().getOption("AFK") + "]" + mName + "&f, &f" + '\n');
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
                sender.sendMessage(mChatSuite.getAPI().addColour(arg));
            }

            sender.sendMessage(mChatSuite.getAPI().addColour("&6-------------------------"));
            return;
        }

        sender.sendMessage(mChatSuite.getAPI().addColour(msg));
    }

    void formatWho(CommandSender sender, Player recipient) {
        String recipientName = mChatSuite.getAPI().ParsePlayerName(recipient);
        Integer locX = (int) recipient.getLocation().getX();
        Integer locY = (int) recipient.getLocation().getY();
        Integer locZ = (int) recipient.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " +"Z: " + locZ);
        String world = recipient.getWorld().getName();

        sender.sendMessage(mChatSuite.getAPI().addColour(mChatSuite.getLocale().getOption("player") + " Name: " + recipient.getName()));
        sender.sendMessage(mChatSuite.getAPI().addColour("Display Name: " + recipient.getDisplayName()));
        sender.sendMessage(mChatSuite.getAPI().addColour("Formatted Name: " + recipientName));
        sender.sendMessage(mChatSuite.getAPI().addColour(mChatSuite.getLocale().getOption("player") + "'s Location: [ " + loc + " ]"));
        sender.sendMessage(mChatSuite.getAPI().addColour(mChatSuite.getLocale().getOption("player") + "'s World: " + world));
        sender.sendMessage(mChatSuite.getAPI().addColour(mChatSuite.getLocale().getOption("player") + "'s Health: " + mChatSuite.getAPI().healthBar(recipient) + " " + recipient.getHealth() + "/20"));
        sender.sendMessage(mChatSuite.getAPI().addColour(mChatSuite.getLocale().getOption("player") + "'s IP: " + recipient.getAddress().toString().replace("/", "")));
        sender.sendMessage(mChatSuite.getAPI().addColour("Current Item: " + recipient.getItemInHand().getType().name()));
        sender.sendMessage(mChatSuite.getAPI().addColour("Entity ID: #" + recipient.getEntityId()));
    }

    String formatMessage(String message) {
        return(mChatSuite.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String formatPNF(String playerNotFound) {
        return(mChatSuite.getAPI().addColour(formatMessage("") + " " + mChatSuite.getLocale().getOption("player") + " &e" + playerNotFound + " &4" + mChatSuite.getLocale().getOption("notFound")));
    }
}

