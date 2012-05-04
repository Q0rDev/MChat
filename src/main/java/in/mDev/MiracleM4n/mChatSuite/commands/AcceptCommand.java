package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

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
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("pmchataccept"))
            return false;

        Player player = (Player) sender;
        String pName = player.getName();
        String pWorld = player.getWorld().getName();

        String rName = plugin.getInvite.get(pName);

        if (rName == null) {
            MessageUtil.sendMessage(sender, MessageUtil.addColour("No pending Convo request."));
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(rName);
        String rWorld = recipient.getWorld().getName();

        if (recipient != null) {
            plugin.getInvite.remove(pName);

            plugin.isConv.put(pName, true);
            plugin.isConv.put(rName, true);

            plugin.chatPartner.put(rName, pName);
            plugin.chatPartner.put(pName, rName);

            MessageUtil.sendMessage(player, "You have started a Convo with &5'&4" + plugin.getParser().parsePlayerName(rName, rWorld) + "&5'&4.");
            MessageUtil.sendMessage(recipient, "Convo request with &5'&4" + plugin.getParser().parsePlayerName(pName, pWorld) + "&5'&4 has been accepted.");
        } else
            MessageUtil.sendMessage(player, "Player: '" + rName + "' is not currently online.");

        return true;
    }
}
