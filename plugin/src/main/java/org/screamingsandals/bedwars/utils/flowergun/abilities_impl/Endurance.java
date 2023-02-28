package org.screamingsandals.bedwars.utils.flowergun.abilities_impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.Ability;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

public class Endurance extends Ability implements IAbility {

    public Endurance(){
        //rare
        this.disassembleResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 15).addResource(ResourceType.POLISHED_RUBY, 12).addResource(ResourceType.BLAZE_POWDER, 30).addResource(ResourceType.CATALYST_RARE, 1);
        this.upgradeResources = new ResourceBundle().addResource(ResourceType.NETHERITE_SCRAP, 50).addResource(ResourceType.POLISHED_RUBY, 30).addResource(ResourceType.BLAZE_POWDER, 100).addResource(ResourceType.EXP_CRYSTAL_LVL2, 2);

        this.name = "Выносливость";
        this.id = "posture";
        this.item = Material.IRON_AXE;
        this.rarity = 3;
        this.icon = IconType.DAMAGE_RESISTANCE;
        this.description = "При удержании топора в основной руке#весь получаемый игроком физический#урон уменьшен на (values1)&7%";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 10 + 4 * level;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {
            if (damageInstance.damageType == DamageType.PHYSICAL && FlowerUtils.axes.contains(victim.getInventory().getItemInMainHand().getType())) {
                playFXDefensiveUtility(victim,1);
                notifyPlayerOnAbilityActivation(victim);
                compoundValueModifier.addExp(-1 * calculateIntValue1(level) / 100.0);
            }
        }

    }

}
