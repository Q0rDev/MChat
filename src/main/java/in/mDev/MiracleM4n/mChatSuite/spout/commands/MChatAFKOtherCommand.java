package in.mDev.MiracleM4n.mChatSuite.spout.commands;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

public class MChatAFKOtherCommand {
    mChatSuite plugin;

    public MChatAFKOtherCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatafkother")) {
            String message = " Away From Keyboard";

            if (args.length > 1) {
                message = "";

                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.afk.other")) {
                    sender.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                    return true;
                }
            }

            if (plugin.getGame().getPlayer(args[0]) == null) {
                sender.sendMessage(formatPNF(args[0]));
                return true;
            }

            Player afkTarget = plugin.getGame().getPlayer(args[0]);

            if (plugin.isAFK.get(afkTarget.getName()) != null &&
                    plugin.isAFK.get(afkTarget.getName())) {
                if (plugin.spoutB) {
                    for (Player players : plugin.getGame().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption("notAFK"), Material.PAPER);
                        else
                            players.sendMessage(plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()) + " " + plugin.getLocale().getOption("notAFK"));
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getLocale().getOption("AFK") + " -" + '\n' + plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()));
                } else
                    plugin.getGame().broadcastMessage(plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()) + " " + plugin.getLocale().getOption("notAFK"));

                afkTarget.setSleepingIgnored(false);
                plugin.isAFK.put(afkTarget.getName(), false);

                if (plugin.useAFKList)
                    if (plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()).length() > 15) {
                        String pLName = plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName());
                        pLName = pLName.substring(0, 16);
                        afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()));

                return true;
            } else {
                if (plugin.spoutB) {
                    for (Player players : plugin.getGame().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption("isAFK"), Material.PAPER);
                        else
                            players.sendMessage(plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()) + " " + plugin.getLocale().getOption("isAFK") + " [" + message + " ]");
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + plugin.getLocale().getOption("AFK") + " -" + '\n' + plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()));
                } else
                    plugin.getGame().broadcastMessage(plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()) + " " + plugin.getLocale().getOption("isAFK") + " [" + message + " ]");

                afkTarget.setSleepingIgnored(true);
                plugin.isAFK.put(afkTarget.getName(), true);
                plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

                if (plugin.useAFKList)
                    if ((plugin.getAPI().addColour("<gold>[" + plugin.getLocale().getOption("AFK") + "] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()))).length() > 15) {
                        String pLName = plugin.getAPI().addColour("[<gold>" + plugin.getLocale().getOption("AFK") + "] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()));
                        pLName = pLName.substring(0, 16);
                        afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(plugin.getAPI().addColour("<gold>[" + plugin.getLocale().getOption("AFK") + "] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName())));

                return true;
            }
        }

        return false;
    }

    String formatPNF(String playerNotFound) {
        return (plugin.getAPI().addColour(plugin.getAPI().formatMessage("") + " " + plugin.getLocale().getOption("player") + " &e" + playerNotFound + " &4" + plugin.getLocale().getOption("notFound")));
    }
    */
}
