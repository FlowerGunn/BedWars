package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Confidence extends Ability implements IAbility {

    public Confidence(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 20).addResource(ResourceType.GLOW_INK_SAC, 15).addResource(ResourceType.IRON, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.RAW_GOLD, 50).addResource(ResourceType.GLOW_INK_SAC, 30).addResource(ResourceType.IRON, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Уверенность";
        this.id = "confidence";
        this.item = Material.IRON_SHOVEL;
        this.rarity = 3;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Весь урон игрока увеличен на +(values1)%";

    }


    @Override
    public int calculateIntValue1(int level) {
        return 3 + 1 * level;
    }


    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() > 0 ) {
            compoundValueModifier.addExp(calculateIntValue1(level) * 0.01);
        }

    }

}
