package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Resource;

import java.util.ArrayList;
import java.util.Random;

public class Greediness extends Ability implements IAbility {

    public Greediness(){
        this.name = "Жадина";
        this.id = "greediness";
        this.icon = Material.EMERALD_BLOCK;
        this.rarity = 3;
        this.description = "Подбирая золото вы получаете Скорость 2#на (values1)&7 секунд, а подбирая#изумруды - Скорость 3 на (values2)&7";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 40 + level * 10;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 40 + level * 10;
    }


    @Override
    public String formatValue1(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateIntValue1(level) / 20.0 );
    }

    @Override
    public String formatValue2(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateIntValue2(level) / 20.0 );
    }

    @Override
    public void pickupItem(int level, Player player, EntityPickupItemEvent event) {

        if (event.getItem().getItemStack().getType() == Material.GOLD_INGOT) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level), 1));
        } else if (event.getItem().getItemStack().getType() == Material.EMERALD) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue2(level), 2));
        }

    }

}
