package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.bukkit.attribute.Attribute;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.CustomStatusEffectType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.DamageInstance;

public class CustomStatusEffect extends BukkitRunnable {

    public final CustomStatusEffectType type;
    public DamageInstance damageExample;
    public boolean isPermanent = false;
    public boolean isActive = true;

    GamePlayer target;
    GamePlayer applier;

    public Attribute attribute;

    public String effectId;

    public CompoundValueModifier valueChange;

    public int ticksDuration;

    public CustomStatusEffect(String effectId,GamePlayer target, GamePlayer applier, Attribute attribute, CompoundValueModifier valueChange, int ticksDuration, boolean isPermanent) {
        this.effectId = effectId;
        this.target = target;
        this.applier = applier;
        this.attribute = attribute;
        this.valueChange = valueChange;
        this.ticksDuration = ticksDuration;
        this.isPermanent = isPermanent;
        this.type = CustomStatusEffectType.BASE_STAT;
    }

    public CustomStatusEffect(String effectId, GamePlayer target, GamePlayer applier, CustomStatusEffectType customStatusEffectType, DamageInstance damageInstance, CompoundValueModifier valueChange, int ticksDuration, boolean isPermanent) {
        this.effectId = effectId;
        this.target = target;
        this.applier = applier;
        this.valueChange = valueChange;
        this.ticksDuration = ticksDuration;
        this.isPermanent = isPermanent;
        this.type = customStatusEffectType;
        this.damageExample = damageInstance;
    }

    public void start() {

    }

    @Override
    public void run() {
        this.isActive = false;
        this.target.recalculateCustomEffects();
    }
}
