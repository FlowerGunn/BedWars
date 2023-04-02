package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

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

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "При убийстве противника одно обычное яблоко#в инвентаре игрока превратится в золотое.#Перезарядка: (values1) секунд#Каждая атака по противнику даст#игроку одно яблоко.";
    }


    @Override
    public int calculateIntValue1(int level) {
        return 20 * 20 - 5 * 20 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event) {

        if (this.isOnCooldown) return;

        Inventory inventory = killer.getInventory();

        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                if (itemStack.getType() == Material.APPLE) {
                    ItemStack stolen = new ItemStack(Material.GOLDEN_APPLE);
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    stolen.setAmount(1);
                    inventory.addItem(stolen);
                    notifyPlayerOnAbilityActivation(killer);
                    playFXItemGained(killer, 3);

                    this.isOnCooldown = true;
                    Player finalAttacker = killer;
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                        notifyPlayerOnCooldownEnd(finalAttacker);
                        this.isOnCooldown = false;
                    }, calculateIntValue1(level));

                    break;
                }
            }
        }

    }

        @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(attacker)) {
            if (damageInstance.damageTarget == DamageTarget.PLAYER && event.getFinalDamage() > 0) {

                Inventory inventory = attacker.getInventory();
                ItemStack apple = new ItemStack(Material.APPLE);
                inventory.addItem(apple);
                playFXItemGained(attacker, 1);

            }
        }

    }

}
