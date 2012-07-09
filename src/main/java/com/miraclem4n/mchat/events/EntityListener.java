package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.DeathType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class EntityListener implements Listener {
    MChat plugin;

    public EntityListener(MChat instance) {
        plugin = instance;
    }

    Boolean messageTimeout = true;

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        if (!ConfigType.MCHAT_ALTER_DEATH.getObject().toBoolean())
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event instanceof PlayerDeathEvent))
            return;

        Player player = event.getEntity();

        String pName = player.getName();
        String pCause = "";
        String world = player.getWorld().getName();

        EntityDamageEvent dEvent = event.getEntity().getLastDamageCause();

        if (dEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) dEvent;

            if (damageEvent.getDamager() instanceof Player)
                pCause = Parser.parsePlayerName(player.getKiller().getName(), player.getKiller().getWorld().getName());
            else if (damageEvent.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) damageEvent.getDamager();

                LivingEntity shooter = projectile.getShooter();

                if (shooter == null)
                    pCause = "Unknown";
                else if (shooter instanceof Player) {
                    Player pShooter = (Player) shooter;

                    pCause = Parser.parsePlayerName(pShooter.getName(), pShooter.getWorld().getName());
                } else
                    pCause = shooter.getType().getName();
            } else
                pCause = damageEvent.getDamager().getType().getName();
        }

        if (ConfigType.MCHAT_ALTER_EVENTS.getObject().toBoolean())
            if (ConfigType.SUPPRESS_USE_DEATH.getObject().toBoolean()) {
                suppressDeathMessage(pName, pCause, world, event.getDeathMessage(), ConfigType.SUPPRESS_MAX_DEATH.getObject().toInteger());
                event.setDeathMessage(null);
            } else
                event.setDeathMessage(handlePlayerDeath(pName, pCause, world, event.getDeathMessage()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!ConfigType.MCHATE_ENABLE.getObject().toBoolean())
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

    String handlePlayerDeath(String pName, String pCause, String world, String dMsg) {
        if (dMsg == null)
            return dMsg;

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("player", Parser.parsePlayerName(pName, world));
        map.put("killer", Parser.parsePlayerName(pCause, world));

        DeathType type = DeathType.fromMsg(dMsg);

        if (type != null)
            return API.replace(type.getValue(), map, IndicatorType.LOCALE_VAR);

        return dMsg;
    }

    void suppressDeathMessage(String pName, String pCause, String world, String dMsg, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.suppress.death")) {
                player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg));
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max))
                if (!API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.suppress.death"))
                    player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg));
        }
    }
}
