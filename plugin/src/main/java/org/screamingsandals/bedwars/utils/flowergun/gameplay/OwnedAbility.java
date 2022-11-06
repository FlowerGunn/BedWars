package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OwnedAbility{

    public int dbEntryId;
    private Player owner;
    public UUID ownerUUID;

    @Getter
    public int ownedLevel;
    public int duplicatesOwned;
    public int instancesCrafted;
    @Setter
    @Getter
    public int lastEquippedSlot;

    @Getter
    protected IAbility ability;

    public OwnedAbility(int dbEntryId,Player player, IAbility ability, int ownedLevel, int duplicatesOwned, int instancesCrafted, int lastEquippedSlot) {
        this.dbEntryId = dbEntryId;
        this.owner = player;
        this.ownerUUID = player.getUniqueId();
        this.ability = ability;

        this.ownedLevel = ownedLevel;
        this.duplicatesOwned = duplicatesOwned;
        this.instancesCrafted = instancesCrafted;
        this.lastEquippedSlot = lastEquippedSlot;
    }

    public OwnedAbility(int dbEntryId, UUID uuid, String abilityId, int ownedLevel, int duplicatesOwned, int instancesCrafted, int lastEquippedSlot) {
        this.dbEntryId = dbEntryId;
        this.ownerUUID = uuid;
        this.ability = ability;

        this.ownedLevel = ownedLevel;
        this.duplicatesOwned = duplicatesOwned;
        this.instancesCrafted = instancesCrafted;
        this.lastEquippedSlot = lastEquippedSlot;
    }

    public Player getOwner() {
        if (this.owner == null) {
            this.owner = Bukkit.getPlayer(ownerUUID);
        }

        return this.owner;
    }


    public LoadedAbility loadAbility( int activeLevel ) {
        return new LoadedAbility( this, activeLevel);
    }
}

