package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.player.SpoutPlayer;

public class BPMChatReplyCommand implements CommandExecutor {
    mChatSuite plugin;

    public BPMChatReplyCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    String message = "";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(formatPMessage(plugin.getAPI().addColour("Console's can't send PM's.")));
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (commandName.equalsIgnoreCase("pmchatreply")) {
            message = "";
            for (String arg : args)
                message += " " + arg;

            if (plugin.lastPMd.get(pName) != null) {
                String rName = plugin.lastPMd.get(pName);
                Player recipient = plugin.getServer().getPlayer(rName);

                if (recipient == null) {
                    player.sendMessage(formatPMessage(plugin.getAPI().addColour("PM Recipient is offline.")));
                    return true;
                }

                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.pm.reply")) {
                    player.sendMessage(formatPMessage(plugin.getAPI().addColour("You are not allowed to use PM reply functions.")));
                    return true;
                }

                final String senderName = plugin.getAPI().ParsePlayerName(pName, world);
                player.sendMessage(formatPMSend(rName, message));

                plugin.lastPMd.put(rName, pName);
                if (plugin.spoutB) {
                    if (plugin.spoutPM) {
                        final SpoutPlayer sRecipient = (SpoutPlayer) recipient;

                        if (sRecipient.isSpoutCraftEnabled()) {
                            Runnable runnable = new Runnable() {
                                public void run() {
                                    for (int i = 0; i < ((message.length() / 40) + 1); i++) {
                                        sRecipient.sendNotification(formatPM(message, ((40 * i) + 1), ((i * 40) + 20)), formatPM(message, ((i * 40) + 21), ((i * 40) + 40)), Material.PAPER);
                                        waiting(2000);
                                    }
                                }
                            };

                            sRecipient.sendNotification("[PM] From:", player.getName(), Material.PAPER);
                            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, 2 * 20);
                            return true;
                        }
                    }
                }

                recipient.sendMessage(formatPMRecieve(senderName, world, message));
                plugin.getAPI().log(formatPMRecieve(senderName, world, message));
                return true;
            } else {
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("No one has yet PM'd you.")));
                return true;
            }
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
        return (plugin.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String formatPMSend(String recipient, String message) {
        return (plugin.getAPI().addColour("&fMe &1-&2-&3-&4> &f" + recipient + "&f: " + message));
    }

    String formatPMRecieve(String sender, String world, String message) {
        return (plugin.getAPI().addColour(plugin.getAPI().ParsePlayerName(sender, world) + " &1-&2-&3-&4> &fMe: " + message));
    }
}
