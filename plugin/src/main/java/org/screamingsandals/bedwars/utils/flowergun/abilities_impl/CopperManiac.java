package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class CopperManiac extends Ability implements IAbility{


    private int actualCooldown;

    public CopperManiac(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.RAW_COPPER, 200).addResource(ResourceType.COPPER, 50).addResource(ResourceType.COPPER_PLATE, 30).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.RAW_COPPER, 500).addResource(ResourceType.COPPER, 100).addResource(ResourceType.COPPER_PLATE, 60).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Медный Маньяк";
        this.id = "coppermaniac";
        this.item = Material.valueOf("COPPER_INGOT");
        this.cooldownMilliseconds = 20000;
        this.rarity = 3;
        this.icon = IconType.COPPER_INGOT;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);

        this.description = "Игрок получает (values1) меди при первом спавне.#и (values2) за каждое убийство противника.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 16 + level * 2;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 16 + level * 4;
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {
        ItemStack reward = Main.getSpawnerType("bronze").getStack();
        reward.setAmount( calculateIntValue3(level) );
        killer.getInventory().addItem(reward);
    }

//    @Override
//    public void playerRespawn(int level, GamePlayer gamePlayer) {
//
//        ItemStack kit = Main.getSpawnerType("bronze").getStack();
//        kit.setAmount( calculateIntValue2(level) );
//        gamePlayer.player.getInventory().addItem(kit);
//
//    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        ItemStack kit = Main.getSpawnerType("bronze").getStack();
        kit.setAmount( calculateIntValue1(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }
}
