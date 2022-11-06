package org.screamingsandals.bedwars.utils.flowergun.gameplay.enums;

import org.bukkit.Material;

public enum ResourceType {
    GOLD( Material.GOLD_INGOT, "Gold", "Gold"), IRON( Material.IRON_INGOT, "Iron", "Iron");

    private Material material;
    private String name;
    private String iconName;

    ResourceType(Material material, String iconName, String name) {
        this.material = material;
        this.iconName = iconName;
        this.name = name;
    }

}