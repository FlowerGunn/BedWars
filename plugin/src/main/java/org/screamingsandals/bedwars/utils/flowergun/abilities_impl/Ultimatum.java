package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
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

        this.abilityCategories.add(AbilityCategory.MADMAN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Раз в (values1) секунд следующая полностью заряженная#ближняя атака по противнику при удержании SHIFT#наложит на обоих игроков эффект Замедление 4 и привяжет#противника к текущему месту на (values2) секунд.#Противник не может покинуть место#привязки в радиусе 3ёх блоков.";
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

        if ( FlowerUtils.isPlayersWeaponFullyCharged(attacker) && attacker.isSneaking() && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.MELEE)

        if (Main.isPlayerInGame(attacker)) {

            Player victim = (Player) event.getEntity();

            notifyPlayerOnAbilityActivation(attacker);

            playFXSlow(attacker,3);
            playFXSlow(victim,3);

            int ticksDuration = calculateIntValue2(level);

            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticksDuration, 3));
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticksDuration, 3));

            Location tiedTo = victim.getLocation().clone();
            double radius = 3.0;

            new BukkitRunnable() {

                int ticks = 0;
                @Override
                public void run() {
                    if ( ticks > ticksDuration ) this.cancel();

                    if ( victim.getLocation().distance(tiedTo) > radius ) {
                        victim.setVelocity( tiedTo.toVector().subtract(victim.getLocation().toVector()).multiply(0.1) );
                        Ability.playLineFX(victim.getLocation(), tiedTo, Color.fromRGB(161, 34, 34));
                        playFXSlow(victim, 3);
                    } else {
                        Ability.playLineFX(victim.getLocation(), tiedTo, Color.fromRGB(54, 54, 54));
                    }

                    ticks += 5;
                }
            }.runTaskTimer(Main.getInstance(), 0L, 5L);

            putOnCooldown(attacker,calculateIntValue1(level));

        }

    }

}
