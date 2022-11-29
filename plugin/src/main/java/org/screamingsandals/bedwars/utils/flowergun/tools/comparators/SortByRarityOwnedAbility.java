package org.screamingsandals.bedwars.utils.flowergun.tools.comparators;

import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;

import java.util.Comparator;

public class SortByRarityOwnedAbility implements Comparator<OwnedAbility> {

    @Override
    public int compare(OwnedAbility oa1, OwnedAbility oa2) {
        return oa1.getAbility().getRarity() - oa2.getAbility().getRarity();
    }
}
