package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;

public class Starling extends Ability implements IAbility {

    public Starling(){
        this.name = "Звёздочка";
        this.id = "starling";
        this.item = Material.GLOWSTONE_DUST;
        this.rarity = 5;
        this.icon = IconType.SPEED;
        this.description = "При получении урона на уровне здоровья#менее 50% от максимального, вы получите эффекты#Скорости 2и Свечения на (values1)&7 секунд";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 60 + 20 * level;
    }


    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }



    @Override
    public void playerReceiveDamage(int level, DamageInstance damageInstance, Player victim, EntityDamageEvent event, CompoundValueModifier compoundValueModifier) {
        //if (!(event instanceof EntityDamageByEntityEvent)) return;

        if (event.isCancelled()) return;

//        EntityDamageByEntityEvent tempEvent = (EntityDamageByEntityEvent) event;
//        Bukkit.getConsoleSender().sendMessage("player receive damage from " + ((EntityDamageByEntityEvent) event).getDamager().getName() + "   source = " + damageSource);
        if (Main.isPlayerInGame(victim)) {

            double max = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            double current = victim.getHealth();

            if ( current <= max/2 ) {
                playFXDefensiveUtility(victim,2);
                notifyPlayerOnAbilityActivation(victim);
                victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, calculateIntValue1(level), 1));
                victim.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, calculateIntValue1(level), 0));
            }
        }

    }

}
