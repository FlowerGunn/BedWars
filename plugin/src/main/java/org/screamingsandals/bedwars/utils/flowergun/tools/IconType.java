package org.screamingsandals.bedwars.utils.flowergun.tools;

import org.bukkit.entity.Player;

public enum IconType {

    GOLD_INGOT, IRON_INGOT, EMERALD, COPPER_INGOT, THUMBS_UP, INCREASE_DAMAGE, WEAKNESS, SPEED, JUMP, SLOW,FAST_DIGGING, SLOW_DIGGING, GLOWING, DAMAGE_RESISTANCE, ABSORPTION, HUNGER, WITHER, POISON, REGENERATION, LUCK, UNLUCK, SLOW_FALLING, LEVITATION, BLINDNESS, FIRE_RESISTANCE;

    public String getIcon(Player player) {
        return IconsManager.requestIcon(this.toString(), player);
    }

}
