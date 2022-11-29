package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;

import java.util.ArrayList;

public class PotionEffectsListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerDamage(ProjectileHitEvent event){

        if ( event.getEntity().getType() == EntityType.SPLASH_POTION ) {

            ThrownPotion potion = (ThrownPotion) event.getEntity();
            ArrayList<PotionEffect> potionEffects = new ArrayList<>(potion.getPotionMeta().getCustomEffects());

        }

    }

}
