package ca.q0r.mchat;

import ca.q0r.mchat.api.API;
import ca.q0r.mchat.api.Writer;
import ca.q0r.mchat.commands.InfoAlterCommand;
import ca.q0r.mchat.commands.MChatCommand;
import ca.q0r.mchat.commands.MeCommand;
import ca.q0r.mchat.events.bukkit.ChatListener;
import ca.q0r.mchat.events.bukkit.CommandListener;
import ca.q0r.mchat.events.bukkit.PlayerListener;
import ca.q0r.mchat.metrics.Metrics;
import ca.q0r.mchat.types.InfoType;
import ca.q0r.mchat.updater.Updater;
import ca.q0r.mchat.util.MessageUtil;
import ca.q0r.mchat.util.Timer;
import ca.q0r.mchat.variables.VariableManager;
import ca.q0r.mchat.yml.YmlManager;
import ca.q0r.mchat.yml.YmlType;
import ca.q0r.mchat.yml.config.ConfigType;
import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
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

    Test AWS Breakage.

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
        getServer().getScheduler().runTaskLater(this, new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Metrics metrics = new Metrics(Bukkit.getPluginManager().getPlugin("MChat"));
                    metrics.findCustomData();
                    metrics.start();
                } catch (Exception ignored) {
                }
            }
        }, 200);

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
            for (Player player : getServer().getOnlinePlayers()) {
                if (YmlManager.getYml(YmlType.INFO_YML).getConfig().get("users." + player.getUniqueId().toString()) == null) {
                    Writer.addBase(player.getUniqueId().toString(), InfoType.USER);
                }
            }
        }

        // Updater
        if (ConfigType.MCHAT_UPDATE_CHECK.getBoolean()) {
            getServer().getScheduler().runTaskLater(this, new BukkitRunnable() {
                @Override
                public void run() {
                    MChat mchat = (MChat) Bukkit.getPluginManager().getPlugin("MChat");
                    Updater updater = new Updater(mchat, 31112, mchat.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
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
            pm.registerEvents(new ChatListener(this), this);
            pm.registerEvents(new CommandListener(), this);
            pm.registerEvents(new PlayerListener(this), this);
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

        regCommands("mchatme", new MeCommand());
    }

    private void regCommands(String command, TabExecutor executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor(executor);
            getCommand(command).setTabCompleter(executor);
        }
    }

    private void initializeClasses() {
        API.initialize();
        VariableManager.initialize();
    }
}