package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;

import java.util.Random;

public class Tempered extends Ability implements IAbility {

    public Tempered(){
        this.name = "Закалка";
        this.id = "tempering";
        this.icon = Material.IRON_CHESTPLATE;
        this.description = "Все нагрудники в магазине#получают Прочность +(values1)";
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
            currentLevel += 2 + calculateIntValue1(activeLevel);
            im.addEnchant(Enchantment.DURABILITY, activeLevel, true);
        }
    }

}
