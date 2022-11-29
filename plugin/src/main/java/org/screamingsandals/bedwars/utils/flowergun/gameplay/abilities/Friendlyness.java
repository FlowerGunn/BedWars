package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

public class Friendlyness extends Ability implements IAbility {

    public Friendlyness(){
        this.name = "Дружелюбие";
        this.id = "friendlyness";
        this.item = Material.FIREWORK_STAR;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;
        this.description = "Вы получаете (values1)&7 бонусных феерверков#и снежков при соответвующих покупках в магазине";
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
