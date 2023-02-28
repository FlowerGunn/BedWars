package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

public class Friendlyness extends Ability implements IAbility {

    public Friendlyness(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.IRON, 25).addResource(ResourceType.GOLD, 20).addResource(ResourceType.POLISHED_RUBY, 15).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.IRON, 50).addResource(ResourceType.GOLD, 40).addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Дружелюбие";
        this.id = "friendlyness";
        this.item = Material.FIREWORK_STAR;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;
        this.description = "Игрок получает (values1) бонусных феерверков#и снежков при соответвующих покупках в магазине.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return level * 3;
    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {
//        Bukkit.getConsoleSender().sendMessage("item processed");
        if ( item.getItemCategory() == ItemCategory.FIREWORK || item.getItemCategory() == ItemCategory.SNOWBALL ) {
//            Bukkit.getConsoleSender().sendMessage("item is a firework");
            item.changeDeal( item.item.getItem().getAmount() + calculateIntValue1(activeLevel) , 0, 0 );

        }
    }

}
