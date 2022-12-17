package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

public class Tempered extends Ability implements IAbility {

    public Tempered(){
        this.name = "Закалка";
        this.id = "tempering";
        this.item = Material.IRON_CHESTPLATE;
        this.icon = IconType.DAMAGE_RESISTANCE;
        this.description = "Все нагрудники в магазине получают#Прочность +(values1)&7 и Защиту от снарядов +2";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 3 + level;
    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {
//        Bukkit.getConsoleSender().sendMessage("item processed");
        if ( item.getItemCategory() == ItemCategory.CHESTPLATE ) {
//            Bukkit.getConsoleSender().sendMessage("item is a chestplate");
            ItemMeta im = item.item.getItemMeta(); //.changeDeal( item.item.getItem().getAmount() + activeLevel, 0, 0 );
            int currentLevel = im.getEnchantLevel(Enchantment.DURABILITY);
            int currentLevel2 = im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
            currentLevel += calculateIntValue1(activeLevel);
            currentLevel2 += 2;
            im.addEnchant(Enchantment.DURABILITY, currentLevel, true);
            im.addEnchant(Enchantment.PROTECTION_PROJECTILE, currentLevel2, true);
        }
    }

}
