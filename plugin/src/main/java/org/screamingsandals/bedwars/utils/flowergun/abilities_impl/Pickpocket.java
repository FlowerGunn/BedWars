package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Pickpocket extends Ability implements IAbility {

    public Pickpocket(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.ECHO_SHARD, 15).addResource(ResourceType.CHAIN, 8).addResource(ResourceType.GOLD_SHEET, 5).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.ECHO_SHARD, 50).addResource(ResourceType.CHAIN, 20).addResource(ResourceType.GOLD_SHEET, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Карманник";
        this.id = "pickpocket";
        this.item = Material.NAME_TAG;
        this.rarity = 4;
        this.icon = IconType.GOLD_INGOT;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Удары в ближнем бою золотыми предметами#украдут 1 золото из инвентаря противника раз#в (values1) секунд. Игрок наносит дополнительные#+(values2) ед. урона золотыми мечами.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 240 - 40 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }
    @Override
    public double calculateDoubleValue1(int level) {
        return 2.0 + 0.5;
    }
    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format( calculateDoubleValue1(level) );
    }


    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {


        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(attacker)) {
            if (this.isOnCooldown) return;
            if (damageInstance.damageRelay == DamageRelay.MELEE && event.getFinalDamage() > 0) {
                if ( attacker.getInventory().getItemInMainHand().getType() == Material.GOLDEN_SWORD ) {
                    compoundValueModifier.addDouble(calculateDoubleValue1(level));
                }
                if ( damageInstance.damageTarget == DamageTarget.PLAYER && attacker.getInventory().getItemInMainHand().getType().toString().contains("GOLD") ) {
                    Player victim = (Player) event.getEntity();
                    Inventory inventory = victim.getInventory();

                    for (ItemStack itemStack : inventory) {
                        if (itemStack != null) {
                            if (itemStack.getType() == Material.GOLD_INGOT) {
                                ItemStack stolen = new ItemStack(itemStack);
                                itemStack.setAmount(itemStack.getAmount() - 1);
                                stolen.setAmount(1);
                                attacker.getInventory().addItem(stolen);
                                notifyPlayerOnAbilityActivation(attacker);
                                playFXItemGained(attacker, 1);

                                putOnCooldown(attacker,calculateIntValue1(level));

                                break;
                            }
                        }
                    }
                }


            }
        }

    }

}
