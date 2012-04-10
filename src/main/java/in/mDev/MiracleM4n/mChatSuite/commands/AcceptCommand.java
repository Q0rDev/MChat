package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {
    mChatSuite plugin;

    public AcceptCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(formatPMessage(Messanger.addColour("Console's can't send PM's.")));
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (cmd.equalsIgnoreCase("pmchataccept")) {
            String rName = plugin.getInvite.get(pName);

            if (rName == null) {
                player.sendMessage(formatPMessage(Messanger.addColour("No pending Convo request.")));
                return true;
            }

            Player recipient = plugin.getServer().getPlayer(rName);
            String rWorld = recipient.getWorld().getName();

            if (rName != null && recipient != null) {
                plugin.getInvite.remove(pName);
                plugin.isConv.put(pName, true);
                plugin.isConv.put(rName, true);
                plugin.chatPartner.put(rName, pName);
                plugin.chatPartner.put(pName, rName);
                player.sendMessage(formatPMessage(Messanger.addColour("You have started a Convo with &5'&4" + plugin.getParser().parsePlayerName(rName, rWorld) + "&5'&4.")));
                recipient.sendMessage(formatPMessage(Messanger.addColour("Convo request with &5'&4" + plugin.getParser().parsePlayerName(pName, world) + "&5'&4 has been accepted.")));
            } else
                player.sendMessage(formatPMessage(Messanger.addColour("No pending Convo request.")));

            return true;
        }

        return false;
    }

    String formatPMessage(String message) {
        return (Messanger.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }
}
