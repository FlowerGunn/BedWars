/*
 * Copyright (C) 2022 ScreamingSandals
 *
 * This file is part of Screaming BedWars.
 *
 * Screaming BedWars is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Screaming BedWars is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Screaming BedWars. If not, see <https://www.gnu.org/licenses/>.
 */

package org.screamingsandals.bedwars.game;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

public enum TeamColor {
    BLACK(ChatColor.BLACK, 0xF, "BLACK", Color.BLACK),
    BLUE(ChatColor.DARK_BLUE, 0xB, "BLUE", Color.fromRGB(0, 0, 170)),
    GREEN(ChatColor.DARK_GREEN, 0xD, "GREEN", Color.fromRGB(0, 170, 0)),
    RED(ChatColor.of("#d62418"), 0xE, "RED", Color.fromRGB(214, 36, 24),IconType.RED_LEFT_DARK,IconType.RED_LEFT,IconType.RED_RIGHT_DARK,IconType.RED_RIGHT,IconType.BED_RED),
    MAGENTA(ChatColor.DARK_PURPLE, 0x2, "MAGENTA", Color.fromRGB(170, 0, 170)),
    ORANGE(ChatColor.GOLD, 0x1, "ORANGE", Color.fromRGB(255, 170, 0)),
    LIGHT_GRAY(ChatColor.GRAY, 0x8, "LIGHT_GRAY", Color.fromRGB(170, 170, 170)),
    GRAY(ChatColor.DARK_GRAY, 0x7, "GRAY", Color.fromRGB(85, 85, 85)),
    LIGHT_BLUE(ChatColor.of("#106FDE"), 0x3, "LIGHT_BLUE", Color.fromRGB(16, 111, 222),IconType.BLUE_LEFT_DARK,IconType.BLUE_LEFT,IconType.BLUE_RIGHT_DARK,IconType.BLUE_RIGHT,IconType.BED_BLUE),
    LIME(ChatColor.of("#01B70F"), 0x5, "LIME", Color.fromRGB(1, 183, 15 ),IconType.GREEN_LEFT_DARK,IconType.GREEN_LEFT,IconType.GREEN_RIGHT_DARK,IconType.GREEN_RIGHT,IconType.BED_LIME),
    CYAN(ChatColor.AQUA, 0x9, "CYAN", Color.fromRGB(85, 255, 255)),
    PINK(ChatColor.LIGHT_PURPLE, 0x6, "PINK", Color.fromRGB(255, 85, 255)),
    YELLOW(ChatColor.of("#F7E109"), 0x4, "YELLOW", Color.fromRGB(247, 225, 9),IconType.YELLOW_LEFT_DARK,IconType.YELLOW_LEFT,IconType.YELLOW_RIGHT_DARK,IconType.YELLOW_RIGHT,IconType.BED_YELLOW),
    WHITE(ChatColor.WHITE, 0x0, "WHITE", Color.WHITE),
    BROWN(ChatColor.DARK_RED, 0xC, "BROWN", Color.fromRGB(139,69,19)),
    DEFAULT(ChatColor.DARK_GRAY, 0x8, "DARK_GRAY", Color.fromRGB(170, 170, 170));

    public final ChatColor chatColor;
    public final String material1_13;
    public final int woolData;
    public final Color leatherColor;


//    public static final String firstHalfAbsorption = ChatColor.GOLD + "畫";
//    public static final String secondHalfAbsorption = ChatColor.GOLD + "\uF801畵";

    public static String getFirstHalfAbsorption() {
        return IconType.GRAY_LEFT_SHADED.getIcon();
    }
    public static String getSecondHalfAbsorption() {
        return IconType.GRAY_RIGHT_SHADED.getIcon();
    }

    public final IconType bed;
    public final IconType firstHalfFull;
    public final IconType firstHalfEmpty;
    public final IconType secondHalfFull ;
    public final IconType secondHalfEmpty;

    public String getFirstHalfFull() {
        return firstHalfFull.getIcon();
    }
    public String getFirstHalfEmpty() {
        return firstHalfEmpty.getIcon();
    }
    public String getSecondHalfFull() {
        return secondHalfFull.getIcon();
    }
    public String getSecondHalfEmpty() {
        return secondHalfEmpty.getIcon();
    }
    public String getBed() {
        return bed.getIcon();
    }




    public String getSupportCell() {
        return firstHalfFull.getIcon() + IconType.OFFSET_MINUS_3.getIcon() + secondHalfFull.getIcon();
    }
    public String getSupportCellEmpty() {
        return firstHalfEmpty.getIcon() + IconType.OFFSET_MINUS_3.getIcon() + secondHalfEmpty.getIcon();
    }

    TeamColor(ChatColor chatColor, int woolData, String material1_13, Color leatherColor) {
        this.chatColor = chatColor;
        this.woolData = woolData;
        this.material1_13 = material1_13;
        this.leatherColor = leatherColor;

        this.firstHalfEmpty = IconType.RED_LEFT_DARK;
        this.firstHalfFull = IconType.RED_LEFT;
        this.secondHalfEmpty = IconType.RED_RIGHT_DARK;
        this.secondHalfFull = IconType.RED_RIGHT;
        this.bed = IconType.BED_WHITE;
    }

    TeamColor(ChatColor chatColor, int woolData, String material1_13, Color leatherColor, IconType firstHalfEmpty, IconType firstHalfFull, IconType secondHalfEmpty, IconType secondHalfFull, IconType bed) {
        this.chatColor = chatColor;
        this.woolData = woolData;
        this.material1_13 = material1_13;
        this.leatherColor = leatherColor;

        this.firstHalfEmpty = firstHalfEmpty;
        this.firstHalfFull = firstHalfFull;
        this.secondHalfEmpty = secondHalfEmpty;
        this.secondHalfFull = secondHalfFull;
        this.bed = bed;
    }



    public ItemStack getWool() {
        if (Main.isLegacy()) {
            return new ItemStack(Material.valueOf("WOOL"), 1, (short) woolData);
        } else {
            return new ItemStack(Material.valueOf(material1_13 + "_WOOL"));
        }

    }

    public ItemStack getWool(ItemStack stack) {
        if (Main.isLegacy()) {
            stack.setType(Material.valueOf("WOOL"));
            stack.setDurability((short) woolData);
        } else {
            stack.setType(Material.valueOf(material1_13 + "_WOOL"));
        }
        return stack;

    }

    public org.screamingsandals.bedwars.api.TeamColor toApiColor() {
        return org.screamingsandals.bedwars.api.TeamColor.valueOf(this.name());
    }

    public static TeamColor fromApiColor(org.screamingsandals.bedwars.api.TeamColor color) {
        return TeamColor.valueOf(color.name());
    }
}
