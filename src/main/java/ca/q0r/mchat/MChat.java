package ca.q0r.mchat;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Writer;
import ca.q0r.mchat.commands.InfoAlterCommand;
import ca.q0r.mchat.commands.MChatCommand;
import ca.q0r.mchat.commands.MeCommand;
import ca.q0r.mchat.events.ChatListener;
import ca.q0r.mchat.events.CommandListener;
import ca.q0r.mchat.events.PlayerListener;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.updater.Updater;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.util.Timer;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.config.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MChat extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

    public Boolean update = false;

    public void onEnable() {
        // Initialize and Start the Timer
        Timer timer = new Timer();

        // Initialize Plugin Data
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        // First we kill EssentialsChat
        killEss();

        // Initialize Metrics
        /*getServer().getScheduler().runTaskLater(this, new BukkitRunnable(){
				@Override
				public void run() {
					try {
						Metrics metrics = new Metrics(Bukkit.getPluginManager().getPlugin("MChat"));
			            metrics.findCustomData();
			            metrics.start();
			        } catch (IOException ignored) {}	
				}
			}, 200);*/

        // Load Yml
        YmlManager.initialize();

        // Initialize Classes
        initializeClasses();

        // Register Events
        registerEvents();

        // Setup Commands
        setupCommands();

        // Add All Players To Info.yml
        if (ConfigType.INFO_ADD_NEW_PLAYERS.getBoolean()) {
            for (Player players : getServer().getOnlinePlayers()) {
                if (YmlManager.getYml(YmlType.INFO_YML).getConfig().get("users." + players.getName()) == null) {
                    Writer.addBase(players.getName(), ConfigType.INFO_DEFAULT_GROUP.getString(), false);
                }
            }
        }

        // Updater
        if (ConfigType.MCHAT_UPDATE_CHECK.getBoolean()) {
            getServer().getScheduler().runTaskLater(this, new BukkitRunnable(){
                @Override
                public void run() {
                    MChat mchat = (MChat) Bukkit.getPluginManager().getPlugin("MChat");
                    Updater updater = new Updater(mchat, "mchat", mchat.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
                    mchat.update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
                }

            }, 50);
        } else {
            MessageUtil.logFormatted("Update Check has been Disabled.");
            MessageUtil.logFormatted("To Enable please change ''mchat.updateCheck''");
            MessageUtil.logFormatted("in config.yml to true!!");
        }

        // Stop the Timer
        timer.stop();

        // Calculate Startup Timer
        long diff = timer.difference();

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is enabled! [" + diff + "ms]");
    }

    public void onDisable() {
        // Initialize and Start the Timer
        Timer timer = new Timer();

        getServer().getScheduler().cancelTasks(this);

        // Unload Yml
        YmlManager.unload();

        // Stop the Timer
        timer.stop();

        // Calculate Shutdown Timer
        long diff = timer.difference();

        MessageUtil.log("[" + pdfFile.getName() + "] " + pdfFile.getName() + " v" + pdfFile.getVersion() + " is disabled! [" + diff + "ms]");
    }

    private void registerEvents() {
        if (!ConfigType.MCHAT_API_ONLY.getBoolean()) {
            pm.registerEvents(new PlayerListener(this), this);

            pm.registerEvents(new ChatListener(this), this);

            pm.registerEvents(new CommandListener(), this);
        }
    }

    private void killEss() {
        Plugin plugin = pm.getPlugin("EssentialsChat");

        if (plugin != null) {
            pm.disablePlugin(plugin);
        }
    }

    private void setupCommands() {
        regCommands("mchat", new MChatCommand(this));

        regCommands("mchatuser", new InfoAlterCommand("mchatuser", InfoType.USER));
        regCommands("mchatgroup", new InfoAlterCommand("mchatgroup", InfoType.GROUP));

        regCommands("mchatme", new MeCommand(this));
    }

    private void regCommands(String command, CommandExecutor executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor(executor);
        }
    }

    private void initializeClasses() {
        API.initialize();
        VariableManager.initialize();
    }
}
