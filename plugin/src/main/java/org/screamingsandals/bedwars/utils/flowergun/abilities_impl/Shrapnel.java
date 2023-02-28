package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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
import org.screamingsandals.bedwars.utils.flowergun.other.enums.*;

public class Shrapnel extends Ability implements IAbility {

    public Shrapnel(){//rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.SCRAP, 50).addResource(ResourceType.NETHERITE_SCRAP, 8).addResource(ResourceType.BOLT, 12).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.SCRAP, 200).addResource(ResourceType.NETHERITE_SCRAP, 20).addResource(ResourceType.BOLT, 30).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Шрапнель";
        this.id = "shrapnel";
        this.item = Material.TNT;
        this.rarity = 3;
        this.icon = IconType.SLOW;
        this.description = "Весь ваш взрывной урон замедляет#противников на -(values1)%.";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 25 + 5 * level;
    }

    @Override
    public void playerFirstSpawn(int level, GamePlayer gamePlayer) {

        this.maxStacks = calculateIntValue1(level);

    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( event.getFinalDamage() > 0 && !event.isCancelled() && damageInstance.damageTarget == DamageTarget.PLAYER && ( damageInstance.damageType == DamageType.EXPLOSION || damageInstance.damageType == DamageType.FIREWORK )) {
            if (Main.isPlayerInGame(attacker)) {

                GamePlayer gAttacker = Main.getPlayerGameProfile(attacker);
                GamePlayer gVictim = Main.getPlayerGameProfile((Player) event.getEntity());
                gVictim.addCustomStatusEffect(new CustomStatusEffect("shrapnel_slow", gVictim, gAttacker, Attribute.GENERIC_MOVEMENT_SPEED, new CompoundValueModifier(0, 0,  calculateIntValue1(level) * -0.01), 40, false));
                playFXSlow(gVictim.player, 1);

            }
        }
    }

}
