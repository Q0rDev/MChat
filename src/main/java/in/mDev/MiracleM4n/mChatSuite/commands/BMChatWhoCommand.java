package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BMChatWhoCommand implements CommandExecutor {
    mChatSuite plugin;

    public BMChatWhoCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatwho")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.who")) {
                        sender.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
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

        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + " Name: " + recipient.getName()));
        sender.sendMessage(plugin.getAPI().addColour("Display Name: " + recipient.getDisplayName()));
        sender.sendMessage(plugin.getAPI().addColour("Formatted Name: " + recipientName));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s Location: [ " + loc + " ]"));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s World: " + world));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s Health: " + plugin.getAPI().healthBar(recipient) + " " + recipient.getHealth() + "/20"));
        sender.sendMessage(plugin.getAPI().addColour(plugin.getLocale().getOption("player") + "'s IP: " + recipient.getAddress().getHostString()));
        sender.sendMessage(plugin.getAPI().addColour("Current Item: " + recipient.getItemInHand().getType().name()));
        sender.sendMessage(plugin.getAPI().addColour("Entity ID: #" + recipient.getEntityId()));
    }

    String formatPNF(String playerNotFound) {
        return (plugin.getAPI().addColour(plugin.getAPI().formatMessage("") + " " + plugin.getLocale().getOption("player") + " &e" + playerNotFound + " &4" + plugin.getLocale().getOption("notFound")));
    }
}
