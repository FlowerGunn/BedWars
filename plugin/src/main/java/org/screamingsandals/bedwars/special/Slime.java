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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.api.Team;
import org.screamingsandals.bedwars.api.game.Game;
import org.screamingsandals.bedwars.game.TeamColor;
import org.screamingsandals.bedwars.utils.external.MiscUtils;

import static org.screamingsandals.bedwars.lib.lang.I18n.i18nonly;

public class Slime extends SpecialItem implements org.screamingsandals.bedwars.api.special.Golem {

    private Location location;
    private ItemStack item;
    private double speed;
    private double followRange;
    private double health;
    private String name;
    private boolean showName;

    public Slime(Game game, Player player, Team team,
                 ItemStack item, Location location, double speed, double followRange, double health,
                 String name, boolean showName) {
        super(game, player, team);
        this.location = location;
        this.item = item;
        this.speed = speed;
        this.followRange = followRange;
        this.health = health;
        this.name = name;
        this.showName = showName;
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public double getFollowRange() {
        return followRange;
    }

    @Override
    public double getHealth() {
        return health;
    }

    public void spawn() {
        spawnMob();



        if (!Main.getConfigurator().config.getBoolean("specials.dont-show-success-messages")) {
            MiscUtils.sendActionBarMessage(player, i18nonly("specials_mob_created"));
        }

        //TODO - make this better by checking full inventory
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
    }

    private void spawnMob() {
        final TeamColor color = TeamColor.fromApiColor(team.getColor());
        final org.bukkit.entity.Slime golem = (org.bukkit.entity.Slime) location.getWorld().spawnEntity(location, EntityType.SLIME);
        golem.setHealth(health);

//        golem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000, 0, false, true));
        golem.setCustomName(name
                .replace("%teamcolor%", color.chatColor.toString())
                .replace("%team%", team.getName()));
        golem.setCustomNameVisible(showName);
        try {
            golem.setInvulnerable(false);
        } catch (Throwable ignored) {
            // Still can throw an exception on some old versions
        }

        entity = golem;
        PersistentDataContainer persistentDataContainer = this.entity.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(Main.getInstance(), "mobTeamName"), PersistentDataType.STRING, team.getName());
        persistentDataContainer.set(new NamespacedKey(Main.getInstance(), "mobGameName"), PersistentDataType.STRING, game.getName());

        game.registerSpecialItem(this);
        Main.registerGameEntity(golem, (org.screamingsandals.bedwars.game.Game) game);

    }
}
