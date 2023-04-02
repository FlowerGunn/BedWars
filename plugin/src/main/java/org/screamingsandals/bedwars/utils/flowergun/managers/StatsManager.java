package org.screamingsandals.bedwars.utils.flowergun.managers;

import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.statistics.PlayerStatistic;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.comparators.ResourceBundleContentsComparator;
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
        resourceLimits.put(ResourceType.BONE , 70);
        resourceLimits.put(ResourceType.GLOW_INK_SAC , 50);
        resourceLimits.put(ResourceType.AMETHYST_SHARD , 70);
        resourceLimits.put(ResourceType.EMERALD , 70);
        resourceLimits.put(ResourceType.ENDER_EYE , 70);
        resourceLimits.put(ResourceType.COAL , 70);
        resourceLimits.put(ResourceType.SLIMEBALL , 70);
        resourceLimits.put(ResourceType.SILK_COCOON , 70);
        resourceLimits.put(ResourceType.RAW_COPPER , 70);
        resourceLimits.put(ResourceType.RAW_IRON , 70);
        resourceLimits.put(ResourceType.RAW_GOLD , 70);
        resourceLimits.put(ResourceType.BLAZE_POWDER , 70);
        resourceLimits.put(ResourceType.ICE_POWDER , 70);
        resourceLimits.put(ResourceType.ECHO_SHARD, 70);
        resourceLimits.put(ResourceType.LAPIS , 70);
        resourceLimits.put(ResourceType.LEATHER , 70);
        resourceLimits.put(ResourceType.QUARTZ , 70);
        resourceLimits.put(ResourceType.PAPER , 50);
        resourceLimits.put(ResourceType.EXP_CRYSTAL_LVL1 , 70);
        resourceLimits.put(ResourceType.SCRAP , 70);
        resourceLimits.put(ResourceType.GOLD , 20);

        resourceMultipliers = new HashMap<>();
        resourceMultipliers.put(ResourceType.BONE , 1.0);
        resourceMultipliers.put(ResourceType.GLOW_INK_SAC , 0.8);
        resourceMultipliers.put(ResourceType.AMETHYST_SHARD , 1.0);
        resourceMultipliers.put(ResourceType.EMERALD , 0.5);
        resourceMultipliers.put(ResourceType.COAL , 1.0);
        resourceMultipliers.put(ResourceType.COPPER , 1.0);
        resourceMultipliers.put(ResourceType.SLIMEBALL , 1.3);
        resourceMultipliers.put(ResourceType.SILK_COCOON , 1.0);
        resourceMultipliers.put(ResourceType.RAW_COPPER , 1.0);
        resourceMultipliers.put(ResourceType.RAW_IRON , 1.0);
        resourceMultipliers.put(ResourceType.RAW_GOLD , 0.5);
        resourceMultipliers.put(ResourceType.BLAZE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ICE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ECHO_SHARD, 0.5);
        resourceMultipliers.put(ResourceType.LAPIS , 1.0);
        resourceMultipliers.put(ResourceType.LEATHER , 1.0);
        resourceMultipliers.put(ResourceType.QUARTZ , 1.3);
        resourceMultipliers.put(ResourceType.PAPER , 1.0);
        resourceMultipliers.put(ResourceType.EXP_CRYSTAL_LVL1 , 1.0);
        resourceMultipliers.put(ResourceType.SCRAP , 1.0);
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
        Game game = gamePlayer.getGame();


        if ( gamePlayer.getTrulyOwnedAbilityById("suffocator") == null ) {
            NotificationManager.sendEventRewardMessage( "\"1 апреля\"", "Первая игра дня", gamePlayer.player);
            for ( int i = 0; i < 6; i++ )
                Main.getAbilitiesManager().giveAbilityToById(gamePlayer.player.getUniqueId(), "suffocator", 1);
            ResourceManager.giveResourcesTo(gamePlayer.player.getUniqueId(), ResourceType.BOOK_EVIL1, 1, true);
        }

        Random random = new Random();
        if (player.isOnline()) {
            player.sendMessage(" ");
        }
        boolean givenAnything = false;
        ArrayList<Resource> resources = resourceBundle.resources;



        if (Main.isPlayerStatisticsEnabled()) {
            PlayerStatistic statistic = Main.getPlayerStatisticsManager().getStatistic(player);
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
        for ( Resource resource : resources ) {
//            Bukkit.getConsoleSender().sendMessage(resource.getType() + " " + resource.getAmount());
            double finalModifier = 1;
            for (int i = 0; i < this.repeats; i++) {
                finalModifier *= random.nextDouble(1 - randomValue, 1 + randomValue );
            }
            int limit = resourceLimits.containsKey(resource.getType()) ? resourceLimits.get(resource.getType()) : FlowerUtils.defaultResourceLimit;
            double multiplier = resourceMultipliers.containsKey(resource.getType()) ? resourceMultipliers.get(resource.getType()) : FlowerUtils.defaultResourceMultiplier;

//            Bukkit.getConsoleSender().sendMessage("limit = " + limit);
//            Bukkit.getConsoleSender().sendMessage("final modifier = " + finalModifier);

            int finalValue = (int) (Math.min(resource.getAmount() * multiplier, limit) * finalModifier);

            if ( finalValue <= 0 ) continue;
            givenAnything = true;

            ResourceManager.giveResourcesTo(uuid, resource.getType(), finalValue);
        }
        if ( !givenAnything ) NotificationManager.noGameEndResources(player);
        if (player.isOnline()) {
            player.sendMessage(" ");
        }

        this.resourcesRewards.remove(uuid);
    }




}
