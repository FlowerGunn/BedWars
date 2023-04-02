package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import org.bukkit.entity.Player;

public class DamageInstance {

    public DamageSource damageSource;
    public DamageTarget damageTarget;
    public DamageRelay damageRelay;
    public DamageType damageType;
    public Player attackerPlayer;
    public Player victimPlayer;

    public DamageInstance( DamageSource damageSource, DamageTarget damageTarget, DamageRelay damageRelay, DamageType damageType ) {
        this.damageRelay = damageRelay;
        this.damageSource = damageSource;
        this.damageTarget = damageTarget;
        this.damageType = damageType;
    }

    public boolean contains( DamageInstance damageInstance ) {
        if ( damageInstance.damageTarget != null && this.damageTarget != damageInstance.damageTarget ) return false;
        if ( damageInstance.damageSource != null && this.damageSource != damageInstance.damageSource ) return false;
        if ( damageInstance.damageType != null && this.damageType != damageInstance.damageType ) return false;
        if ( damageInstance.damageRelay != null && this.damageRelay != damageInstance.damageRelay ) return false;
        return true;
    }


}
