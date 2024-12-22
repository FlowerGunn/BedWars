package org.screamingsandals.bedwars.utils.flowergun.customobjects;

import lombok.Getter;
import lombok.Setter;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerConfig {

    @Getter
    private UUID uuid;

    @Getter
    @Setter
    private String parameter1;
    @Getter
    @Setter
    private String parameter2;
    @Getter
    @Setter
    private String parameter3;
    @Getter
    @Setter
    private String parameter4;
    @Getter
    @Setter
    private String parameter5;
    @Getter
    @Setter
    private String parameter6;
    @Getter
    @Setter
    private String parameter7;
    @Getter
    @Setter
    private String parameter8;
    @Getter
    private PlayerConfigType playerConfigType;
    @Getter
    private int entryId;
    @Getter
    private Timestamp lastEdit;

    public PlayerConfig(int entryId, UUID uuid, PlayerConfigType playerConfigType, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, String parameter7, String parameter8, Timestamp lastEdit ) {
        this.entryId = entryId;
        this.uuid = uuid;
        this.playerConfigType = playerConfigType;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
        this.parameter3 = parameter3;
        this.parameter4 = parameter4;
        this.parameter5 = parameter5;
        this.parameter6 = parameter6;
        this.parameter7 = parameter7;
        this.parameter8 = parameter8;
        this.lastEdit = lastEdit;
    }


}
