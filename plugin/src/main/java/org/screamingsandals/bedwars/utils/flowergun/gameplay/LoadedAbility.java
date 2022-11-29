package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Comparator;

public class LoadedAbility{

    @Getter
    int activeLevel = 1;

    @Getter
    OwnedAbility ownedAbility;

    public LoadedAbility(OwnedAbility ownedAbility, int activeLevel) {

        this.ownedAbility = ownedAbility;
        this.activeLevel = activeLevel;
    }

    public static LoadedAbility getEmptyLoadedAbility() {
        return new LoadedAbility(null, 0);
    }

    public boolean isEmpty() {
        return this.ownedAbility == null;
    }

}

class SortByRarityLoadedAbility implements Comparator<LoadedAbility> {

    @Override
    public int compare(LoadedAbility la1, LoadedAbility la2) {
        return la2.getOwnedAbility().getAbility().getRarity() - la1.getOwnedAbility().getAbility().getRarity();
    }
}
