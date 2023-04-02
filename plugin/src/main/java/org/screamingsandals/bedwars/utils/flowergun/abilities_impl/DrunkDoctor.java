package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;

public class DrunkDoctor extends Ability implements IAbility {


    public DrunkDoctor(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 100).addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.THICK_SLIME, 20).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.QUARTZ, 200).addResource(ResourceType.POLISHED_RUBY, 70).addResource(ResourceType.THICK_SLIME, 50).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Пьяный Доктор";
        this.id = "drunkdoctor";
        this.item = Material.GLISTERING_MELON_SLICE;
        this.rarity = 4;
        this.icon = IconType.REGENERATION;

        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.SCOUT);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Попадание стрелами лечения по блокам наложит#соотвествующий эффект на ближайшего союзника в радиусе#(values1) блоков и даст игроку +(values2)%#к скорости передвижения на 5 секунд.";

    }

    @Override
    public int calculateIntValue1(int level) {
        return 4 + level;
    }


    @Override
    public int calculateIntValue2(int level) {
        return 20 + 8 * level;
    }


    @Override
    public void projectileHit(int level, Player shooter, ProjectileHitEvent event) {

        if ( event.isCancelled() ) return;

        if ( event.getHitBlock() != null && event.getEntity() instanceof Arrow) {

            Arrow arrow = (Arrow) event.getEntity();
            PotionEffectType effect = arrow.getBasePotionData().getType().getEffectType();
            if (effect == null) return;

            GamePlayer gTarget = findClosestAlly(shooter, arrow.getLocation(), calculateIntValue1(level));

            if (gTarget == null) return;

            if ( effect.equals(PotionEffectType.HEAL)) {
                Ability.healHealth(shooter, gTarget.player, ((arrow.getBasePotionData().isUpgraded()? 1 : 0 ) + 1 ) * FlowerUtils.healingArrowHealPerLevel );
                arrow.remove();
                Ability.playLineFX(arrow.getLocation(), gTarget.player.getLocation().add(0,1,0), Color.fromRGB(240, 10, 36));
            }
            else if (effect.equals(PotionEffectType.REGENERATION)) {
                Ability.healRegen(shooter, gTarget.player, new PotionEffect(PotionEffectType.REGENERATION, FlowerUtils.regenArrowDuration, arrow.getBasePotionData().isUpgraded()? 1 : 0, false, false) );
                arrow.remove();
                Ability.playLineFX(arrow.getLocation(), gTarget.player.getLocation().add(0,1,0), Color.fromRGB(232, 56, 162));
            } else return;

            GamePlayer gShooter = Main.getPlayerGameProfile(shooter);
            gShooter.addCustomStatusEffect(new CustomStatusEffect("drunk_doctor_speed", gShooter, gShooter, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier( 0, 0, calculateIntValue2(level) * 0.01), 100, false));




        }

    }


}
