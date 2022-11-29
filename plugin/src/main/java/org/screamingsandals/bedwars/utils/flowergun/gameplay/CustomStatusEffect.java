package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import org.bukkit.attribute.Attribute;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.game.GamePlayer;

public class CustomStatusEffect extends BukkitRunnable {

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
        this.isActive = true;
    }

    public void start() {

    }

    @Override
    public void run() {
        this.isActive = false;
        this.target.recalculateCustomEffects();
    }
}
