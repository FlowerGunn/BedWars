package org.screamingsandals.bedwars.utils.flowergun.other.comparators;

import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;

import java.util.Comparator;

public class SortByRarityOwnedAbility implements Comparator<OwnedAbility> {

    @Override
    public int compare(OwnedAbility oa1, OwnedAbility oa2) {
        if ( ( oa1.getAbility().isDailyTrial() ) && ( !oa2.getAbility().isDailyTrial() ) ) return -1;
        else
        if ( ( !oa1.getAbility().isDailyTrial() ) && ( oa2.getAbility().isDailyTrial() ) ) return 1;
        else
        if ( ( oa1.isAvailable() ) && ( !oa2.isAvailable() ) ) return -1;
        else
        if ( ( !oa1.isAvailable() ) && ( oa2.isAvailable() ) ) return 1;
        else
        return oa1.getAbility().getRarity() - oa2.getAbility().getRarity();
    }
}
