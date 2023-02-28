package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RarityManager {

    public static ArrayList<String> rarityNames;
    public static ArrayList<ChatColor> rarityColours;
    public static ArrayList<ItemStack> rarityGlassPanes;

    public RarityManager() { }

    static {
        rarityNames = new ArrayList<>();
        rarityColours = new ArrayList<>();
        rarityGlassPanes = new ArrayList<>();

        ItemStack item;

        rarityNames.add("Неизвестная");
        rarityColours.add(ChatColor.of("#424242"));
        item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(" ");
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Обычная");
        rarityColours.add(ChatColor.of("#bdbdbd"));
        item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Необычная");
        rarityColours.add(ChatColor.of("#66c94b"));
        item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(new ItemStack(Material.LIME_STAINED_GLASS_PANE));

        rarityNames.add("Редкая");
        rarityColours.add(ChatColor.of("#186ccc"));
        item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Эпическая");
        rarityColours.add(ChatColor.of("#ae00ff"));
        item = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Легендарная");
        rarityColours.add(ChatColor.of("#e6ad05"));
        item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Мифическая");
        rarityColours.add(ChatColor.of("#00eeff"));
        item = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);

        rarityNames.add("Лимитированная");
        rarityColours.add(ChatColor.of("#c22011"));
        item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        item.setItemMeta(itemMeta);
        rarityGlassPanes.add(item);
    }

    public static String getFullRarity(int i) {
        return ColoursManager.gray + "[ " + rarityColours.get(i) + rarityNames.get(i) + ColoursManager.gray + " ]";
    }

    public static TextComponent getFullRarityComponent(int i) {
        return Component.text().append(Component.text("[ ").color(ColoursManager.getComponent(ColoursManager.gray)))
                .append(Component.text(rarityNames.get(i)).color(ColoursManager.getComponent(rarityColours.get(i))))
                .append(Component.text(" ]").color(ColoursManager.getComponent(ColoursManager.gray)))
                .build();
    }

    @Deprecated
    public static String getFullLocalizedSlot(String slot) {
        return ColoursManager.gray + "[ " + ChatColor.GRAY + getLocalizedSlotType(slot) + ColoursManager.gray + " ]";
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


    public static ChatColor getRarityColour(int rarity) {
        return rarityColours.get(rarity);
    }
}
