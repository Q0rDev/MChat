package in.mDev.MiracleM4n.mChatSuite.bukkit.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BPMChatAcceptCommand implements CommandExecutor {
    mChatSuite plugin;

    public BPMChatAcceptCommand(mChatSuite plugin) {
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
        String world = player.getWorld().getName();

        if (commandName.equalsIgnoreCase("pmchataccept")) {
            String rName = plugin.getInvite.get(pName);
            Player recipient = plugin.getServer().getPlayer(rName);
            String rWorld = recipient.getWorld().getName();

            if (rName != null && recipient != null) {
                plugin.getInvite.remove(pName);
                plugin.isConv.put(pName, true);
                plugin.isConv.put(rName, true);
                plugin.chatPartner.put(rName, pName);
                plugin.chatPartner.put(pName, rName);
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("You have started a Convo with &5'&4" + plugin.getAPI().ParsePlayerName(rName, rWorld) + "&5'&4.")));
                recipient.sendMessage(formatPMessage(plugin.getAPI().addColour("Convo request with &5'&4" + plugin.getAPI().ParsePlayerName(pName, world) + "&5'&4 has been accepted.")));
            } else
                player.sendMessage(formatPMessage(plugin.getAPI().addColour("No pending Convo request.")));

            return true;
        }

        return false;
    }

    String formatPMessage(String message) {
        return (plugin.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }
}
