package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Slimy extends Ability implements IAbility {

    public Slimy(){//legendary
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 10).addResource(ResourceType.THICK_SLIME, 20).addResource(ResourceType.GLUE, 8).addResource(ResourceType.CATALYST_LEGENDARY, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.CURSED_SILK, 20).addResource(ResourceType.THICK_SLIME, 50).addResource(ResourceType.GLUE, 20).addResource(ResourceType.EXP_CRYSTAL_LVL2, 12);

        this.name = "Слизняк";
        this.id = "slimebody";
        this.item = Material.SLIME_SPAWN_EGG;
        this.rarity = 5;
        this.icon = IconType.WEAKNESS;
        this.description = "Ближний урон игрока уменьшен на 50%,#раз в (values2) секунд противник атакующий игрока#в ближнем бою получит Слабость 1 на (values1) секунд.";
        this.isOnCooldown = false;
    }

    @Override
    public int calculateIntValue1(int level) {
        return 40 + 20 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }

    @Override
    public int calculateIntValue2(int level) {
        return 500 - 100 * level;
    }

    @Override
    public String formatValue2(int level) {
        return "" + calculateIntValue2(level) / 20;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {

        if (this.isOnCooldown) return;

        if (event.isCancelled()) return;

        if (!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (event.getFinalDamage() != 0 && damageInstance.damageRelay == DamageRelay.MELEE && damageInstance.damageSource == DamageSource.PLAYER) {

                Player attacker = (Player) tempEvent.getDamager();
                playFXSlow(attacker, 3);
                notifyPlayerOnAbilityActivation(victim);
                (attacker).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, calculateIntValue1(level), 0));

                this.isOnCooldown = true;
                Player finalAttacker = victim;
                Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                    notifyPlayerOnCooldownEnd(finalAttacker);
                    this.isOnCooldown = false;
                },calculateIntValue2(level));

            }
        }
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(attacker)) {
            if (damageInstance.damageRelay == DamageRelay.MELEE) {
                compoundValueModifier.addExp(-0.5);
            }
        }
    }
}
