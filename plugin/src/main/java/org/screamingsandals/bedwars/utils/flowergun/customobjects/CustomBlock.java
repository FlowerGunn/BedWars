package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class CustomBlock extends CustomItem{

    private Material originalType;
    private Block block;

    public CustomBlock( CustomItem customItem, Block block ) {
        super(customItem);
        this.block = block;
        this.originalType = customItem.item.getType();
    }

    public Block getBlock() {
        return this.block;
    }

    public Material getOriginalType() {
        return this.originalType;
    }
}
