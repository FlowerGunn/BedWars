package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageTarget;

public class Appleperson extends Ability implements IAbility {

    public Appleperson(){
        this.name = "Яблочник";
        this.id = "appleperson";
        this.item = Material.APPLE;
        this.rarity = 3;
        this.icon = IconType.ABSORPTION;
        this.description = "При убийстве противника одно обычное яблоко#в вашем инвентаре превратится в золотое.#Перезарядка: (values1)&7 секунд#Каждая атака по противнику положит#им в инвентарь одно яблоко";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 70 * 20 - 10 * 20 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

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

                Player victim = (Player) event.getEntity();
                Inventory inventory = victim.getInventory();
                ItemStack apple = new ItemStack(Material.APPLE);
                inventory.addItem(apple);
                playFXItemGained(victim, 1);

            }
        }

    }

}
