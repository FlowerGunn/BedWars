package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Sniper extends Ability implements IAbility {

    public Sniper(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.EMERALD_DUST, 8).addResource(ResourceType.ICE_POWDER, 25).addResource(ResourceType.COMPONENT1, 2).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.EMERALD_DUST, 20).addResource(ResourceType.ICE_POWDER, 50).addResource(ResourceType.COMPONENT1, 8).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Снайпер";
        this.id = "sniper";
        this.item = Material.IRON_HOE;
        this.rarity = 4;
        this.icon = IconType.IRON_INGOT;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Попадание снарядом по противнику на дистанции минимум#(values2) блоков даст игроку 4 серебра.#Перезарядка: (values1) секунд";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 160 + -20 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 23 + -3 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());


        if (this.isOnCooldown) return;

        if ( event.getFinalDamage() > 0 && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.PROJECTILE)

            if (Main.isPlayerInGame(attacker)) {

                Entity victim = event.getEntity();

                if ( attacker.getLocation().distance(victim.getLocation()) < calculateIntValue2(level) ) return;

                ItemStack reward = Main.getSpawnerType("iron").getStack();
                reward.setAmount(4);
                attacker.getInventory().addItem(reward);
                playFXItemGained(attacker, 1);
                notifyPlayerOnAbilityActivation(attacker);

                this.isOnCooldown = true;
                Player finalAttacker = attacker;
                Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                    notifyPlayerOnCooldownEnd(finalAttacker);
                    this.isOnCooldown = false;
                },calculateIntValue1(level));

            }

    }

}
