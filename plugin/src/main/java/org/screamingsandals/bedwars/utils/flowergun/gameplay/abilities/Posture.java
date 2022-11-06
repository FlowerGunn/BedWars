package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageRelay;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.DamageType;

public class Posture extends Ability implements IAbility {

    public Posture(){
        this.name = "Стойкость";
        this.id = "posture";
        this.icon = Material.IRON_AXE;
        this.rarity = 3;
        this.description = "При удержании топора в основной руке#весь получаемый физический урон#уменьшен на (values1)&7%";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 10 + 5 * level;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        if (!(event instanceof EntityDamageByEntityEvent)) return;
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
