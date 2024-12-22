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

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.APIUtils;
import org.screamingsandals.bedwars.api.events.BedwarsApplyPropertyToBoughtItem;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.api.game.GameStatus;
import org.screamingsandals.bedwars.api.special.SpecialItem;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.special.Slime;
import org.screamingsandals.bedwars.utils.external.DelayFactory;
import org.screamingsandals.bedwars.utils.external.MiscUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Triggers;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.GadgetType;

import java.util.List;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18nonly;

public class SlimeListener implements Listener {
    private static final String SLIME_PREFIX = "Module:Slime:";


    @EventHandler
    public void onGolemRegister(BedwarsApplyPropertyToBoughtItem event) {
        if (event.getPropertyName().equalsIgnoreCase("slime")) {
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

//        Bukkit.getConsoleSender().sendMessage("Interact event");

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (game.getStatus() == GameStatus.RUNNING && !gamePlayer.isSpectator && event.getItem() != null) {
                ItemStack stack = event.getItem();
                String unhidden = APIUtils.unhashFromInvisibleStringStartsWith(stack, SLIME_PREFIX);

                if (unhidden != null) {

//                    Bukkit.getConsoleSender().sendMessage("Interact PHANTOM");

                    if (!game.isDelayActive(player, Slime.class)) {
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
                        Slime golem = new Slime(game, player, game.getTeamOfPlayer(player),
                                stack, location, speed, follow, health, name, showName);

                        if (delay > 0) {
                            DelayFactory delayFactory = new DelayFactory(delay, golem, player, game);
                            game.registerDelay(delayFactory);
                        }
                        Triggers.gadgetUsed(player, GadgetType.SLIME);
                        golem.spawn();
//                        golem.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 0, false, true));
                    } else {
                        event.setCancelled(true);

                        int delay = game.getActiveDelay(player, Slime.class).getRemainDelay();
                        MiscUtils.sendActionBarMessage(player, i18nonly("special_item_delay").replace("%time%", String.valueOf(delay)));
                    }
                }

            }
        }
    }

    @EventHandler
    public void onGolemTargetDie(PlayerDeathEvent event) {
        if (Main.isPlayerInGame(event.getEntity())) {
            Game game = Main.getPlayerGameProfile(event.getEntity()).getGame();

            List<SpecialItem> slimes = game.getActivedSpecialItems(Slime.class);
            for (SpecialItem item : slimes) {
                Slime golem = (Slime) item;
                org.bukkit.entity.Slime iron = (org.bukkit.entity.Slime) golem.getEntity();
                if (iron.getTarget() != null && iron.getTarget().equals(event.getEntity())) {
                    iron.setTarget(null);
                }
            }
        }
    }


    private String applyProperty(BedwarsApplyPropertyToBoughtItem event) {
        return SLIME_PREFIX
                + MiscUtils.getDoubleFromProperty(
                "speed", "specials.slime.speed", event) + ":"
                + MiscUtils.getDoubleFromProperty(
                "follow-range", "specials.slime.follow-range", event) + ":"
                + MiscUtils.getDoubleFromProperty(
                "health", "specials.slime.health", event) + ":"
                + MiscUtils.getBooleanFromProperty(
                "show-name", "specials.slime.show-name", event) + ":"
                + MiscUtils.getIntFromProperty(
                "delay", "specials.slime.delay", event) + ":"
                + MiscUtils.getBooleanFromProperty("collidable", "specials.slime.collidable", event) + ":"
                + MiscUtils.getStringFromProperty(
                "name-format", "specials.slime.name-format", event);
    }
}
