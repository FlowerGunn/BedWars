package org.screamingsandals.bedwars.utils.flowergun.tools.comparators;

import org.screamingsandals.bedwars.utils.flowergun.gameplay.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;

import java.util.Comparator;

public class ImpactInstanceComparator implements Comparator<ImpactInstance> {

    @Override
    public int compare(ImpactInstance ii1, ImpactInstance ii2) {
        return ii1.countdownTimestamp - ii2.countdownTimestamp;
    }

}
