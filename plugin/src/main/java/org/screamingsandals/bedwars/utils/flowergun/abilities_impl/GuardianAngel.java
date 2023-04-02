package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
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

public class GuardianAngel extends Ability implements IAbility {

    public GuardianAngel(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 10).addResource(ResourceType.EMERALD_PLATE, 5).addResource(ResourceType.RUBY, 50).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 30).addResource(ResourceType.EMERALD_PLATE, 15).addResource(ResourceType.RUBY, 100).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Ангел-Хранитель";
        this.id = "guardianangel";
        this.item = Material.SHIELD;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;

        this.abilityCategories.add(AbilityCategory.HEALER);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);
        this.abilityCategories.add(AbilityCategory.SUPPORT);

        this.description = "Лечение союзников даст им +1 к броне#на (values1) секунд, а также даст игроку 1 заряд Веры.#Максимальное количество зарядов - (values2)#При полных зарядах Веры игрок получает#+2 к макс. здоровью до конца игры.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 + 40 * level;
    }
    @Override
    public int calculateIntValue2(int level) {
        return 40 - 5 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        this.maxStacks = calculateIntValue2(level);

    }

    @Override
    public void healPlayer(int level, Player healer, Player target, CompoundValueModifier compoundValueModifier) {

        GamePlayer gHealer = Main.getPlayerGameProfile(healer);
        GamePlayer gTarget = Main.getPlayerGameProfile(target);
        gTarget.addCustomStatusEffect(new CustomStatusEffect("guardian_angel_armor", gTarget, gHealer, Attribute.GENERIC_ARMOR, new CompoundValueModifier(1, 0, 0), calculateIntValue1(level), false));
        playFXDefensiveUtility(target, 1);

        if (stacks < maxStacks) {
            stacks++;
            if ( stacks >= maxStacks ) {
                notifyPlayerOnAbilityActivation(healer);
                gHealer.addCustomStatusEffect(new CustomStatusEffect("guardian_angel_max_health", gHealer, gHealer, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(2, 0, 0), 100, true));
                playFXDefensiveUtility(target, 3);
            } else {
                notifyPlayerOnStackCount(healer);
            }
        }
    };


}
