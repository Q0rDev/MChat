package in.mDev.MiracleM4n.mChatSuite.spout.commands;

import in.mDev.MiracleM4n.mChatSuite.spout.mChatSuite;

public class MChatSayCommand {
    mChatSuite plugin;

    public MChatSayCommand(mChatSuite plugin) {
        this.plugin = plugin;
    }

    //TODO Wait for implementation
    /*
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("mchatsay")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args)
                    message += " " + arg;

                message = message.trim();

                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.say")) {
                        sender.sendMessage(plugin.getAPI().formatMessage(plugin.getLocale().getOption("noPermissions") + " " + commandName + "."));
                        return true;
                    }
                }

                plugin.getGame().broadcastMessage(plugin.getLocale().getOption("sayName") + " " +  message);
                return true;
            }
        }

        return false;
    }
    */
}
