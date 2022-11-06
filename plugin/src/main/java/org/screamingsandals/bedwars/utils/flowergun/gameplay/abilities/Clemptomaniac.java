package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.ItemSpawnerType;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;

import java.util.Random;

public class Clemptomaniac extends Ability implements IAbility {

    public Clemptomaniac(){
        this.name = "Торгаш";
        this.id = "trader";
        this.icon = Material.GOLD_NUGGET;
        this.description = "Вы имеете шанс в (values1)&7% #вернуть 1 золото при покупке любого#предмета требующего золото.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 25 + level * 5;
    }

    @Override
    public void shopPurchase(int level, Game game, Player player, PurchasableItem item, int amount) {
//        Bukkit.getConsoleSender().sendMessage("shit");
        if (item.priceType1 == Main.getSpawnerType("gold") || item.priceType2 == Main.getSpawnerType("gold")) {
            Random random = new Random();
            int chance = calculateIntValue1(level);

            ItemStack discount = Main.getSpawnerType("gold").getStack();

            for ( int i = 0; i < amount; i++ ) {
                if ( random.nextInt(100) < chance ) player.getInventory().addItem(discount);
            }
        }
    }

}
