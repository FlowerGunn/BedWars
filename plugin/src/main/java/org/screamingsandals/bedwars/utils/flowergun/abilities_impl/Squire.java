package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Squire extends Ability implements IAbility {

    public Squire(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.COPPER_PLATE, 10).addResource(ResourceType.LEATHER, 30).addResource(ResourceType.SILK_COCOON, 50).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.COPPER_PLATE, 20).addResource(ResourceType.LEATHER, 100).addResource(ResourceType.SILK_COCOON, 120).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Оруженосец";
        this.id = "squire";
        this.item = Material.ARROW;
        this.rarity = 3;
        this.icon = IconType.IRON_INGOT;
        this.description = "Убийства или помощи в убийстве#принесут игроку (values2) стрел.#Перезарядка: (values1) секунд";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 480 + -80 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 4 + level * 3;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

        if (this.isOnCooldown) return;

        ItemStack reward = new ItemStack(Material.ARROW);
        reward.setAmount(calculateIntValue2(level));
        killer.getInventory().addItem(reward);
        playFXItemGained(killer, 1);
        notifyPlayerOnAbilityActivation(killer);

        this.isOnCooldown = true;
        Player finalAttacker = killer;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            notifyPlayerOnCooldownEnd(finalAttacker);
            this.isOnCooldown = false;
        },calculateIntValue1(level));

        notifyPlayerOnAbilityActivation(killer);

    };

    @Override
    public void playerKillAssist(int level, Player killer, Player victim, Player assistant) {

        if (this.isOnCooldown) return;

        ItemStack reward = new ItemStack(Material.ARROW);
        reward.setAmount(calculateIntValue2(level));
        assistant.getInventory().addItem(reward);
        playFXItemGained(assistant, 1);
        notifyPlayerOnAbilityActivation(assistant);

        this.isOnCooldown = true;
        Player finalAttacker = assistant;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            notifyPlayerOnCooldownEnd(finalAttacker);
            this.isOnCooldown = false;
        },calculateIntValue1(level));

        notifyPlayerOnAbilityActivation(assistant);

    };

}
