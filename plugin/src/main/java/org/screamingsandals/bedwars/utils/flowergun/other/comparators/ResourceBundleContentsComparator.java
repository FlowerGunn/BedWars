package org.screamingsandals.bedwars.utils.flowergun.other.comparators;

import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.mechanics.ImpactInstance;

import java.util.Comparator;

public class ResourceBundleContentsComparator implements Comparator<Resource> {

    @Override
    public int compare(Resource r1, Resource r2) {
        return r1.getType().getRarity() - r2.getType().getRarity();
    }

}
