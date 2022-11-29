package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageType;

import java.util.Random;

public class MagmaSkin extends Ability implements IAbility {

    public MagmaSkin(){
        this.name = "Магмовая кожа";
        this.id = "magmaskin";
        this.item = Material.MAGMA_BLOCK;
        this.rarity = 3;
        this.icon = IconType.FIRE_RESISTANCE;
        this.description = "При получении урона от огня у вас#есть шанс в (values2)&7% получить эффект#Огнестойкости и Спешки 1 на (values1)&7 секунд.#Перезарядка: (values1)&7 секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 140 + 60 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 30 + 10 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }





    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (damageInstance.damageType == DamageType.FIRE) {

                Random random = new Random();
                int chance = calculateIntValue2(level);

                if ( random.nextInt(100) < chance ) {

                    victim.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, calculateIntValue1(level), 0, false, false));
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 0, false, false));

                    playFXDefensiveUtility(victim, 2);
                    notifyPlayerOnAbilityActivation(victim);

                    this.isOnCooldown = true;
                    Player user = victim;
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                        notifyPlayerOnCooldownEnd(user);
                        this.isOnCooldown = false;
                    },calculateIntValue1(level));

                }
            }
        }
    }
}
