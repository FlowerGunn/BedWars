package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.ArrayList;

public class Appleperson extends Ability implements IAbility {

    public Appleperson(){
        //rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.GOLD, 5).addResource(ResourceType.SILK_COCOON, 100).addResource(ResourceType.THICK_SLIME, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.GOLD, 20).addResource(ResourceType.SILK_COCOON, 200).addResource(ResourceType.THICK_SLIME, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Яблочник";
        this.id = "appleperson";
        this.item = Material.APPLE;
        this.rarity = 3;
        this.icon = IconType.ABSORPTION;
        this.maxStacks = 5;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Каждые 5 убийств или помощей одно обычное яблоко#в инвентаре игрока превратится в золотое.#Съедание яблок восполнит игроку дополнительные#(values1) сытости и (values2) насыщения.";
    }


    @Override
    public int calculateIntValue1(int level) {
        return 2 + level;
    }


    @Override
    public double calculateDoubleValue1(int level) {
        return 1 + 0.2 * level;
    }

    @Override
    public String formatValue2(int level) {
        return "" + FlowerUtils.singleDecimal.format( calculateDoubleValue1(level) );
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {
        progressApples(killer);
    }
    @Override
    public void playerKillAssist(int level, Player killer, Player victim, Player assistant) {
        progressApples(assistant);
//        notifyPlayerOnAbilityActivation(assistant);
    }

    private void progressApples(Player player) {
        this.stacks++;
        if ( this.stacks >= this.maxStacks ) {
            this.stacks = 0;
            Inventory inventory = player.getInventory();

            for (ItemStack itemStack : inventory) {
                if (itemStack != null) {
                    if (itemStack.getType() == Material.APPLE) {
                        ItemStack stolen = new ItemStack(Material.GOLDEN_APPLE);
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        stolen.setAmount(1);
                        inventory.addItem(stolen);
                        notifyPlayerOnAbilityActivation(player);
                        playFXItemGained(player, 3);

                        break;
                    }
                }
            }
        } else notifyPlayerOnStackCount(player);
    }

    @Override
    public void itemConsume(int activeLevel, Player player, PlayerItemConsumeEvent event) {

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(player)) {
            if ( event.getItem().getType() == Material.APPLE ) {
                healFood(player, player, calculateIntValue1(activeLevel), calculateDoubleValue1(activeLevel));
            }
        }
    };

//        @Override
//    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
//
//        if (this.isOnCooldown) return;
//
//        if (event.isCancelled()) return;
//
////        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
//        if (Main.isPlayerInGame(attacker)) {
//            if (damageInstance.damageTarget == DamageTarget.PLAYER && event.getFinalDamage() > 0) {
//
//                Inventory inventory = attacker.getInventory();
//                ItemStack apple = new ItemStack(Material.APPLE);
//                inventory.addItem(apple);
//                playFXItemGained(attacker, 1);
//
//            }
//        }
//
//    }

}
