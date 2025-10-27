package org.screamingsandals.bedwars.utils.flowergun.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.LoadedAbility;
import org.screamingsandals.bedwars.utils.flowergun.abilities_base.OwnedAbility;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceChestReward;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.AbilityCategory;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.IconType;

import java.util.ArrayList;
import java.util.Arrays;
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
        lore.add( ColoursManager.gray + "[ " + ColoursManager.red + IconType.RED_LEFT.getIcon() + IconType.RED_RIGHT + ColoursManager.gray + " ]");
        return lore;
    }

    public static ArrayList<String> getSufficientMaterialsMessage() {//夜
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add( ColoursManager.gray + "[ " + ColoursManager.green + IconType.GREEN_LEFT.getIcon() + IconType.GREEN_RIGHT.getIcon() + ColoursManager.gray + " ]");
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

    public static ArrayList<String> divideAndParseLines( String text, String delimiter ) {

        String str[] = text.split(delimiter);
        ArrayList<String> description = new ArrayList<>(Arrays.asList(str));
        description.replaceAll(s -> ColoursManager.gray + s);

        return description;
    }

    public static void sendEventRewardMessage(String eventName, String reason, Player player) {
        player.sendMessage(IconsManager.green_excl + ColoursManager.gray + " Награда за событие " + ColoursManager.green + eventName + ColoursManager.gray + " (" + ColoursManager.light_blue + reason + ColoursManager.gray + ") :");
    }

    public static void abilityAutomaticallyLoaded(Player player, OwnedAbility ownedAbility, int level, int slot, AbilityCategory abilityCategory) {
        if ( abilityCategory == null )
            player.sendMessage(IconsManager.blue_excl + ColoursManager.gray + " Автоустановка : " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(ownedAbility.getOwner()) + ColoursManager.gray + " [ " + ColoursManager.darkGray + "?" + ColoursManager.gray + " ]");
        else
            player.sendMessage(IconsManager.blue_excl + ColoursManager.gray + " Автоустановка : " + ChatColor.RESET + ownedAbility.getAbility().getNameWithIcon(ownedAbility.getOwner()) + " " + abilityCategory.getFullName());
    }

    public static void abilitySelectionLockedMessage(Player player, int played, int required) {
        player.sendMessage("");
        player.sendMessage(IconsManager.yellow_excl + ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( required - played, 0 ) + ColoursManager.yellow + " игр, чтобы разблокировать выбор способностей." );
        player.sendMessage("");
    }

    public static void sendListOfAbilitiesInChat(CommandSender messageReveiver, GamePlayer gamePlayer, int tickDelay, int ticksInterval) {
        messageReveiver.sendMessage("   ");
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if ( i > 2 ) {
                    this.cancel();
                    return;
                }

                LoadedAbility loadedAbility = gamePlayer.loadedAbilities.get(i);
                messageReveiver.sendMessage("   " + AbilitiesManager.formatLoadedAbilityNameInSlot(gamePlayer, i));
                if (messageReveiver instanceof Player player)
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5F, 1.0F);
                if (!loadedAbility.isEmpty()) {
                    ArrayList<String> lore = loadedAbility.getOwnedAbility().getAbility().parseDescriptionOnly(loadedAbility.getActiveLevel(), i + 1);
                    for ( String line : lore ) {
                        messageReveiver.sendMessage("     " + line);
                    }
                }
                messageReveiver.sendMessage("   ");

                i++;
            }
        }.runTaskTimer(Main.getInstance(), tickDelay, ticksInterval);
        for (int i = 0; i < gamePlayer.loadedAbilities.size(); i++) {

        }
    }
}
