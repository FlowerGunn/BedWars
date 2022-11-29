package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class PotionThrowListener implements Listener {

    @EventHandler
    public void onPotionThrow(PlayerLaunchProjectileEvent event) {

        if ( event.getProjectile().getType() == EntityType.SPLASH_POTION ) {
            event.getProjectile().setVelocity(event.getPlayer().getLocation().getDirection().clone().multiply(1.5));
        }

    }

}
