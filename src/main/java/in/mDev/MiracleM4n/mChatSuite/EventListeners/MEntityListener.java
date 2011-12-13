package in.mDev.MiracleM4n.mChatSuite.eventListeners;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

import org.getspout.spoutapi.player.SpoutPlayer;

public class MEntityListener extends EntityListener {
    mChatSuite plugin;

    public MEntityListener(mChatSuite plugin) {
        this.plugin = plugin;
    }

    Boolean messageTimeout = true;

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

        PlayerDeathEvent subEvent = (PlayerDeathEvent) event;
        EntityDamageEvent dEvent = player.getLastDamageCause();

        if (dEvent instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent dEEvent = (EntityDamageByEntityEvent) dEvent;
                if (dEEvent.getDamager() instanceof Player)
                    pCause = mChatSuite.getAPI().ParsePlayerName(((Player) dEEvent.getDamager()).getName()) + ".";
                else
                    pCause =  "a" + parseEntityName(dEEvent.getDamager()) + ".";
        }

        if (plugin.sDeathB) {
            suppressDeathMessage(pName, pCause, subEvent, plugin.sDeathI);
            subEvent.setDeathMessage("");
        } else
            subEvent.setDeathMessage(handlePlayerDeath(pName, pCause, subEvent));

    }

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
                        sPlayer.setTitle(mChatSuite.getAPI().ParsePlayerName(player));
                    }
                };

                if (messageTimeout) {
                    for (Player players : plugin.getServer().getOnlinePlayers()) {
                        if (players != player) {
                            if (plugin.spoutB) {
                                SpoutPlayer sPlayers = (SpoutPlayer) players;
                                if (plugin.healthAchievement) {
                                    if (sPlayers.isSpoutCraftEnabled()) {
                                        if(player.getName().length() >= 25)
                                            sPlayers.sendNotification(healthBarDamage(player, event.getDamage()), player.getName().substring(0, 24), Material.LAVA);
                                        else
                                            sPlayers.sendNotification(healthBarDamage(player, event.getDamage()), player.getName(), Material.LAVA);

                                        continue;
                                    }
                                }
                            }

                            if ((player.getHealth() - event.getDamage()) < 1)
                                players.sendMessage(healthBarDamage(player, event.getDamage()) + " " + mChatSuite.getAPI().ParsePlayerName(player) + " " + mChatSuite.getLocale().getOption("playerDied"));
                            else
                                players.sendMessage(healthBarDamage(player, event.getDamage()) + " " + mChatSuite.getAPI().ParsePlayerName(player) + " " + mChatSuite.getLocale().getOption("playerDamaged") + " " + (player.getHealth() - event.getDamage()) + " " + mChatSuite.getLocale().getOption("healthLeft"));
                        } else {
                            if (plugin.spoutB) {
                                SpoutPlayer sPlayer = (SpoutPlayer) player;
                                if (plugin.healthAchievement) {
                                    if (sPlayer.isSpoutCraftEnabled()) {
                                        if ((player.getHealth() - event.getDamage()) < 1) {
                                            sPlayer.sendNotification(healthBarDamage(player, event.getDamage()), mChatSuite.getLocale().getOption("youDied"), Material.LAVA);
                                        } else {
                                            sPlayer.sendNotification(healthBarDamage(player, event.getDamage()), mChatSuite.getLocale().getOption("youHave") + " " + (player.getHealth() - event.getDamage()) + " " + mChatSuite.getLocale().getOption("healthLeft"), Material.LAVA);
                                        }
                                    }
                                }
                            }

                            if ((player.getHealth() - event.getDamage()) < 1)
                                player.sendMessage(healthBarDamage(player, event.getDamage()) + " " + mChatSuite.getLocale().getOption("youDied"));
                            else
                                player.sendMessage(healthBarDamage(player, event.getDamage()) + " " + mChatSuite.getLocale().getOption("youDamaged") + " " + mChatSuite.getLocale().getOption("youHave") + " " + (player.getHealth() - event.getDamage()) + " " + mChatSuite.getLocale().getOption("healthLeft"));
                        }
                    }

                    plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, timeRunnable, 20);
                    messageTimeout = false;
                }

                if (plugin.spoutB) {
                    SpoutPlayer sPlayer = (SpoutPlayer) player;

                    plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, runnable, 4*20);

                    sPlayer.setTitle(ChatColor.valueOf(mChatSuite.getLocale().getOption("spoutChatColour").toUpperCase()) + "- " + healthBarDamage(player, event.getDamage()) + ChatColor.valueOf(mChatSuite.getLocale().getOption("spoutChatColour").toUpperCase()) + " -" + '\n' + mChatSuite.getAPI().ParsePlayerName(player));

                    plugin.chatt.put(player.getName(), false);
                }
            }
        }
    }

    String handlePlayerDeath(String pName, String pCause, PlayerDeathEvent event) {
        String dMsg = event.getDeathMessage();

        if (dMsg == null)
            return dMsg;

        if (dMsg.contains("went up in flames")) 
            return deathMessage(pName, pCause, plugin.deathInFire);

        else if (dMsg.contains("burned to death")) 
            return deathMessage(pName, pCause, plugin.deathOnFire);

        else if (dMsg.contains("tried to swim in lava")) 
            return deathMessage(pName, pCause, plugin.deathLava);

        else if (dMsg.contains("suffocated in a wall")) 
            return deathMessage(pName, pCause, plugin.deathInWall);

        else if (dMsg.contains("drowned")) 
            return deathMessage(pName, pCause, plugin.deathDrown);

        else if (dMsg.contains("starved to death")) 
            return deathMessage(pName, pCause, plugin.deathStarve);

        else if (dMsg.contains("was pricked to death")) 
            return deathMessage(pName, pCause, plugin.deathCactus);

        else if (dMsg.contains("hit the ground too hard")) 
            return deathMessage(pName, pCause, plugin.deathFall);

        else if (dMsg.contains("fell out of the world")) 
            return deathMessage(pName, pCause, plugin.deathOutOfWorld);

        else if (dMsg.contains("died")) 
            return deathMessage(pName, pCause, plugin.deathGeneric);

        else if (dMsg.contains("blew up")) 
            return deathMessage(pName, pCause, plugin.deathExplosion);

        else if (dMsg.contains("was killed by"))
            return deathMessage(pName, pCause, plugin.deathMagic);

        else if (dMsg.contains("was slain by")) 
            return deathMessage(pName, pCause, plugin.deathEntity);

        else if (dMsg.contains("was shot by")) 
            return deathMessage(pName, pCause, plugin.deathArrow);

        else if (dMsg.contains("was fireballed by")) 
            return deathMessage(pName, pCause, plugin.deathFireball);

        else if (dMsg.contains("was pummeled by")) 
            return deathMessage(pName, pCause, plugin.deathThrown);

        return dMsg;
    }

    String parseEntityName(Entity entity) {
        if (entity instanceof CaveSpider)
            return " CaveSpider";

        else if (entity instanceof Chicken)
            return " Chicken";

        else if (entity instanceof Cow)
            return " Cow";

        else if (entity instanceof Creeper)
            return " Creeper";

        else if (entity instanceof Enderman)
            return "n Enderman";

        else if (entity instanceof Ghast)
            return " Ghast";

        else if (entity instanceof Giant)
            return " Giant";

        else if (entity instanceof Pig)
            return " Pig";

        else if (entity instanceof PigZombie)
            return " PigZombie";

        else if (entity instanceof Sheep)
            return " Sheep";

        else if (entity instanceof Silverfish)
            return " Silverfish";

        else if (entity instanceof Skeleton)
            return " Skeleton";

        else if (entity instanceof Slime)
            return " Slime";

        else if (entity instanceof Spider)
            return " Spider";

        else if (entity instanceof Squid)
            return " Squid";

        else if (entity instanceof Wolf)
            return " Wolf";

        else if (entity instanceof Zombie)
            return " Zombie";

        else if (entity instanceof Monster)
            return " Monster";

        return entity.toString().replace("Craft", "");
    }

    String deathMessage(String pName, String pCause, String msg) {
        return mChatSuite.getAPI().ParseEventName(pName) + " " + mChatSuite.getAPI().ParseMessage(pName, "", msg)
                    .replaceAll("\\+CName", mChatSuite.getAPI().ParseEventName(pCause));
    }

    String healthBarDamage(Player player, Integer damage) {
        float maxHealth = 20;
        float barLength = 10;
        float health = player.getHealth();
        int fill = Math.round(((health - damage) / maxHealth) * barLength);

        String barColor = (fill <= (barLength/4)) ? "&4" : (fill <= (barLength/7)) ? "&e" : "&2";

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

    void suppressDeathMessage(String pName, String pCause, PlayerDeathEvent event, Integer max) {
        if (!(plugin.getServer().getOnlinePlayers().length > max))
            for (Player player : plugin.getServer().getOnlinePlayers())
                if (!mChatSuite.getAPI().checkPermissions(player, "mchat.suppress.death"))
                    player.sendMessage(handlePlayerDeath(pName, pCause, event));

        mChatSuite.getAPI().log(handlePlayerDeath(pName, pCause, event));
    }
}
