package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageSource;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class LuckyPiere extends Ability implements IAbility {

    public LuckyPiere(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.RUBY_LAMP, 6).addResource(ResourceType.PROCESSING_UNIT, 3).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.EMERALD_PLATE, 16).addResource(ResourceType.RUBY_LAMP, 12).addResource(ResourceType.PROCESSING_UNIT, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Счастливчик";
        this.id = "luckypiere";
        this.item = Material.IRON_NUGGET;
        this.rarity = 5;
        this.icon = IconType.DAMAGE_RESISTANCE;
        this.description = "При получении урона от противников, когда у игрока#меньше 30% максимального здоровья эта атака#заблокируется, а игрок получит эффект#Сопротивления 4 на (values1) секунд.#Кулдаун: (values2) минуты.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 10 * level;
    }

    @Override
    public String formatValue1(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue1(level) / 20.0);
    }

    @Override
    public int calculateIntValue2(int level) {
        return 20*60*4 - 20*30 * (level - 1);
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0 / 60.0);
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

                    playFXDefensiveUtility(victim, 3);
                    notifyPlayerOnAbilityActivation(victim);

                    victim.getLocation().getWorld().playSound(victim.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.4F, 0.5F);
                    victim.getLocation().getWorld().playSound(victim.getLocation(), Sound.BLOCK_BELL_USE, 0.2F, 1.5F);

                    event.setCancelled(true);

                    victim.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, calculateIntValue1(level), 3, false, false));

                    this.isOnCooldown = true;
                    Player finalAttacker = victim;
                    Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                        notifyPlayerOnCooldownEnd(finalAttacker);
                        this.isOnCooldown = false;
                    }, calculateIntValue2(level));
                }
            }
        }
    }

}
