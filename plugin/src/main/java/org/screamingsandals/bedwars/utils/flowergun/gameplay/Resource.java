package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import lombok.Getter;
import org.screamingsandals.bedwars.utils.flowergun.gameplay.enums.ResourceType;

public class Resource {

    @Getter
    private int amount;
    @Getter
    private ResourceType type;

    public Resource( ResourceType resourceType, int amount) {
        this.type = resourceType;
        this.amount = amount;
    }

}
