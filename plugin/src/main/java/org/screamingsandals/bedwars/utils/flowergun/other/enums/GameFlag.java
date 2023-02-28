package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import lombok.Getter;

import java.util.ArrayList;

import static org.screamingsandals.bedwars.lib.lang.I.i18n;
import static org.screamingsandals.bedwars.lib.lang.I.i18nonly;

public enum GameFlag {
    INTELLECT_LEVEL_1(1), INTELLECT_LEVEL_2(2), INTELLECT_LEVEL_3(3), INTELLECT_LEVEL_4A(4), INTELLECT_LEVEL_4B(4), GOLD_AND_EMERALDS_SPAWN,
    AGILITY_LEVEL_1(1),AGILITY_LEVEL_2(2),AGILITY_LEVEL_3(3),AGILITY_LEVEL_4A(4),AGILITY_LEVEL_4B(4),
    VITALITY_LEVEL_1(1),VITALITY_LEVEL_2(2),VITALITY_LEVEL_3(3),VITALITY_LEVEL_4A(4),VITALITY_LEVEL_4B(4),

    NO_PLAYER_HEADS;

    static {
        INTELLECT_LEVEL_2.previousFlag = INTELLECT_LEVEL_1;
        INTELLECT_LEVEL_3.previousFlag = INTELLECT_LEVEL_2;
        INTELLECT_LEVEL_4A.previousFlag = INTELLECT_LEVEL_3;
        INTELLECT_LEVEL_4A.neighbourFlag = INTELLECT_LEVEL_4B;
        INTELLECT_LEVEL_4B.previousFlag = INTELLECT_LEVEL_3;
        INTELLECT_LEVEL_4B.neighbourFlag = INTELLECT_LEVEL_4A;

        INTELLECT_LEVEL_1.name = i18nonly("upgrade_intellect_level1_name");
        INTELLECT_LEVEL_1.description = new ArrayList<>();
        INTELLECT_LEVEL_1.description.add("");
        INTELLECT_LEVEL_1.description.add(i18nonly("upgrade_intellect_level1_description1"));
        INTELLECT_LEVEL_1.description.add(i18nonly("upgrade_intellect_level1_description2"));
        INTELLECT_LEVEL_1.description.add(i18nonly("upgrade_intellect_level1_description3"));
        INTELLECT_LEVEL_1.description.add(i18nonly("upgrade_price_row").replace("%price%", INTELLECT_LEVEL_1.getHeadCost() + ""));

        INTELLECT_LEVEL_2.name = i18nonly("upgrade_intellect_level2_name");
        INTELLECT_LEVEL_2.description = new ArrayList<>();
        INTELLECT_LEVEL_2.description.add("");
        INTELLECT_LEVEL_2.description.add(i18nonly("upgrade_intellect_level2_description1"));
        INTELLECT_LEVEL_2.description.add(i18nonly("upgrade_intellect_level2_description2"));
        INTELLECT_LEVEL_2.description.add(i18nonly("upgrade_intellect_level2_description3"));
        INTELLECT_LEVEL_2.description.add(i18nonly("upgrade_price_row").replace("%price%", INTELLECT_LEVEL_2.getHeadCost() + ""));

        INTELLECT_LEVEL_3.name = i18nonly("upgrade_intellect_level3_name");
        INTELLECT_LEVEL_3.description = new ArrayList<>();
        INTELLECT_LEVEL_3.description.add("");
        INTELLECT_LEVEL_3.description.add(i18nonly("upgrade_intellect_level3_description1"));
        INTELLECT_LEVEL_3.description.add(i18nonly("upgrade_intellect_level3_description2"));
        INTELLECT_LEVEL_3.description.add(i18nonly("upgrade_intellect_level3_description3"));
        INTELLECT_LEVEL_3.description.add(i18nonly("upgrade_price_row").replace("%price%", INTELLECT_LEVEL_3.getHeadCost() + ""));

        INTELLECT_LEVEL_4A.name = i18nonly("upgrade_intellect_level4a_name");
        INTELLECT_LEVEL_4A.description = new ArrayList<>();
        INTELLECT_LEVEL_4A.description.add("");
        INTELLECT_LEVEL_4A.description.add(i18nonly("upgrade_intellect_level4a_description1"));
        INTELLECT_LEVEL_4A.description.add(i18nonly("upgrade_intellect_level4a_description2"));
        INTELLECT_LEVEL_4A.description.add(i18nonly("upgrade_intellect_level4a_description3"));
        INTELLECT_LEVEL_4A.description.add(i18nonly("upgrade_price_row").replace("%price%", INTELLECT_LEVEL_4A.getHeadCost() + ""));

        INTELLECT_LEVEL_4B.name = i18nonly("upgrade_intellect_level4b_name");
        INTELLECT_LEVEL_4B.description = new ArrayList<>();
        INTELLECT_LEVEL_4B.description.add("");
        INTELLECT_LEVEL_4B.description.add(i18nonly("upgrade_intellect_level4b_description1"));
        INTELLECT_LEVEL_4B.description.add(i18nonly("upgrade_intellect_level4b_description2"));
        INTELLECT_LEVEL_4B.description.add(i18nonly("upgrade_intellect_level4b_description3"));
        INTELLECT_LEVEL_4B.description.add(i18nonly("upgrade_price_row").replace("%price%", INTELLECT_LEVEL_4B.getHeadCost() + ""));

        AGILITY_LEVEL_2.previousFlag = AGILITY_LEVEL_1;
        AGILITY_LEVEL_3.previousFlag = AGILITY_LEVEL_2;
        AGILITY_LEVEL_4A.previousFlag = AGILITY_LEVEL_3;
        AGILITY_LEVEL_4A.neighbourFlag = AGILITY_LEVEL_4B;
        AGILITY_LEVEL_4B.previousFlag = AGILITY_LEVEL_3;
        AGILITY_LEVEL_4B.neighbourFlag = AGILITY_LEVEL_4A;

        AGILITY_LEVEL_1.name = i18nonly("upgrade_agility_level1_name");
        AGILITY_LEVEL_1.description = new ArrayList<>();
        AGILITY_LEVEL_1.description.add("");
        AGILITY_LEVEL_1.description.add(i18nonly("upgrade_agility_level1_description1"));
        AGILITY_LEVEL_1.description.add(i18nonly("upgrade_agility_level1_description2"));
        AGILITY_LEVEL_1.description.add(i18nonly("upgrade_agility_level1_description3"));
        AGILITY_LEVEL_1.description.add(i18nonly("upgrade_price_row").replace("%price%", AGILITY_LEVEL_1.getHeadCost() + ""));

        AGILITY_LEVEL_2.name = i18nonly("upgrade_agility_level2_name");
        AGILITY_LEVEL_2.description = new ArrayList<>();
        AGILITY_LEVEL_2.description.add("");
        AGILITY_LEVEL_2.description.add(i18nonly("upgrade_agility_level2_description1"));
        AGILITY_LEVEL_2.description.add(i18nonly("upgrade_agility_level2_description2"));
        AGILITY_LEVEL_2.description.add(i18nonly("upgrade_agility_level2_description3"));
        AGILITY_LEVEL_2.description.add(i18nonly("upgrade_price_row").replace("%price%", AGILITY_LEVEL_2.getHeadCost() + ""));

        AGILITY_LEVEL_3.name = i18nonly("upgrade_agility_level3_name");
        AGILITY_LEVEL_3.description = new ArrayList<>();
        AGILITY_LEVEL_3.description.add("");
        AGILITY_LEVEL_3.description.add(i18nonly("upgrade_agility_level3_description1"));
        AGILITY_LEVEL_3.description.add(i18nonly("upgrade_agility_level3_description2"));
        AGILITY_LEVEL_3.description.add(i18nonly("upgrade_agility_level3_description3"));
        AGILITY_LEVEL_3.description.add(i18nonly("upgrade_price_row").replace("%price%", AGILITY_LEVEL_3.getHeadCost() + ""));

        AGILITY_LEVEL_4A.name = i18nonly("upgrade_agility_level4a_name");
        AGILITY_LEVEL_4A.description = new ArrayList<>();
        AGILITY_LEVEL_4A.description.add("");
        AGILITY_LEVEL_4A.description.add(i18nonly("upgrade_agility_level4a_description1"));
        AGILITY_LEVEL_4A.description.add(i18nonly("upgrade_agility_level4a_description2"));
        AGILITY_LEVEL_4A.description.add(i18nonly("upgrade_agility_level4a_description3"));
        AGILITY_LEVEL_4A.description.add(i18nonly("upgrade_price_row").replace("%price%", AGILITY_LEVEL_4A.getHeadCost() + ""));

        AGILITY_LEVEL_4B.name = i18nonly("upgrade_agility_level4b_name");
        AGILITY_LEVEL_4B.description = new ArrayList<>();
        AGILITY_LEVEL_4B.description.add("");
        AGILITY_LEVEL_4B.description.add(i18nonly("upgrade_agility_level4b_description1"));
        AGILITY_LEVEL_4B.description.add(i18nonly("upgrade_agility_level4b_description2"));
        AGILITY_LEVEL_4B.description.add(i18nonly("upgrade_agility_level4b_description3"));
        AGILITY_LEVEL_4B.description.add(i18nonly("upgrade_price_row").replace("%price%", AGILITY_LEVEL_4B.getHeadCost() + ""));


        VITALITY_LEVEL_2.previousFlag = VITALITY_LEVEL_1;
        VITALITY_LEVEL_3.previousFlag = VITALITY_LEVEL_2;
        VITALITY_LEVEL_4A.previousFlag = VITALITY_LEVEL_3;
        VITALITY_LEVEL_4A.neighbourFlag = VITALITY_LEVEL_4B;
        VITALITY_LEVEL_4B.previousFlag = VITALITY_LEVEL_3;
        VITALITY_LEVEL_4B.neighbourFlag = VITALITY_LEVEL_4A;

        VITALITY_LEVEL_1.name = i18nonly("upgrade_vitality_level1_name");
        VITALITY_LEVEL_1.description = new ArrayList<>();
        VITALITY_LEVEL_1.description.add("");
        VITALITY_LEVEL_1.description.add(i18nonly("upgrade_vitality_level1_description1"));
        VITALITY_LEVEL_1.description.add(i18nonly("upgrade_vitality_level1_description2"));
        VITALITY_LEVEL_1.description.add(i18nonly("upgrade_vitality_level1_description3"));
        VITALITY_LEVEL_1.description.add(i18nonly("upgrade_price_row").replace("%price%", VITALITY_LEVEL_1.getHeadCost() + ""));

        VITALITY_LEVEL_2.name = i18nonly("upgrade_vitality_level2_name");
        VITALITY_LEVEL_2.description = new ArrayList<>();
        VITALITY_LEVEL_2.description.add("");
        VITALITY_LEVEL_2.description.add(i18nonly("upgrade_vitality_level2_description1"));
        VITALITY_LEVEL_2.description.add(i18nonly("upgrade_vitality_level2_description2"));
        VITALITY_LEVEL_2.description.add(i18nonly("upgrade_vitality_level2_description3"));
        VITALITY_LEVEL_2.description.add(i18nonly("upgrade_price_row").replace("%price%", VITALITY_LEVEL_2.getHeadCost() + ""));

        VITALITY_LEVEL_3.name = i18nonly("upgrade_vitality_level3_name");
        VITALITY_LEVEL_3.description = new ArrayList<>();
        VITALITY_LEVEL_3.description.add("");
        VITALITY_LEVEL_3.description.add(i18nonly("upgrade_vitality_level3_description1"));
        VITALITY_LEVEL_3.description.add(i18nonly("upgrade_vitality_level3_description2"));
        VITALITY_LEVEL_3.description.add(i18nonly("upgrade_vitality_level3_description3"));
        VITALITY_LEVEL_3.description.add(i18nonly("upgrade_price_row").replace("%price%", VITALITY_LEVEL_3.getHeadCost() + ""));

        VITALITY_LEVEL_4A.name = i18nonly("upgrade_vitality_level4a_name");
        VITALITY_LEVEL_4A.description = new ArrayList<>();
        VITALITY_LEVEL_4A.description.add("");
        VITALITY_LEVEL_4A.description.add(i18nonly("upgrade_vitality_level4a_description1"));
        VITALITY_LEVEL_4A.description.add(i18nonly("upgrade_vitality_level4a_description2"));
        VITALITY_LEVEL_4A.description.add(i18nonly("upgrade_vitality_level4a_description3"));
        VITALITY_LEVEL_4A.description.add(i18nonly("upgrade_price_row").replace("%price%", VITALITY_LEVEL_4A.getHeadCost() + ""));

        VITALITY_LEVEL_4B.name = i18nonly("upgrade_vitality_level4b_name");
        VITALITY_LEVEL_4B.description = new ArrayList<>();
        VITALITY_LEVEL_4B.description.add("");
        VITALITY_LEVEL_4B.description.add(i18nonly("upgrade_vitality_level4b_description1"));
        VITALITY_LEVEL_4B.description.add(i18nonly("upgrade_vitality_level4b_description2"));
        VITALITY_LEVEL_4B.description.add(i18nonly("upgrade_vitality_level4b_description3"));
        VITALITY_LEVEL_4B.description.add(i18nonly("upgrade_price_row").replace("%price%", VITALITY_LEVEL_4B.getHeadCost() + ""));



    }



    @Getter
    private int headCost;

    @Getter
    private String name;

    @Getter
    private ArrayList<String> description;

    @Getter
    private GameFlag previousFlag;
    @Getter
    private GameFlag neighbourFlag;
    GameFlag(int headCost) { this.headCost = headCost; }
//    GameFlag(int headCost, GameFlag previousFlag) { this.headCost = headCost; this.previousFlag = previousFlag; }
    GameFlag() { this.headCost = 100; }

}


