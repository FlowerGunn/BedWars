package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;
import org.screamingsandals.bedwars.utils.flowergun.shoputils.PurchasableItem;

public class Equipment extends Ability implements IAbility {

    public Equipment(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 5).addResource(ResourceType.COPPER, 25).addResource(ResourceType.EMERALD, 8).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 15).addResource(ResourceType.COPPER, 30).addResource(ResourceType.EMERALD, 16).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Экипировка";
        this.id = "equipment";
        this.item = Material.WOODEN_PICKAXE;
        this.rarity = 3;
        this.icon = IconType.COPPER_INGOT;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.ECONOMIST);

        this.description = "В начале игры игрок получит#золотой меч и (values1) стейков.";

    }


    @Override
    public int calculateIntValue1(int level) {
        return 5 + level;
    }



    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {


//        gamePlayer.getGame().shop.findItemById("stonePick").giveForFree(gamePlayer.player);
        gamePlayer.getGame().shop.findItemById("goldenSword2").giveForFree(gamePlayer.player);

        ItemStack kit = new ItemStack(Material.COOKED_BEEF);
        kit.setAmount( calculateIntValue1(level) );
        gamePlayer.player.getInventory().addItem(kit);

        kit = new ItemStack(Material.SANDSTONE);
        kit.setAmount( calculateIntValue2(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }


}
