package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.types.config.ConfigType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.TreeSet;

public class CommandListener implements Listener {
    mChatSuite plugin;

    public CommandListener(mChatSuite instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        String command = msg.split(" ")[0].replace("/", "");

        TreeSet<ConfigType> commands = new TreeSet<ConfigType>();

        commands.add(ConfigType.ALIASES_AFK);
        commands.add(ConfigType.ALIASES_ME);
        commands.add(ConfigType.ALIASES_WHO);
        commands.add(ConfigType.ALIASES_LIST);
        commands.add(ConfigType.ALIASES_SAY);
        commands.add(ConfigType.ALIASES_AFK);
        commands.add(ConfigType.ALIASES_AFK_OTHER);
        commands.add(ConfigType.ALIASES_PM);
        commands.add(ConfigType.ALIASES_PM_REPLY);
        commands.add(ConfigType.ALIASES_PM_INVITE);
        commands.add(ConfigType.ALIASES_PM_ACCEPT);
        commands.add(ConfigType.ALIASES_PM_DENY);
        commands.add(ConfigType.ALIASES_PM_LEAVE);
        commands.add(ConfigType.ALIASES_SHOUT);
        commands.add(ConfigType.ALIASES_MUTE);
        commands.add(ConfigType.ALIASES_CHANNEL);

        for (ConfigType oCommand : commands.descendingSet())
            for (String string : oCommand.getObject().toStringList())
                if (command.equalsIgnoreCase(string)) {
                    event.setMessage(msg.replaceFirst("/" + string, "/" + oCommand));
                    return;
                }
    }
}
