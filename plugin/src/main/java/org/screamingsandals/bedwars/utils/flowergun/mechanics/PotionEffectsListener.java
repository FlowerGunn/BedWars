package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.A;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collection;

public class PotionEffectsListener implements Listener {

    public static ArrayList<PotionEffectType> positiveEffects;
    public static ArrayList<PotionEffectType> negativeEffects;

    static {
        positiveEffects = new ArrayList<>();
        positiveEffects.add(PotionEffectType.REGENERATION);
        positiveEffects.add(PotionEffectType.DAMAGE_RESISTANCE);
        positiveEffects.add(PotionEffectType.INCREASE_DAMAGE);
        positiveEffects.add(PotionEffectType.HEAL);
        positiveEffects.add(PotionEffectType.HEALTH_BOOST);
        positiveEffects.add(PotionEffectType.SPEED);
        positiveEffects.add(PotionEffectType.LUCK);
        positiveEffects.add(PotionEffectType.FIRE_RESISTANCE);

        negativeEffects = new ArrayList<>();
        negativeEffects.add(PotionEffectType.SLOW);
        negativeEffects.add(PotionEffectType.SLOW_DIGGING);
        negativeEffects.add(PotionEffectType.BLINDNESS);
        negativeEffects.add(PotionEffectType.GLOWING);
        negativeEffects.add(PotionEffectType.POISON);
        negativeEffects.add(PotionEffectType.HARM);
        negativeEffects.add(PotionEffectType.HUNGER);
        negativeEffects.add(PotionEffectType.UNLUCK);
        negativeEffects.add(PotionEffectType.DARKNESS);
        negativeEffects.add(PotionEffectType.WITHER);
        negativeEffects.add(PotionEffectType.WEAKNESS);
    }


    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPotionSplash(PotionSplashEvent event){

//        Bukkit.getConsoleSender().sendMessage("potion did smth");
        if ( event.getEntity().getType() == EntityType.SPLASH_POTION ) {

//            Bukkit.getConsoleSender().sendMessage("potion splashed");

            ThrownPotion potion = event.getPotion();
            PotionEffectType thisType;
            ArrayList<PotionEffect> customPotionEffects = new ArrayList<>(potion.getPotionMeta().getCustomEffects());
            if (customPotionEffects.size() <= 0) {
                if (potion.getPotionMeta().getBasePotionData().getType().getEffectType() == null) return;
//                Bukkit.getConsoleSender().sendMessage("effect = " + potion.getPotionMeta().getBasePotionData().getType().getEffectType());
                thisType = potion.getPotionMeta().getBasePotionData().getType().getEffectType();
            } else thisType = customPotionEffects.get(0).getType();


//            Bukkit.getConsoleSender().sendMessage("shooter = " + potion.getShooter());
            if (!(potion.getShooter() instanceof Player)) return;
//            Bukkit.getConsoleSender().sendMessage("potion owned by player");

            Player shooter = (Player) potion.getShooter();
            GamePlayer gShooter = Main.getPlayerGameProfile(shooter);
            if (!gShooter.isInGame()) return;

            Game game = gShooter.getGame();
            CurrentTeam shooterTeam = game.getPlayerTeam(gShooter);


            ArrayList<LivingEntity> affectedEntitiesArray = new ArrayList<>(event.getAffectedEntities());

            for ( int j = 0; j < affectedEntitiesArray.size(); j++ ) {

//                Bukkit.getConsoleSender().sendMessage("found affected entity");
                LivingEntity livingEntity = affectedEntitiesArray.get(j);
                if ( livingEntity instanceof Player ) {

//                    Bukkit.getConsoleSender().sendMessage("entity is a player");
                    Player target = (Player) livingEntity;
                    GamePlayer gTarget = Main.getPlayerGameProfile(target);
                    CurrentTeam targetTeam = game.getPlayerTeam(gTarget);

//                    if ( shooterTeam == targetTeam ) {
//                        event.setIntensity(livingEntity, 0);
////                        target.sendMessage("this should not affect you");
//                    }

                    if ( positiveEffects.contains(thisType) && gShooter != gTarget && shooterTeam != targetTeam ) {
                        event.setIntensity(livingEntity, 0);
//                        event.setCancelled(true);
                    } else if ( negativeEffects.contains(thisType) && gShooter != gTarget && shooterTeam == targetTeam ) {
                        event.setIntensity(livingEntity, 0);
//                        event.setCancelled(true);
                    }


//                    for ( int i = 0; i < potionEffects.size(); i++ ) {
//                        PotionEffect potionEffect = potionEffects.get(i);
//                        if (potionEffect.getType() == PotionEffectType.HEAL) {
//                            potionEffects.remove(i);
//                            i--;
//
//                            event.
//
//                        }
//                    }

                }
            }

        }

    }


    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLingeringSplash(EntityPotionEffectEvent event) {
        if (! (event.getEntity() instanceof Player player )) return;
        if ( event.getCause() == EntityPotionEffectEvent.Cause.AREA_EFFECT_CLOUD ) {
            double closestDistance = 100;
            ThrownPotion lingeringPotion = null;
            for ( Entity entity : player.getNearbyEntities(3, 3, 3) ) {
                if ( entity instanceof ThrownPotion thrownPotion ) {
                    double distance = thrownPotion.getLocation().distance(player.getLocation());
                    if ( distance < closestDistance ) {
                        closestDistance = distance;
                        lingeringPotion = thrownPotion;
                    }
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLingeringSplash(LingeringPotionSplashEvent event) {

    }

}
