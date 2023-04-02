package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Starling extends Ability implements IAbility {

    public Starling(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 60).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.ECHO_DUST, 10).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 150).addResource(ResourceType.PROCESSING_UNIT, 8).addResource(ResourceType.ECHO_DUST, 25).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Звёздочка";
        this.id = "starling";
        this.item = Material.GLOWSTONE_DUST;
        this.rarity = 5;
        this.icon = IconType.SPEED;

        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.RANGER);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "При получении урона на уровне здоровья#менее 50% от максимального, игрок получит эффекты#Скорости 2 и Свечения на (values1) секунд.";
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
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        //if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

//        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {

            double max = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double current = victim.getHealth();

            if ( current <= max/2 ) {
                playFXSpeed(victim,1);
                notifyPlayerOnAbilityActivation(victim);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level), 1));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, calculateIntValue1(level), 0));
            }
        }

    }

}
