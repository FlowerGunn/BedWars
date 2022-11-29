package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customgui.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;

public class Lumberjack extends Ability implements IAbility {

    public Lumberjack(){
        this.name = "Дровосек";
        this.id = "lumberjack";
        this.item = Material.OAK_WOOD;
        this.icon = IconType.FAST_DIGGING;
        this.description = "Покупка деревянных блоков в магазине стоит#на (values2)&7 ресурсов дешевле. Ломая деревянные блоки#вы получаете эффект Спешки 3 на (values1)&7 секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 40 + level * 20;
    }

    @Override
    public int calculateIntValue2(int level) {
        return level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void blockBreak(int level, BlockBreakEvent event) {

        if (event.isCancelled()) return;
        String material = event.getBlock().getType().toString();

        if (material.contains("LOG") || material.contains("PLANKS") || material.contains("FENCE") || material.contains("GATE")) {

            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 1, false, false));
            notifyPlayerOnAbilityActivation(event.getPlayer());

        }

    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {

        if ( item.getItemCategory() == ItemCategory.WOOD ) {
//            Bukkit.getConsoleSender().sendMessage("item is a firework");
            item.changeDeal( 0 , item.priceAmount1 - calculateIntValue2(activeLevel), 0 );

        }
    }

}
