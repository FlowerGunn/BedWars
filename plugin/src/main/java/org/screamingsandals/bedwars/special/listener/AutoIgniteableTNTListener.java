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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.RunningTeam;
import org.screamingsandals.bedwars.api.events.BedwarsApplyPropertyToBoughtItem;
import org.screamingsandals.bedwars.game.CurrentTeam;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.api.events.BedwarsPlayerBuildBlock;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.special.AutoIgniteableTNT;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class AutoIgniteableTNTListener implements Listener {
    private static final String AUTO_IGNITEABLE_TNT_PREFIX = "Module:AutoIgniteableTnt:";

    @EventHandler
    public void onAutoIgniteableTNTRegistered(BedwarsApplyPropertyToBoughtItem event) {
        if (event.getPropertyName().equalsIgnoreCase("autoigniteabletnt")) {
            ItemStack stack = event.getStack();
            APIUtils.hashIntoInvisibleString(stack, applyProperty(event));
        }
    }

    @EventHandler
    public void onPlace(BedwarsPlayerBuildBlock event) {
        Game game = event.getGame();
        Block block = event.getBlock();
        ItemStack stack = event.getItemInHand();
        Player player = event.getPlayer();
        String unhidden = APIUtils.unhashFromInvisibleStringStartsWith(stack, AUTO_IGNITEABLE_TNT_PREFIX);
        if (unhidden != null) {
            block.setType(Material.AIR);
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            int explosionTime = Integer.parseInt(unhidden.split(":")[2]);
            boolean damagePlacer = Boolean.parseBoolean(unhidden.split(":")[3]);
            float damage = Float.parseFloat(unhidden.split(":")[4]);
            AutoIgniteableTNT special = new AutoIgniteableTNT(game, player, game.getTeamOfPlayer(player), explosionTime,
                    damagePlacer, damage);
            special.spawn(location);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!Main.isPlayerInGame(player)) {
            return;
        }

        if (event.getDamager() instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) event.getDamager();

//            Bukkit.getConsoleSender().sendMessage( "Damaging " + player.getName() + " with tnt by " + tnt.getMetadata("owner").get(0).asString());



            if ( tnt.hasMetadata("owner")) {

                Player owner = Bukkit.getPlayer(tnt.getMetadata("owner").get(0).asString());
                Player victim = (Player) event.getEntity();

                double distance = victim.getLocation().distance(event.getDamager().getLocation());
                event.setDamage(FlowerUtils.TNTDamage * ( 1 - ( distance / FlowerUtils.TNTRadius) ) );

                GamePlayer gOwner = Main.getPlayerGameProfile(owner);
                GamePlayer gVictim = Main.getPlayerGameProfile(victim);

                Game game = gVictim.getGame();
                RunningTeam teamVictim = game.getTeamOfPlayer(victim);
                RunningTeam teamOwner = game.getTeamOfPlayer(owner);

                if ( teamOwner == teamVictim && victim != owner ) {
                    event.setCancelled(true);
//                    Bukkit.getConsoleSender().sendMessage( "Same team TNT damage cancelled for " + player.getName());
                } else {
                    DamageInstance damageInstance = new DamageInstance(DamageSource.PLAYER, DamageTarget.PLAYER, DamageRelay.CONTACT, DamageType.EXPLOSION);
                    gOwner.lastDealtDamageInstance = damageInstance;
                }
            }



//            if (tnt.hasMetadata(player.getUniqueId().toString()) && tnt.hasMetadata("autoignited")) {
//                event.setCancelled(true);
//                Bukkit.getConsoleSender().sendMessage( "TNT damage cancelled for " + player.getName());
//            }
        }
    }

    private String applyProperty(BedwarsApplyPropertyToBoughtItem event) {
        return AUTO_IGNITEABLE_TNT_PREFIX
                + MiscUtils.getIntFromProperty("explosion-time", "specials.auto-igniteable-tnt.explosion-time", event) + ":"
                + MiscUtils.getBooleanFromProperty("damage-placer", "specials.auto-igniteable-tnt.damage-placer", event) + ":"
                + MiscUtils.getDoubleFromProperty("damage", "specials.auto-igniteable-tnt.damage", event);
    }

}
