package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

import java.util.Random;

public class Clemptomaniac extends Ability implements IAbility {

    public Clemptomaniac(){
        this.name = "Торгаш";
        this.id = "trader";
        this.item = Material.GOLD_NUGGET;
        this.icon = IconType.GOLD_INGOT;
        this.description = "Вы имеете шанс в (values1)&7% #вернуть 1 золото при покупке любого#предмета требующего золото.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 25 + level * 10;
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
