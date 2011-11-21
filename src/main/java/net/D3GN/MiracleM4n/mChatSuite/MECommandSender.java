package net.D3GN.MiracleM4n.mChatSuite;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MECommandSender implements CommandExecutor {
	mChatSuite plugin;

	public MECommandSender(mChatSuite plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase("mchatme")) {
            if (args.length > 0) {
                String message = "";

                for (String arg : args) {
                    message += " " + arg;
                }

                if (sender instanceof Player) {
                	Player player = (Player) sender;
                	String senderName = plugin.mAPI.ParsePlayerName(player);
              		if (plugin.mAPI.checkPermissions(player, "mchat.me")) {
        	            plugin.getServer().broadcastMessage("*" + " " + senderName + message);
        	            plugin.mAPI.log("*" + " " + senderName + message);
        	            return true;
            		} else {
            			sender.sendMessage(formatMessage(plugin.lListener.noPerm + " " + commandName + "."));
            			return true;
            		}
    			} else {
    				String senderName = "Console";
    	            plugin.getServer().broadcastMessage("*" + " " + senderName + message);
    	            plugin.mAPI.log("*" + " " + senderName + message);
    	            return true;
    			}
            }
        } else if (commandName.equalsIgnoreCase("mchatwho")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
			 		Player player = (Player) sender;
			 		if (!plugin.mAPI.checkPermissions(player, "mchat.who")) {
            			sender.sendMessage(formatMessage(plugin.lListener.noPerm + " " + commandName + "."));
            			return true;
                    }
                }

                if (plugin.getServer().getPlayer(args[0]) == null) {
    			    sender.sendMessage(formatPNF(args[0]));
			        return true;
    		    } else {
			        Player receiver = plugin.getServer().getPlayer(args[0]);
			        formatWho(sender, receiver);
			        return true;
    		    }
            }
        } else if(commandName.equalsIgnoreCase("mchatafk")) {
            String message = " Away From Keyboard";

            if (args.length > 0) {
                message = "";

                for (String arg : args) {
                    message += " " + arg;
                }
            }

        	if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!plugin.mAPI.checkPermissions(player, "mchat.afk")) {
                    player.sendMessage(formatMessage(plugin.lListener.noPerm + " " + commandName + "."));
    				return true;
                }

				if (plugin.isAFK.get(player.getName())) {
					if (plugin.spoutB) {
						if (plugin.spoutEnabled) {
							for (Player players : plugin.getServer().getOnlinePlayers()) {
								SpoutPlayer sPlayers = (SpoutPlayer) players;

                                if (sPlayers.isSpoutCraftEnabled())
									sPlayers.sendNotification(player.getName(), plugin.lListener.nAFK, Material.PAPER);
								else
                                    players.sendMessage(plugin.mAPI.ParsePlayerName(player) + " " + plugin.lListener.nAFK);
							}
						}

						SpoutManager.getAppearanceManager().setGlobalTitle(player, ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + "- " + plugin.lListener.AFK + " -" + '\n' + plugin.mAPI.ParsePlayerName(player));
					} else
                        plugin.getServer().broadcastMessage(plugin.mAPI.ParsePlayerName(player) + " " + plugin.lListener.nAFK);

					player.setSleepingIgnored(false);
					plugin.isAFK.put(player.getName(), false);

                    if (plugin.useAFKList)
                        if (("[" + plugin.lListener.AFK + "] " + plugin.mAPI.ParseTabbedList(player)).length() > 15) {
                                String pLName = "[" + plugin.lListener.AFK + "] " + plugin.mAPI.ParseTabbedList(player);
                                pLName = pLName.substring(0, 16);
                                player.setPlayerListName(pLName);
                        } else
                            player.setPlayerListName("[" + plugin.lListener.AFK + "] " + plugin.mAPI.ParseTabbedList(player));

                    return true;
				} else {
					if (plugin.spoutB) {
						if (plugin.spoutEnabled) {
							for (Player players : plugin.getServer().getOnlinePlayers()) {
								SpoutPlayer sPlayers = (SpoutPlayer) players;
								if (sPlayers.isSpoutCraftEnabled())
									sPlayers.sendNotification(player.getName(), plugin.lListener.iAFK, Material.PAPER);
							    else
                                    players.sendMessage(plugin.mAPI.ParsePlayerName(player) + " " + plugin.lListener.iAFK + " [" + message + " ]");
                            }
						}

						SpoutManager.getAppearanceManager().setGlobalTitle(player, ChatColor.valueOf(plugin.lListener.spoutChatColour.toUpperCase()) + "- " + plugin.lListener.AFK + " -" + '\n' + plugin.mAPI.ParsePlayerName(player));
					} else
                        plugin.getServer().broadcastMessage(plugin.mAPI.ParsePlayerName(player) + " " + plugin.lListener.iAFK + " [" + message + " ]");

					player.setSleepingIgnored(true);
					plugin.isAFK.put(player.getName(), true);
					plugin.AFKLoc.put(player.getName(), player.getLocation());

                    if (plugin.useAFKList)
                        if (plugin.mAPI.ParseTabbedList(player).length() > 15) {
                                String pLName = plugin.mAPI.ParseTabbedList(player);
                                pLName = pLName.substring(0, 16);
                                player.setPlayerListName(pLName);
                        } else
                            player.setPlayerListName(plugin.mAPI.ParseTabbedList(player));

					return true;
				}
			} else {
				plugin.mAPI.log(formatMessage("Console's can't be AFK."));
				return true;
			}
        } else if(commandName.equalsIgnoreCase("mchatlist")) {
            if (sender instanceof Player) {
			    Player player = (Player) sender;
				if (!plugin.mAPI.checkPermissions(player, "mchat.list")) {
					player.sendMessage(formatMessage(plugin.lListener.noPerm + " " + commandName + "."));
					return true;
				}
            }

		    // My Way
		    // sender.sendMessage(plugin.mAPI.addColour("&4" + plugin.lListener.pOffline + ": &8" + plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers()));

		    // Waxdt's Way
            sender.sendMessage(plugin.mAPI.addColour("&6-- There are &8" + plugin.getServer().getOnlinePlayers().length + "&6 out of the maximum of &8" + plugin.getServer().getMaxPlayers() + "&6 Players online. --"));
		    formatList(sender);
            return true;
        }

		return false;
	}

    void formatList(CommandSender sender) {
        String msg = "";
        String[] msgS;

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            String iVar = plugin.mAPI.getInfo(players, plugin.listVar);
            String mName = plugin.mAPI.ParseListCmd(players.getName());

            if (iVar.isEmpty())
                continue;

            if (plugin.isAFK.get(players.getName())) {
                if (msg.contains(iVar + ": &f")) {
                    msg = msg.replace(iVar + ": &f", iVar + ": &f&4[" + plugin.lListener.AFK + "]" + mName + "&f, &f");
                } else {
                    msg += (iVar + ": &f&4[" + plugin.lListener.AFK + "]" + mName + "&f, &f" + '\n');
                }
            } else {
                if (msg.contains(iVar + ": &f")) {
                    msg = msg.replace(iVar + ": &f", iVar + ": &f" + mName + "&f, &f");
                } else {
                    msg += (iVar + ": &f" + mName + "&f, &f" + '\n');
                }
            }

            if (!msg.contains("" + '\n'))
                msg += '\n';
        }

        if (msg.contains("" + '\n')) {
            msgS = msg.split("" + '\n');

            for (String arg : msgS) {
                sender.sendMessage(plugin.mAPI.addColour(arg));
            }

            sender.sendMessage(plugin.mAPI.addColour("&6-------------------------"));
            return;
        }

        sender.sendMessage(plugin.mAPI.addColour(msg));
    }

	void formatWho(CommandSender sender, Player recipient) {
		String recipientName = plugin.mAPI.ParsePlayerName(recipient);
		Integer locX = (int) recipient.getLocation().getX();
		Integer locY = (int) recipient.getLocation().getY();
		Integer locZ = (int) recipient.getLocation().getZ();
		String loc = ("X: " + locX + ", " + "Y: " + locY + ", " +"Z: " + locZ);
		String world = recipient.getWorld().getName();

        sender.sendMessage(plugin.mAPI.addColour(plugin.lListener.player + " Name: " + recipient.getName()));
        sender.sendMessage(plugin.mAPI.addColour("Display Name: " + recipient.getDisplayName()));
		sender.sendMessage(plugin.mAPI.addColour("Formatted Name: " + recipientName));
        sender.sendMessage(plugin.mAPI.addColour(plugin.lListener.player + "'s Location: [ " + loc + " ]"));
        sender.sendMessage(plugin.mAPI.addColour(plugin.lListener.player + "'s World: " + world));
        sender.sendMessage(plugin.mAPI.addColour(plugin.lListener.player + "'s Health: " + plugin.mAPI.healthBar(recipient) + " " + recipient.getHealth() + "/20"));
		sender.sendMessage(plugin.mAPI.addColour(plugin.lListener.player + "'s IP: " + recipient.getAddress().toString().replace("/", "")));
        sender.sendMessage(plugin.mAPI.addColour("Current Item: " + recipient.getItemInHand().toString().replace("ItemStack", "")));
        sender.sendMessage(plugin.mAPI.addColour("Entity ID: #" + recipient.getEntityId()));
	}

	String formatMessage(String message) {
		return(plugin.mAPI.addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
	}

	String formatPNF(String playerNotFound) {
        return(plugin.mAPI.addColour(formatMessage("") + " " + plugin.lListener.player + " &e" + playerNotFound + " &4" + plugin.lListener.nFound));
    }
}

