package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.utils.flowergun.managers.ColoursManager;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.UUID;

public class Resource {

    @Getter
    public int amount;
    @Getter
    public ResourceType type;

    public Resource(ResourceType resourceType, int amount) {
        this.type = resourceType;
        this.amount = amount;
    }

    public String getResourceWithAmount() {
        return this.type.getName() + ColoursManager.gray + " x" + this.amount;
    }

    public String getResourceWithAmountAndIcon(Player player) {
        return ChatColor.WHITE + this.type.getIconType().getIcon(player) + " " + getResourceWithAmount();
    }

    public String getResourceWithIcon(Player player) {
        return ChatColor.WHITE + this.type.getIconType().getIcon(player) + " " + this.getType().getName();
    }

    public Resource copy() {
        return new Resource(type, amount);
    }

}
