package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MChatWhoCommand implements CommandExecutor {
    mChatSuite plugin;

    public MChatWhoCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatwho")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.who")) {
                        sender.sendMessage(Messanger.format(plugin.getLocale().getOption("noPermissions") + " " + cmd + "."));
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
        }

        return false;
    }

    void formatWho(CommandSender sender, Player recipient) {
        String recipientName = plugin.getAPI().ParsePlayerName(recipient.getName(), recipient.getWorld().getName());
        Integer locX = (int) recipient.getLocation().getX();
        Integer locY = (int) recipient.getLocation().getY();
        Integer locZ = (int) recipient.getLocation().getZ();
        String loc = ("X: " + locX + ", " + "Y: " + locY + ", " + "Z: " + locZ);
        String world = recipient.getWorld().getName();

        sender.sendMessage(Messanger.addColour(plugin.getLocale().getOption("player") + " Name: " + recipient.getName()));
        sender.sendMessage(Messanger.addColour("Display Name: " + recipient.getDisplayName()));
        sender.sendMessage(Messanger.addColour("Formatted Name: " + recipientName));
        sender.sendMessage(Messanger.addColour(plugin.getLocale().getOption("player") + "'s Location: [ " + loc + " ]"));
        sender.sendMessage(Messanger.addColour(plugin.getLocale().getOption("player") + "'s World: " + world));
        sender.sendMessage(Messanger.addColour(plugin.getLocale().getOption("player") + "'s Health: " + plugin.getAPI().healthBar(recipient) + " " + recipient.getHealth() + "/20"));
        sender.sendMessage(Messanger.addColour(plugin.getLocale().getOption("player") + "'s IP: " + recipient.getAddress().getHostString()));
        sender.sendMessage(Messanger.addColour("Current Item: " + recipient.getItemInHand().getType().name()));
        sender.sendMessage(Messanger.addColour("Entity ID: #" + recipient.getEntityId()));
    }

    String formatPNF(String playerNotFound) {
        return (Messanger.addColour(Messanger.format("") + " " + plugin.getLocale().getOption("player") + " &e" + playerNotFound + " &4" + plugin.getLocale().getOption("notFound")));
    }
}
