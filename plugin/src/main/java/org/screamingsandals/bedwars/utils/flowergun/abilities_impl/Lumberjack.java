package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ItemCategory;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

public class Lumberjack extends Ability implements IAbility {

    public Lumberjack(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 30).addResource(ResourceType.SLIMEBALL, 40).addResource(ResourceType.COPPER_PLATE, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 80).addResource(ResourceType.SLIMEBALL, 100).addResource(ResourceType.COPPER_PLATE, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Дровосек";
        this.id = "lumberjack";
        this.item = Material.OAK_WOOD;
        this.icon = IconType.FAST_DIGGING;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.BUILDER);

        this.description = "Покупка деревянных блоков в магазине стоит#на (values2) ресурсов дешевле. Ломая деревянные блоки#игрок получает эффект Спешки 3 на (values1) секунд#и восполняет себе (values3) ед. сытости.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 40 + level * 40;
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
    public double calculateDoubleValue1(int level) {
        return 0.8 + 0.4 * level;
    }

    @Override
    public String formatValue3(int level) {
        return FlowerUtils.singleDecimal.format(calculateDoubleValue1(level));
    }

    @Override
    public void blockBreak(int level, BlockBreakEvent event) {

        if (event.isCancelled()) return;
        String material = event.getBlock().getType().toString();

        if (material.contains("LOG") || material.contains("PLANKS") || material.contains("FENCE") || material.contains("GATE")) {


            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 2, false, false));
            Ability.healFood( event.getPlayer(), event.getPlayer(), 0, calculateDoubleValue1(level) );
            notifyPlayerOnAbilityActivation(event.getPlayer());

        }

    }

    @Override
    public void processPurchasibleItem(int activeLevel, GamePlayer gamePlayer, PurchasableItem item) {

        if ( item.getItemCategory() == ItemCategory.WOODY) {
//            Bukkit.getConsoleSender().sendMessage("item is a firework");
            item.changeDeal( 0 , item.priceAmount1 - calculateIntValue2(activeLevel), 0 );

        }
    }

}
