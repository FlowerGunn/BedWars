package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceChestReward;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

import java.util.ArrayList;
import java.util.Collection;

public class NotificationManager {

    public static void getResourcesMessage(Player player, Resource resource) {

        player.sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Вы получили " + ChatColor.RESET + resource.getResourceWithAmountAndIcon(player));
    }

    public static void unlockResourceMessage(Player player, Resource resource) {
        player.sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Новый ресурс разблокирован: " + ChatColor.RESET + resource.getResourceWithAmountAndIcon(player));
    }

    public static void getAbilityMessage(Player player, OwnedAbility ownedAbility) {

        if ( ownedAbility.getOwnedLevel() == 1 && ownedAbility.duplicatesOwned == 0 )
        player.sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Вы получили новую способность: " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(player));
        else
        player.sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Вы получили дубликат способности: " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(player));
    }

    public static void noGameEndResources(Player player) {
        player.sendMessage(IconsManager.red_excl + ChatColor.GRAY + " Вы не получили никаких ресурсов за игру.");
    }

    public static ArrayList<String> getInsufficientMaterialsMessage() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add( ColoursManager.gray + "[ " + ColoursManager.red + "画\uF804爲" + ColoursManager.gray + " ]");
        return lore;
    }

    public static ArrayList<String> getSufficientMaterialsMessage() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add( ColoursManager.gray + "[ " + ColoursManager.green + "夜\uF804亱" + ColoursManager.gray + " ]");
        return lore;
    }

    public static void getUpgradedAbilityMessage(OwnedAbility ownedAbility) {
        ownedAbility.getOwner().sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Вы улучшили способность " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(ownedAbility.getOwner()) + ColoursManager.gray + " до уровня " + ColoursManager.white + ownedAbility.ownedLevel + ColoursManager.gray + " !");
    }

    public static void getDisassembledAbilityMessage(OwnedAbility ownedAbility) {
        ownedAbility.getOwner().sendMessage(IconsManager.blue_excl + ChatColor.GRAY + " Вы разобрали способность " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(ownedAbility.getOwner()) + ColoursManager.gray + " !");
    }

    public static Collection<String> parseTime(long overallSeconds) {
        ArrayList<String> lore = new ArrayList<>();

        long leftTimeInDays = overallSeconds / (24 * 60 * 60);
        long leftTimeInHours = overallSeconds / (60 * 60) % 24;
        long leftTimeInMinutes = overallSeconds / (60) % 60;
        long leftTimeInSeconds = overallSeconds % 60;
        String message = "";
        boolean foundNonZero = false;

        if ( leftTimeInDays != 0 ) {
            message += ColoursManager.gray + "" + leftTimeInDays + " дней" + ColoursManager.darkGray + " | ";
            foundNonZero = true;
        }
        if ( leftTimeInHours != 0 || foundNonZero ) {
            message += ColoursManager.gray + "" + leftTimeInHours + " часов" + ColoursManager.darkGray + " | ";
            foundNonZero = true;
        }
        if ( leftTimeInMinutes != 0 || foundNonZero ) {
//            if (leftTimeInMinutes < 10 && foundNonZero)
//                message += ColoursManager.gray + "0" + leftTimeInMinutes + " минут" + ColoursManager.darkGray + " | ";
//            else
                message += ColoursManager.gray + "" + leftTimeInMinutes + " минут" + ColoursManager.darkGray + " | ";
            foundNonZero = true;
        }
        if ( leftTimeInSeconds != 0 || foundNonZero ) {
//            if (leftTimeInSeconds < 10 && foundNonZero)
//                message += ColoursManager.gray + "0" + leftTimeInSeconds + " секунд";
//            else
                message += ColoursManager.gray + "" + leftTimeInSeconds + " секунд";
        }

        lore.add(message);

        return lore;
    }

    public static ArrayList<String> getRewardTypeMessage(ResourceChestReward resourceChestReward) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        String rewardType = resourceChestReward.getResource() != null ? ColoursManager.darkGray + "Ресурс" : ColoursManager.darkGray + "Способность";
        lore.add( ColoursManager.gray + "[ " + rewardType + ColoursManager.gray + " ]");
        return lore;
    }

    public static Component generatePlayerNameWithAbilitiesComponent(GamePlayer gPlayer, ChatColor chatColor) {

        ComponentBuilder chatMessage = Component.text();
        Player player = gPlayer.player;

        for ( int i = 0; i < gPlayer.loadedAbilities.size(); i++ ) {
            LoadedAbility loadedAbility = gPlayer.loadedAbilities.get(i);
            if (!loadedAbility.isEmpty()) {
                chatMessage.append(Component.text(loadedAbility.getOwnedAbility().getAbility().getIconString(player)).hoverEvent(loadedAbility.getOwnedAbility().parseDescriptionComponent(i + 1, player )));
            } else {
                chatMessage.append(Component.text(IconType.SLOT_LOCKED.getIcon(player)));
            }
        }

        chatMessage.append(Component.text(" "));

        chatMessage.append(Component.text(player.getName()).color(ColoursManager.getComponent(chatColor)));

        return chatMessage.build();

    }
}
