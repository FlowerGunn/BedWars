package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;

public class ProjectileProcessingEvent implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if ( event.getEntity().getShooter() instanceof Player )
        Triggers.projectileHit(event);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if ( event.getEntity().getShooter() instanceof Player )
            Triggers.projectileLaunch(event);
    }

}
