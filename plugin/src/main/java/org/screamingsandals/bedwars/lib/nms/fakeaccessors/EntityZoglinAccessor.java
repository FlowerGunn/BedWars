package org.screamingsandals.bedwars.lib.nms.fakeaccessors;

import org.screamingsandals.bedwars.lib.nms.accessors.AccessorUtils;

import java.lang.Class;

public class EntityZoglinAccessor {
    public static Class<?> getType() {
        return AccessorUtils.getType(org.screamingsandals.bedwars.lib.nms.accessors.EntityIronGolemAccessor.class, mapper -> {
            mapper.map("spigot", "1.17", "net.minecraft.world.entity.animal.EntityIronGolem");
        });
    }
}
