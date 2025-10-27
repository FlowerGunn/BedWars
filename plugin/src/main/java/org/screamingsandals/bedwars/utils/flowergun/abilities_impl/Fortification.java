package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

import java.util.List;

public class Fortification extends Ability implements IAbility {

    public Fortification(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.STEEL_PLATE, 4).addResource(ResourceType.RUBY_LAMP, 3).addResource(ResourceType.REINFORCED_BONE_PLATE, 4).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.STEEL_PLATE, 10).addResource(ResourceType.RUBY_LAMP, 8).addResource(ResourceType.REINFORCED_BONE_PLATE, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Фортификация";
        this.id = "fortification";
        this.item = Material.CHAINMAIL_CHESTPLATE;
        this.rarity = 5;
        this.icon = IconType.ABSORPTION;

        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.TANK);

        this.description = "При получении физического урона#от противника игрок получит 0.25 ед.#щита за каждого игрока в радиусе 10 блоков.#Перезарядка: (values1) секунд.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 220 - 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        if (this.isOnCooldown) return;


        if ( damageInstance.damageType == DamageType.PHYSICAL && damageInstance.damageSource == DamageSource.PLAYER && event.getFinalDamage() > 0) {

            List<Entity> entities = victim.getNearbyEntities( 15, 15, 15 );
            int amount = 0;
            for ( Entity entity : entities ) {
                if ( entity instanceof Player && entity.getLocation().distance(victim.getLocation()) <= 10 ) amount++;
            }

            if ( amount == 0 ) return;

            playFXDefensiveUtility(victim,1);
            healOverhealth(victim, victim, 0.25 + amount * 0.25);
            notifyPlayerOnAbilityActivation(victim);


            putOnCooldown(victim,calculateIntValue1(level));

        }


    }


}
