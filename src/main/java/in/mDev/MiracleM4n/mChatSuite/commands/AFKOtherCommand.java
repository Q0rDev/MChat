package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.player.SpoutPlayer;

public class AFKOtherCommand implements CommandExecutor {
    mChatSuite plugin;

    public AFKOtherCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatafkother")) {
            String message = " Away From Keyboard";

            if (args.length > 1) {
                message = "";

                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.afk.other")) {
                    Messanger.sendMessage(sender, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.afk.other"));
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
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption(LocaleType.PLAYER_NOT_AFK).replace("%player%", ""), Material.PAPER);
                        else
                            players.sendMessage(plugin.getLocale().getOption(LocaleType.PLAYER_NOT_AFK).replace("%player%", plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())));
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()));
                } else
                    plugin.getServer().broadcastMessage(plugin.getLocale().getOption(LocaleType.PLAYER_NOT_AFK).replace("%player%", plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())));

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
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        SpoutPlayer sPlayers = (SpoutPlayer) players;

                        if (sPlayers.isSpoutCraftEnabled())
                            sPlayers.sendNotification(afkTarget.getName(), plugin.getLocale().getOption(LocaleType.PLAYER_AFK).replace("%player%", ""), Material.PAPER);
                        else
                            players.sendMessage(plugin.getLocale().getOption(LocaleType.PLAYER_AFK).replace("%player%", plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())).replace("%reason%" , message));
                    }

                    SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption(LocaleType.SPOUT_COLOUR).toUpperCase()) + "- AFK -" + '\n' + plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName()));
                } else
                    plugin.getServer().broadcastMessage(plugin.getLocale().getOption(LocaleType.PLAYER_AFK).replace("%player%", plugin.getAPI().ParsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())).replace("%reason%" , message));

                afkTarget.setSleepingIgnored(true);
                plugin.isAFK.put(afkTarget.getName(), true);
                plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

                if (plugin.useAFKList)
                    if ((Messanger.addColour("<gold>[AFK] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()))).length() > 15) {
                        String pLName = Messanger.addColour("[<gold>AFK] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName()));
                        pLName = pLName.substring(0, 16);
                        afkTarget.setPlayerListName(pLName);
                    } else
                        afkTarget.setPlayerListName(Messanger.addColour("<gold>[AFK] " + plugin.getAPI().ParseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName())));

                return true;
            }
        }

        return false;
    }

    String formatPNF(String playerNotFound) {
        return Messanger.format("&4Player &e'" + playerNotFound + "'&4 Not found.");
    }
}
