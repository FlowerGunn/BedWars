package org.screamingsandals.bedwars.utils.flowergun.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
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

    public static String requestIcon(IconType iconType) {
        if (!isLoaded) {
            return "";
        }
        return newicons.get(iconType);
    }

    public static void loadIcons(Player player) {
        if (!isLoaded) {
            Main.getInstance().getLogger().info("Trying to load icons with " + player.getName() + "!");
            if ( Main.getInstance().isIALoaded() ) {
                isLoaded = true;
                for (IconType it : IconType.values()) {
                    newicons.put(it, PlaceholderAPI.setPlaceholders(player, it.getIconId()) );
                }
                Main.getInstance().getLogger().info("Loaded icons with " + player.getName() + "!");
                return;
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    loadIcons(player);
                }
            }.runTaskLater(Main.getInstance(),10L);
        }
    }
}
