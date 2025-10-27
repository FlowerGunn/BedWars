package org.screamingsandals.bedwars.utils.flowergun.other.enums;

public enum PlayerConfigType {
    CUSTOM_PLAYER_ABILITIES_BUILD, HIDE_RESOURCES("1"), HIDE_FORGE("1"), DEFAULT_ABILITIES_AUTOSELECT("1"), SIMPLIFIED_ABILITY_SELECTION("1"), ENABLE_TIPS("1"), EVENT_REWARD, QUEST;

    private final String defaultValue;

    PlayerConfigType() {
        this.defaultValue = "0";
    }

    PlayerConfigType(String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

}
