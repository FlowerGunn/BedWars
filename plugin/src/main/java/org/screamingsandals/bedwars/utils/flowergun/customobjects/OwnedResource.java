package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.managers.RarityManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class OwnedResource extends Resource{

    @Getter
    private int entry_id;

    @Getter
    private UUID owner;

    public OwnedResource(ResourceType resourceType, int amount, int entry_id, UUID uuid) {
        super(resourceType, amount);
        this.entry_id = entry_id;
        this.owner = uuid;
    }

    public OwnedResource(String resourceId, int amount, int entry_id, UUID uuid) {
        super(ResourceType.valueOf(resourceId), amount);
        this.entry_id = entry_id;
        this.owner = uuid;
    }

    public int getAmount() {
        return this.amount;
    }

    public ResourceType getType() {
        return this.type;
    }

    public ArrayList<String> generateInventoryLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(RarityManager.getFullRarity(this.type.getRarity()));
        lore.add("");
        lore.add(ColoursManager.gray + " Количество: " + this.getAmount());
        return lore;
    }
}
