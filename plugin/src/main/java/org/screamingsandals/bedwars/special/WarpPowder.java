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

package org.screamingsandals.bedwars.special;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.Team;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.utils.external.SpawnEffects;
import org.screamingsandals.bedwars.lib.nms.entity.PlayerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.GadgetType;

import static org.screamingsandals.bedwars.lib.lang.I.i18nc;

public class WarpPowder extends SpecialItem implements org.screamingsandals.bedwars.api.special.WarpPowder {
    private BukkitTask teleportingTask = null;
    private int teleportingTime;
    private int fullTeleportingTime;

    private ItemStack item;

    public WarpPowder(Game game, Player player, Team team, ItemStack item, int teleportingTime) {
        super(game, player, team);
        this.item = item;
        this.teleportingTime = teleportingTime * 4;
        this.fullTeleportingTime = this.teleportingTime;
    }

    @Override
    public ItemStack getStack() {
        return item;
    }

    @Override
    public void cancelTeleport(boolean showCancelledMessage) {
        try {
            teleportingTask.cancel();
        } catch (Exception ignored) {

        }

        game.unregisterSpecialItem(this);

        if (showCancelledMessage) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 0.3F, 0.5F);
//            player.sendMessage(i18nc("specials_warp_powder_canceled", game.getCustomPrefix()));player.sendTitle(ColoursManager.purple + "...телепортация...", durationString, 0, 6, 1);
            player.sendTitle("", ColoursManager.red + "Телепортация отменена.", 0, 20, 10);

        }
    }

    @Override
    public void runTask() {
        game.registerSpecialItem(this);

//        player.sendMessage(i18nc("specials_warp_powder_started", game.getCustomPrefix()).replace("%time%", Double.toString(teleportingTime)));
        player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.3F, 0.65F);

        teleportingTask = new BukkitRunnable() {

            @Override
            public void run() {
                if (teleportingTime == 0) {
                    cancelTeleport(false);
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        try {
                            if (player.getInventory().getItemInOffHand().equals(item)) {
                                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            } else {
                                player.getInventory().remove(item);
                            }
                        } catch (Throwable e) {
                            player.getInventory().remove(item);
                        }
                    }
                    player.updateInventory();
                    Triggers.gadgetUsed(player, GadgetType.TP);
                    PlayerUtils.teleportPlayer(player, team.getTeamSpawn());
                    player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.7F, 0.8F);

                } else {
//                    SpawnEffects.spawnEffect(game, player, "game-effects.warppowdertick");
                    player.getLocation().getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 5, 0.5, 1, 0.5,0.05);
                    player.getLocation().getWorld().spawnParticle(Particle.REVERSE_PORTAL, player.getLocation(), 5, 0.5, 1, 0.5, 0.05);
                    player.getLocation().getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 5, 0.5, 1, 0.5, 0);
                    String durationString = "";

                    for ( int i = 0; i < fullTeleportingTime; i++) {
                        if ( i < fullTeleportingTime - teleportingTime) durationString += ColoursManager.portal + "|";
                        else durationString += ColoursManager.darkGray + "|";
                    }

                    player.sendTitle(ColoursManager.purple + "...телепортация...", durationString, 0, 6, 1);
                    teleportingTime--;
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 5L);
    }
}
