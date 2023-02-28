    package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

    public class WoolManiac extends Ability implements IAbility{


    private int actualCooldown;

    public WoolManiac(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 250).addResource(ResourceType.SCRAP, 40).addResource(ResourceType.COAL, 50).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SILK_COCOON, 500).addResource(ResourceType.SCRAP, 100).addResource(ResourceType.COAL, 120).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Шерстяной Маньяк";
        this.id = "woolmaniac";
        this.item = Material.WHITE_WOOL;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;
        this.description = "Игрок получает неломаемую зажигалку,#неломаемые ножницы на Эффективность (values1)#и (values2) шерсти при первом спавне.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 2 + level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 16 + level * 16;
    }

//    @Override
//    public void playerRespawn(int level, GamePlayer gamePlayer) {
//
//    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        ItemStack kit2 = new ItemStack(Material.SHEARS);
        ItemMeta itemMeta2 = kit2.getItemMeta();
        itemMeta2.setUnbreakable(true);
        itemMeta2.addEnchant(Enchantment.DIG_SPEED, calculateIntValue1(level), true);
//        itemMeta2.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        kit2.setItemMeta(itemMeta2);
        gamePlayer.player.getInventory().addItem(kit2);

        ItemStack kit3 = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta itemMeta3 = kit3.getItemMeta();
        itemMeta3.setUnbreakable(true);
//        itemMeta3.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        kit3.setItemMeta(itemMeta3);
        gamePlayer.player.getInventory().addItem(kit3);

        ItemStack kit = new ItemStack(Material.WHITE_WOOL);
        kit.setAmount( calculateIntValue2(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }
}
