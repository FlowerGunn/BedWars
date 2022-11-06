/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.special.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.events.BedwarsApplyPropertyToBoughtItem;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.special.SpecialItem;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.lib.nms.entity.EntityUtils;
import org.screamingsandals.bedwars.special.Golem;
import org.screamingsandals.bedwars.special.Blaze;
import org.screamingsandals.bedwars.utils.DelayFactory;
import org.screamingsandals.bedwars.utils.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;

import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18nonly;

public class BlazeListener implements Listener {
    private static final String BLAZE_PREFIX = "Module:Blaze:";


    @EventHandler
    public void onGolemRegister(BedwarsApplyPropertyToBoughtItem event) {
        if (event.getPropertyName().equalsIgnoreCase("blaze")) {
            ItemStack stack = event.getStack();
            APIUtils.hashIntoInvisibleString(stack, applyProperty(event));
        }
    }

    @EventHandler
    public void onGolemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!Main.isPlayerInGame(player)) {
            return;
        }

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        Game game = gamePlayer.getGame();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (game.getStatus() == GameStatus.RUNNING && !gamePlayer.isSpectator && event.getItem() != null) {
                ItemStack stack = event.getItem();
                String unhidden = APIUtils.unhashFromInvisibleStringStartsWith(stack, BLAZE_PREFIX);

                if (unhidden != null) {
                    if (!game.isDelayActive(player, Blaze.class)) {
                        event.setCancelled(true);

                        double speed = Double.parseDouble(unhidden.split(":")[2]);
                        double follow = Double.parseDouble(unhidden.split(":")[3]);
                        double health = Double.parseDouble(unhidden.split(":")[4]);
                        boolean showName = Boolean.parseBoolean(unhidden.split(":")[5]);
                        int delay = Integer.parseInt(unhidden.split(":")[6]);
                        //boolean collidable = Boolean.parseBoolean((unhidden.split(":")[7])); //keeping this to keep configs compatible
                        String name = unhidden.split(":")[8];

                        Location location;

                        if (event.getClickedBlock() == null) {
                            location = player.getLocation();
                        } else {
                            location = event.getClickedBlock().getRelative(event.getBlockFace())
                                    .getLocation().add(0.5, 0.5, 0.5);
                        }
                        Blaze golem = new Blaze(game, player, game.getTeamOfPlayer(player),
                                stack, location, speed, follow, health, name, showName);

                        if (delay > 0) {
                            DelayFactory delayFactory = new DelayFactory(delay, golem, player, game);
                            game.registerDelay(delayFactory);
                        }

                        golem.spawn();
//                        golem.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 0, false, true));
                    } else {
                        event.setCancelled(true);

                        int delay = game.getActiveDelay(player, Blaze.class).getRemainDelay();
                        MiscUtils.sendActionBarMessage(player, i18nonly("special_item_delay").replace("%time%", String.valueOf(delay)));
                    }
                }

            }
        }
    }

    @EventHandler
    public void onGolemDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Blaze)) {
            return;
        }

        org.bukkit.entity.Blaze ironGolem = (org.bukkit.entity.Blaze) event.getEntity();
        for (String name : Main.getGameNames()) {
            Game game = Main.getGame(name);
            if (game.getStatus() == GameStatus.RUNNING && ironGolem.getWorld().equals(game.getGameWorld())) {
                List<SpecialItem> blazes = game.getActivedSpecialItems(Blaze.class);
                for (SpecialItem item : blazes) {
                    if (item instanceof Blaze) {
                        Blaze golem = (Blaze) item;
                        if (golem.getEntity().equals(ironGolem)) {
                            if (event.getDamager() instanceof Player) {
                                Player player = (Player) event.getDamager();
                                if (Main.isPlayerInGame(player)) {
                                    if (golem.getTeam() != game.getTeamOfPlayer(player)) {
                                        return;
                                    }
                                }
                            } else if (event.getDamager() instanceof Projectile) {
                                ProjectileSource shooter = ((Projectile) event.getDamager()).getShooter();
                                if (shooter instanceof Player) {
                                    Player player = (Player) shooter;
                                    if (Main.isPlayerInGame(player)) {
                                        if (golem.getTeam() != game.getTeamOfPlayer(player)) {
                                            return;
                                        }
                                    }
                                }
                            }

                            event.setCancelled(game.getOriginalOrInheritedFriendlyfire());
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGolemAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof org.bukkit.entity.Blaze)) {
            return;
        }

        org.bukkit.entity.Blaze ironGolem = (org.bukkit.entity.Blaze) event.getDamager();
        for (String name : Main.getGameNames()) {
            Game game = Main.getGame(name);
            if (game.getStatus() == GameStatus.RUNNING && ironGolem.getWorld().equals(game.getGameWorld())) {
                List<SpecialItem> blazes = game.getActivedSpecialItems(Blaze.class);
                for (SpecialItem item : blazes) {
                    if (item instanceof Blaze) {
                        Blaze golem = (Blaze) item;
                        if (golem.getEntity().equals(ironGolem)) {

                            if ( event.getEntity() instanceof Player ) {
                                Player player = (Player) event.getEntity();

                                if (golem.getTeam() == game.getTeamOfPlayer(player)) {
                                        Bukkit.getConsoleSender().sendMessage("Blaze stopped revenging after being attacked");
                                        event.setCancelled(true);
                                        ironGolem.setTarget(null);
                                        return;
                                    }


                                event.setDamage(FlowerUtils.blazeDamage);
                                Bukkit.getConsoleSender().sendMessage("Blaze damages " + player.getName() + " for " + event.getFinalDamage() + " (" + event.getDamage() + ")");
                            }

                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGolemTarget(EntityTargetEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Blaze)) {
            return;
        }


        //else Bukkit.getConsoleSender().sendMessage("Target reason = " + event.getReason());

        org.bukkit.entity.Blaze entityBlaze = (org.bukkit.entity.Blaze) event.getEntity();
        for (String name : Main.getGameNames()) {
            Game game = Main.getGame(name);
            if ((game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) && entityBlaze.getWorld().equals(game.getGameWorld())) {
                List<SpecialItem> activeBlazes = game.getActivedSpecialItems(Blaze.class);
                List<SpecialItem> activeGolems = game.getActivedSpecialItems(Golem.class);
                List<SpecialItem> activeMobs = game.getActivedSpecialItems();
                for (SpecialItem item : activeBlazes) {
                    if (item instanceof Blaze) {
                        Blaze activeBlaze = (Blaze) item;
                        if (activeBlaze.getEntity().equals(entityBlaze)) {
                            if (event.getTarget() instanceof Player) {
                                final Player player = (Player) event.getTarget();
                                if (game.isProtectionActive(player)) {
                                    event.setCancelled(true);
                                    return;
                                }

                                if (Main.isPlayerInGame(player)) {

                                    if (activeBlaze.getTeam() == game.getTeamOfPlayer(player)) {
                                        if (event.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) {
                                            Bukkit.getConsoleSender().sendMessage("Blaze stopped revenging after being attacked");
                                            EntityUtils.makeMobForgetTarget(entityBlaze);
                                            event.setCancelled(true);
                                            return;
                                        }
                                        event.setCancelled(true);
//                                        Bukkit.getConsoleSender().sendMessage("Blaze saw an ally and event cancelled");
                                        // Try to find enemy
                                        Player playerTarget = MiscUtils.findTarget(game, player, activeBlaze.getFollowRange());
                                        if (playerTarget != null) {
                                            // Oh. We found enemy!
                                            entityBlaze.setTarget(playerTarget);
//                                            Bukkit.getConsoleSender().sendMessage("Blaze from team " + activeBlaze.getTeam().getName() + " targets player from team " + game.getTeamOfPlayer(player).getName() + " through event targeting");
                                            return;
                                        }
                                    } else {
//                                        Bukkit.getConsoleSender().sendMessage("Blaze saw an enemy player");
                                    }
                                }
                            } else if (event.getTarget() instanceof org.bukkit.entity.Blaze) {
                                for (SpecialItem activeSpecialEntity : activeMobs) {
                                    if (  activeSpecialEntity.getEntity().equals(event.getTarget()) && activeSpecialEntity.getTeam() == activeBlaze.getTeam()) {
                                        event.setCancelled(true);
                                    }

                                }
                            }
                            else if (event.getTarget() instanceof org.bukkit.entity.Villager) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGolemTargetDie(PlayerDeathEvent event) {
        if (Main.isPlayerInGame(event.getEntity())) {
            Game game = Main.getPlayerGameProfile(event.getEntity()).getGame();

            List<SpecialItem> blazes = game.getActivedSpecialItems(Blaze.class);
            for (SpecialItem item : blazes) {
                Blaze golem = (Blaze) item;
                org.bukkit.entity.Blaze iron = (org.bukkit.entity.Blaze) golem.getEntity();
                if (iron.getTarget() != null && iron.getTarget().equals(event.getEntity())) {
                    iron.setTarget(null);
                }
            }
        }
    }

    @EventHandler
    public void onGolemDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Blaze)) {
            return;
        }

        org.bukkit.entity.Blaze ironGolem = (org.bukkit.entity.Blaze) event.getEntity();
        for (String name : Main.getGameNames()) {
            Game game = Main.getGame(name);
            if ((game.getStatus() == GameStatus.RUNNING || game.getStatus() == GameStatus.GAME_END_CELEBRATING) && ironGolem.getWorld().equals(game.getGameWorld())) {
                List<SpecialItem> blazes = game.getActivedSpecialItems(Blaze.class);
                for (SpecialItem item : blazes) {
                    if (item instanceof Blaze) {
                        Blaze golem = (Blaze) item;
                        if (golem.getEntity().equals(ironGolem)) {
                            event.getDrops().clear();
                        }
                    }
                }
            }
        }
    }

    private String applyProperty(BedwarsApplyPropertyToBoughtItem event) {
        return BLAZE_PREFIX
                + MiscUtils.getDoubleFromProperty(
                "speed", "specials.blaze.speed", event) + ":"
                + MiscUtils.getDoubleFromProperty(
                "follow-range", "specials.blaze.follow-range", event) + ":"
                + MiscUtils.getDoubleFromProperty(
                "health", "specials.blaze.health", event) + ":"
                + MiscUtils.getBooleanFromProperty(
                "show-name", "specials.blaze.show-name", event) + ":"
                + MiscUtils.getIntFromProperty(
                "delay", "specials.blaze.delay", event) + ":"
                + MiscUtils.getBooleanFromProperty("collidable", "specials.blaze.collidable", event) + ":"
                + MiscUtils.getStringFromProperty(
                "name-format", "specials.blaze.name-format", event);
    }
}
