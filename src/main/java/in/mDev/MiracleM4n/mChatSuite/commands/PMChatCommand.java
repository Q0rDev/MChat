package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.player.SpoutPlayer;

public class PMChatCommand implements CommandExecutor {
    mChatSuite plugin;

    public PMChatCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    String message = "";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(formatPMessage(Messanger.addColour("Console's can't send PM's.")));
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (cmd.equalsIgnoreCase("pmchat")) {
            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.pm.pm")) {
                player.sendMessage(formatPMessage(Messanger.addColour("You are not allowed to use PM functions.")));
                return true;
            }

            if (args.length < 2)
                return false;

            message = "";
            for (int i = 1; i < args.length; ++i)
                message += " " + args[i];

            if (plugin.getServer().getPlayer(args[0]) == null) {
                player.sendMessage(formatPNF(args[0]));
                return true;
            }

            Player recipient = plugin.getServer().getPlayer(args[0]);
            String rName = recipient.getName();
            String senderName = plugin.getAPI().ParsePlayerName(pName, world);

            player.sendMessage(formatPMSend(rName, recipient.getWorld().getName(), message));

            if (plugin.spoutB) {
                if (plugin.spoutPM) {
                    final SpoutPlayer sRecipient = (SpoutPlayer) recipient;

                    if (sRecipient.isSpoutCraftEnabled()) {
                        Runnable runnable = new Runnable() {
                            public void run() {
                                for (int i = 0; i < ((message.length() / 40) + 1); i++) {
                                    sRecipient.sendNotification(formatPM(message, ((40 * i) + 1), ((i * 40) + 20)), formatPM(message, ((i * 40) + 21), ((i * 40) + 40)), Material.PAPER);
                                    waiting(2);
                                }
                            }
                        };

                        if (plugin.lastPMd != null)
                            plugin.lastPMd.remove(rName);

                        plugin.lastPMd.put(rName, pName);
                        sRecipient.sendNotification("[pmChat] From:", player.getName(), Material.PAPER);
                        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, 2 * 20);
                        return true;
                    }
                }
            }

            plugin.lastPMd.put(rName, pName);
            Messanger.log(formatPMRecieve(senderName, world, message));
            recipient.sendMessage(formatPMRecieve(senderName, world, message));
            return true;
        }

        return false;
    }

    void waiting(int n) {
        long t0, t1;

        t0 = System.currentTimeMillis();

        do {
            t1 = System.currentTimeMillis();
        } while ((t1 - t0) < n * 1000);
    }

    String formatPM(String message, Integer start, Integer finish) {
        while (message.length() < finish) message += " ";
        return message.substring(start, finish);
    }

    String formatPMessage(String message) {
        return (Messanger.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String formatPNF(String playerNotFound) {
        return (Messanger.addColour("&4[" + (plugin.pdfFile.getName()) + "]" + " Player &e" + playerNotFound + " &4not found."));
    }

    String formatPMSend(String recipient, String world, String message) {
        return (Messanger.addColour("&fMe &1-&2-&3-&4> &f" + plugin.getAPI().ParsePlayerName(recipient, world) + "&f: " + message));
    }

    String formatPMRecieve(String sender, String world, String message) {
        return (Messanger.addColour(plugin.getAPI().ParsePlayerName(sender, world) + " &1-&2-&3-&4> &fMe: " + message));
    }
}
