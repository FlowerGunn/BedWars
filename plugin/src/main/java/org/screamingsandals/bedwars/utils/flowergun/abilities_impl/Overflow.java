package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Overflow extends Ability implements IAbility {

    public Overflow(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.STEEL_INGOT, 10).addResource(ResourceType.RUBY_LAMP, 20).addResource(ResourceType.EMERALD_PLATE, 3).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.STEEL_INGOT, 30).addResource(ResourceType.RUBY_LAMP, 10).addResource(ResourceType.EMERALD_PLATE, 10).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Переполнение";
        this.id = "overflow";
        this.item = Material.REDSTONE;
        this.rarity = 5;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Исходящее лечение игрока увеличено на +15%.#Лечение цели при здоровье >80%#увеличит весь их физический урон#на (values1)% на 10 секунд.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 13 + 2 * level;
    }



    @Override
    public void healPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        if ( target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.8 - target.getHealth() < 0.1 ) {
            GamePlayer gTarget = Main.getPlayerGameProfile(target);
            GamePlayer gHealer = Main.getPlayerGameProfile(healer);
            gTarget.addCustomStatusEffect(new CustomStatusEffect("overflow_boost", gTarget, gHealer, CustomStatusEffectType.DAMAGE_DEALT, new DamageInstance( null, DamageTarget.PLAYER, null, DamageType.PHYSICAL), new CompoundValueModifier(0, 0,  calculateIntValue1(level) * 0.01), 100, false));
            playFXBuff(gTarget.player, 1);
            compoundValueModifier.addExp( 0.15);
        }
    };



    }
