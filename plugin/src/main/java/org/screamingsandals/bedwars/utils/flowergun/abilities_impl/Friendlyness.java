package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;

public class Friendlyness extends Ability implements IAbility {

    public Friendlyness(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.IRON, 25).addResource(ResourceType.GOLD, 20).addResource(ResourceType.POLISHED_RUBY, 15).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.IRON, 50).addResource(ResourceType.GOLD, 40).addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Дружелюбие";
        this.id = "friendlyness";
        this.item = Material.FIREWORK_STAR;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;

        this.abilityCategories.add(AbilityCategory.SNOWMAN);
        this.abilityCategories.add(AbilityCategory.PYROTECHNIC);
        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Игрок получает (values1) бонусных фейерверков#и снежков при соответвующих покупках в магазине.#Урон снежков и фейерверков увеличен на (values2)%.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 6 + level * 2;
    }


    @Override
    public int calculateIntValue2(int level) {
        return 14 + level * 2;
    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {
//        Bukkit.getConsoleSender().sendMessage("item processed");
        if ( item.getItemCategory() == ItemCategory.FIREWORK || item.getItemCategory() == ItemCategory.SNOWBALL ) {
//            Bukkit.getConsoleSender().sendMessage("item is a firework");
            item.changeDeal( item.item.getItem().getAmount() + calculateIntValue1(activeLevel) , 0, 0 );

        }
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( event.isCancelled() ) return;

        if ( event.getFinalDamage() > 0 && damageInstance.damageSource == DamageSource.PLAYER && ( damageInstance.damageType == DamageType.SNOWBALL || damageInstance.damageType == DamageType.FIREWORK) ) {
            compoundValueModifier.addExp(calculateIntValue2(level) * 0.01);
        }
    }

}
