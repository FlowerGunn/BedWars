package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;

public class ColoursManager {

    public static final ChatColor red = ChatColor.of("#d40f1c");
    public static final ChatColor green = ChatColor.of("#28bf11");
    public static final ChatColor blue = ChatColor.of("#2250e6");
    public static final ChatColor yellow = ChatColor.of("#f2e600");
    public static final ChatColor orange = ChatColor.of("#ff8800");
    public static final ChatColor purple = ChatColor.of("#b300ff");

    public static final ChatColor gray = ChatColor.of("#a1a1a1");
    public static final ChatColor darkGray = ChatColor.of("#666666");
    public static final ChatColor darkRed = ChatColor.of("#910f00");
    public static final ChatColor white = ChatColor.of("#ffffff");
    public static final ChatColor pink = ChatColor.of("#ffabee");
    public static final ChatColor portal = purple;
    public static final ChatColor elytra = ChatColor.of("#3ba1b8");
    public static final ChatColor annihilation = ChatColor.of("#4c00b0");
    public static final ChatColor light_blue = ChatColor.of("#6589fc");

    public static TextColor getComponent(ChatColor chatColor ) {
        return TextColor.color( chatColor.getColor().getRGB() );
    }

}
