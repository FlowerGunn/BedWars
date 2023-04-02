package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Evasion extends Ability implements IAbility {

    public Evasion(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 15).addResource(ResourceType.GOLD_SHEET, 3).addResource(ResourceType.SEMICONDUCTOR, 6).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.MAGIC_SILK, 30).addResource(ResourceType.GOLD_SHEET, 10).addResource(ResourceType.SEMICONDUCTOR, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Уклонение";
        this.id = "evasion";
        this.item = Material.LEATHER_BOOTS;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;

        this.abilityCategories.add(AbilityCategory.TANK);
        this.abilityCategories.add(AbilityCategory.SCOUT);

        this.description = "Игрок получает на (values1)% меньше урона#от дальних атак. Если игрок не имеет надетого#нагрудника, то эффект усиливается в 3 раза.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 26 + 2 * level;
    }

    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (event.getFinalDamage() > 0 && (damageInstance.damageRelay == DamageRelay.PROJECTILE)) {
                playFXDefensiveUtility(victim, 1);
                if (victim.getInventory().getChestplate() == null)
                compoundValueModifier.addExp(calculateIntValue1(level) * 0.01 * 3);
                else
                compoundValueModifier.addExp(calculateIntValue1(level) * 0.01);
            }
        }

    }

}
