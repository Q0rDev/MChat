package com.miraclem4n.mchat.commands;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Reader;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.InfoType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.LocaleType;
import com.miraclem4n.mchat.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ListCommand implements CommandExecutor {
    mChatSuite plugin;

    public ListCommand(mChatSuite instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName();

        if (!cmd.equalsIgnoreCase("mchatlist"))
            return false;

        if (!(sender instanceof Player))
            return false;

        if (!API.checkPermissions(sender, "mchat.list")) {
            MessageUtil.sendMessage(sender, LocaleType.NO_PERMS.getValue().replace("%permission%", "mchat.list"));
            return true;
        }

        sender.sendMessage(MessageUtil.addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --"));
        formatList(sender);

        return true;
    }

    void formatList(CommandSender sender) {
        HashMap<String, Integer> cLMap = new HashMap<String, Integer>();

        String msg = "";
        String line = "";
        String[] msgS;

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.canSee(players))
                    continue;
            }

            String iVar = Reader.getInfo(players.getName(), InfoType.USER, players.getWorld().getName(), ConfigType.MCHATE_LIST_VAR.getObject().toString());
            String mName = Parser.parseListCmd(players.getName(), players.getWorld().getName());

            Boolean collapsed = false;

            if (iVar.isEmpty())
                iVar = "Default";

            for (String string : ConfigType.MCHATE_COLLAPSED_LIST_VAR.getObject().toString().split(",")) {
                if (!iVar.equals(string))
                    continue;

                collapsed = true;

                if (cLMap.get(string) != null) {
                    Integer sVal = cLMap.get(string);

                    cLMap.put(string, sVal+1);
                } else
                    cLMap.put(string, 1);
            }

            if (collapsed)
                continue;

            if (plugin.isAFK.get(players.getName()) != null && plugin.isAFK.get(players.getName()))
                if (msg.contains(iVar + ": &f"))
                    msg = msg.replace(iVar + ": &f", iVar + ": &f&4[AFK]" + mName + "&f, &f");
                else
                    msg += (iVar + ": &f&4[AFK]" + mName + "&f, &f" + '\n');
            else
            if (msg.contains(iVar + ": &f"))
                msg = msg.replace(iVar + ": &f", iVar + ": &f" + mName + "&f, &f");
            else
                msg += (iVar + ": &f" + mName + "&f, &f" + '\n');

            if (!msg.contains("" + '\n'))
                msg += '\n';
        }

        for (Map.Entry<String, Integer> entry : cLMap.entrySet())
            msg += (entry.getKey() + ": &f" + entry.getValue() + '\n');

        if (msg.contains("" + '\n')) {
            if (ConfigType.MCHATE_USE_GROUPED_LIST.getObject().toBoolean()) {
                msgS = msg.split("" + '\n');


                for (String arg : msgS)
                    sender.sendMessage(MessageUtil.addColour(arg));
            } else {
                msg = MessageUtil.addColour(msg.replace("" + '\n', "&5 | &f"));

                sender.sendMessage(msg);
            }
        } else
            sender.sendMessage(MessageUtil.addColour(msg));

        for (int i = 20; i < MessageUtil.addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --").length(); i++)
            line += "-";

        sender.sendMessage(MessageUtil.addColour("&6" + line));
    }
}
