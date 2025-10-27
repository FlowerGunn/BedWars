package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Butcher extends Ability implements IAbility {

    public Butcher(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.REINFORCED_BONE_PLATE, 5).addResource(ResourceType.MICROSCHEMA, 1).addResource(ResourceType.NETHERITE_SCRAP, 10).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.REINFORCED_BONE_PLATE, 15).addResource(ResourceType.MICROSCHEMA, 6).addResource(ResourceType.NETHERITE_SCRAP, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Мясник";
        this.id = "butcher";
        this.item = Material.DIAMOND_AXE;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.VIKING);
        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.TANK);

        this.description = "Полностью заряженные удары топором#наносят (values1)% от значения брони игрока#в виде дополнительного урона.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) { return 10 + 2 * level; }



    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());

        if (!(event.getEntity() instanceof LivingEntity)) return;

        if (event.isCancelled()) return;

        if (Main.isPlayerInGame(attacker)) {
            if ( FlowerUtils.isPlayersWeaponFullyCharged(attacker) && FlowerUtils.axes.contains(attacker.getInventory().getItemInMainHand().getType())) {

                double armour = attacker.getAttribute(Attribute.GENERIC_ARMOR).getValue();
                Main.getInstance().getLogger().info("Armor " + armour + " - " + (armour * calculateIntValue1(level) * 0.01));
                compoundValueModifier.addDouble(armour * calculateIntValue1(level) * 0.01);
                playFXDamage((LivingEntity) event.getEntity(), 1);
            }
        }
    }

}
