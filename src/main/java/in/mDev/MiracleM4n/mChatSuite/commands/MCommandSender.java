package in.mDev.MiracleM4n.mChatSuite.commands;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCommandSender implements CommandExecutor {
    mChatSuite plugin;

    public MCommandSender(mChatSuite plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (sender instanceof  Player)
            player = (Player) sender;

        String cmd = command.getName();

        if (args.length == 0)
            return false;

        if (cmd.equalsIgnoreCase("mchat")) {
            if (args[0].equalsIgnoreCase("gui")) {
                if (sender instanceof Player) {
                    if (!mChatSuite.getAPI().checkPermissions(player, "mchat.gui")) {
                        sender.sendMessage(formatMessage("You are not to look at my fail GUI."));
                        return true;
                    }

                    mChatSuite.mGUI.openMainPopup(player);
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("reload")
             || args[0].equalsIgnoreCase("r")) {
                if (args.length > 1)
                    if (args[1].equalsIgnoreCase("config")
                     || args[1].equalsIgnoreCase("co")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.reload.config")) {
                                sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                                return true;
                            }

                        mChatSuite.cListener.load();
                        mChatSuite.cListener.checkConfig();
                        mChatSuite.cListener.loadConfig();
                        sender.sendMessage(formatMessage("Config Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("info")
                            || args[1].equalsIgnoreCase("i")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.reload.info")) {
                                sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                                return true;
                            }

                        mChatSuite.mIListener.load();
                        mChatSuite.mIListener.checkConfig();
                        sender.sendMessage(formatMessage("Info Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("censor")
                        || args[1].equalsIgnoreCase("ce")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.reload.censor")) {
                                sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                                return true;
                            }

                        mChatSuite.mCListener.load();
                        mChatSuite.mCListener.loadConfig();
                        sender.sendMessage(formatMessage("Censor Reloaded."));
                        return true;
                    } else if (args[1].equalsIgnoreCase("all")
                            || args[1].equalsIgnoreCase("a")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.reload.all")) {
                                sender.sendMessage(formatMessage("You are not allowed to reload mChat."));
                                return true;
                            }

                        plugin.loadConfigs();
                        plugin.setupConfigs();
                        sender.sendMessage(formatMessage("All Config's Reloaded."));
                        return true;
                    }
            } else if (args[0].equalsIgnoreCase("u")
                    || args[0].equalsIgnoreCase("user")) {
                if (args.length == 1) {
                    sender.sendMessage(formatMessage("Use '/mchat user add/edit/remove' for user help."));
                    return true;
                } else if (args[1].equalsIgnoreCase("a")
                        || args[1].equalsIgnoreCase("add")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat user add':"));
                        sender.sendMessage("- /mchat user add player Player DefaultGroup");
                        sender.sendMessage("- /mchat user add ivar Player InfoVariable InfoValue");
                        sender.sendMessage("- /mchat user add world Player World");
                        sender.sendMessage("- /mchat user add world Player World InfoVariable InfoValue");
                        return true;
                    } else if (args[2].equalsIgnoreCase("p")
                            || args[2].equalsIgnoreCase("player")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.add.player")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addPlayer(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user add player Player DefaultGroup"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.add.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addPlayerInfoVar(args[3], args[4], stringArgs(args, 5));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user add ivar Player InfoVariable InfoValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.add.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addPlayerWorld(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user add world Player World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.add.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addPlayerWorldVar(args[3], args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user add world Player World InfoVariable InfoValue"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("e")
                        || args[1].equalsIgnoreCase("edit")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat user edit':"));
                        sender.sendMessage("- /mchat user edit name Player NewName");
                        sender.sendMessage("- /mchat user edit ivar Player OldInfoVariable NewInfoVariable");
                        sender.sendMessage("- /mchat user edit ival Player InfoVariable NewValue");
                        sender.sendMessage("- /mchat user edit group Player Group");
                        sender.sendMessage("- /mchat user edit world Player OldWorld NewWorld");
                        sender.sendMessage("- /mchat user edit wvar Player World OldInfoVariable NewInfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("n")
                            || args[2].equalsIgnoreCase("name")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.name")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerName(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            sender.sendMessage(formatMessage("/mchat user edit name Player NewName"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerInfoVar(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit ivar Player OldInfoVariable NewInfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVal")
                            || args[2].equalsIgnoreCase("infoValue")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.ival")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerInfoValue(args[3], args[4], stringArgs(args, 5));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit ival Player InfoVariable NewValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("group")
                            || args[2].equalsIgnoreCase("g")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.group")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().setPlayerGroup(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit group Player Group"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerWorldName(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit world Player OldWorld NewWorld"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerWorldVar(args[3], args[4], args[5], args[6]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit wvar Player World OldInfoVariable NewInfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVal")
                            || args[2].equalsIgnoreCase("worldValue")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.edit.wval")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editPlayerWorldValue(args[3], args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user edit wval Player World InfoVariable NewValue"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("r")
                        || args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat user remove':"));
                        sender.sendMessage("- /mchat user remove Player");
                        sender.sendMessage("- /mchat user remove Player InfoVariable");
                        sender.sendMessage("- /mchat user remove Player World");
                        sender.sendMessage("- /mchat user remove Player World InfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("p")
                            || args[2].equalsIgnoreCase("player")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.remove.player")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removePlayer(args[3]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user remove Player"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.remove.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removePlayerInfoVar(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user remove Player InfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.remove.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removePlayerWorld(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user remove Player World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.user.remove.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removePlayerWorldVar(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat user remove Player World InfoVariable"));
                            return true;
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("g")
                    || args[0].equalsIgnoreCase("group")) {
                if (args.length == 1) {
                    sender.sendMessage(formatMessage("Use '/mchat group add/edit/remove' for group help."));
                    return true;
                } else if (args[1].equalsIgnoreCase("a")
                        || args[1].equalsIgnoreCase("add")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat group add':"));
                        sender.sendMessage("- /mchat group add group Group");
                        sender.sendMessage("- /mchat group add ivar Group InfoVariable InfoValue");
                        sender.sendMessage("- /mchat group add world Group World");
                        sender.sendMessage("- /mchat group add world Group World InfoVariable InfoValue");
                        return true;
                    } else if (args[2].equalsIgnoreCase("g")
                            || args[2].equalsIgnoreCase("group")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.add.group")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addGroup(args[3]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group add group Group"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.add.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addGroupInfoVar(args[3], args[4], stringArgs(args, 5));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group add ivar Group InfoVariable InfoValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.add.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addGroupWorld(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group add world Group World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.add.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().addGroupWorldVar(args[3], args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group add world Group World InfoVariable InfoValue"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("e")
                        || args[1].equalsIgnoreCase("edit")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat group edit':"));
                        sender.sendMessage("- /mchat group edit name Group NewName");
                        sender.sendMessage("- /mchat group edit ivar Group OldInfoVariable NewInfoVariable");
                        sender.sendMessage("- /mchat group edit ival Group InfoVariable NewValue");
                        sender.sendMessage("- /mchat group edit world Group OldWorld NewWorld");
                        sender.sendMessage("- /mchat group edit wvar Group World OldInfoVariable NewInfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("n")
                            || args[2].equalsIgnoreCase("name")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.name")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupName(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit name Group NewName"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupInfoVar(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit ivar Group OldInfoVariable NewInfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVal")
                            || args[2].equalsIgnoreCase("infoValue")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.ival")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupInfoValue(args[3], args[4], stringArgs(args, 5));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit ival Group InfoVariable NewValue"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupWorldName(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit world Group OldWorld NewWorld"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupWorldVar(args[3], args[4], args[5], args[6]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit wvar Group World OldInfoVariable NewInfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVal")
                            || args[2].equalsIgnoreCase("worldValue")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.edit.wval")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().editGroupWorldValue(args[3], args[4], args[5], stringArgs(args, 6));
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group edit wval Group World InfoVariable NewValue"));
                            return true;
                        }
                    }
                } else if (args[1].equalsIgnoreCase("r")
                        || args[1].equalsIgnoreCase("remove")) {
                    if (args.length == 2) {
                        sender.sendMessage(formatMessage("Usage for '/mchat group remove':"));
                        sender.sendMessage("- /mchat group remove Group");
                        sender.sendMessage("- /mchat group remove Group InfoVariable");
                        sender.sendMessage("- /mchat group remove Group World");
                        sender.sendMessage("- /mchat group remove Group World InfoVariable");
                        return true;
                    } else if (args[2].equalsIgnoreCase("g")
                            || args[2].equalsIgnoreCase("group")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.remove.group")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removeGroup(args[3]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group remove Group"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("iVar")
                            || args[2].equalsIgnoreCase("infoVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.remove.ivar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removeGroupInfoVar(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group remove Group InfoVariable"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("w")
                            || args[2].equalsIgnoreCase("world")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.remove.world")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removeGroupWorld(args[3], args[4]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group remove Group World"));
                            return true;
                        }
                    } else if (args[2].equalsIgnoreCase("wVar")
                            || args[2].equalsIgnoreCase("worldVariable")) {
                        if (sender instanceof Player)
                            if (!mChatSuite.getAPI().checkPermissions(player, "mchat.group.remove.wvar")) {
                                sender.sendMessage(formatMessage("You don't have Permission to do that."));
                                return true;
                            }
                        try {
                            mChatSuite.getInfoReader().removeGroupWorldVar(args[3], args[4], args[5]);
                            sender.sendMessage(formatMessage("Info Addition Successful."));
                            return true;
                        } catch (ArrayIndexOutOfBoundsException er) {
                            sender.sendMessage(formatMessage("/mchat group remove Group World InfoVariable"));
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public String formatMessage(String message) {
        return (mChatSuite.getAPI().addColour("&4[" + (plugin.pdfFile.getName()) + "] " + message));
    }

    String stringArgs (String[] args, Integer startingPoint)  {
        String argString = "";

        for (int i = startingPoint; i < args.length; ++i) {
            if (i == args.length - 1) {
                argString += args[i];
            } else
                argString += args[i] + " ";
        }

        return argString;
    }
}
