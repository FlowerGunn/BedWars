package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CustomStatusEffect;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Berserk extends Ability implements IAbility {

    public Berserk(){
        //legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.REINFORCED_BONE_PLATE, 4).addResource(ResourceType.SEMICONDUCTOR, 3).addResource(ResourceType.ANOMALY, 6).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.REINFORCED_BONE_PLATE, 10).addResource(ResourceType.SEMICONDUCTOR, 10).addResource(ResourceType.ANOMALY, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Берсерк";
        this.id = "berserk";
        this.item = Material.GOLDEN_AXE;
        this.rarity = 5;
        this.icon = IconType.SPEED;

        this.abilityCategories.add(AbilityCategory.VIKING);
        this.abilityCategories.add(AbilityCategory.FIGHTER);
        this.abilityCategories.add(AbilityCategory.MANIPULATOR);

        this.description = "Полностью заряженные удары топором не имея щита#во второй руке наносят на (values2)% больше урона,#ускоряют игрока на (values1)% и замедляют#цель на (values1)% на 1.5 сек.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) { return 15 + 5 * level; }


    @Override
    public int calculateIntValue2(int level) { return 13 + 3 * level; }


    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());

        if (!(event.getEntity() instanceof LivingEntity)) return;

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() > 0 ) {
            if (Main.isPlayerInGame(attacker)) {
                if (attacker.getInventory().getItemInOffHand().getType() != Material.SHIELD && FlowerUtils.axes.contains(attacker.getInventory().getItemInMainHand().getType()) && FlowerUtils.isPlayersWeaponFullyCharged(attacker)) {

                    compoundValueModifier.addExp(calculateIntValue2(level) * 0.01);

                    GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);
                    gAttacker.addCustomStatusEffect(new CustomStatusEffect("berserk_speed", gAttacker, gAttacker, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0, calculateIntValue1(level) * 0.01), 30, false));
                    playFXDefensiveUtility(attacker, 1);

                    if (event.getEntity() instanceof Player) {
                        GamePlayer gVictim = Main.getPlayerGameProfile((Player) event.getEntity());
                        gVictim.addCustomStatusEffect(new CustomStatusEffect("berserk_slow", gVictim, gAttacker, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0,  calculateIntValue1(level) * -0.01), 30, false));
                        playFXSlow(gVictim.player, 1);
                    }

                    //playFXCrowdControl((LivingEntity) event.getEntity(), 1);
                }
            }
        }
    }

}
