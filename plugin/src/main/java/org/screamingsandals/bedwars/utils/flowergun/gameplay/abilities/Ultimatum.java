package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageTarget;

public class Ultimatum extends Ability implements IAbility {

    public Ultimatum(){
        this.name = "Ультиматум";
        this.id = "ultimatum";
        this.icon = Material.CHAIN;
        this.rarity = 5;
        this.description = "Раз в (values1)&7 секунд следующая ближняя атака#по игроку с уроном &f6 ед.&7 и выше при удержании SHIFT#наложит на обоих игроков эффект Замедление 4#и отключит прыжки на (values2)&7 секунд";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 320 + -40 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 40 + 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if ( event.getFinalDamage() > 0 && attacker.isSneaking() && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.MELEE)

        if (Main.isPlayerInGame(attacker)) {

            Player victim = (Player) event.getEntity();

            notifyPlayerOnAbilityActivation(attacker);

            playFXCrowdControl(attacker,3);
            playFXCrowdControl(victim,3);

            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, calculateIntValue2(level), 3));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue2(level), 128));
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, calculateIntValue2(level), 3));
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue2(level), 128));

            this.isOnCooldown = true;
            Player finalAttacker = attacker;
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                notifyPlayerOnCooldownEnd(finalAttacker);
                this.isOnCooldown = false;
            },calculateIntValue1(level));

        }

    }

}
