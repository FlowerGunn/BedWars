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
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.events.BedwarsApplyPropertyToBoughtItem;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.MiscUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class MagnetShoesListener implements Listener {
    // Class for special item is not needed in this case (so this special item is not registered in game)
    public static final String MAGNET_SHOES_PREFIX = "Module:MagnetShoes:";

    @EventHandler
    public void onMagnetShoesRegistered(BedwarsApplyPropertyToBoughtItem event) {
        if (event.getPropertyName().equalsIgnoreCase("magnetshoes")) {
            ItemStack stack = event.getStack();
            int probability = MiscUtils.getIntFromProperty("probability", "magnet-shoes.probability", event);

            APIUtils.hashIntoInvisibleString(stack, MAGNET_SHOES_PREFIX + probability);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
            return;
        }

//        if (event instanceof EntityDamageByEntityEvent) {

//            EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
//            if (edbee.getDamager() instanceof Player) {
//
//                Player damager = (Player) edbee.getDamager();
//
                Player victim = (Player) event.getEntity();

                if (Main.isPlayerInGame(victim)) {
                    ItemStack boots = victim.getInventory().getBoots();
                    if (boots != null) {
                        String magnetShoes = APIUtils.unhashFromInvisibleStringStartsWith(boots, MAGNET_SHOES_PREFIX);
                        if (magnetShoes != null) {

//                            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
////                                Bukkit.getConsoleSender().sendMessage("returned on entity explosion");
//                                return;
//                            }

//                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
//                        Projectile projectile = (Projectile) edbee.getDamager();
//                        if (projectile instanceof Fireball && game.getStatus() == GameStatus.RUNNING) {
//                            final double damage = Main.getConfigurator().config.getDouble("specials.throwable-fireball.damage");
//                            event.setDamage(damage);
//                        } else if (projectile.getShooter() instanceof Player) {
//                            Player damager = (Player) projectile.getShooter();
//                            if (Main.isPlayerInGame(damager)) {
//                                GamePlayer gDamager = Main.getPlayerGameProfile(damager);
//                                if (gDamager.isSpectator || gDamager.getGame().getPlayerTeam(gDamager) == game.getPlayerTeam(gPlayer) && !game.getOriginalOrInheritedFriendlyfire()) {
//                                    event.setCancelled(true);
//                                }
//                            }
//                        }
                            if (event instanceof EntityDamageByEntityEvent) {
                                EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) event;
                                if (edbee.getDamager() != victim) {
                                    int probability = Integer.parseInt(magnetShoes.split(":")[2]);
                                    int randInt = MiscUtils.randInt(0, 100);
//                                    Bukkit.getConsoleSender().sendMessage("probability :" + probability);
                                    if (randInt <= probability) {
                                        event.setCancelled(true);

//                                        Bukkit.getConsoleSender().sendMessage("boots " + victim.getDisplayName() + " " + event.getDamage() + " " + event.getFinalDamage() + " " + event.getCause());
                                        victim.damage(event.getDamage());
                                    }
                                }
                            }
                        }
                    }
                }
//            }
//        }
    }
}
