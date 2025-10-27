package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.Random;

public class ArrowRain extends Ability implements IAbility {

    private double radius;

    public ArrowRain(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 200).addResource(ResourceType.SEMICONDUCTOR, 6).addResource(ResourceType.REINFORCED_BONE_PLATE, 5).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 600).addResource(ResourceType.SEMICONDUCTOR, 12).addResource(ResourceType.REINFORCED_BONE_PLATE, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Дождь из Стрел";
        this.id = "arrowrain";
        this.item = Material.ARROW;
        this.rarity = 5;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.ARCHER);
        this.abilityCategories.add(AbilityCategory.RANGER);
        this.abilityCategories.add(AbilityCategory.GUARDIAN);

        this.description = "Попадание обычными стрелами по блокам#c удержанием SHIFT создаст (values1) стрел#10 блоков над местом приземления стрелы#и разбросом в 3 блока.";//#Перезарядка: (values2) секунд.";

        this.radius = 3.0;

    }

    @Override
    public int calculateIntValue1(int level) {
        return 8 + 4 * level;
    }
//
//    @Override
//    public int calculateIntValue2(int level) {
//        return 260 - 60 * level;
//    }
//
//    @Override
//    public String formatValue2(int level) {
//        return "" + calculateIntValue2(level) / 20;
//    }



    @Override
    public void projectileHit(int level, Player shooter, ProjectileHitEvent event) {

//        if ( this.isOnCooldown ) return;
        if ( event.isCancelled() ) return;
        if ( !shooter.isSneaking() ) return;
        if ( event.getHitBlock() != null && ( event.getEntity() instanceof Arrow ) ) {

            Arrow arrow = (Arrow) event.getEntity();
            PotionEffectType effect = arrow.getBasePotionData().getType().getEffectType();
            if (effect != null) return;
            if ( !arrow.isCritical() ) return;

            Random random = new Random();

            for ( int i = 0; i < calculateIntValue1(level); i++ ) {
                
//                Location newArrowLocation = arrow.getLocation().clone().add(random.nextDouble(radius * -1, radius), 10, random.nextDouble(radius * -1, radius));
                Location newArrowLocation = arrow.getLocation().clone().add(random.nextDouble(radius * -1, radius), 10, random.nextDouble(radius * -1, radius));
                Arrow newArrow = (Arrow) shooter.getWorld().spawnEntity( newArrowLocation, EntityType.ARROW, true);
                newArrow.setShooter(shooter);
                newArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                Bukkit.getConsoleSender().sendMessage("arrow damage " + arrow.getDamage());
                newArrow.setVelocity( new Vector( 0, -1, 0 ));
                newArrow.setDamage(arrow.getDamage() * 2.5);
                newArrow.setCritical(arrow.isCritical());
                newArrow.setKnockbackStrength(arrow.getKnockbackStrength());
                newArrow.setFireTicks(arrow.getFireTicks());
                newArrow.setPierceLevel(arrow.getPierceLevel());
                newArrow.setMetadata("fakearrow", new FixedMetadataValue(Main.getInstance(), "true"));

            }

            notifyPlayerOnAbilityActivation(shooter);
//            this.isOnCooldown = true;
//            Player user = shooter;
//            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
//                notifyPlayerOnCooldownEnd(user);
//                this.isOnCooldown = false;
//            },calculateIntValue2(level));

        }

    }


}
