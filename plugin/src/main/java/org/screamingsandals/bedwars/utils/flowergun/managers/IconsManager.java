package org.screamingsandals.bedwars.utils.flowergun.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

import java.util.HashMap;
import java.util.Map;

public class IconsManager {

    public static String gem = "方";
    public static String coin = "別";
    public static String red_excl = "ꑜ";
    public static String blue_excl = "ꑦ";
    public static String yellow_excl = "ꑟ";
    public static String green_excl = "ꑠ";
    public static boolean isLoaded = false;

    public static HashMap<IconType,String> newicons = new HashMap<>();

    static {
//
//        icons.put("INCREASE_DAMAGE","%img_strength%");
//        icons.put("WEAKNESS","%img_weakness%");
//        icons.put("SPEED","%img_speed%");
//        icons.put("JUMP","%img_jump_boost%");
//        icons.put("SLOW","%img_slowness%");
//        icons.put("FAST_DIGGING","%img_haste%");
//        icons.put("SLOW_DIGGING","%img_mining_fatigue%");
//        icons.put("GLOWING","%img_glowing%");
//        icons.put("DAMAGE_RESISTANCE","%img_resistance%");
//        icons.put("ABSORPTION","%img_absorption%");
//        icons.put("HUNGER","%img_hunger%");
//        icons.put("WITHER","%img_wither%");
//        icons.put("POISON","%img_poison%");
//        icons.put("REGENERATION","%img_regeneration%");
//        icons.put("LUCK","%img_luck%");
//        icons.put("UNLUCK","%img_unluck%");
//        icons.put("SLOW_FALLING","%img_slow_falling%");
//        icons.put("LEVITATION","%img_levitation%");
//        icons.put("BLINDNESS","%img_blindness%");
//        icons.put("FIRE_RESISTANCE","%img_fire_resistance%");
//
//
//        icons.put("SKULL","뜦");
//
//        icons.put("GOLD_INGOT","%img_gold_ingot%");
//        icons.put("IRON_INGOT","%img_iron_ingot%");
//        icons.put("EMERALD","%img_emerald%");
//        icons.put("COPPER_INGOT","%img_copper_ingot%");
//        icons.put("AMETHYST_SHARD","%img_amethyst_shard%");
//        icons.put("QUARTZ","%img_quartz%");
//        icons.put("SLIMEBALL","%img_slime_ball%");
//        icons.put("NETHERITE_SCRAP","%img_netherite_scrap%");
//        icons.put("PAPER","%img_paper%");
//        icons.put("SPYGLASS","%img_spyglass%");
//        icons.put("LEATHER","%img_leather%");
//        icons.put("RAW_IRON","%img_raw_iron%");
//        icons.put("GLOW_INK_SAC","%img_glow_ink_sac%");
//        icons.put("ENDER_EYE","%img_ender_eye%");
//        icons.put("CAKE","%img_cake%");
//        icons.put("BONE","%img_bone%");
//
//        icons.put("THUMBS_UP","띕");
//        icons.put("OFFSET-3","\uF804");


    }

    @Deprecated
    public static String requestIcon(String legacyIconId, Player player) {
        return requestIcon(IconType.valueOf(legacyIconId), player);
    }

    public static String requestIcon(IconType iconType, Player player) {
        if (!isLoaded) {
            for (IconType it : IconType.values()) {
                newicons.put(it, PlaceholderAPI.setPlaceholders(player, it.getIconId()) );
            }
            isLoaded = true;
        }
        return newicons.get(iconType);
    }
}
