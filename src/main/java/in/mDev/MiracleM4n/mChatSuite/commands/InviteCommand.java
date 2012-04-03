package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.Messanger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteCommand implements CommandExecutor {
    mChatSuite plugin;

    public InviteCommand(mChatSuite instance) {
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

        if (cmd.equalsIgnoreCase("pmchatinvite")) {
            if (args.length < 1)
                return false;

            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.pm.invite")) {
                player.sendMessage(formatPMessage(Messanger.addColour("You are not allowed to use Invite functions.")));
                return true;
            }

            Player recipient = plugin.getServer().getPlayer(args[0]);
            String rName = recipient.getName();
            String rWorld = recipient.getWorld().getName();

            if (recipient == null) {
                player.sendMessage(formatPNF(args[0]));
                return true;
            }

            if (plugin.getInvite.get(rName) == null) {
                plugin.getInvite.put(rName, pName);
                player.sendMessage(formatPMessage(Messanger.addColour("You have invited &5'&4" + plugin.getAPI().ParsePlayerName(rName, rWorld) + "&5'&4 to have a Convo.")));
                recipient.sendMessage(formatPMessage(Messanger.addColour("You have been invited to a Convo by &5'&4" + plugin.getAPI().ParsePlayerName(pName, world) + "&5'&4 use /pmchataccept to accept.")));
            } else
                player.sendMessage(formatPMessage(Messanger.addColour("&5'&4" + plugin.getAPI().ParsePlayerName(rName, rWorld) + "&5'&4 Already has a Convo request.")));

            return true;
        }

        return false;
    }

    String formatPMessage(String message) {
        return (Messanger.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String formatPNF(String playerNotFound) {
        return (Messanger.addColour("&4[" + (plugin.pdfFile.getName()) + "]" + " Player &e" + playerNotFound + " &4not found."));
    }
}
