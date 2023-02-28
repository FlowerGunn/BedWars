package org.screamingsandals.bedwars.utils.flowergun.managers;

import dev.lone.itemsadder.api.FontImages.PlayerHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.screamingsandals.bedwars.Main;
import org.screamingsandals.bedwars.game.Game;
import org.screamingsandals.bedwars.game.GamePlayer;
import org.screamingsandals.bedwars.utils.flowergun.customobjects.*;
import org.screamingsandals.bedwars.utils.flowergun.other.enums.ResourceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ForgeManager {

    public void initializeDatabase() {

        Main.getInstance().getLogger().info("Loading abilities from database ...");

        try {
            if (!Main.getDatabaseManager().initialized) Main.getDatabaseManager().initialize();

            try (Connection connection = Main.getDatabaseManager().getConnection()) {
                connection.setAutoCommit(false);
                PreparedStatement preparedStatement = connection
                        .prepareStatement(Main.getDatabaseManager().getCreateForgeTableSql());
                preparedStatement.executeUpdate();
                connection.commit();
                preparedStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadActiveForgeRecipes(GamePlayer gamePlayer) {

        ArrayList<ActiveForgeRecipe> activeForgeRecipes = Main.getDatabaseManager().readAllForgesByUUID(gamePlayer.player.getUniqueId());
        for ( int i = 0; i < activeForgeRecipes.size(); i++ ) {
            if (activeForgeRecipes.get(i).isRedeemed()) {
                activeForgeRecipes.remove(i);
                i--;
            }
        }

        gamePlayer.activeForgeRecipes = activeForgeRecipes;

    }

    public static void loadAvailableRecipes(GamePlayer gamePlayer) {

        ArrayList<ResourceType> availableResources = new ArrayList<>();

        for ( ResourceType resourceType : ResourceType.values()) {
            if ( resourceType.getRarity() <= 1) availableResources.add(resourceType);
        }

        for ( OwnedResource ownedResource : gamePlayer.ownedResourceBundle.resources ) {
             availableResources.add(ownedResource.getType());
        }

        ArrayList<ForgeRecipe> forgeRecipes = new ArrayList<>();

        for ( ForgeRecipe recipe : RecipeBook.globalRecipes ) {
            for ( Resource resource : recipe.getInput().resources ) {
                if ( availableResources.contains(resource.getType())) {
                    forgeRecipes.add(recipe);
                    break;
                }
            }
        }

        gamePlayer.availableRecipes = forgeRecipes;

    }

    public void loadForgeMenu(Player player) {

        GamePlayer gamePlayer = Main.getPlayerGameProfile(player);



    }



}
