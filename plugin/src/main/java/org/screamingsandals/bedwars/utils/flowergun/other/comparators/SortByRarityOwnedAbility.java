package org.screamingsandals.bedwars.utils.flowergun.other.comparators;

import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;

import java.util.Comparator;

public class SortByRarityOwnedAbility implements Comparator<OwnedAbility> {

    @Override
    public int compare(OwnedAbility oa1, OwnedAbility oa2) {
        if ( ( oa1.isAvailable() ) && ( !oa2.isAvailable() ) ) return 0;
        if ( ( !oa1.isAvailable() ) && ( oa2.isAvailable() ) ) return 1;
        return oa1.getAbility().getRarity() - oa2.getAbility().getRarity();
    }
}
