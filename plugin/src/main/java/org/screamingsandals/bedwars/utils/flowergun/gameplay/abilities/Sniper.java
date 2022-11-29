package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageTarget;

public class Sniper extends Ability implements IAbility {

    public Sniper(){
        this.name = "Снайпер";
        this.id = "SNIPER";
        this.item = Material.IRON_HOE;
        this.rarity = 4;
        this.icon = IconType.IRON_INGOT;
        this.description = "Попадание снарядом по игроку на дистанции минимум#(values2)&7 блоков даст владельцу 1 серебро#Перезарядка: (values1)&7 секунд";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 160 + -20 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 22 + -2 * level;
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
