package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

public class Tempered extends Ability implements IAbility {

    public Tempered(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.IRON_PLATE, 15).addResource(ResourceType.BLAZE_POWDER, 25).addResource(ResourceType.ICE_POWDER, 30).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.IRON_PLATE, 20).addResource(ResourceType.BLAZE_POWDER, 60).addResource(ResourceType.ICE_POWDER, 70).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Закалка";
        this.id = "tempering";
        this.item = Material.IRON_CHESTPLATE;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;

        this.abilityCategories.add(AbilityCategory.TANK);

        this.description = "Все нагрудники в магазине получают#Прочность +(values1) и Защиту от снарядов +3.";
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
//            Bukkit.getConsoleSender().sendMessage("DURABILITY = " + currentLevel);
            int currentLevel2 = im.getEnchantLevel(Enchantment.PROTECTION_PROJECTILE);
//            Bukkit.getConsoleSender().sendMessage("PROTECTION_PROJECTILE = " + currentLevel2);
            currentLevel += calculateIntValue1(activeLevel);
            currentLevel2 += 3;
            im.addEnchant(Enchantment.DURABILITY, currentLevel, true);
            im.addEnchant(Enchantment.PROTECTION_PROJECTILE, currentLevel2, true);
            item.item.setItemMeta(im);
            item.item.build();

        }
    }

}
