package org.screamingsandals.bedwars.utils.flowergun.tools;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

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

    public static HashMap<String,String> icons = new HashMap<>();
    public static HashMap<IconType,String> newicons = new HashMap<>();

    static {

        icons.put("INCREASE_DAMAGE","%img_strength%");
        icons.put("WEAKNESS","%img_weakness%");
        icons.put("SPEED","%img_speed%");
        icons.put("JUMP","%img_jump_boost%");
        icons.put("SLOW","%img_slowness%");
        icons.put("FAST_DIGGING","%img_haste%");
        icons.put("SLOW_DIGGING","%img_mining_fatigue%");
        icons.put("GLOWING","%img_glowing%");
        icons.put("DAMAGE_RESISTANCE","%img_resistance%");
        icons.put("ABSORPTION","%img_absorption%");
        icons.put("HUNGER","%img_hunger%");
        icons.put("WITHER","%img_wither%");
        icons.put("POISON","%img_poison%");
        icons.put("REGENERATION","%img_regeneration%");
        icons.put("LUCK","%img_luck%");
        icons.put("UNLUCK","%img_unluck%");
        icons.put("SLOW_FALLING","%img_slow_falling%");
        icons.put("LEVITATION","%img_levitation%");
        icons.put("BLINDNESS","%img_blindness%");
        icons.put("FIRE_RESISTANCE","%img_fire_resistance%");


        icons.put("SKULL","뜦");

        icons.put("GOLD_INGOT","\uE25E");
        icons.put("IRON_INGOT","\uE24C");
        icons.put("EMERALD","\uE253");
        icons.put("COPPER_INGOT","\uE255");

        icons.put("THUMBS_UP","띕");
        icons.put("OFFSET-3","\uF804");


    }

    public static String requestIcon(String key, Player player) {
//        Bukkit.getConsoleSender().sendMessage("icon " + key + " was requested = " + icons.get(key));
        if (isLoaded) return icons.get(key);
        else {
            for (Map.Entry<String, String> icon : icons.entrySet()) {
                icon.setValue(PlaceholderAPI.setPlaceholders(player, icon.getValue()));
            }
            isLoaded = true;
            return icons.get(key);
        }
    }

    public static String getEffectIcon(String type, Player player) {

//        Bukkit.getConsoleSender().sendMessage("effect icon " + type + " was requested");
        switch (type) {
            case "INCREASE_DAMAGE": {
                return requestIcon("%img_strength%", player);
            }
            case "WEAKNESS": {
                return requestIcon("%img_weakness%", player);
            }
            case "SPEED": {
                return requestIcon("%img_speed%", player);
            }
            case "JUMP": {
                return requestIcon("%img_jump_boost%", player);
            }
            case "SLOW": {
                return requestIcon("%img_slowness%", player);
            }
            case "FAST_DIGGING": {
                return requestIcon("%img_haste%", player);
            }
            case "SLOW_DIGGING": {
                return requestIcon("%img_mining_fatigue%", player);
            }
            case "GLOWING": {
                return requestIcon("%img_glowing%", player);
            }
            case "DAMAGE_RESISTANCE": {
                return requestIcon("%img_resistance%", player);
            }
            case "ABSORPTION": {
                return requestIcon("%img_absorption%", player);
            }
            case "HUNGER": {
                return requestIcon("%img_hunger%", player);
            }
            case "WITHER": {
                return requestIcon("%img_wither%", player);
            }
            case "POISON": {
                return requestIcon("%img_poison%", player);
            }
            case "REGENERATION": {
                return requestIcon("%img_regeneration%", player);
            }
            case "LUCK": {
                return requestIcon("%img_luck%", player);
            }
            case "UNLUCK": {
                return requestIcon("%img_unluck%", player);
            }
            case "SLOW_FALLING": {
                return requestIcon("%img_slow_falling%", player);
            }
            case "LEVITATION": {
                return requestIcon("%img_levitation%", player);
            }
            case "BLINDNESS": {
                return requestIcon("%img_blindness%", player);
            }
            case "FIRE_RESISTANCE": {
                return requestIcon("%img_fire_resistance%", player);
            }
            default: {
                return type;
            }
        }
    }
}
