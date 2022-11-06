package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;

public class Friendlyness extends Ability implements IAbility {

    public Friendlyness(){
        this.name = "Дружелюбие";
        this.id = "friendlyness";
        this.icon = Material.FIREWORK_ROCKET;
        this.rarity = 4;
        this.description = "Вы получает (values1)&7 бонусных#феерверков при покупке феерверков";
    }

    @Override
    public int calculateIntValue1(int level) {
        return level * 3;
    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {
//        Bukkit.getConsoleSender().sendMessage("item processed");
        if ( item.getItemCategory() == ItemCategory.FIREWORK ) {
//            Bukkit.getConsoleSender().sendMessage("item is a firework");
            item.changeDeal( item.item.getItem().getAmount() + calculateIntValue1(activeLevel) , 0, 0 );

        }
    }

}
