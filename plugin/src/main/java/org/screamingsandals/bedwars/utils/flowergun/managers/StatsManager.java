package org.screamingsandals.bedwars.utils.flowergun.managers;

import org.bukkit.entity.Player;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.FlowerUtils;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.Resource;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.ResourceBundle;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class StatsManager {


    private HashMap<ResourceType, Integer> resourceLimits;
    private HashMap<ResourceType, Double> resourceMultipliers;

    private HashMap<UUID, ResourceBundle> resourcesRewards = new HashMap<>();

    private final double randomValue = 0.2;
    private final int repeats = 4;

    public StatsManager() {
        resourceLimits = new HashMap<>();
        resourceLimits.put(ResourceType.BONE , 70);
        resourceLimits.put(ResourceType.GLOW_INK_SAC , 70);
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

        resourceMultipliers = new HashMap<>();
        resourceMultipliers.put(ResourceType.BONE , 1.0);
        resourceMultipliers.put(ResourceType.GLOW_INK_SAC , 1.0);
        resourceMultipliers.put(ResourceType.AMETHYST_SHARD , 1.0);
        resourceMultipliers.put(ResourceType.EMERALD , 0.5);
        resourceMultipliers.put(ResourceType.COAL , 1.0);
        resourceMultipliers.put(ResourceType.COPPER , 1.0);
        resourceMultipliers.put(ResourceType.SLIMEBALL , 1.0);
        resourceMultipliers.put(ResourceType.SILK_COCOON , 1.0);
        resourceMultipliers.put(ResourceType.RAW_COPPER , 1.0);
        resourceMultipliers.put(ResourceType.RAW_IRON , 1.0);
        resourceMultipliers.put(ResourceType.RAW_GOLD , 0.5);
        resourceMultipliers.put(ResourceType.BLAZE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ICE_POWDER , 0.5);
        resourceMultipliers.put(ResourceType.ECHO_SHARD, 0.5);
        resourceMultipliers.put(ResourceType.LAPIS , 1.0);
        resourceMultipliers.put(ResourceType.LEATHER , 1.0);
        resourceMultipliers.put(ResourceType.QUARTZ , 1.0);
        resourceMultipliers.put(ResourceType.PAPER , 1.0);
        resourceMultipliers.put(ResourceType.EXP_CRYSTAL_LVL1 , 1.0);
        resourceMultipliers.put(ResourceType.SCRAP , 1.0);
        resourceMultipliers.put(ResourceType.NETHERITE_SCRAP , 0.1);
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

        Random random = new Random();
        if (player.isOnline()) {
            player.sendMessage(" ");
        }
        boolean givenAnything = false;
        for ( Resource resource : resourceBundle.resources ) {
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
