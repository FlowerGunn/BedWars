package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import lombok.Getter;
import org.screamingsandals.bedwars.Main;

import java.util.function.Function;

public enum GameEventType {

    GENERATORS_SPEED_UP_1(  true ),
    GENERATORS_SPEED_UP_2(  true ),
    GENERATORS_SPEED_UP_3(  true ),
    DEATHMATCH(  true ),
    ANNIHILATION(  true ),
    END(  true ),
    DEATHMATCH_WARNING_1(  false ),
    DEATHMATCH_WARNING_2(  false );

    @Getter
    private boolean separator;

    GameEventType( boolean separator ) {
        this.separator = separator;
    }

}
