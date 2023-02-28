package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class CustomDamageListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event){

        Triggers.processDamageEvent(event);

//        DamageSource damageSource = DamageSource.PLAYER;
//        DamageRelay damageRelay = DamageRelay.MELEE;
//        DamageType damageType = DamageType.PHYSICAL;
//        DamageTarget damageTarget = event.getEntity() instanceof Player ? DamageTarget.PLAYER : DamageTarget.MOB;

//        Player player;
//        if ( event.getDamager() instanceof Player ) {
//            player = (Player) event.getDamager();
//            damageRelay = DamageRelay.MELEE;
//        } else if ( event.getDamager() instanceof Projectile ) {
//            if ( ((Projectile) event.getDamager()).getShooter() instanceof Player ) {
//                player = (Player) ((Projectile) event.getDamager()).getShooter();
//                damageRelay = DamageRelay.PROJECTILE;
//            }
//            else return;
//        } else return;


//        if (Main.isPlayerInGame(player)) {
//
//            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
//            CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);
//
//            DamageInstance damageInstance = new DamageInstance(damageSource, damageTarget, damageRelay, damageType);
//
//            Triggers.playerDealDamage(damageInstance, player, event);
//
//        }
    }

}
