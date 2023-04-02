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
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Compassion extends Ability implements IAbility {

    public Compassion(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.LAPIS, 20).addResource(ResourceType.ANOMALY, 4).addResource(ResourceType.RUBY, 50).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.LAPIS, 80).addResource(ResourceType.ANOMALY, 10).addResource(ResourceType.RUBY, 150).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Сострадание";
        this.id = "compassion";
        this.item = Material.BREAD;
        this.rarity = 3;
        this.icon = IconType.REGENERATION;

        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Исходящее лечение игрока увеличено на +(values1)%,#а принимаемое уменьшено на -(values2)%.#При смерти игрока ближайший#союзник лечится на (values3) ед.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 5 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 35 - 5 * level;
    }

    @Override
    public int calculateIntValue3(int level) {
        return 3 + 1 * level;
    }



    @Override
    public void healPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        compoundValueModifier.addExp(calculateIntValue1(level) * 0.01);
    };

    @Override
    public void healedByPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        compoundValueModifier.addExp(calculateIntValue2(level) * -0.01);
    };


    @Override
    public void playerDeath(int level, Player victim, Player killer, PlayerDeathEvent event) {

        if (event.isCancelled()) return;

        GamePlayer gTarget = findClosestAlly(victim, 15);

        if (gTarget == null) return;

        healHealth(victim, gTarget.player, calculateIntValue3(level));
        notifyPlayerOnAbilityActivation(victim);

    }


    }
