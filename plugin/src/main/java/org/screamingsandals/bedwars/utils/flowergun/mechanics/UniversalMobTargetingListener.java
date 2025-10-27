package org.screamingsandals.bedwars.utils.flowergun.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.special.SpecialItem;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.lib.nms.entity.EntityUtils;
import org.screamingsandals.bedwars.special.Zoglin;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.List;

public class UniversalMobTargetingListener implements Listener {

    public static boolean areEntitiesAllies(Entity entity1, Entity entity2) {

        PersistentDataContainer persistentDataContainer1 = entity1.getPersistentDataContainer();
        PersistentDataContainer persistentDataContainer2 = entity2.getPersistentDataContainer();
        String teamName1;
        String teamName2;

        if ( entity1 instanceof Player player ) {
            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
            teamName1 = gamePlayer.getGame().getTeamOfPlayer(player).getName();
        } else {
            teamName1 = persistentDataContainer1.has(new NamespacedKey(Main.getInstance(), "mobTeamName")) ? persistentDataContainer1.get(new NamespacedKey(Main.getInstance(), "mobTeamName"), PersistentDataType.STRING) : null;
        }

        if ( entity2 instanceof Player player ) {
            GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
            teamName2 = gamePlayer.getGame().getTeamOfPlayer(player).getName();
        } else {
            teamName2 = persistentDataContainer2.has(new NamespacedKey(Main.getInstance(), "mobTeamName")) ? persistentDataContainer2.get(new NamespacedKey(Main.getInstance(), "mobTeamName"), PersistentDataType.STRING) : null;
        }

        if ( teamName1 == null || teamName2 == null ) return false;

        return teamName1.equals(teamName2);

    }

    public static String getEntityGameName(Entity entity) {

        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        return persistentDataContainer.has(new NamespacedKey(Main.getInstance(), "mobGameName")) ? persistentDataContainer.get(new NamespacedKey(Main.getInstance(), "mobGameName"), PersistentDataType.STRING) : null;

    }

    @EventHandler
    public void onMobTarget(EntityTargetEvent event) {

        if ( !( event.getEntity() instanceof Mob mob ) ) return;

        String gameName = getEntityGameName(event.getEntity());

        Game game = Main.getGame(gameName);

        if ( game == null ) return;

        if ((game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING)) {

            if (event.getTarget() instanceof Player) {
                final Player player = (Player) event.getTarget();
                if (game.isProtectionActive(player)) {
                    event.setCancelled(true);
                    return;
                }

                if (Main.isPlayerInGame(player)) {

                    if (areEntitiesAllies(player, event.getEntity())) {
//                        if (event.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) {
//                            EntityUtils.makeMobForgetTarget(mob);
//                            mob.setTarget(null);
////                            Bukkit.getConsoleSender().sendMessage("calcelled same team mob on player attack when attacked");
//                            event.setCancelled(true);
//                            return;
//                        }
//                        Bukkit.getConsoleSender().sendMessage("calcelled same team mob on player attack");
                        event.setCancelled(true);
                        Player playerTarget = MiscUtils.findTarget(game, player, FlowerUtils.universalFollowRange);
                        if (playerTarget != null) {
                            mob.setTarget(playerTarget);
                            return;
                        }
                    }
                }
            } else if (event.getTarget() instanceof org.bukkit.entity.Villager) {
                event.setCancelled(true);
            } else if (event.getTarget() instanceof Mob) {
                if ( areEntitiesAllies(event.getEntity(), event.getTarget()) ) {
                    event.setCancelled(true);
                }

            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onMobAttack(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (!Main.isPlayerInGame(player)) return;

        }

            Entity mobAttacker = event.getDamager();

            if ( mobAttacker instanceof Projectile ) {
                mobAttacker = (Entity) ((Projectile) mobAttacker).getShooter();
            }

            if ( !(mobAttacker instanceof Mob mob ) ) return;

            if ( areEntitiesAllies(event.getEntity(), mob) ) {
                event.setCancelled(true);
                mob.setTarget(null);
                return;
            }


            switch(mob.getType()) {
                case ZOGLIN -> event.setDamage(FlowerUtils.zoglinDamage);
                case PHANTOM -> {
                    event.setDamage(FlowerUtils.phantomDamage);
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 300, 0, false, false));
                }
                case IRON_GOLEM -> event.setDamage(FlowerUtils.golemDamage);
                case BLAZE -> event.setDamage(FlowerUtils.blazeDamage);
                case HUSK -> event.setDamage(FlowerUtils.huskDamage);
                case STRAY -> event.setDamage(FlowerUtils.strayDamage);
                case SLIME -> event.setDamage(FlowerUtils.slimeDamage);
                case SHULKER -> event.setDamage(FlowerUtils.shulkerDamage);
                case GHAST -> event.setDamage(FlowerUtils.ghastDamage);
                case GUARDIAN -> event.setDamage(FlowerUtils.guardianDamage);
            }

//                                Bukkit.getConsoleSender().sendMessage("Zoglin damages " + player.getName() + " for " + event.getFinalDamage() + " (" + event.getDamage() + ")");

    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {

        event.getDrops().clear();
        event.setDroppedExp(0);

    }

}
