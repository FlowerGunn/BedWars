package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class FuryOfTheSeas extends Ability implements IAbility {

    public FuryOfTheSeas(){//epic
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.ICE_POWDER, 50).addResource(ResourceType.COMPONENT1, 2).addResource(ResourceType.CRYO_SLIME, 5).addResource(ResourceType.CATALYST_EPIC, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.ICE_POWDER, 120).addResource(ResourceType.COMPONENT1, 5).addResource(ResourceType.CRYO_SLIME, 15).addResource(ResourceType.EXP_CRYSTAL_LVL2, 5);

        this.name = "Ярость Морей";
        this.id = "furyoftheseas";
        this.item = Material.TRIDENT;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;

        this.abilityCategories.add(AbilityCategory.POSEIDON);
        this.abilityCategories.add(AbilityCategory.RANGER);
        this.abilityCategories.add(AbilityCategory.FIGHTER);

        this.description = "Убийство противника трезубцем даст игроку#1 заряд Ярости Морей. Каждый заряд увеличивает#любой урон игрока трезубцем на 4%.#Максимум (values1) зарядов.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 5 + 1 * level;
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        this.maxStacks = calculateIntValue1(level);

    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if (event.isCancelled()) return;

        if ( event.getFinalDamage() > 0 )
        if (damageInstance.damageTarget == DamageTarget.PLAYER) {
            if (Main.isPlayerInGame(attacker)) {
                if (damageInstance.damageRelay == DamageRelay.MELEE && attacker.getInventory().getItemInMainHand().getType() == Material.TRIDENT) {
                    compoundValueModifier.addExp(stacks * 0.04);
                } else if (damageInstance.damageRelay == DamageRelay.PROJECTILE && damageInstance.relayEntity instanceof Trident) {
                    compoundValueModifier.addExp(stacks * 0.04);
                }
            }
        }
    }

    @Override
    public void playerKill(int level, Player victim, Player killer, PlayerDeathEvent event)  {

        GamePlayer gVictim = Main.getPlayerGameProfile(victim);

        if ( gVictim.lastReceivedDamageInstance.relayEntity instanceof Trident || ( gVictim.lastReceivedDamageInstance.damageRelay == DamageRelay.MELEE && killer.getInventory().getItemInMainHand().getType() == Material.TRIDENT )) {
            if ( stacks < maxStacks ) {
                stacks++;
                notifyPlayerOnStackCount(killer);
            }
        }

    }

}
