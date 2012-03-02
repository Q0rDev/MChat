package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import org.getspout.spoutapi.player.SpoutPlayer;

public class BMEntityListener implements Listener {
    mChatSuite plugin;

    public BMEntityListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    Boolean messageTimeout = true;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.alterDMessages)
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event instanceof PlayerDeathEvent))
            return;

        Player player = (Player) event.getEntity();

        String pName = player.getName();
        String pCause = "";
        String world = player.getWorld().getName();

        Boolean isPlayer = false;

        PlayerDeathEvent subEvent = (PlayerDeathEvent) event;
        EntityDamageEvent dEvent = player.getLastDamageCause();

        if (dEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent dEEvent = (EntityDamageByEntityEvent) dEvent;

            if (dEEvent.getDamager() instanceof Player) {
                pCause = plugin.getAPI().ParsePlayerName(((Player) dEEvent.getDamager()).getName(), dEEvent.getDamager().getWorld().getName());
                isPlayer = true;
            } else
                pCause = "a " + dEEvent.getDamager().getClass().getSimpleName().replace("Craft", "");
        }

        if (plugin.alterEvents)
            if (plugin.sDeathB) {
                suppressDeathMessage(pName, pCause, world, subEvent, plugin.sDeathI, isPlayer);
                subEvent.setDeathMessage("");
            } else
                subEvent.setDeathMessage(handlePlayerDeath(pName, pCause, world, subEvent, isPlayer));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!plugin.mChatEB)
            return;

        if (event.isCancelled())
            return;

        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
            Entity attacker = subEvent.getDamager();
            Entity damaged = subEvent.getEntity();

            if (attacker instanceof Player) {
                Player player = (Player) attacker;

                if (plugin.isAFK.get(player.getName()) == null)
                    return;

                if (plugin.isAFK.get(player.getName())) {
                    damaged.setLastDamageCause(null);
                    event.setCancelled(true);
                }
            }
        }

        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();

            if (plugin.isAFK.get(player.getName()) != null)
                if (plugin.isAFK.get(player.getName())) {
                    event.setCancelled(true);
                    return;
                }

            if (plugin.healthNotify) {
                Runnable timeRunnable = new Runnable() {
                    public void run() {
                        messageTimeout = true;
                    }
                };

                Runnable runnable = new Runnable() {
                    public void run() {
                        SpoutPlayer sPlayer = (SpoutPlayer) player;
                        sPlayer.setTitle(plugin.getAPI().ParsePlayerName(player.getName(), player.getWorld().getName()));
                    }
                };

                if (messageTimeout) {
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        if (players != player) {
                            if (plugin.spoutB) {
                                SpoutPlayer sPlayers = (SpoutPlayer) players;
                                if (plugin.healthAchievement) {
                                    if (sPlayers.isSpoutCraftEnabled()) {
                                        if (player.getName().length() >= 25)
                                            sPlayers.sendNotification(healthBarDamage(player, event.getDamage()), player.getName().substring(0, 24), Material.LAVA);
                                        else
                                            sPlayers.sendNotification(healthBarDamage(player, event.getDamage()), player.getName(), Material.LAVA);

                                        continue;
                                    }
                                }
                            }

                            if ((player.getHealth() - event.getDamage()) < 1)
                                players.sendMessage(healthBarDamage(player, event.getDamage()) + " " + plugin.getAPI().ParsePlayerName(player.getName(), player.getWorld().getName()) + " " + plugin.getLocale().getOption("playerDied"));
                            else
                                players.sendMessage(healthBarDamage(player, event.getDamage()) + " " + plugin.getAPI().ParsePlayerName(player.getName(), player.getWorld().getName()) + " " + plugin.getLocale().getOption("playerDamaged") + " " + (player.getHealth() - event.getDamage()) + " " + plugin.getLocale().getOption("healthLeft"));
                        } else {
                            if (plugin.spoutB) {
                                SpoutPlayer sPlayer = (SpoutPlayer) player;
                                if (plugin.healthAchievement) {
                                    if (sPlayer.isSpoutCraftEnabled()) {
                                        if ((player.getHealth() - event.getDamage()) < 1) {
                                            sPlayer.sendNotification(healthBarDamage(player, event.getDamage()), plugin.getLocale().getOption("youDied"), Material.LAVA);
                                        } else {
                                            sPlayer.sendNotification(healthBarDamage(player, event.getDamage()), plugin.getLocale().getOption("youHave") + " " + (player.getHealth() - event.getDamage()) + " " + plugin.getLocale().getOption("healthLeft"), Material.LAVA);
                                        }
                                    }
                                }
                            }

                            if ((player.getHealth() - event.getDamage()) < 1)
                                player.sendMessage(healthBarDamage(player, event.getDamage()) + " " + plugin.getLocale().getOption("youDied"));
                            else
                                player.sendMessage(healthBarDamage(player, event.getDamage()) + " " + plugin.getLocale().getOption("youDamaged") + " " + plugin.getLocale().getOption("youHave") + " " + (player.getHealth() - event.getDamage()) + " " + plugin.getLocale().getOption("healthLeft"));
                        }
                    }

                    plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, timeRunnable, 20);
                    messageTimeout = false;
                }

                if (plugin.spoutB) {
                    SpoutPlayer sPlayer = (SpoutPlayer) player;

                    plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, 4 * 20);

                    sPlayer.setTitle(ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + healthBarDamage(player, event.getDamage()) + ChatColor.valueOf(plugin.getLocale().getOption("spoutChatColour").toUpperCase()) + " -" + '\n' + plugin.getAPI().ParsePlayerName(player.getName(), player.getWorld().getName()));

                    plugin.chatt.put(player.getName(), false);
                }
            }
        }
    }

    String handlePlayerDeath(String pName, String pCause, String world, PlayerDeathEvent event, Boolean isPlayer) {
        String dMsg = event.getDeathMessage();

        if (dMsg == null)
            return dMsg;

        if (dMsg.contains("went up in flames"))
            return deathMessage(pName, world, pCause, plugin.deathInFire, isPlayer);

        else if (dMsg.contains("burned to death"))
            return deathMessage(pName, world, pCause, plugin.deathOnFire, isPlayer);

        else if (dMsg.contains("tried to swim in lava"))
            return deathMessage(pName, world, pCause, plugin.deathLava, isPlayer);

        else if (dMsg.contains("suffocated in a wall"))
            return deathMessage(pName, world, pCause, plugin.deathInWall, isPlayer);

        else if (dMsg.contains("drowned"))
            return deathMessage(pName, world, pCause, plugin.deathDrown, isPlayer);

        else if (dMsg.contains("starved to death"))
            return deathMessage(pName, world, pCause, plugin.deathStarve, isPlayer);

        else if (dMsg.contains("was pricked to death"))
            return deathMessage(pName, world, pCause, plugin.deathCactus, isPlayer);

        else if (dMsg.contains("hit the ground too hard"))
            return deathMessage(pName, world, pCause, plugin.deathFall, isPlayer);

        else if (dMsg.contains("fell out of the world"))
            return deathMessage(pName, world, pCause, plugin.deathOutOfWorld, isPlayer);

        else if (dMsg.contains("died"))
            return deathMessage(pName, world, pCause, plugin.deathGeneric, isPlayer);

        else if (dMsg.contains("blew up"))
            return deathMessage(pName, world, pCause, plugin.deathExplosion, isPlayer);

        else if (dMsg.contains("was killed by"))
            return deathMessage(pName, world, pCause, plugin.deathMagic, isPlayer);

        else if (dMsg.contains("was slain by"))
            return deathMessage(pName, world, pCause, plugin.deathEntity, isPlayer);

        else if (dMsg.contains("was shot by"))
            return deathMessage(pName, world, pCause, plugin.deathArrow, isPlayer);

        else if (dMsg.contains("was fireballed by"))
            return deathMessage(pName, world, pCause, plugin.deathFireball, isPlayer);

        else if (dMsg.contains("was pummeled by"))
            return deathMessage(pName, world, pCause, plugin.deathThrown, isPlayer);

        return dMsg;
    }

    String deathMessage(String pName, String world, String pCause, String msg, Boolean isPlayer) {
        if (isPlayer)
            return plugin.getAPI().ParseEventName(pName, world) + " " + plugin.getAPI().ParseMessage(pName, world, "", msg)
                    .replace("+CName", plugin.getAPI().ParseEventName(pCause, world));

        return plugin.getAPI().ParseEventName(pName, world) + " " +  plugin.getAPI().ParseMessage(pName, world, "", msg)
                .replace("+CName", pCause);
    }

    String healthBarDamage(Player player, Integer damage) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();
        int fill = Math.round(((health - damage) / maxHealth) * barLength);

        String barColor = (fill <= (barLength / 4)) ? "&4" : (fill <= (barLength / 7)) ? "&e" : "&2";

        StringBuilder out = new StringBuilder();
        out.append(barColor);

        for (int i = 0; i < barLength; i++) {
            if (i == fill)
                out.append("&8");

            out.append("|");
        }

        out.append("&f");

        return out.toString().replaceAll("(&([A-Fa-f0-9]))", "\u00A7$2");
    }

    void suppressDeathMessage(String pName, String pCause, String world, PlayerDeathEvent event, Integer max, Boolean isPlayer) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.suppress.death")) {
                player.sendMessage(handlePlayerDeath(pName, pCause, world, event, isPlayer));
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.suppress.death"))
                    player.sendMessage(handlePlayerDeath(pName, pCause, world, event, isPlayer));
        }

        if (plugin.eBroadcast) {
            plugin.bMessage.checkState();
            plugin.bMessage.sendMessage(handlePlayerDeath(pName, pCause, world, event, isPlayer));
        }

        plugin.getAPI().log(handlePlayerDeath(pName, pCause, world, event, isPlayer));
    }
}
