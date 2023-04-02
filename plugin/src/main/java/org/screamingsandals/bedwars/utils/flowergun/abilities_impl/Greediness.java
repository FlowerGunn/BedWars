package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Greediness extends Ability implements IAbility {

    public Greediness(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 30).addResource(ResourceType.GOLD, 10).addResource(ResourceType.EMERALD, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 100).addResource(ResourceType.GOLD, 20).addResource(ResourceType.EMERALD, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Жадина";
        this.id = "greediness";
        this.item = Material.EMERALD_BLOCK;
        this.rarity = 3;
        this.icon = IconType.EMERALD;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.ECONOMIST);

        this.description = "Подбирая золото игрок получает Скорость 2#на (values1) секунд, а подбирая#изумруды - Скорость 3 на (values2) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 50 + level * 30;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 50 + level * 30;
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
