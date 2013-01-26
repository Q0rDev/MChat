package com.miraclem4n.mchat.events;

import com.miraclem4n.mchat.MChat;
import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.api.Parser;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.types.config.ConfigType;
import com.miraclem4n.mchat.types.config.DeathType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.TreeMap;

public class EntityListener implements Listener {
    private MChat plugin;

    public EntityListener(MChat instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        if (!ConfigType.MCHAT_ALTER_DEATH.getBoolean()) {
            return;
        }

        Player player = event.getEntity();

        String pName = player.getName();
        String pCause = "";
        String world = player.getWorld().getName();

        EntityDamageEvent dEvent = event.getEntity().getLastDamageCause();

        if (dEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) dEvent;

            if (damageEvent.getDamager() instanceof Player) {
                Player killer = player.getKiller();

                if (killer != null) {
                    pCause = killer.getName();
                }
            } else if (damageEvent.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) damageEvent.getDamager();

                LivingEntity shooter = projectile.getShooter();

                if (shooter == null) {
                    pCause = "Unknown";
                } else if (shooter instanceof Player) {
                    Player pShooter = (Player) shooter;

                    pCause = pShooter.getName();
                } else {
                    pCause = shooter.getType().getName();
                }
            } else {
                pCause = damageEvent.getDamager().getType().getName();
            }
        }

        if (ConfigType.MCHAT_ALTER_EVENTS.getBoolean()) {
            if (ConfigType.SUPPRESS_USE_DEATH.getBoolean()) {
                suppressDeathMessage(pName, pCause, world, event.getDeathMessage(), ConfigType.SUPPRESS_MAX_DEATH.getInteger());
                event.setDeathMessage(null);
            } else {
                event.setDeathMessage(handlePlayerDeath(pName, pCause, world, event.getDeathMessage()));
            }
        }
    }

    String handlePlayerDeath(String pName, String pCause, String world, String dMsg) {
        if (dMsg == null) {
            return dMsg;
        }

        if (pCause == null) {
            pCause = pName;
        }

        TreeMap<String, String> map = new TreeMap<String, String>();

        map.put("player", Parser.parsePlayerName(pName, world));
        map.put("killer", Parser.parsePlayerName(pCause, world));

        DeathType type = DeathType.fromMsg(dMsg);

        return API.replace(type.getValue(), map, IndicatorType.LOCALE_VAR);
    }

    void suppressDeathMessage(String pName, String pCause, String world, String dMsg, Integer max) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.bypass.suppress.death")) {
                player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg));
                continue;
            }

            if (!(plugin.getServer().getOnlinePlayers().length > max)) {
                if (!API.checkPermissions(player.getName(), player.getWorld().getName(), "mchat.suppress.death")) {
                    player.sendMessage(handlePlayerDeath(pName, pCause, world, dMsg));
                }
            }
        }
    }
}
