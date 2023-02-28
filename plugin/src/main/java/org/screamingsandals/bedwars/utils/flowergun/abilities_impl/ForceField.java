package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class ForceField extends Ability implements IAbility {

    public ForceField(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 15).addResource(ResourceType.BOLT, 20).addResource(ResourceType.MAGIC_SILK, 30).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE_PLATE, 50).addResource(ResourceType.BOLT, 50).addResource(ResourceType.MAGIC_SILK, 60).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Силовое поле";
        this.id = "forcefield";
        this.item = Material.PHANTOM_MEMBRANE;
        this.rarity = 4;
        this.icon = IconType.FAST_DIGGING;
        this.description = "Блокирование атак щитом даст#игроку эффекты Сопротивления 1,#а также Спешки 3 если атака была ближняя#или Скорости 2 если атака была дальняя#на (values1) секунд.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 100 + 20 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }


    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (victim.isBlocking() && event.getFinalDamage() == 0) {
                playFXDefensiveUtility(victim, 1);
                notifyPlayerOnAbilityActivation(victim);
                if ((damageInstance.damageRelay == DamageRelay.PROJECTILE))
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, calculateIntValue1(level), 2));
                    else
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level), 1));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, calculateIntValue1(level), 0));
            }
        }

    }

}
