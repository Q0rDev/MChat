package com.miraclem4n.mchat;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.api.Writer;
import com.miraclem4n.mchat.commands.InfoAlterCommand;
import com.miraclem4n.mchat.commands.MChatCommand;
import com.miraclem4n.mchat.commands.MeCommand;
import com.miraclem4n.mchat.configs.YmlManager;
import com.miraclem4n.mchat.configs.YmlType;
import com.miraclem4n.mchat.configs.config.ConfigType;
import com.miraclem4n.mchat.events.ChatListener;
import com.miraclem4n.mchat.events.CommandListener;
import com.miraclem4n.mchat.events.PlayerListener;
import com.miraclem4n.mchat.types.InfoType;
import com.miraclem4n.mchat.util.MessageUtil;
import com.miraclem4n.mchat.util.Timer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MChat extends JavaPlugin {
    // Default Plugin Data
    public PluginManager pm;
    public PluginDescriptionFile pdfFile;

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
        Parser.initialize();
    }
}
