package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommand implements CommandExecutor {
    mChatSuite plugin;

    public DenyCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage(formatPMessage(MessageUtil.addColour("Console's can't send PM's.")));
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (cmd.equalsIgnoreCase("pmchatdeny")) {
            String rName = plugin.getInvite.get(pName);
            Player recipient = plugin.getServer().getPlayer(rName);
            String rWorld = recipient.getWorld().getName();

            if (rName != null && recipient != null) {
                plugin.getInvite.remove(pName);
                plugin.isConv.put(pName, false);
                plugin.isConv.put(rName, false);
                player.sendMessage(formatPMessage(MessageUtil.addColour("You have denied a Convo request from &5'&4" + plugin.getParser().parsePlayerName(rName, rWorld) + "&5'&4.")));
                recipient.sendMessage(formatPMessage(MessageUtil.addColour("Convo request with &5'&4" + plugin.getParser().parsePlayerName(pName, world) + "&5'&4 has been denied.")));
            } else
                player.sendMessage(formatPMessage(MessageUtil.addColour("No pending Convo request.")));

            return true;
        }

        return false;
    }

    String formatPMessage(String message) {
        return (MessageUtil.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }
}
