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
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsApplyPropertyToBoughtItem;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerBreakBlock;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerBuildBlock;
import org.screamingsandals.bedwars.api.special.SpecialItem;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.special.Trap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.nio.Buffer;
import java.util.List;
import java.util.Map;

import static org.screamingsandals.bedwars.lib.lang.I.i18nc;
import static org.screamingsandals.bedwars.lib.lang.I18n.i18n;

public class TrapListener implements Listener {
    private static final String TRAP_PREFIX = "Module:Trap:";

    @EventHandler
    public void onTrapRegistered(BedwarsApplyPropertyToBoughtItem event) {
        if (event.getPropertyName().equalsIgnoreCase("trap")) {
            ItemStack stack = event.getStack();
            Main.getInstance().getLogger().info(String.valueOf((List<Map<String, Object>>) event.getProperty("data")));
            Main.getInstance().getLogger().info(String.valueOf(event.getProperty("data")));
            Trap trap = new Trap(event.getGame(), event.getPlayer(),
                    event.getGame().getTeamOfPlayer(event.getPlayer()),
                    (List<Map<String, Object>>) event.getProperty("data"));

            int id = System.identityHashCode(trap);
            String trapString = TRAP_PREFIX + id;

            APIUtils.hashIntoInvisibleString(stack, trapString);
        }

    }

    @EventHandler
    public void onTrapBuild(BedwarsPlayerBuildBlock event) {
        if (event.isCancelled()) {
            return;
        }

        ItemStack trapItem = event.getItemInHand();
        String unhidden = APIUtils.unhashFromInvisibleStringStartsWith(trapItem, TRAP_PREFIX);
        if (unhidden != null) {
            int classID = Integer.parseInt(unhidden.split(":")[2]);

            for (SpecialItem special : event.getGame().getActivedSpecialItems(Trap.class)) {
                Trap trap = (Trap) special;
                if (System.identityHashCode(trap) == classID) {
                    trap.place(event.getBlock().getLocation());
                    event.getPlayer().sendMessage(i18nc("trap_built", event.getGame().getCustomPrefix()));
                    return;
                }
            }
        }

    }

    @EventHandler
    public void onTrapBreak(BedwarsPlayerBreakBlock event) {
        final Player player = event.getPlayer();
        if (!Main.isPlayerInGame(player)) {
            return;
        }

        for (SpecialItem special : event.getGame().getActivedSpecialItems(Trap.class)) {
            Trap trapBlock = (Trap) special;
            RunningTeam runningTeam = event.getTeam();

            if (trapBlock.isPlaced()
                    && event.getBlock().getLocation().equals(trapBlock.getLocation())) {
                event.setDrops(false);
                trapBlock.process(event.getPlayer(), runningTeam, true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled() || !Main.isPlayerInGame(player)) {
            return;
        }

        double difX = Math.abs(event.getFrom().getX() - event.getTo().getX());
        double difZ = Math.abs(event.getFrom().getZ() - event.getTo().getZ());

        if (difX == 0.0 && difZ == 0.0) {
            return;
        }

        GamePlayer gPlayer = Main.getPlayerGameProfile(player);
        Game game = gPlayer.getGame();
        if (game.getStatus() == GameStatus.RUNNING && !gPlayer.isSpectator) {
            for (SpecialItem special : game.getActivedSpecialItems(Trap.class)) {
                Trap trapBlock = (Trap) special;

                if (trapBlock.isPlaced()) {
                    if (game.getTeamOfPlayer(player) != trapBlock.getTeam()) {
                        if (event.getTo().getBlock().getLocation().equals(trapBlock.getLocation())) {
//                            Bukkit.getConsoleSender().sendMessage("trap material" + event.getTo().getBlock().getType());
//                            Bukkit.getConsoleSender().sendMessage("trap material above" + event.getTo().add(0,1,0).getBlock().getType());
                            if (event.getTo().getBlock().getType() != Material.TRIPWIRE) trapBlock.process(player, game.getPlayerTeam(gPlayer), true);
                            else {
                                trapBlock.process(player, game.getPlayerTeam(gPlayer), false);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2, true, false, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 2, true, false, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2, true, false, true));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 300, 0, true, false, true));
                            }
                        }
                    }
                }
            }
        }
    }
}
