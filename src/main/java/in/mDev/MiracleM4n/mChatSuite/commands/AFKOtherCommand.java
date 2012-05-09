package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import in.mDev.MiracleM4n.mChatSuite.types.config.LocaleType;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class AFKOtherCommand implements CommandExecutor {
    mChatSuite plugin;

    public AFKOtherCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (cmd.equalsIgnoreCase("mchatafkother"))
            return false;

        if (!plugin.getAPI().checkPermissions(sender, "mchat.afk.other")) {
            MessageUtil.sendMessage(sender, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.afk.other"));
            return true;
        }

        Player afkTarget = plugin.getServer().getPlayer(args[0]);

        if (afkTarget == null) {
            MessageUtil.sendMessage(sender, "&4Player &e'" + args[0] + "'&4 Not found.");
            return true;
        }

        Boolean isAfk = plugin.isAFK.get(afkTarget.getName()) != null &&
                plugin.isAFK.get(afkTarget.getName());

        String notification = LocaleType.PLAYER_AFK.getValue();

        String message = "";

        String title = plugin.getParser().parsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName());


        if (!isAfk) {
            if (args.length > 0) {
                for (int i = 1; i < args.length; ++i)
                    message += " " + args[i];

                message = message.trim();
            } else
                message = "Away From Keyboard";

            notification = LocaleType.PLAYER_NOT_AFK.getValue();

            title = ChatColor.valueOf(LocaleType.SPOUT_COLOUR.getValue().toUpperCase()) + "- AFK -" + '\n' + title;
        }

        if (plugin.spoutB) {
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                SpoutPlayer sPlayers = (SpoutPlayer) players;

                if (sPlayers.isSpoutCraftEnabled())
                    sPlayers.sendNotification(afkTarget.getName(), notification.replace("%player%", ""), Material.PAPER);
                else
                    players.sendMessage(notification.replace("%player%", plugin.getParser().parsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())).replace("%reason%" , message));
            }

            SpoutPlayer sPlayer = (SpoutPlayer) afkTarget;

            sPlayer.setTitle(title);
        } else
            plugin.getServer().broadcastMessage(notification.replace("%player%", plugin.getParser().parsePlayerName(afkTarget.getName(), afkTarget.getWorld().getName())).replace("%reason%" , message));

        afkTarget.setSleepingIgnored(!isAfk);
        plugin.isAFK.put(afkTarget.getName(), !isAfk);

        String pLName = plugin.getParser().parseTabbedList(afkTarget.getName(), afkTarget.getWorld().getName());

        if (!isAfk) {
            plugin.AFKLoc.put(afkTarget.getName(), afkTarget.getLocation());

            pLName = MessageUtil.addColour("<gold>[AFK] ") + pLName;
        }

        if (ConfigType.MCHATE_USE_AFK_LIST.getObject().toBoolean())
            if (pLName.length() > 15) {
                pLName = pLName.substring(0, 16);
                afkTarget.setPlayerListName(pLName);
            } else
                afkTarget.setPlayerListName(pLName);

        return true;
    }
}
