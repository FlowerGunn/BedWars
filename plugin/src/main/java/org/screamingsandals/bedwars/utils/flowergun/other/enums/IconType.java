package org.screamingsandals.bedwars.utils.flowergun.other.enums;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.utils.flowergun.managers.IconsManager;

public enum IconType {

//    SLIMEBALLAMETHYST_SHARDQUARTZNETHERITE_SCRAPPAPERSPYGLASSLEATHERRAW_IRONGLOW_INK_SACENDER_EYECAKEBONE
//    GOLD_INGOT IRON_INGOT EMERALD COPPER_INGOT THUMBS_UP INCREASE_DAMAGE WEAKNESS SPEED JUMP SLOWFAST_DIGGING SLOW_DIGGING GLOWING DAMAGE_RESISTANCE ABSORPTION HUNGER WITHER POISON REGENERATION LUCK UNLUCK SLOW_FALLING LEVITATION BLINDNESS FIRE_RESISTANCE;

    INCREASE_DAMAGE("%img_strength%"),
    WEAKNESS("%img_weakness%"),
    SPEED("%img_speed%"),
    JUMP("%img_jump_boost%"),
    SLOW("%img_slowness%"),
    FAST_DIGGING("%img_haste%"),
    SLOW_DIGGING("%img_mining_fatigue%"),
    GLOWING("%img_glowing%"),
    DAMAGE_RESISTANCE("%img_resistance%"),
    ABSORPTION("%img_absorption%"),
    HUNGER("%img_hunger%"),
    WITHER("%img_wither%"),
    POISON("%img_poison%"),
    REGENERATION("%img_regeneration%"),
    LUCK("%img_luck%"),
    UNLUCK("%img_unluck%"),
    SLOW_FALLING("%img_slow_falling%"),
    LEVITATION("%img_levitation%"),
    BLINDNESS("%img_blindness%"),
    DARKNESS("%img_blindness%"),
    FIRE_RESISTANCE("%img_fire_resistance%"),


    SKULL("뜦"),

    GOLD_INGOT("%img_gold_ingot%"),
    IRON_INGOT("%img_iron_ingot%"),
    EMERALD("%img_emerald%"),
    COPPER_INGOT("%img_copper_ingot%"),
    AMETHYST_SHARD("%img_amethyst_shard%"),
    QUARTZ("%img_quartz%"),
    SLIMEBALL("%img_slime_ball%"),
    NETHERITE_SCRAP("%img_netherite_scrap%"),
    PAPER("%img_paper%"),
    SPYGLASS("%img_spyglass%"),
    LEATHER("%img_leather%"),
    RAW_IRON("%img_raw_iron%"),
    RAW_COPPER("%img_raw_copper%"),
    RAW_GOLD("%img_raw_gold%"),
    GLOW_INK_SAC("%img_glow_ink_sac%"),
    ENDER_EYE("%img_ender_eye%"),
    CAKE("%img_cake%"),
    BONE("%img_bone%"),
    THICK_SLIME,
    STEEL_PLATE,
    STEEL_INGOT,
    SLOT_LOCKED,
    SILK_COCOON,
    RUBY,
    RUBY_LAMP,
    REINFORCED_BONE_PLATE,
    POLISHED_RUBY,
    NETHERITE_PLATE,
    PROCESSING_UNIT("%img_motherboard%"),
    MICROSCHEMA,
    MAGIC_SILK,
    MAGIC_DUST,
    LOCK,
    LAPIS_SHEET,
    SCRAP("%img_raw_iron_wood%"),
    IRON_PLATE,
    ICE_POWDER,
    GLUE,
    EXP_CRYSTAL_LVL1,
    EXP_CRYSTAL_LVL2,
    EXP_CRYSTAL_LVL3,
    EXOPLATE,
    ENDER_PEARL_DUST,
    EMERALD_PLATE,
    EMERALD_DUST,
    CURSED_SILK,
    CRYO_SLIME,
    COPPER_PLATE,
    COMPONENT1,
    CHAIN,
    CATALYST_RARE,
    CATALYST_EPIC,
    CATALYST_LEGENDARY,
    BOOK_SAFE1,
    BOOK_SAFE2,
    BOOK_EVIL1,
    BOOK_EVIL2,
    BOOK,
    WRITABLE_BOOK,
    BONE_PLATE,
    BOLT,
    ANOMALY,
    SEMICONDUCTOR,
    GOLD_SHEET,
    BED_CROSSED,
    BED_CROSSED_ALT,
    BED_WHITE,
    BED_RED,
    BED_GRAY,
    BED_ORANGE,
    BED_YELLOW,
    BED_LIME,
    BED_LIGHT_BLUE,
    BED_CYAN,
    BED_BLUE,
    BED_MAGENTA,
    BED_PURPLE,
    BLUE_RIGHT,
    BLUE_RIGHT_DARK,
    BLUE_LEFT,
    BLUE_LEFT_DARK,
    CYAN_RIGHT,
    CYAN_RIGHT_DARK,
    CYAN_LEFT,
    CYAN_LEFT_DARK,
    GRAY_RIGHT,
    GRAY_RIGHT_DARK,
    GRAY_LEFT,
    GRAY_LEFT_DARK,
    GREEN_RIGHT,
    GREEN_RIGHT_DARK,
    GREEN_LEFT,
    GREEN_LEFT_DARK,
    LIGHT_BLUE_RIGHT,
    LIGHT_BLUE_RIGHT_DARK,
    LIGHT_BLUE_LEFT,
    LIGHT_BLUE_LEFT_DARK,
    ORANGE_RIGHT,
    ORANGE_RIGHT_DARK,
    ORANGE_LEFT,
    ORANGE_LEFT_DARK,
    PINK_RIGHT,
    PINK_RIGHT_DARK,
    PINK_LEFT,
    PINK_LEFT_DARK,
    PURPLE_RIGHT,
    PURPLE_RIGHT_DARK,
    PURPLE_LEFT,
    PURPLE_LEFT_DARK,
    RED_RIGHT,
    RED_RIGHT_DARK,
    RED_LEFT,
    RED_LEFT_DARK,
    YELLOW_RIGHT,
    YELLOW_RIGHT_DARK,
    YELLOW_LEFT,
    YELLOW_LEFT_DARK,
    GRAY_RIGHT_SHADED,
    GRAY_LEFT_SHADED,

    GLOBAL,

    COAL,
    VIP("%img_vip_blue%"),
    PREMIUM("%img_premium_orange%"),
    LEGEND("%img_legend_yellow%"),
    TITAN("%img_titan_red%"),


    THUMBS_UP("띕"),
    OFFSET_MINUS_3("\uF804"), BLAZE_POWDER, ECHO_SHARD, ENDER_PEARL, LAPIS("%img_lapis_lazuli%"), NETHERITE_INGOT("img_iron_ingot");


    IconType (String id) {
        this.iconId = id;
    }
    IconType () {
        this.iconId = "%img_" + this + "%";
    }

    @Getter
    private String iconId;

    public String getIcon(Player player) {
        String icon = IconsManager.requestIcon(this, player);
        if (icon == null) return this.getIconId();
        else return icon;
    }

    public String getIcon() {
        return IconsManager.requestIcon(this);
    }

    

}
