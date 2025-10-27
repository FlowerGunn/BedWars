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

package org.screamingsandals.bedwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;

import java.util.*;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class TestCommand extends BaseCommand {

    public TestCommand() {
        super("test", ADMIN_PERMISSION, false, false);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {


        if ( !(sender instanceof Player player)) return false;
        if ( Main.isPlayerInGame(player) ) {
            Main.getPlayerGameProfile(player).getGame().updateScoreboard();
        } else {
            Game.clearScoreboard(player);
            player.sendMessage("cleared");
        }





        new BukkitRunnable() {
            Vector finalDirection;
            Vector currentDirection = player.getLocation().getDirection().clone();
            Vector addition;

            double minSpeed = 0.2;
            double maxSpeed = 1.5;
            double speed = 1;

            double vectorModifier = 0;
            double vectorModifierStep = 0.005;
            double maxVectorModifier = 1;
            Location point = player.getLocation().clone();
            Location end = player.getLocation().clone().add(10, 10, 10);

            boolean test = false;
            @Override
            public void run() {
                //recalculate end if a player is moving
                if ( test ) {
                    end = player.getLocation().clone();
//                    player.sendTitle( " ", end.toString(), 5, 20 , 5);
                }

                //stop if player is dead or the projectile travelled too far
                //check if we reached the target
                if ( end.distance(point) < 2 ) {
                    if ( test ) this.cancel();
                    else {
                        test = true;
//                        Bukkit.getConsoleSender().sendMessage("test");
                    }
                }

                //establish final direction
                finalDirection = end.toVector().subtract(point.toVector());

                //move on the currentDirection
                point.add(currentDirection.clone().normalize().multiply(speed));
                point.getWorld().spawnParticle(Particle.END_ROD, point, 1, 0,0,0,0);

                //adjustSpeed
                speed = Math.pow( 1 - currentDirection.angle(finalDirection) / (Math.PI), 2 ) * (maxSpeed - minSpeed) + minSpeed;
//                Bukkit.getConsoleSender().sendMessage("speed " + speed);

                //change currentDirection
                vectorModifier = Math.min(vectorModifier + vectorModifierStep, maxVectorModifier);
                addition = finalDirection.clone().subtract(currentDirection).multiply(vectorModifier);
                currentDirection = currentDirection.add(addition);

            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);

//        BlockDisplay blockDisplay = (BlockDisplay) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.BLOCK_DISPLAY);

        //Main.getPlayerGameProfile(player).setSetting( PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT, "0" );

        return true;
    }

    @Override
    public void completeTab(List<String> completion, CommandSender sender, List<String> args) {

    }

}
