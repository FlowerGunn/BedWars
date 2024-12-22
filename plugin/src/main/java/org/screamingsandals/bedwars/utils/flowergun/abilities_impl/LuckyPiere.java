package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class LuckyPiere extends Ability implements IAbility {

    public LuckyPiere(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.RUBY_LAMP, 6).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.EMERALD_PLATE, 16).addResource(ResourceType.RUBY_LAMP, 12).addResource(ResourceType.PROCESSING_UNIT, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Счастливчик";
        this.id = "luckypiere";
        this.item = Material.EMERALD;
        this.rarity = 5;
        this.icon = IconType.EMERALD;

        this.abilityCategories.add(AbilityCategory.ECONOMIST);
        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.SCOUT);

        this.description = "При получении урона от противников, когда#у игрока меньше 30% максимального здоровья игрок#излечится на (values3) ед. и получит 1 изумруд.#Перезарядка: (values2) минуты.";
        this.isOnCooldown = false;
    }

//    @Override
//    public int calculateIntValue1(int level) {
//        return 40 - 10 * level;
//    }
//
//    @Override
//    public String formatValue1(int level) {
//        return FlowerUtils.singleDecimal.format(calculateIntValue1(level) / 20.0);
//    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public int calculateIntValue2(int level) {
        return 20*60*4 - 20*30 * (level - 1);
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0 / 60.0);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public double calculateDoubleValue1(int level) {
        return 1 + 0.5 * level;
    }
    @Override
    public String formatValue3(int level) {
        return FlowerUtils.singleDecimal.format(calculateDoubleValue1(level) );
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if (event.isCancelled()) return;

        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;

        if (Main.isPlayerInGame(victim)) {
            if (event.getFinalDamage() > 0 && damageInstance.damageSource == DamageSource.PLAYER) {
                double max = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                double current = victim.getHealth();

                if ( current <= max * 0.3 ) {

//                    playFXDefensiveUtility(victim, 3);
                    notifyPlayerOnAbilityActivation(victim);

//                    victim.getLocation().getWorld().playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.4F, 0.5F);
//                    victim.getLocation().getWorld().playSound(victim.getLocation(), Sound.BLOCK_BELL_USE, 0.2F, 1.5F);
//
//                    event.setCancelled(true);

//                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, calculateIntValue1(level), 0, false, false));
                    Ability.healHealth(victim, victim, calculateDoubleValue1(level));
                    Ability.playFXHealing(victim,victim,2);
                    ItemStack kit = Main.getSpawnerType("emerald").getStack();
                    victim.getInventory().addItem(kit);
                    playFXItemGained(victim, 3);

                    putOnCooldown(victim,calculateIntValue2(level));
                }
            }
        }
    }

}
