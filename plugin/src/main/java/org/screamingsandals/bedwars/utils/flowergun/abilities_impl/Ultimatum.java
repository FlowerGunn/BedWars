package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Ultimatum extends Ability implements IAbility {

    public Ultimatum(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 15).addResource(ResourceType.CHAIN, 100).addResource(ResourceType.ANOMALY, 10).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 30).addResource(ResourceType.CHAIN, 200).addResource(ResourceType.ANOMALY, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Ультиматум";
        this.id = "ultimatum";
        this.item = Material.CHAIN;
        this.rarity = 5;
        this.icon = IconType.SLOW;
        this.description = "Раз в (values1) секунд следующая ближняя атака#по противнику с уроном 3ед. и выше при удержании SHIFT#наложит на обоих игроков эффект Замедление 4#и отключит прыжки на (values2) секунд.";
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

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() >= 3 && attacker.isSneaking() && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.MELEE)

        if (Main.isPlayerInGame(attacker)) {

            Player victim = (Player) event.getEntity();

            notifyPlayerOnAbilityActivation(attacker);

            playFXSlow(attacker,3);
            playFXSlow(victim,3);

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
