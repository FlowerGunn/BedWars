package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageTarget;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageType;

public class FrostKiss extends Ability implements IAbility {

    public FrostKiss(){
        this.name = "Ледяной Поцелуй";
        this.id = "frostkiss";
        this.icon = Material.ICE;
        this.rarity = 3;
        this.description = "Раз в (values1)&7 секунд попадание#снежком наложит на цель эффект#Замедление 3 на (values2)&7 секунд";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 260 + -20 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 40 + 10 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0);
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if ( event.getFinalDamage() > 0 && damageInstance.damageType == DamageType.SNOWBALL)

        if (Main.isPlayerInGame(attacker)) {

            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, calculateIntValue2(level), 2));
            playFXCrowdControl(victim,1);
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
