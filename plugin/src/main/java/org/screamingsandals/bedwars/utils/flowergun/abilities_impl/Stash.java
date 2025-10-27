package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;

public class Stash extends Ability implements IAbility {

    public Stash(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 5).addResource(ResourceType.COPPER, 25).addResource(ResourceType.IRON, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 15).addResource(ResourceType.COPPER, 30).addResource(ResourceType.IRON, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Заначка";
        this.id = "stash";
        this.item = Material.IRON_INGOT;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Игрок получит (values1) серебра в начале#игры и +1 за каждое убийство.";

    }


    @Override
    public int calculateIntValue1(int level) {
        return 2 + 1 * level;
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        ItemStack kit = Main.getSpawnerType("iron").getStack();
        kit.setAmount( calculateIntValue1(level) );
        gamePlayer.player.getInventory().addItem(kit);

    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {

        notifyPlayerOnAbilityActivation(killer);

        ItemStack kit = Main.getSpawnerType("iron").getStack();
//        kit.setAmount( 1 );
        killer.getInventory().addItem(kit);

    };

}
