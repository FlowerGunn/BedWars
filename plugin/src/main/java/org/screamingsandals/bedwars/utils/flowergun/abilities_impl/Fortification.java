package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Fortification extends Ability implements IAbility {

    public Fortification(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.POLISHED_RUBY, 60).addResource(ResourceType.PROCESSING_UNIT, 1).addResource(ResourceType.REINFORCED_BONE_PLATE, 2).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.POLISHED_RUBY, 150).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.REINFORCED_BONE_PLATE, 5).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Фортификация";
        this.id = "fortification";
        this.item = Material.GOLDEN_CARROT;
        this.rarity = 5;
        this.icon = IconType.ABSORPTION;
        this.description = "Исходящее лечение игрока увеличено на +(values1)%,#а принимаемое уменьшено на -(values2)%";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 5 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 35 - 5 * level;
    }



    @Override
    public void healPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        compoundValueModifier.addExp(calculateIntValue1(level) * 0.01);
    };

    @Override
    public void healedByPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        compoundValueModifier.addExp(calculateIntValue2(level) * -0.01);
    };


}
