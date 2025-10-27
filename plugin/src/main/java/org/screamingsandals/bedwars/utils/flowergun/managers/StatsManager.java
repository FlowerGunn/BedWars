package org.screamingsandals.bedwars.utils.flowergun.managers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.ResourceBundleContentsComparator;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.PlayerConfigType;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.*;

public class StatsManager {


    private HashMap<ResourceType, Integer> resourceLimits;
    private HashMap<ResourceType, Double> resourceMultipliers;

    private HashMap<UUID, ResourceBundle> resourcesRewards = new HashMap<>();

    private final double randomValue = 0.2;
    private final int repeats = 4;

    public StatsManager() {
        resourceLimits = new HashMap<>();
        resourceLimits.put(ResourceType.BONE , 40);
        resourceLimits.put(ResourceType.GLOW_INK_SAC , 50);
        resourceLimits.put(ResourceType.AMETHYST_SHARD , 70);
        resourceLimits.put(ResourceType.EMERALD , 70);
        resourceLimits.put(ResourceType.ENDER_EYE , 70);
        resourceLimits.put(ResourceType.COAL , 70);
        resourceLimits.put(ResourceType.SLIMEBALL , 70);
        resourceLimits.put(ResourceType.SILK_COCOON , 70);
        resourceLimits.put(ResourceType.RAW_COPPER , 40);
        resourceLimits.put(ResourceType.RAW_IRON , 70);
        resourceLimits.put(ResourceType.RAW_GOLD , 70);
        resourceLimits.put(ResourceType.BLAZE_POWDER , 70);
        resourceLimits.put(ResourceType.ICE_POWDER , 70);
        resourceLimits.put(ResourceType.ECHO_SHARD, 70);
        resourceLimits.put(ResourceType.LAPIS , 70);
        resourceLimits.put(ResourceType.LEATHER , 40);
        resourceLimits.put(ResourceType.QUARTZ , 70);
        resourceLimits.put(ResourceType.PAPER , 50);
        resourceLimits.put(ResourceType.EXP_CRYSTAL_LVL1 , 70);
        resourceLimits.put(ResourceType.SCRAP , 70);
        resourceLimits.put(ResourceType.GOLD , 20);

        resourceMultipliers = new HashMap<>();
        resourceMultipliers.put(ResourceType.BONE , 0.5);
        resourceMultipliers.put(ResourceType.GLOW_INK_SAC , 0.8);
        resourceMultipliers.put(ResourceType.AMETHYST_SHARD , 1.0);
        resourceMultipliers.put(ResourceType.EMERALD , 0.5);
        resourceMultipliers.put(ResourceType.COAL , 1.0);
        resourceMultipliers.put(ResourceType.COPPER , 1.0);
        resourceMultipliers.put(ResourceType.SLIMEBALL , 1.3);
        resourceMultipliers.put(ResourceType.SILK_COCOON , 0.1);
        resourceMultipliers.put(ResourceType.RAW_COPPER , 0.2);
        resourceMultipliers.put(ResourceType.RAW_IRON , 0.2);
        resourceMultipliers.put(ResourceType.RAW_GOLD , 0.5);
        resourceMultipliers.put(ResourceType.BLAZE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ICE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ECHO_SHARD, 0.5);
        resourceMultipliers.put(ResourceType.LAPIS , 2.0);
        resourceMultipliers.put(ResourceType.LEATHER , 0.4);
        resourceMultipliers.put(ResourceType.QUARTZ , 1.3);
        resourceMultipliers.put(ResourceType.PAPER , 1.0);
        resourceMultipliers.put(ResourceType.EXP_CRYSTAL_LVL1 , 1.0);
        resourceMultipliers.put(ResourceType.SCRAP , 0.1);
        resourceMultipliers.put(ResourceType.NETHERITE_SCRAP , 0.1);
        resourceMultipliers.put(ResourceType.GOLD , 0.1);
    }

    public void addResourceToPlayer(GamePlayer gamePlayer, ResourceType resourceType, int amount) {
        addResourceToPlayer(gamePlayer.player, resourceType, amount);
    }

    public void addResourceToPlayer(Player player, ResourceType resourceType, int amount) {
        ResourceBundle resourceBundle = this.resourcesRewards.get(player.getUniqueId());
        if (resourceBundle == null) {
            resourceBundle = new ResourceBundle();
            this.resourcesRewards.put(player.getUniqueId(), resourceBundle);
        }
        resourceBundle.addResource(resourceType, amount);
    }

    public void reward(Player player) {

        UUID uuid = player.getUniqueId();


        ResourceBundle resourceBundle = this.resourcesRewards.get(uuid);
        if (resourceBundle == null) return;

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);
        PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(player);
        Game game = gamePlayer.getGame();

        if ( statistic.getGames() >= FlowerUtils.unlockAbilitySelectionGamesPlayedRequirement && !gamePlayer.settingExists(PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT)) {
            if (player.isOnline()) {
                player.sendMessage(" ");
                player.sendMessage(IconsManager.green_excl + ColoursManager.green + " Вы разблокировали меню выбора способностей!");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendTitle(IconsManager.green_excl + " ",  ColoursManager.green + "Способности разблокированы!", 10, 80, 10);
                    }
                }.runTaskLater(Main.getInstance(), 60);
            }
            gamePlayer.setSetting( PlayerConfigType.DEFAULT_ABILITIES_AUTOSELECT, "0");
            gamePlayer.setSetting( PlayerConfigType.SIMPLIFIED_ABILITY_SELECTION, "1");
        } else if ( statistic.getGames() >= FlowerUtils.unlockResourcesGamesPlayedRequirement && !gamePlayer.settingExists(PlayerConfigType.HIDE_RESOURCES) ) {

            if (player.isOnline()) {
                player.sendMessage(" ");
                player.sendMessage(IconsManager.green_excl + ColoursManager.green + " Вы разблокировали меню ресурсов!");new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendTitle(IconsManager.green_excl + " ", ColoursManager.green + "Ресурсы разблокированы!", 10, 80, 10);
                    }
                }.runTaskLater(Main.getInstance(), 60);
            }
            gamePlayer.setSetting( PlayerConfigType.HIDE_RESOURCES, "0");
        } else if ( statistic.getGames() >= FlowerUtils.unlockForgeGamesPlayedRequirement && !gamePlayer.settingExists(PlayerConfigType.HIDE_FORGE) ) {

            if (player.isOnline()) {
                player.sendMessage(" ");
                player.sendMessage(IconsManager.green_excl + ColoursManager.green + " Вы разблокировали Кузницу!");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendTitle(IconsManager.green_excl + " ", ColoursManager.green + "Крафты Кузницы разблокированы!", 10, 80, 10);
                    }
                }.runTaskLater(Main.getInstance(), 60);
            }
            gamePlayer.setSetting( PlayerConfigType.HIDE_FORGE, "0");
        }

//        if ( gamePlayer.getTrulyOwnedAbilityById("suffocator") == null ) {
//            NotificationManager.sendEventRewardMessage( "\"1 апреля\"", "Первая игра дня", gamePlayer.player);
//            for ( int i = 0; i < 6; i++ )
//                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), "suffocator", 1);
//            ResourceManager.giveResourcesTo(gamePlayer.player.getUniqueId(), ResourceType.BOOK_EVIL1, 1, true);
//        }

        Random random = new Random();
        if (player.isOnline()) {
            player.sendMessage(" ");
        }
        boolean givenAnything = false;
        ArrayList<Resource> resources = resourceBundle.resources;



        if (Main.isPlayerStatisticsEnabled()) {
            if ( statistic.getGames() < FlowerUtils.maxBonusBookGames ) {
                if ( (double) FlowerUtils.bonusBookChance1 * (FlowerUtils.maxBonusBookGames - statistic.getGames()) / FlowerUtils.maxBonusBookGames > random.nextInt(100))
                    Main.getStatsManager().addResourceToPlayer(gamePlayer, ResourceType.BOOK, 1);
                if ( (double) FlowerUtils.bonusBookChance2 * (FlowerUtils.maxBonusBookGames - statistic.getGames()) / FlowerUtils.maxBonusBookGames > random.nextInt(100))
                    Main.getStatsManager().addResourceToPlayer(gamePlayer, ResourceType.WRITABLE_BOOK, 1);
            }
        }



        if ( resources.size() >= 10) {
            if (game.countdown / 60.0 * game.getMatchedPlayers().size() * 0.1 > random.nextInt(100))
                Main.getStatsManager().addResourceToPlayer(gamePlayer, ResourceType.BOOK, 1);
            if (game.countdown / 60.0 * game.getMatchedPlayers().size() * 0.03 > random.nextInt(100))
                Main.getStatsManager().addResourceToPlayer(gamePlayer, ResourceType.WRITABLE_BOOK, 1);
//        if ( game.countdown / 60.0 * game.getMatchedPlayers().size() * 0.01 > random.nextInt(100) )
//            Main.getStatsManager().addResourceToPlayer(gamePlayer, ResourceType.BOOK_EVIL1, 1);
        }

        Collections.sort(resources, new ResourceBundleContentsComparator());
        boolean hide = gamePlayer.getSetting( PlayerConfigType.HIDE_RESOURCES );
        for ( Resource resource : resources ) {
//            Bukkit.getConsoleSender().sendMessage(resource.getType() + " " + resource.getAmount());
            double finalModifier = 1;
            for (int i = 0; i < this.repeats; i++) {
                finalModifier *= random.nextDouble(1 - randomValue, 1 + randomValue );
            }
            int limit = resourceLimits.containsKey(resource.getType()) ? resourceLimits.get(resource.getType()) : FlowerUtils.defaultResourceLimit;
            double multiplier = resourceMultipliers.containsKey(resource.getType()) ? resourceMultipliers.get(resource.getType()) : FlowerUtils.defaultResourceMultiplier;


            int finalValue = (int) (Math.min(resource.getAmount() * multiplier, limit) * finalModifier);

            if ( finalValue <= 0 ) continue;
            givenAnything = true;

            ResourceManager.giveResourcesTo(uuid, resource.getType(), finalValue, !hide);
        }
        if ( hide ) {
            int gamesPlayed = statistic.getGames();
            player.sendMessage(IconsManager.yellow_excl + ColoursManager.yellow + " Сыграйте ещё " + ColoursManager.white + Math.max( FlowerUtils.unlockResourcesGamesPlayedRequirement - gamesPlayed, 1 ) + ColoursManager.yellow + " игр, чтобы разблокировать инвентарь ресурсов." );
        }
        if ( !givenAnything ) NotificationManager.noGameEndResources(player);
        if (player.isOnline()) {
            player.sendMessage(" ");
        }



        this.resourcesRewards.remove(uuid);
    }




}
