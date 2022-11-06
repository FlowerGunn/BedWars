package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.*;

import java.util.Random;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;

public class DealDamageListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerDealDamage(EntityDamageByEntityEvent event){

        DamageSource damageSource = DamageSource.PLAYER;
        DamageRelay damageRelay = DamageRelay.MELEE;
        DamageType damageType = DamageType.PHYSICAL;
        DamageTarget damageTarget = event.getEntity() instanceof Player ? DamageTarget.PLAYER : DamageTarget.MOB;

        Player player;
        if ( event.getDamager() instanceof Player ) {
            player = (Player) event.getDamager();
            damageRelay = DamageRelay.MELEE;
        } else if ( event.getDamager() instanceof Projectile ) {
            if ( ((Projectile) event.getDamager()).getShooter() instanceof Player ) {
                player = (Player) ((Projectile) event.getDamager()).getShooter();
                damageRelay = DamageRelay.PROJECTILE;
            }
            else return;
        } else return;


        if (Main.isPlayerInGame(player)) {

            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
            CurrentTeam team = gamePlayer.getGame().getPlayerTeam(gamePlayer);

            DamageInstance damageInstance = new DamageInstance(damageSource, damageTarget, damageRelay, damageType);

            Triggers.playerDealDamage(damageInstance, player, event);

        }
    }

}
