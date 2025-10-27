package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
//        if ( event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || event.getState() == PlayerFishEvent.State.IN_GROUND || event.getState() == PlayerFishEvent.State.REEL_IN )
//        Bukkit.getConsoleSender().sendMessage(event.getState() + " fishing state");
        Triggers.onInteract(event);
    }

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
//        if ( event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY || event.getState() == PlayerFishEvent.State.IN_GROUND || event.getState() == PlayerFishEvent.State.REEL_IN )
//        Bukkit.getConsoleSender().sendMessage(event.getState() + " fishing state");
        Triggers.onFishing(event);
    }

}
