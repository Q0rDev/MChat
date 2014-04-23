package ca.q0r.mchat.commands;

import ca.q0r.mchat.events.custom.MeEvent;
import ca.q0r.mchat.util.CommandUtil;
import ca.q0r.mchat.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MeCommand implements TabExecutor {
    public MeCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mchatme")
                || !CommandUtil.hasCommandPerm(sender, "mchat.me")) {
            return true;
        }

        if (args.length > 0) {
            String message = "";

            for (String arg : args) {
                message += " " + arg;
            }

            message = message.trim();

            if (sender instanceof Player) {
                Player player = (Player) sender;
                World world = player.getWorld();

                MeEvent event = new MeEvent(player.getUniqueId(), world.getName(), message);

                Bukkit.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    Bukkit.getServer().broadcastMessage(event.getFormat());
                }

                return true;
            } else {
                String senderName = "Console";
                Bukkit.getServer().broadcastMessage("* " + senderName + " " + message);
                MessageUtil.log("* " + senderName + " " + message);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Arrays.asList();
    }
}