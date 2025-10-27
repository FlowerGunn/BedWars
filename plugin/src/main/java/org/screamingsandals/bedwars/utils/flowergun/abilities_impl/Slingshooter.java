package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;

public class Slingshooter extends Ability implements IAbility {

    public Slingshooter(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 200).addResource(ResourceType.ICE_POWDER, 30).addResource(ResourceType.CRYO_SLIME, 10).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 500).addResource(ResourceType.ICE_POWDER, 60).addResource(ResourceType.CRYO_SLIME, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Метатель";
        this.id = "slingshooter";
        this.item = Material.STRING;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.SNOWMAN);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Урон снежков увеличивается на (values2) ед.,#кулдаун уменьшается на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 15 + 10 * level;
    }

//    @Override
//    public int calculateIntValue2(int level) {
//        return 20 * level;
//    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 4 + 1 * level;
    }

    @Override
    public String formatValue1(int level) {
        return FlowerUtils.doubleDecimal.format( calculateIntValue1(level) / 20 );
    }



    @Override
    public String formatValue2(int level) {
        return FlowerUtils.doubleDecimal.format( calculateDoubleValue1(level) );
    }


    @Override
    public void gadgetUsed(int activeLevel, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier) {
        if (gadgetType == GadgetType.SNOWBALL) {
            compoundValueModifier.addInt( calculateIntValue1(activeLevel) );
        }
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( damageInstance.damageSource == DamageSource.PLAYER && damageInstance.damageType == DamageType.SNOWBALL ) {
            compoundValueModifier.addDouble(-1 * calculateDoubleValue1(level));
        }
    }
}
