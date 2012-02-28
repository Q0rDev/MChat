package in.mDev.MiracleM4n.mChatSuite.spout.commands;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

public class MChatShoutCommand {
    mChatSuite plugin;

    public MChatShoutCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatshout")) {
            String message = "";

            for (String arg : args)
                message += " " + arg;

            if (!(sender instanceof Player)) {
                plugin.getAPI().log(plugin.getAPI().formatMessage("Console's can't shout."));
                return true;
            }

            Player player = (Player) sender;

            if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.shout")) {
                player.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                return true;
            }

            plugin.isShouting.put(sender.getName(), true);

            plugin.getGame().broadcastMessage(plugin.getAPI().ParseChatMessage(player.getName(), player.getWorld().getName(), message));

            plugin.isShouting.put(sender.getName(), false);

            return true;
        }

        return false;
    }
    */
}
