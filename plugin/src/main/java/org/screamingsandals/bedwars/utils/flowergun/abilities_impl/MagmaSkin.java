package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.Random;

public class MagmaSkin extends Ability implements IAbility {

    public MagmaSkin(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 10).addResource(ResourceType.STEEL_INGOT, 15).addResource(ResourceType.BLAZE_POWDER, 40).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 30).addResource(ResourceType.STEEL_INGOT, 40).addResource(ResourceType.BLAZE_POWDER, 80).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Магмовая кожа";
        this.id = "magmaskin";
        this.item = Material.MAGMA_BLOCK;
        this.rarity = 3;
        this.icon = IconType.FIRE_RESISTANCE;
        this.description = "При получении урона от огня у игрока#есть шанс в (values2)&7% получить эффект#Огнестойкости и Спешки 5 на (values1) секунд.#Перезарядка: (values1) секунд.";
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
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 4, false, false));

//                    playFXSpeed(victim, 1);
                    playFXDefensiveUtility(victim, 1);
                    playFXFire(victim, 1);
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
