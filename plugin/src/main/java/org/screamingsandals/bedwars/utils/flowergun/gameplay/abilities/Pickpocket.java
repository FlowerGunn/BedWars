package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageTarget;

public class Pickpocket extends Ability implements IAbility {

    public Pickpocket(){
        this.name = "Карманник";
        this.id = "pickpocket";
        this.item = Material.NAME_TAG;
        this.rarity = 4;
        this.icon = IconType.GOLD_INGOT;
        this.description = "Удары в ближнем бою золотыми#предметами украдут 1 золото из инвентаря#противника раз в (values1)&7 секунд";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 60 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }



    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(attacker)) {
            if (damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.MELEE && attacker.getInventory().getItemInMainHand().getType().toString().contains("GOLD") && event.getFinalDamage() > 0) {
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

                            this.isOnCooldown = true;
                            Player finalAttacker = attacker;
                            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                                notifyPlayerOnCooldownEnd(finalAttacker);
                                this.isOnCooldown = false;
                            },calculateIntValue1(level));

                            break;
                        }
                    }
                }


            }
        }

    }

}
