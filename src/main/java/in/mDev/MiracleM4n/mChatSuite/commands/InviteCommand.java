package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

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
            MessageUtil.sendMessage(sender, "Console's can't send PM's.");
            return true;
        }

        if (!cmd.equalsIgnoreCase("pmchatinvite"))
            return false;

        Player player = (Player) sender;
        String pName = player.getName();
        String world = player.getWorld().getName();

        if (args.length < 1)
            return false;

        if (!plugin.getAPI().checkPermissions(sender, "mchat.pm.invite")) {
            MessageUtil.sendMessage(player, plugin.getLocale().getOption(LocaleType.NO_PERMS).replace("%permission%", "mchat.pm.invite"));
            return true;
        }

        Player recipient = plugin.getServer().getPlayer(args[0]);
        String rName = recipient.getName();
        String rWorld = recipient.getWorld().getName();

        if (recipient == null) {
            MessageUtil.sendMessage(player, "&4Player &e'" + args[0] + "'&4 not Found.");
            return true;
        }

        if (plugin.getInvite.get(rName) == null) {
            plugin.getInvite.put(rName, pName);

            MessageUtil.sendMessage(player, "You have invited &5'&4" + plugin.getParser().parsePlayerName(rName, rWorld) + "&5'&4 to have a Convo.");
            MessageUtil.sendMessage(recipient, "You have been invited to a Convo by &5'&4" + plugin.getParser().parsePlayerName(pName, world) + "&5'&4 use /pmchataccept to accept.");
        } else
            MessageUtil.sendMessage(player, "&5'&4" + plugin.getParser().parsePlayerName(rName, rWorld) + "&5'&4 Already has a Convo request.");

        return true;
    }
}
