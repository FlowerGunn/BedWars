package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Evasion extends Ability implements IAbility {

    public Evasion(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 15).addResource(ResourceType.GOLD_SHEET, 3).addResource(ResourceType.SEMICONDUCTOR, 6).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 30).addResource(ResourceType.GOLD_SHEET, 10).addResource(ResourceType.SEMICONDUCTOR, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Уклонение";
        this.id = "evasion";
        this.item = Material.LEATHER_BOOTS;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Игрок получает на (values1)% меньше урона#от дальних атак. Попадание снарядами по противникам#на расстоянии 8 блоков от игрока подбросит игрока#вверх и даст эффект Медленного Падения 1 на 5 сек.#Перезарядка: (values2) cекунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 5 * level;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 7 + level;
    }

//    @Override
//    public int calculateIntValue2(int level) {
//        return 40 + 10 * level;
//    }
//
//    @Override
//    public String formatValue2(int level) {
//        return "" + FlowerUtils.singleDecimal.format( calculateIntValue1(level) / 20.0 );
//    }


    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if ((damageInstance.damageRelay == DamageRelay.PROJECTILE)) {
                playFXDefensiveUtility(victim, 1);
                compoundValueModifier.addExp(calculateIntValue1(level) * -0.01);
            }
        }

    }

    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());

        if ( event.isCancelled() ) return;
        if ( isOnCooldown ) return;

        if ( event.getFinalDamage() > 0 && damageInstance.damageTarget == DamageTarget.PLAYER && damageInstance.damageRelay == DamageRelay.PROJECTILE)

            if (Main.isPlayerInGame(attacker)) {

                Entity victim = event.getEntity();

                if ( attacker.getLocation().distance(victim.getLocation()) > 8 ) return;

                attacker.setVelocity(new Vector(0, 1.2, 0));
                attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0));
                notifyPlayerOnAbilityActivation(attacker);
                putOnCooldown(attacker, calculateIntValue2(level) * 20);

            }

    }

}
