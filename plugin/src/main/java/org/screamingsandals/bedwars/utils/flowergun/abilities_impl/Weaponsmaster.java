package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Weaponsmaster extends Ability implements IAbility {

    private boolean charged = false;
    private int chargedTimestamp = 100000;

    public Weaponsmaster(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.BONE, 80).addResource(ResourceType.CHAIN, 5).addResource(ResourceType.IRON, 20).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.BONE, 200).addResource(ResourceType.CHAIN, 15).addResource(ResourceType.IRON, 45).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Оружейный мастер";
        this.id = "weaponsmaster";
        this.item = Material.DIAMOND_SWORD;
        this.rarity = 3;
        this.icon = IconType.INCREASE_DAMAGE;
        this.description = "После нанесение хотя бы 3ед. урона#в ближнем бою cледующая дальняя атака#в течении (values1) секунд нанесёт#3ед. бонусного урона.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 3 * level;
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
        //Bukkit.getConsoleSender().sendMessage("player deal damage with " + attacker.getName());


//        if (this.isOnCooldown) return;

        if (event.getFinalDamage() >= 3 && damageInstance.damageRelay == DamageRelay.MELEE) {
            if (Main.isPlayerInGame(attacker)) {

                LivingEntity victim = (LivingEntity) event.getEntity();

//                if ( attacker.getLocation().distance(victim.getLocation()) < calculateIntValue2(level) ) return;
//                ItemStack reward = Main.getSpawnerType("iron").getStack();
//                attacker.getInventory().addItem(reward);

                this.charged = true;
                this.chargedTimestamp = Main.getPlayerGameProfile(attacker).getGame().countdown;

//                this.isOnCooldown = true;
//                Player finalAttacker = attacker;
//                Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
//                    notifyPlayerOnCooldownEnd(finalAttacker);
//                    this.isOnCooldown = false;
//                },calculateIntValue1(level));
            }
        }
        else if ( event.getFinalDamage() > 0 && damageInstance.damageRelay == DamageRelay.PROJECTILE) {
            if (Main.isPlayerInGame(attacker)) {

                if ( this.charged && this.chargedTimestamp - Main.getPlayerGameProfile(attacker).getGame().countdown <= calculateIntValue1(level)) {

                    this.charged = false;
                    LivingEntity victim = (LivingEntity) event.getEntity();
                    playFXDamage(victim, 1);
                    notifyPlayerOnAbilityActivation(attacker);

                    compoundValueModifier.addDouble(3.0);
                }

            }
        }
    }
}
