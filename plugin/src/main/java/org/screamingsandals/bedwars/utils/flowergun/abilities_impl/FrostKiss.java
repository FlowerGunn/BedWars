package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class FrostKiss extends Ability implements IAbility {

    public FrostKiss(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.IRON, 30).addResource(ResourceType.CRYO_SLIME, 10).addResource(ResourceType.ICE_POWDER, 40).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.IRON, 70).addResource(ResourceType.CRYO_SLIME, 20).addResource(ResourceType.ICE_POWDER, 80).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Ледяной Поцелуй";
        this.id = "frostkiss";
        this.item = Material.ICE;
        this.rarity = 3;
        this.icon = IconType.SLOW;

        this.abilityCategories.add(AbilityCategory.SNOWMAN);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.RANGER);

        this.description = "Раз в (values1) секунд попадание#снежком наложит на противника эффект#Замедление 5 на (values2) секунд.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 260 + -40 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 40 + 10 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.singleDecimal.format(calculateIntValue2(level) / 20.0);
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() > 0 && damageInstance.damageType == DamageType.SNOWBALL)

        if (Main.isPlayerInGame(attacker)) {

            LivingEntity victim = (LivingEntity) event.getEntity();
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, calculateIntValue2(level), 4));
            playFXSlow(victim,1);
            notifyPlayerOnAbilityActivation(attacker);

            this.isOnCooldown = true;
            Player finalAttacker = attacker;
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                notifyPlayerOnCooldownEnd(finalAttacker);
                this.isOnCooldown = false;
            },calculateIntValue1(level));

        }

    }

}
