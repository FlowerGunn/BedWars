package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.Random;

public class Weightless extends Ability implements IAbility {

    public Weightless(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 20).addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.ANOMALY, 7).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 50).addResource(ResourceType.EMERALD_PLATE, 15).addResource(ResourceType.ANOMALY, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Невесомость";
        this.id = "weightless";
        this.item = Material.FEATHER;
        this.rarity = 5;
        this.icon = IconType.LEVITATION;
        this.description = "При убийстве противника игрок получает случайный эффект:#Прыгучесть 3 или Левитация 1 на (values1) секунд.#При получении игроком любого урона их эффект#левитации будет продлён на 2 секунды.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void playerKill(int level, Player killer, PlayerDeathEvent event) {

        if (this.isOnCooldown) return;

        Random random = new Random();

        if ( random.nextBoolean() ) {
            killer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, calculateIntValue1(level), 0, false, false));
        } else {
            killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, calculateIntValue1(level), 2, false, false));
        }

        playFXSpeed(killer, 2);
        notifyPlayerOnAbilityActivation(killer);

    };

    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {

            if ( victim.hasPotionEffect(PotionEffectType.LEVITATION) ) {
                int duration = victim.getPotionEffect(PotionEffectType.LEVITATION).getDuration();
                victim.addPotionEffect(victim.getPotionEffect(PotionEffectType.LEVITATION).withDuration(duration + 40));
            }

        }
    }
}
