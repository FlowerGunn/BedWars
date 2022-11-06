package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageType;

public class SnowballListener implements Listener {

    @EventHandler
    public void onSnowballUse(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        if (Main.isPlayerInGame(player)) {
            if ( event.getEntity() instanceof Snowball ) {
                Triggers.gadgetUsed(player, GadgetType.SNOWBALL);
            }
        }
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;
        Snowball snowball = (Snowball) event.getEntity();
        if (snowball.getShooter() instanceof Player) {
            if (event.getHitEntity() instanceof Player) {
                Player shooter = (Player) snowball.getShooter();
                Player victim = (Player) event.getHitEntity();

                if (Main.isPlayerInGame(shooter)) {

                    GamePlayer gamePlayerShooter = Main.getPlayerGameProfile(shooter);
                    GamePlayer gamePlayerVictim = Main.getPlayerGameProfile(victim);
                    Game game = gamePlayerShooter.getGame();

                    CurrentTeam teamShooter = game.getPlayerTeam(gamePlayerShooter);
                    CurrentTeam teamVictim = game.getPlayerTeam(gamePlayerVictim);


                    if ( teamShooter.getName() == teamVictim.getName() ) return;

                    if (gamePlayerVictim.isSpectator) return;

                    double damage = FlowerUtils.snowballDamage;
                    if (victim.isBlocking()) damage = 0;
                    else victim.setVelocity( snowball.getVelocity().clone().multiply( Math.max(1.0 - victim.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue(), 0.0 ) ));

                    gamePlayerShooter.damageTypeAttackInstance = DamageType.SNOWBALL;
                    gamePlayerShooter.damageRelayAttackInstance = DamageRelay.PROJECTILE;
                    gamePlayerShooter.damageTypeDefenceInstance = DamageType.SNOWBALL;
                    gamePlayerShooter.damageRelayDefenceInstance = DamageRelay.PROJECTILE;
                    victim.damage(damage, shooter);
//                    Bukkit.getConsoleSender().sendMessage( "knockback res " + victim.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue() + "");

                }

            } else if ( event.getHitEntity() instanceof LivingEntity ) {

                if (event.isCancelled()) return;

                Player shooter = (Player) snowball.getShooter();
                if (Main.isPlayerInGame(shooter)) {

                    GamePlayer gamePlayerShooter = Main.getPlayerGameProfile(shooter);
                    Game game = gamePlayerShooter.getGame();

                    CurrentTeam teamShooter = game.getPlayerTeam(gamePlayerShooter);

                    double damage = FlowerUtils.snowballDamage;

                    gamePlayerShooter.damageTypeAttackInstance = DamageType.SNOWBALL;
                    gamePlayerShooter.damageRelayAttackInstance = DamageRelay.PROJECTILE;
                    ((LivingEntity) event.getHitEntity()).damage(damage, shooter);
                    event.getHitEntity().setVelocity( snowball.getVelocity().clone() );


                }
            }
        }
    }

}
