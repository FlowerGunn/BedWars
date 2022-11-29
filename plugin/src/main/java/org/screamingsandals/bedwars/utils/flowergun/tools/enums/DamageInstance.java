package org.screamingsandals.bedwars.utils.flowergun.tools.enums;

public class DamageInstance {

    public DamageSource damageSource;
    public DamageTarget damageTarget;
    public DamageRelay damageRelay;
    public DamageType damageType;

    public DamageInstance( DamageSource damageSource, DamageTarget damageTarget, DamageRelay damageRelay, DamageType damageType ) {
        this.damageRelay = damageRelay;
        this.damageSource = damageSource;
        this.damageTarget = damageTarget;
        this.damageType = damageType;
    }


}
