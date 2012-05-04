package in.mDev.MiracleM4n.mChatSuite.events;

import in.mDev.MiracleM4n.mChatSuite.mChatSuite;
import in.mDev.MiracleM4n.mChatSuite.util.MessageUtil;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class EntityListener implements Listener {
    mChatSuite plugin;

    public EntityListener(mChatSuite instance) {
        plugin = instance;
    }

    Boolean messageTimeout = true;

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        if (!plugin.alterDMessages)
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event instanceof PlayerDeathEvent))
            return;

        Player player = event.getEntity();

        String pName = player.getName();
        String pCause = "";
        String world = player.getWorld().getName();

        Boolean isPlayer = false;

        EntityDamageEvent dEvent = event.getEntity().getLastDamageCause();

        if (dEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) dEvent;

            if (damageEvent.getDamager() instanceof Player) {
                pCause = plugin.getParser().parsePlayerName(player.getKiller().getName(), player.getKiller().getWorld().getName());
                isPlayer = true;
            } else if (damageEvent.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) damageEvent.getDamager();

                LivingEntity shooter = projectile.getShooter();

                if (shooter == null) {
                    pCause = "Unknown";
                } else if (shooter instanceof Player) {
                    Player pShooter = (Player) shooter;

                    pCause = plugin.getParser().parsePlayerName(pShooter.getName(), pShooter.getWorld().getName());
                } else
                    pCause = shooter.getType().getName();
            } else
                pCause = damageEvent.getDamager().getType().getName();
        }

        if (plugin.alterEvents)
            if (plugin.sDeathB) {
                suppressDeathMessage(pName, pCause, world, event.getDeathMessage(), plugin.sDeathI, isPlayer);
                event.setDeathMessage(null);
            } else
                event.setDeathMessage(handlePlayerDeath(pName, pCause, world, event.getDeathMessage(), isPlayer));
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
            Player player = (Player) event.getEntity();

            if (plugin.isAFK.get(player.getName()) != null)
                if (plugin.isAFK.get(player.getName()))
                    event.setCancelled(true);
        }
    }

    String handlePlayerDeath(String pName, String pCause, String world, String dMsg, Boolean isPlayer) {
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
            return plugin.getParser().parseEventName(pName, world) + " " + plugin.getParser().parseMessage(pName, world, "", msg)
                    .replace(plugin.varIndicator + "killer", plugin.getParser().parseEventName(pCause, world));

        return MessageUtil.addColour(plugin.getParser().parseEventName(pName, world) + " " + plugin.getParser().parseMessage(pName, world, "", msg)
                .replace(plugin.varIndicator + "killer", plugin.deathMobFormat)
                .replace(plugin.varIndicator + "killer", pCause));
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

    void suppressDeathMessage(String pName, String pCause, String world, String dMsg, Integer max, Boolean isPlayer) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.suppress.death")) {
                player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg, isPlayer));
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!plugin.getAPI().checkPermissions(player.getName(), player.getWorld().getName(), "mchat.suppress.death"))
                    player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg, isPlayer));
        }
    }
}
