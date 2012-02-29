package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPMChatLeaveCommand implements CommandExecutor {
    mChatSuite plugin;

    public BPMChatLeaveCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(formatPMessage(plugin.getAPI().addColour("Console's can't send PM's.")));
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();

        if (commandName.equalsIgnoreCase("pmchatleave")) {
            String rName = plugin.chatPartner.get(pName);
            Player recipient = null;

            if (rName != null)
                recipient = plugin.getServer().getPlayer(rName);

            if (plugin.isConv.get(pName) == null)
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("You are not currently in a Convo.")));
            else if (plugin.isConv.get(pName)) {
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("You have left the convo.")));
                recipient.sendMessage(formatPMessage(plugin.getAPI().addColour("Conversation has been ended.")));
                plugin.isConv.put(pName, false);
                plugin.isConv.put(rName, false);
                plugin.chatPartner.remove(rName);
                plugin.chatPartner.remove(pName);
            } else
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("You are not currently in a Convo.")));

            return true;
        }

        return false;
    }

    String formatPMessage(String message) {
        return (plugin.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }
}
