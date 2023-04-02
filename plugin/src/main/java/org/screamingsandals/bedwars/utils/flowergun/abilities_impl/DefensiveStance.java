package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class DefensiveStance extends Ability implements IAbility {

    public DefensiveStance(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.LAPIS_SHEET, 10).addResource(ResourceType.POLISHED_RUBY, 15).addResource(ResourceType.THICK_SLIME, 10).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.LAPIS_SHEET, 30).addResource(ResourceType.BONE_PLATE, 50).addResource(ResourceType.THICK_SLIME, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Защитная Стойка";
        this.id = "defensivestance";
        this.item = Material.ANVIL;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;

        this.abilityCategories.add(AbilityCategory.TANK);
        this.abilityCategories.add(AbilityCategory.MADMAN);

        this.description = "Игрок получает на (values1)% меньше урона,#когда находится под эффектом Замедления,#Медленного копания или Темноты.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 10 + 3 * level;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
//        if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

        if (event.isCancelled()) return;

//        GamePlayer gVictim = Main.getPlayerGameProfile(victim);
//        gVictim.addCustomStatusEffect(new CustomStatusEffect("test", gVictim, gVictim, Attribute.GENERIC_MAX_HEALTH, new CompoundValueModifier(4, 0, 0), 200, false));

//        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (victim.hasPotionEffect(PotionEffectType.SLOW) || victim.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || victim.hasPotionEffect(PotionEffectType.DARKNESS)) {
                playFXDefensiveUtility(victim,1);
                notifyPlayerOnAbilityActivation(victim);
                compoundValueModifier.addExp(-1 * calculateIntValue1(level) / 100.0);
            }
        }

    }

}
