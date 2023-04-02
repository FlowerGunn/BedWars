package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import lombok.Getter;

public enum GadgetType {
    TRAMPOLINE("trampoline") , ZOGLIN("zoglin"),BLAZE("blaze"),PHANTOM("phantom"),WALL("wall"), PLATFORM("platform"), PLATFORM_SLIME("platform_slime"), TP("tp"), ARROW_SHIELD("arrow_shield"), TNT("tnt"), FIREBALL("fireball"), SHEEP("sheep"), TRAP("trap"), GOLEM("golem"), TRACKER("tracker"), BOOTS("boots"),NEW_BOOTS("new_boots"),FIREWORK("firework"),
    SNOWBALL("snowball"), MEGA_WALL("mega_wall");

    @Getter
    private String id;


    GadgetType(String id) {
        this.id = id;
    }

}
