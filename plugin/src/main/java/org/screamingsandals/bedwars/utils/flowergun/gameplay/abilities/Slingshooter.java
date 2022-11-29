package org.screamingsandals.bedwars.utils.flowergun.gameplay.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.GadgetType;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.Ability;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.CompoundValueModifier;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.IAbility;
import org.screamingsandals.bedwars.utils.flowergun.tools.IconType;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageInstance;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageSource;
import org.screamingsandals.bedwars.utils.flowergun.tools.enums.DamageType;

public class Slingshooter extends Ability implements IAbility {

    public Slingshooter(){
        this.name = "Метатель";
        this.id = "slingshooter";
        this.item = Material.STRING;
        this.rarity = 4;
        this.icon = IconType.INCREASE_DAMAGE;
        this.description = "Урон снежков увеличивается на (values1)&7,#кулдаун увеличивает на (values2)&7 секунд";
    }

    @Override
    public int calculateIntValue1(int level) {
        return 20 * level;
    }

    @Override
    public double calculateDoubleValue1(int level) {
        return 2 + 1.0 * level;
    }

    @Override
    public String formatValue1(int level) {
        return "" + calculateIntValue1(level) / 20;
    }
    @Override
    public String formatValue2(int level) {
        return "" + calculateDoubleValue1(level);
    }

    @Override
    public void gadgetUsed(int activeLevel, GamePlayer gamePlayer, GadgetType gadgetType, CompoundValueModifier compoundValueModifier) {
        if (gadgetType == GadgetType.SNOWBALL) {
            compoundValueModifier.addInt( calculateIntValue1(activeLevel) );
        }
    }

    @Override
    public void playerDealDamage(int level, DamageInstance damageInstance, Player attacker, EntityDamageByEntityEvent event, CompoundValueModifier compoundValueModifier) {

        if ( damageInstance.damageSource == DamageSource.PLAYER && damageInstance.damageType == DamageType.SNOWBALL ) {
            compoundValueModifier.addDouble(calculateDoubleValue1(level));
        }
    }
}
