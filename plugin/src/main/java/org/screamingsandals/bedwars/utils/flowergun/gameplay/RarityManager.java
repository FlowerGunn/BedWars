package org.screamingsandals.bedwars.utils.flowergun.gameplay;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RarityManager {

    public static ArrayList<String> rarityNames;
    public static ArrayList<String> rarityColours;
    public static ArrayList<ItemStack> rarityGlassPanes;

    public RarityManager() { }

    static {
        rarityNames = new ArrayList<>();
        rarityColours = new ArrayList<>();
        rarityGlassPanes = new ArrayList<>();

        rarityNames.add("Неизвестная");
        rarityColours.add(ChatColor.DARK_GRAY + "");
        rarityGlassPanes.add(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        rarityNames.add("Обычная");
        rarityColours.add(ChatColor.GRAY + "");
        rarityGlassPanes.add(new ItemStack(Material.WHITE_STAINED_GLASS_PANE));

        rarityNames.add("Необычная");
        rarityColours.add(ChatColor.GREEN + "");
        rarityGlassPanes.add(new ItemStack(Material.LIME_STAINED_GLASS_PANE));

        rarityNames.add("Редкая");
        rarityColours.add(ChatColor.BLUE + "");
        rarityGlassPanes.add(new ItemStack(Material.BLUE_STAINED_GLASS_PANE));

        rarityNames.add("Эпическая");
        rarityColours.add(ChatColor.DARK_PURPLE + "");
        rarityGlassPanes.add(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE));

        rarityNames.add("Легендарная");
        rarityColours.add(ChatColor.GOLD + "");
        rarityGlassPanes.add(new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));

        rarityNames.add("Мифическая");
        rarityColours.add(ChatColor.AQUA + "");
        rarityGlassPanes.add(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));

        rarityNames.add("Лимитированная");
        rarityColours.add(ChatColor.RED + "");
        rarityGlassPanes.add(new ItemStack(Material.RED_STAINED_GLASS_PANE));
    }

    public static String getFullRarity(int i) {
        return ChatColor.WHITE + "[ " + rarityColours.get(i) + rarityNames.get(i) + ChatColor.WHITE + " ]";
    }

    @Deprecated
    public static String getFullLocalizedSlot(String slot) {
        return ChatColor.WHITE + "[ " + ChatColor.GRAY + getLocalizedSlotType(slot) + ChatColor.WHITE + " ]";
    }

    @Deprecated
    public static String getLocalizedSlotType(String slot){
        switch (slot){
            case "dash_particle": return "Рывок: Частицы";
            case "dash_sound": return "Рывок: Звук";
            case "launcher_particle": return "Пусковое устройство: Частицы";
            case "launcher_sound": return "Пусковое устройство: Звук";
            case "tnt_particle": return "Динамит: Частицы";
            case "tnt_sound": return "Динамит: Звук";
            case "pearl_particle": return "Эндер жемчуг: Частицы";
            case "pearl_sound": return "Эндер жемчуг: Звук";
            case "switcheroo_particle": return "Путаница: Частицы";
            case "switcheroo_sound": return "Путаница: Звук";
            case "fireball_particle": return "Огненный шар: Частицы";
            case "fireball_sound": return "Огненный шар: Звук";
            case "lasso_particle": return "Лассо: Частицы";
            case "lasso_sound": return "Лассо: Звук";
            case "levitation_particle": return "Левитация: Частицы";
            case "levitation_sound": return "Левитация: Звук";
            case "win_message": return "Сообщение победы";
            case "win_animation": return "Эффект победы";
            default: return slot;
        }
    }


}
