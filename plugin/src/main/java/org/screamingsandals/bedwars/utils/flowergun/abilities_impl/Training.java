package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Training extends Ability implements IAbility {

    public Training(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.PAPER, 80).addResource(ResourceType.STEEL_INGOT, 15).addResource(ResourceType.BOLT, 10).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.PAPER, 200).addResource(ResourceType.STEEL_INGOT, 30).addResource(ResourceType.BOLT, 25).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Тренировка";
        this.id = "training";
        this.item = Material.WOODEN_SWORD;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.KNIGHT);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Попадания полностью заряженным ударом#меча по противнику, приносит 1 заряд Тренировки.#Максимальное количество зарядов - (values1).#При неполных зарядах Тренировки, удары мечом#по противникам наносят на 1 ед. урона меньше,#при полных - на (values2) ед. больше.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 30 - 5 * level;
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 0.75 + 0.25 * level;
    }

    @Override
    public String formatValue2(int level) {
        return FlowerUtils.doubleDecimal.format( calculateDoubleValue1(level) );
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        this.maxStacks = calculateIntValue1(level);

    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() > 0 && FlowerUtils.isPlayersWeaponFullyCharged(attacker) && damageInstance.damageTarget == DamageTarget.PLAYER) {
            if (Main.isPlayerInGame(attacker)) {
                if ( FlowerUtils.swords.contains(attacker.getInventory().getItemInMainHand().getType()) ) {
                    if ( stacks >= maxStacks ) {
                        compoundValueModifier.addDouble( calculateDoubleValue1(level) );
                    } else {
                        compoundValueModifier.addDouble( -1 );
                        this.stacks++;
                        notifyPlayerOnStackCount(attacker);
                    }
                }
            }
        }
    }

}
